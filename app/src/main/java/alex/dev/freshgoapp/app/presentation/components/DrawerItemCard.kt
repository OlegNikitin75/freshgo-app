package alex.dev.freshgoapp.app.presentation.components

import alex.dev.freshgoapp.app.presentation.screens.home.DrawerItems
import alex.dev.freshgoapp.app.presentation.theme.FontSize
import alex.dev.freshgoapp.app.presentation.theme.oswaldVariableFont
import alex.dev.freshgoapp.ui.theme.IconSecondary
import alex.dev.freshgoapp.ui.theme.IconTertiary
import alex.dev.freshgoapp.ui.theme.TextWhite
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun DrawerItemCard(
    drawerItem: DrawerItems,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(99.dp))
            .clickable {
                onClick()
            }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(drawerItem.icon),
            contentDescription = "Drawer icon",
            tint = IconTertiary
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = stringResource(drawerItem.titleRes),
            color = TextWhite,
            fontFamily = oswaldVariableFont(),
            fontSize = FontSize.EXTRA_REGULAR
        )
    }
}