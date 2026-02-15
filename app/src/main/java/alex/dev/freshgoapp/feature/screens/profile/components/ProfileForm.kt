package alex.dev.freshgoapp.feature.screens.profile.components

import alex.dev.freshgoapp.feature.components.AppTextField
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun ProfileForm(
    modifier: Modifier = Modifier,
    firstName: String,
    onFirstNameChange: (String) -> Unit,
    lastName: String,
    onLastNameChange: (String) -> Unit,
    email: String,
    city: String?,
    onCityChange: (String) -> Unit,
    postalCode: Int?,
    onPostalCodeChange: (Int?) -> Unit,
    address: String?,
    onAddressChange: (String) -> Unit,
    phoneNumber: String?,
    onPhoneNumberChange: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AppTextField(
            value = firstName,
            onValueChange = onFirstNameChange,
            placeholder = "Имя",
            error = firstName.length !in 3..20
        )
        AppTextField(
            value = lastName,
            onValueChange = onLastNameChange,
            placeholder = "Фамилия",
            error = lastName.length !in 3..20
        )
        AppTextField(
            value = email,
            onValueChange = {},
            placeholder = "Электронная почта",
            enabled = false
        )
        AppTextField(
            value = city ?: "",
            onValueChange = onCityChange,
            placeholder = "Город",
            error = city?.length !in 3..20
        )
        AppTextField(
            value = "${postalCode ?: ""}",
            onValueChange = { onPostalCodeChange(it.toIntOrNull()) },
            placeholder = "Индекс",
            error = postalCode?.toString()?.length !in 3..8,
            keyboardOptions = KeyboardOptions(
                keyboardType =
                    KeyboardType.Number
            )
        )
        AppTextField(
            value = address ?: "",
            onValueChange = onAddressChange,
            placeholder = "Адрес",
            error = address?.length !in 3..50
        )
        AppTextField(
            value = phoneNumber ?: "",
            onValueChange = onPhoneNumberChange,
            placeholder = "Телефон",
            error = phoneNumber.toString().length !in 3..20,
            keyboardOptions = KeyboardOptions(
                keyboardType =
                    KeyboardType.Number
            )
        )
    }
}