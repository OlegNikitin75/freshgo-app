package alex.dev.freshgoapp.feature.screens.home

import alex.dev.freshgoapp.app.feature.theme.Resources
import alex.dev.freshgoapp.R
import alex.dev.freshgoapp.feature.navigation.Screens
import androidx.annotation.StringRes

enum class BottomBarDestinations(
    val icon: Int,
    @param:StringRes val titleRes: Int,
    val screen: Screens
) {
    ProductOverviewScreen(
        icon = Resources.Icon.Home,
        titleRes = R.string.bottom_bar_home,
        screen = Screens.ProductOverviewScreen
    ),
    CartScreen(
        icon = Resources.Icon.ShoppingCart,
        titleRes = R.string.bottom_bar_cart,
        screen = Screens.CartScreen
    ),
    NotificationsScreen(
        icon = Resources.Icon.Bell,
        titleRes = R.string.bottom_bar_notification,
        screen = Screens.NotificationsScreen
    ),
    CategoriesScreen(
        icon = Resources.Icon.Categories,
        titleRes = R.string.bottom_bar_categories,
        screen = Screens.CategoriesScreen
    )
}

