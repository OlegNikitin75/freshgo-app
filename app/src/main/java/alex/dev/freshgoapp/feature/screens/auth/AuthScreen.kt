package alex.dev.freshgoapp.feature.screens.auth

import alex.dev.freshgoapp.R
import alex.dev.freshgoapp.app.feature.theme.Resources
import alex.dev.freshgoapp.core.data.auth.GoogleUiClient
import alex.dev.freshgoapp.feature.components.GoogleButton
import alex.dev.freshgoapp.feature.components.PrimaryButton
import alex.dev.freshgoapp.ui.theme.FontSize
import alex.dev.freshgoapp.ui.theme.oswaldVariableFont
import alex.dev.freshgoapp.util.RequestState
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.io.IOException
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import kotlin.coroutines.cancellation.CancellationException

@Composable
fun AuthScreen(
    navigateToHome: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as Activity
    val scope = rememberCoroutineScope()
    val authViewModel: AuthViewModel = koinViewModel()
    val googleUiClient: GoogleUiClient = koinInject()
    var loadingState by remember { mutableStateOf(false) }





    Scaffold() { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(0.8f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(Resources.Image.Logo),
                    contentDescription = "Signing logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(180.dp)
                )
                Text(
                    text = stringResource(R.string.signing_text),
                    fontFamily = oswaldVariableFont(),
                    fontSize = FontSize.MEDIUM
                )
            }
            GoogleButton(
                loading = loadingState,
                onClick = {
                    scope.launch {
                        loadingState = true
                        try {
                            val authResult = googleUiClient.signInWithGoogle(activity)
                            val user = authResult.user
                            if (user != null) {
                                authViewModel.createCustomer(
                                    user = user,
                                    onSuccess = { RequestState.Success(user) },
                                    onError = { RequestState.Error("Unable to create new user") }
                                )
                                navigateToHome()
                            } else {
                                RequestState.Error("Google sign-in failed")
                            }
                        } catch (e: CancellationException) {
                        } catch (e: IOException) {
                            RequestState.Error("No internet connection")
                        } catch (e: Exception) {
                            RequestState.Error("Sign-in failed. Please try again.")
                        }
                    }
                },
                icon = painterResource(Resources.Image.GoogleLogo)
            )
            Spacer(modifier = Modifier.height(12.dp))
            PrimaryButton(
                text = stringResource(R.string.guest_signing_text),
                icon = painterResource(Resources.Icon.Login),
                onClick = {
                    scope.launch {
                        try {
                            val guestResult = googleUiClient.guestSignIn()
                            val user = guestResult.user
                            if (user != null) {
                                authViewModel.createCustomer(
                                    user = user,
                                    onSuccess = { RequestState.Success(user) },
                                    onError = { RequestState.Error("Unable to create new user") }
                                )
                                navigateToHome()
                            } else {
                                RequestState.Error("Guest sign-in failed")
                            }
                        } catch (e: CancellationException) {
                        } catch (e: IOException) {
                            RequestState.Error("No internet connection")
                        } catch (e: Exception) {
                            RequestState.Error("Sign-in failed. Please try again.")
                        }
                    }
                }
            )
        }
    }
}