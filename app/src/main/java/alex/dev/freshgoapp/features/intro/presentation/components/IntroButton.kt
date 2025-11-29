package alex.dev.freshgoapp.features.intro.presentation.components

import alex.dev.freshgoapp.R
import alex.dev.freshgoapp.ui.theme.BrandBrown
import alex.dev.freshgoapp.ui.theme.FontSize
import alex.dev.freshgoapp.ui.theme.TextWhite
import alex.dev.freshgoapp.ui.theme.sentientVariable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.stephennnamani.burgerrestaurantapp.ui.theme.Resources

@Composable
fun IntroButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color = BrandBrown,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
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