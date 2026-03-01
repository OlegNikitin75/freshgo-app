package alex.dev.freshgoapp.feature.screens.profile

import alex.dev.freshgoapp.app.feature.theme.Resources
import alex.dev.freshgoapp.feature.components.InfoCard
import alex.dev.freshgoapp.feature.components.LoadingCard
import alex.dev.freshgoapp.feature.components.PrimaryButton
import alex.dev.freshgoapp.feature.screens.profile.components.ProfileForm
import alex.dev.freshgoapp.ui.theme.FontSize
import alex.dev.freshgoapp.ui.theme.IconPrimary
import alex.dev.freshgoapp.ui.theme.Surface
import alex.dev.freshgoapp.ui.theme.TextPrimary
import alex.dev.freshgoapp.ui.theme.oswaldVariableFont
import alex.dev.freshgoapp.util.DisplayResult
import alex.dev.freshgoapp.util.RequestState
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navigateBack: () -> Unit
) {
    val profileScreenViewModel = koinViewModel<ProfileScreenViewModel>()
    val screenState = profileScreenViewModel.screenState
    val screenReady = profileScreenViewModel.screenReady
    val isFormValid = profileScreenViewModel.isFormValid
    val isSubmitted = profileScreenViewModel.isSubmitted
    val context = LocalContext.current
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
                            contentDescription = "Иконка кнопки назад",
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
            screenReady.DisplayResult(
                onLoading = { LoadingCard(modifier = Modifier.fillMaxSize()) },
                onSuccess = {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ProfileForm(
                            modifier = Modifier.weight(1f),
                            isLoading = screenReady.isLoading(),
                            firstName = screenState.firstName,
                            onFirstNameChange = profileScreenViewModel::updateFirstName,
                            lastName = screenState.lastName,
                            onLastNameChange = profileScreenViewModel::updateLastName,
                            email = screenState.email,
                            city = screenState.city,
                            onCityChange = profileScreenViewModel::updateCity,
                            postalCode = screenState.postalCode,
                            onPostalCodeChange = profileScreenViewModel::updatePostalCode,
                            address = screenState.address,
                            onAddressChange = profileScreenViewModel::updateAddress,
                            phoneNumber = screenState.phoneNumber?.number,
                            onPhoneNumberChange = profileScreenViewModel::updatePhoneNumber
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        PrimaryButton(
                            text = "Обновить профиль",
                            icon = painterResource(Resources.Icon.Checkmark),
                            enabled = isFormValid,
                            onClick = {
                                profileScreenViewModel.onSubmitAttempt()
                                profileScreenViewModel.updateCustomer(
                                    onSuccess = {
                                        RequestState.Success("Профиль пользователя обновлен!")
                                        Toast.makeText(
                                            context,
                                            "Профиль пользователя обновлен!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    },
                                    onError = { message ->
                                        RequestState.Error("Ошибка обновления профиля.")
                                        Toast.makeText(
                                            context,
                                            "Ошибка обновления профиля.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                )
                            }
                        )
                    }
                },
                onError = { message ->
                    InfoCard(
                        image = Resources.Image.UserProfileNotFound,
                        title = "Упс!",
                        subtitle = message
                    )
                }
            )
        }
    }
}
