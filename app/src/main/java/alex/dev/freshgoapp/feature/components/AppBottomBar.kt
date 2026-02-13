package alex.dev.freshgoapp.feature.components

import alex.dev.freshgoapp.feature.screens.home.BottomBarDestinations
import alex.dev.freshgoapp.ui.theme.IconPrimary
import alex.dev.freshgoapp.ui.theme.IconSecondary
import alex.dev.freshgoapp.ui.theme.SurfaceLighter
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    selected: BottomBarDestinations,
    onSelected: (BottomBarDestinations) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceLighter)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BottomBarDestinations.entries.forEach { destinations ->
            val animatedTint by animateColorAsState(
                targetValue = if (selected == destinations) IconSecondary else IconPrimary
            )
            IconButton(onClick = {
                onSelected(destinations)
            }) {
                Icon(
                    painter = painterResource(destinations.icon),
                    contentDescription = "Bottom bar destinations icon",
                    tint = animatedTint
                )
            }
        }
    }
}