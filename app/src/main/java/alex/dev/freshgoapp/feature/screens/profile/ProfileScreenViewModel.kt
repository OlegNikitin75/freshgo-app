package alex.dev.freshgoapp.feature.screens.profile

import alex.dev.freshgoapp.core.data.entities.Customer
import alex.dev.freshgoapp.core.data.entities.PhoneNumber
import alex.dev.freshgoapp.core.data.repository.CustomerRepository
import alex.dev.freshgoapp.util.RequestState
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileScreenViewModel(
    private val customerRepository: CustomerRepository
) : ViewModel() {
    var screenReady: RequestState<Unit> by mutableStateOf(RequestState.Loading)
    var screenState: ProfileScreenState by mutableStateOf(ProfileScreenState())
        private set
    var isSubmitted: Boolean by mutableStateOf(false)
        private set
    val isFormValid: Boolean
        get() = with(screenState) {
            (firstName.length in 3..20) &&
                    (lastName.length in 3..20) &&
                    (city?.length in 3..20) &&
                    (postalCode != null || postalCode.toString().length in 3..8) &&
                    (address?.length in 3..50) &&
                    (phoneNumber?.number?.length in 3..20)
        }

    init {
        viewModelScope.launch { observeCustomer() }
    }

    private suspend fun observeCustomer() {
        customerRepository.readCustomerFlow().collectLatest { data ->
            when {
                data.isSuccess() -> {
                    val fetched = data.getSuccessData()
                    val dial = fetched.phoneNumber?.dialCode
                    screenState = ProfileScreenState(
                        id = fetched.id,
                        firstName = fetched.firstName,
                        lastName = fetched.lastName,
                        email = fetched.email,
                        city = fetched.city,
                        address = fetched.address,
                        phoneNumber = fetched.phoneNumber,
                        avatarUrl = fetched.avatarUrl,
                    )
                    screenReady = RequestState.Success(Unit)
                }

                data.isError() -> {
                    screenReady = RequestState.Error(data.getErrorMessage())
                }

                else -> Unit
            }
        }
    }
    fun onSubmitAttempt() {
        isSubmitted = true
    }

    fun updateFirstName(value: String) {
        screenState = screenState.copy(firstName = value)
    }

    fun updateLastName(value: String) {
        screenState = screenState.copy(lastName = value)
    }

    fun updateCity(value: String) {
        screenState = screenState.copy(city = value)
    }

    fun updateAddress(value: String) {
        screenState = screenState.copy(address = value)
    }

    fun updatePostalCode(value: Int?) {
        screenState = screenState.copy(postalCode = value)
    }

    fun updatePhoneNumber(value: String) {
        screenState = screenState.copy(
            phoneNumber = PhoneNumber(
                dialCode = screenState.phoneNumber?.dialCode ?: 0,
                number = value
            )
        )
    }

    fun updateCustomer(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            customerRepository.updatedCustomer(
                customer = Customer(
                    id = screenState.id,
                    firstName = screenState.firstName,
                    lastName = screenState.lastName,
                    email = screenState.email,
                    city = screenState.city,
                    address = screenState.address,
                    phoneNumber = screenState.phoneNumber,
                    avatarUrl = screenState.avatarUrl,
                ),
                onSuccess = onSuccess,
                onError = onError,
            )
        }
    }
}

data class ProfileScreenState
    (
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val city: String? = null,
    val address: String? = null,
    val postalCode: Int? = null,
    val phoneNumber: PhoneNumber? = null,
    val avatarUrl: String? = null
)