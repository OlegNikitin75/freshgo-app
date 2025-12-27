package alex.dev.freshgoapp.app.data.repositrory

import alex.dev.freshgoapp.app.domain.model.Customer
import alex.dev.freshgoapp.app.domain.repository.CustomerRepository
import alex.dev.freshgoapp.app.util.RequestState
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class CustomerRepoImpl : CustomerRepository {
    override fun getCurrentUserId(): String? =
        FirebaseAuth.getInstance().currentUser?.uid

    override suspend fun createCustomer(
        user: FirebaseUser,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val customerCollection = Firebase.firestore.collection("customer")
        val docRef = customerCollection.document(user.uid)
        val snapshot = docRef.get().await()
        if (!snapshot.exists()) {
            val customer = Customer(
                id = user.uid,
                firstName = user.displayName?.split(" ")?.firstOrNull() ?: "Unknown",
                lastName = user.displayName?.split(" ")?.lastOrNull() ?: "Unknown",
                email = user.email ?: "Unknown",
            )
            docRef.set(customer).await()
        }
        Unit
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