package alex.dev.freshgoapp.feature.screens.intro

import alex.dev.freshgoapp.R
import alex.dev.freshgoapp.app.feature.theme.Resources
import alex.dev.freshgoapp.core.data.auth.GoogleUiClient
import alex.dev.freshgoapp.feature.components.IntroButton
import alex.dev.freshgoapp.ui.theme.BrandYellow
import alex.dev.freshgoapp.ui.theme.oswaldVariableFont
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.koinInject

@Composable
fun IntroScreen(
    navigateToAuth: () -> Unit,
    navigateToHome: () -> Unit
) {
    val googleAuthUiClient: GoogleUiClient = koinInject()
    val scale = remember { Animatable(0f) }

    LaunchedEffect(key1 = true, block = {
        scale.animateTo(
            targetValue = 0.7f, animationSpec = tween(
                durationMillis = 600, easing = {
                    OvershootInterpolator(7f).getInterpolation(it)
                }
            ))
    })
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BrandYellow)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .systemBarsPadding()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(Resources.Image.LogoLM),
                    contentDescription = "Application logo", contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(150.dp)
                        .scale(scale.value),
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = stringResource(R.string.intro_header),
                    fontFamily = oswaldVariableFont(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.intro_subtext),
                    fontFamily = oswaldVariableFont(),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 16.sp
                )
            }


            Column(
                modifier = Modifier.padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IntroButton(onClick = {
                    val user = googleAuthUiClient.currentUser
                    if (user == null) {
                        navigateToAuth()
                    } else {
                        navigateToHome()
                    }
                })
            }
        }
    }
}




