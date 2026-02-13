package alex.dev.freshgoapp.feature.navigation

import alex.dev.freshgoapp.feature.screens.auth.AuthScreen
import alex.dev.freshgoapp.feature.screens.home.HomeScreen
import alex.dev.freshgoapp.feature.screens.intro.IntroScreen
import alex.dev.freshgoapp.feature.screens.profile.ProfileScreen
import android.util.Log
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
                    Log.d("NavigationGraph", "NavigationGraph: navigateToAuth")
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
                },
                navigateToProfile = {
                    navController.navigate(Screens.ProfileScreen)
                }
            )
        }
        composable<Screens.ProfileScreen> {
            ProfileScreen(
                navigateBack = {
                    navController.navigateUp()
                })
        }
    }
}