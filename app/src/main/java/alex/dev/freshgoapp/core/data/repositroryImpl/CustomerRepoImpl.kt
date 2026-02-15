package alex.dev.freshgoapp.core.data.repositroryImpl

import alex.dev.freshgoapp.core.data.entities.Customer
import alex.dev.freshgoapp.core.data.entities.PhoneNumber
import alex.dev.freshgoapp.core.data.repository.CustomerRepository
import alex.dev.freshgoapp.util.RequestState
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.tasks.await

class CustomerRepoImpl : CustomerRepository {
    override fun getCurrentUserId(): String? =
        FirebaseAuth.getInstance().currentUser?.uid

    override suspend fun createCustomer(
        user: FirebaseUser,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val customerCollection = Firebase.firestore.collection("customer")
            val docRef = customerCollection.document(user.uid)
            val snapshot = docRef.get().await()
            if (!snapshot.exists()) {
                val customer = Customer(
                    id = user.uid,
                    firstName = user.displayName?.split(" ")?.firstOrNull() ?: "Unknown",
                    lastName = user.displayName?.split(" ")?.lastOrNull() ?: "Unknown",
                    email = user.email ?: "Unknown",
                    avatarUrl = user.photoUrl.toString(),
                )
                docRef.set(customer).await()
            }
            onSuccess()
        } catch (e: Exception) {
            RequestState.Error(message = "Ошибка создания пользователя: ${e.message}")
        }
    }

    override fun readCustomerFlow(): Flow<RequestState<Customer>> = channelFlow {
        try {
            val userId = getCurrentUserId()
            if (userId != null) {
                val database = Firebase.firestore
                database.collection("customer")
                    .document(userId)
                    .snapshots()
                    .collectLatest { documentSnapshot ->
                        if (documentSnapshot.exists()) {
                            val customer = Customer(
                                id = documentSnapshot.id,
                                firstName = documentSnapshot.getString("firstName") ?: "",
                                lastName = documentSnapshot.getString("lastName") ?: "",
                                email = documentSnapshot.getString("email") ?: "",
                                city = documentSnapshot.getString("city"),
                                address = documentSnapshot.getString("address"),
                                country = documentSnapshot.getString("country"),
                                avatarUrl = documentSnapshot.getString("avatarUrl")
                                    ?: documentSnapshot.getString("photoUrl")
                                    ?: "",
                                postalCode = when (val value = documentSnapshot.get("postalCode")) {
                                    is String -> value.toIntOrNull()
                                    is Number -> value.toInt()
                                    is Long -> value.toInt()
                                    else -> null
                                },
                                phoneNumber = (documentSnapshot.get("phoneNumber") as? Map<*, *>)?.let { map ->
                                    val dialCode = (map["CountryCode"] as? Number)?.toInt()
                                        ?: (map["dialCode"] as? Number)?.toInt()
                                        ?: (map["code"] as? Number)?.toInt()
                                    val number = map["number"] as? String
                                    if (dialCode != null && !number.isNullOrBlank()) {
                                        PhoneNumber(dialCode, number)
                                    } else null
                                }
                            )

                            send(RequestState.Success(customer))
                        }
                    }
            } else {
                send(RequestState.Error("Пользователь не доступен"))
            }
        } catch (e: Exception) {
            send(RequestState.Error(message = "Ошибка при обработке информации о клиенте: ${e.message}"))
        }
    }

    override suspend fun updatedCustomer(
        customer: Customer,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val userId = getCurrentUserId()
            if (userId != null) {
                val firestore = Firebase.firestore
                val customerCollection = firestore.collection("customer")
                val existingCustomer = customerCollection
                    .document(customer.id)
                    .get().await()
                if (existingCustomer.exists()) {
                    val phoneNumberMap = customer.phoneNumber?.let {
                        mapOf(
                            "CountryCode" to it.dialCode,
                            "number" to it.number
                        )
                    }
                    customerCollection
                        .document(customer.id)
                        .update(
                            mapOf(
                                "firstName" to customer.firstName,
                                "lastName" to customer.lastName,
                                "city" to customer.city,
                                "postalCode" to customer.postalCode,
                                "address" to customer.address,
                                "phoneNumber" to phoneNumberMap,
                            )
                        ).await()
                    onSuccess()
                } else {
                    RequestState.Error("Профиль пользователя не найден")
                }
            } else {
                RequestState.Error("Пользователь не доступен")
            }
        } catch (e: Exception) {
            onError("Ошибка обновления профиля пользователя: ${e.message}")
        }
    }

    override suspend fun sighOut(): RequestState<Unit> {
        return try {
            Firebase.auth.signOut()
            RequestState.Success(Unit)
        } catch (e: Exception) {
            RequestState.Error("Error while signing out: ${e.message}")
        }
    }
}