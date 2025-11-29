package alex.dev.freshgoapp.features.auth.presentation.components

import alex.dev.freshgoapp.ui.theme.BorderIdle
import alex.dev.freshgoapp.ui.theme.FontSize
import alex.dev.freshgoapp.ui.theme.IconSecondary
import alex.dev.freshgoapp.ui.theme.SurfaceLighter
import alex.dev.freshgoapp.ui.theme.TextPrimary
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun GoogleButton(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    primaryText: String = "Войти с Google",
    secondaryText: String = "Пожалуйста подождите...",
    icon: Painter? = null,
    shape: Shape = RoundedCornerShape(16.dp),
    backgroundColor: Color = SurfaceLighter,
    borderColor: Color = BorderIdle,
    progressIndicatorColor: Color = IconSecondary,
    onClick: () -> Unit
) {
    var buttonText by remember { mutableStateOf(primaryText) }
    LaunchedEffect(loading) {
        buttonText = if (loading) secondaryText else primaryText
    }
    Surface(
        modifier = Modifier
            .clip(shape)
            .border(width = 1.dp, color = borderColor, shape = shape)
            .clickable(enabled = !loading) { onClick() },
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .animateContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AnimatedContent(
                targetState = loading
            ) { loadingState ->
                if (!loadingState) {
                    if (icon != null) {
                        Icon(
                            painter = icon,
                            contentDescription = "Logo signing button",
                            tint = Color.Unspecified
                        )
                    } else {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp,
                            color = progressIndicatorColor
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = buttonText, color = TextPrimary, fontSize = FontSize.REGULAR)
        }
    }
}