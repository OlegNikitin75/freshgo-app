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
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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

    override fun readCustomerFlow(): Flow<RequestState<Customer>> = callbackFlow {
        val userId = getCurrentUserId()
        if (userId == null) {
            trySend(RequestState.Error("Пользователь не авторизован"))
            close()
            return@callbackFlow
        }

        val docRef = Firebase.firestore.collection("customer").document(userId)

        val listener = docRef.addSnapshotListener { snapshot, error ->
            when {
                error != null -> {
                    trySend(RequestState.Error("Ошибка слушателя Firestore: ${error.message}"))
                }
                snapshot != null -> {
                    if (snapshot.exists()) {
                        val customer = Customer(
                            id = snapshot.id,
                            firstName = snapshot.getString("firstName") ?: "",
                            lastName = snapshot.getString("lastName") ?: "",
                            email = snapshot.getString("email") ?: "",
                            city = snapshot.getString("city"),
                            address = snapshot.getString("address"),
                            postalCode = snapshot.get("postalCode")?.let { v ->
                                when (v) {
                                    is String -> v.toIntOrNull()
                                    is Number -> v.toInt()
                                    else -> null
                                }
                            },
                            phoneNumber = (snapshot.get("phoneNumber") as? Map<*, *>)?.let { map ->
                                val dial = (map["dialCode"] as? Number)?.toInt()
                                    ?: (map["CountryCode"] as? Number)?.toInt()
                                val num = map["number"] as? String
                                if (dial != null && !num.isNullOrBlank()) PhoneNumber(dial, num) else null
                            },
                            avatarUrl = snapshot.getString("avatarUrl") ?: "",
                        )
                        trySend(RequestState.Success(customer))
                    } else {
                        trySend(RequestState.Error("Профиль не найден"))
                    }
                }
                else -> {
                    trySend(RequestState.Error("Снапшот null"))
                }
            }
        }

        awaitClose { listener.remove() }
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