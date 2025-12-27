package alex.dev.freshgoapp.app.domain.repository

import alex.dev.freshgoapp.app.util.RequestState
import com.google.firebase.auth.FirebaseUser

interface CustomerRepository {
    fun getCurrentUserId(): String?
    suspend fun createCustomer(
        user: FirebaseUser,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    suspend fun sighOut(): RequestState<Unit>
}