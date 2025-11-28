package alex.dev.freshgoapp.app.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screens {
    @Serializable
    data object IntroScreen : Screens()

    @Serializable
    data object AuthScreen : Screens()

    @Serializable
    data object HomeGraph : Screens()
}