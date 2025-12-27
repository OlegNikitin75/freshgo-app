package alex.dev.freshgoapp.app.presentation.navigation

import alex.dev.freshgoapp.app.presentation.screens.auth.AuthScreen
import alex.dev.freshgoapp.app.presentation.screens.home.HomeScreen
import alex.dev.freshgoapp.app.presentation.screens.intro.IntroScreen
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
        composable<Screens.IntroScreen> {
            IntroScreen(
                navigateToAuth = {
                    navController.navigate(Screens.AuthScreen) {
                        popUpTo<Screens.IntroScreen> { inclusive = true }
                    }
                },
                navigateToHome = {
                    navController.navigate(Screens.HomeGraph) {
                        popUpTo<Screens.AuthScreen> { inclusive = true }
                    }
                }
            )
        }
        composable<Screens.AuthScreen> {
            AuthScreen(
                navigateToHome = {
                    navController.navigate(Screens.HomeGraph) {
                        popUpTo<Screens.AuthScreen> { inclusive = true }
                    }
                }
            )
        }
        composable<Screens.HomeGraph> {
            HomeScreen(
                navigateToAuth = {
                    navController.navigate(Screens.AuthScreen) {
                        popUpTo<Screens.HomeGraph> { inclusive = true }
                    }
                }
            )
        }
    }
}