package alex.dev.freshgoapp.core.ui.componets

import alex.dev.freshgoapp.ui.theme.ButtonDisabled
import alex.dev.freshgoapp.ui.theme.ButtonPrimary
import alex.dev.freshgoapp.ui.theme.ButtonSecondary
import alex.dev.freshgoapp.ui.theme.FontSize
import alex.dev.freshgoapp.ui.theme.TextPrimary
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: Painter? = null,
    enabled: Boolean = true,
    secondary: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxWidth(), onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (secondary) ButtonSecondary else ButtonPrimary,
            contentColor = TextPrimary,
            disabledContainerColor = ButtonDisabled,
            disabledContentColor = TextPrimary.copy(0.5f)
        ),
        contentPadding = PaddingValues(20.dp),
    ) {
        Text(
            text = text,
            fontSize = FontSize.REGULAR,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.width(12.dp))
        if (icon != null) {
            Icon(
                modifier = Modifier.size(14.dp),
                painter = icon,
                contentDescription = "Icon button"
            )
        }
    }
}