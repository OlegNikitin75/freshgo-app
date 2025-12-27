package alex.dev.freshgoapp.app.presentation.components

import alex.dev.freshgoapp.R
import alex.dev.freshgoapp.app.presentation.screens.home.DrawerItems
import alex.dev.freshgoapp.app.presentation.theme.FontSize
import alex.dev.freshgoapp.app.presentation.theme.Resources
import alex.dev.freshgoapp.app.presentation.theme.oswaldVariableFont
import alex.dev.freshgoapp.ui.theme.BrandYellow
import alex.dev.freshgoapp.ui.theme.TextBrand
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppDrawer(
    onProfileClick: () -> Unit,
    onContactUsClick: () -> Unit,
    onSignOutClick: () -> Unit,
    onAdminPanelClick: () -> Unit,
) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    Spacer(modifier = Modifier.height(50.dp))
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.6f)
            .padding(12.dp)
    ) {
        AsyncImage(
            model = currentUser?.photoUrl ?: "Unknown",
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            fallback = painterResource(Resources.Icon.Person)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(
                R.string.welcome,
                currentUser?.displayName?.split(" ")?.firstOrNull() ?: stringResource(R.string.user)
            ),
            fontFamily = oswaldVariableFont(),
            fontSize = FontSize.EXTRA_REGULAR,
            fontWeight = FontWeight.Medium,
            color = TextBrand,

        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(top = 8.dp)
                .clip(RoundedCornerShape(99.dp)),
            thickness = 2.dp,
            color = BrandYellow
        )
        Spacer(modifier = Modifier.height(30.dp))
        DrawerItems.entries.take(n = 6).forEach { drawerItem ->
            DrawerItemCard(
                drawerItem = drawerItem,
                onClick = {
                    when (drawerItem) {
                        DrawerItems.Profile -> onProfileClick()
                        DrawerItems.ContactUs -> onContactUsClick()
                        DrawerItems.SignOut -> onSignOutClick()
                        else -> {}
                    }
                }
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
        Spacer(modifier = Modifier.weight(1f))
        DrawerItemCard(
            drawerItem = DrawerItems.AdminPanel,
            onClick = onAdminPanelClick
        )
        Spacer(modifier = Modifier.height(20.dp))
    }

}