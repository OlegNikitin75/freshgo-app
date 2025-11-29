package alex.dev.freshgoapp.features.auth.presentation.screens

import alex.dev.freshgoapp.R
import alex.dev.freshgoapp.core.ui.componets.PrimaryButton
import alex.dev.freshgoapp.features.auth.presentation.components.GoogleButton
import alex.dev.freshgoapp.ui.theme.FontSize
import alex.dev.freshgoapp.ui.theme.oswaldVariableFont
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.stephennnamani.burgerrestaurantapp.ui.theme.Resources

@Composable
fun AuthScreen() {
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
                loading = false,
                onClick = {},
                icon = painterResource(Resources.Image.GoogleLogo)
            )
            Spacer(modifier = Modifier.height(12.dp))
            PrimaryButton(
                text = stringResource(R.string.guest_signing_text),
                icon = painterResource(Resources.Icon.Login),
                onClick = {}
            )
        }
    }
}