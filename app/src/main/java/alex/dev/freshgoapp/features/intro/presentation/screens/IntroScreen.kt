package alex.dev.freshgoapp.features.intro.presentation.screens

import alex.dev.freshgoapp.R
import alex.dev.freshgoapp.ui.theme.BrandBrown
import alex.dev.freshgoapp.ui.theme.BrandYellow
import alex.dev.freshgoapp.ui.theme.FontSize
import alex.dev.freshgoapp.ui.theme.TextWhite
import alex.dev.freshgoapp.ui.theme.oswaldVariableFont
import alex.dev.freshgoapp.ui.theme.sentientVariable
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stephennnamani.burgerrestaurantapp.ui.theme.Resources

@Composable
fun IntroScreen() {
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
            .background(BrandYellow)
            .navigationBarsPadding()
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.padding(bottom = 100.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(Resources.Image.Logo),
                    contentDescription = "Application logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(320.dp)
                        .scale(scale.value),
                    )
                Text(
                    text = stringResource(R.string.intro_header),
                    fontFamily = oswaldVariableFont(),
                    fontSize = 16.sp, fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = stringResource(R.string.intro_subtext),
                    fontFamily = sentientVariable(),
                    textAlign = TextAlign.Center,
                )
            }
            IntroButton(onClick = {})
        }
    }
}

@Composable
fun IntroButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color = BrandBrown,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.intro_btn_text),
                fontFamily = sentientVariable(),
                color = TextWhite,
                fontSize = FontSize.EXTRA_REGULAR
            )
            Spacer(modifier = Modifier.width(12.dp))
            Icon(
                painterResource(Resources.Icon.Login),
                contentDescription = "login button",
                tint = Color.White
            )
        }
    }
}


