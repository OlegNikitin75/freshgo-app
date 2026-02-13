package alex.dev.freshgoapp.feature.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screens {
    @Serializable
    data object IntroScreen : Screens()

    @Serializable
    data object AuthScreen : Screens()

    @Serializable
    data object HomeGraph : Screens()

    @Serializable
    data object ProductOverviewScreen : Screens()

    @Serializable
    data object CartScreen : Screens()

    @Serializable
    data object NotificationsScreen : Screens()

    @Serializable
    data object CategoriesScreen : Screens()

    @Serializable
    data object ProfileScreen : Screens()
}