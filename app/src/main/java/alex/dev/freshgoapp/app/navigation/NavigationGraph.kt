package alex.dev.freshgoapp.app.navigation

import alex.dev.freshgoapp.features.intro.presentation.screens.IntroScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationGraph(startDestination: Screens = Screens.IntroScreen) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Screens.IntroScreen>{
            IntroScreen()
        }
    }
}