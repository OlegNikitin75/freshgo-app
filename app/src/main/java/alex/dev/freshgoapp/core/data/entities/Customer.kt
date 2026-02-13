package alex.dev.freshgoapp.core.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class Customer(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val postCoder: Int? = null,
    val phoneNumber: PhoneNumber? = null,
    val isAdmin: Boolean = false
)

@Serializable
data class PhoneNumber(
    val dataCode: Int,
    val number: String
)