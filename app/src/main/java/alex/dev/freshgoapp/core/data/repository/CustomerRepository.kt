package alex.dev.freshgoapp.core.data.repository

import alex.dev.freshgoapp.core.data.entities.Customer
import alex.dev.freshgoapp.util.RequestState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    fun getCurrentUserId(): String?
    suspend fun createCustomer(
        user: FirebaseUser,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    suspend fun readCustomerFlow(): Flow<RequestState<Customer>>
    suspend fun updatedCustomer(
        customer: Customer,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    suspend fun sighOut(): RequestState<Unit>
}