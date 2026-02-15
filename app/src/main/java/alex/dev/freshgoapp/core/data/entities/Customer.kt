package alex.dev.freshgoapp.core.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class Customer(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val country: String? = null,
    val city: String? = null,
    val address: String? = null,
    val postalCode: Int? = null,
    val phoneNumber: PhoneNumber? = null,
    val isAdmin: Boolean = false,
    val avatarUrl: String? = null
)

@Serializable
data class PhoneNumber(
    val dialCode: Int,
    val number: String
)