package alex.dev.freshgoapp.feature.screens.profile

import alex.dev.freshgoapp.feature.screens.profile.components.ProfileForm
import alex.dev.freshgoapp.ui.theme.FontSize
import alex.dev.freshgoapp.app.feature.theme.Resources
import alex.dev.freshgoapp.ui.theme.oswaldVariableFont
import alex.dev.freshgoapp.ui.theme.IconPrimary
import alex.dev.freshgoapp.ui.theme.Surface
import alex.dev.freshgoapp.ui.theme.TextPrimary
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navigateBack: () -> Unit
) {
    Scaffold(
        containerColor = Surface,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Мой профиль",
                        fontFamily = oswaldVariableFont(),
                        fontSize = FontSize.LARGE,
                        color = TextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            painter = painterResource(Resources.Icon.BackArrow),
                            contentDescription = "Arrow back button",
                            tint = IconPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Surface,
                    scrolledContainerColor = Surface,
                    navigationIconContentColor = IconPrimary,
                    titleContentColor = TextPrimary,
                    actionIconContentColor = IconPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(paddingValues)
                .imePadding()
        ) {
            ProfileForm(
                modifier = Modifier.weight(1f),
                firstName = "Олег",
                onFirstNameChange = {},
                lastName = "Никитин",
                onLastNameChange = {},
                email = "devtest.mail.ru",
                city = "Гомель",
                onCityChange = {},
                postalCode = 246043,
                onPostalCodeChange = {},
                address = "Сосновая, 23",
                onAddressChange = {},
                phoneNumber = "111222333",
                onPhoneNumberChange = {}
            )
        }
    }
}