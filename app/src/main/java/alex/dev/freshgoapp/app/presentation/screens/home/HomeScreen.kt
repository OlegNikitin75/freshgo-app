package alex.dev.freshgoapp.app.presentation.screens.home

import alex.dev.freshgoapp.app.presentation.components.AppBottomBar
import alex.dev.freshgoapp.app.presentation.navigation.Screens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState()
    val selectedDestination by remember {
        derivedStateOf {
            val route = currentRoute.value?.destination?.route.toString()
            when {
                route.contains(BottomBarDestinations.ProductOverviewScreen.screen.toString()) -> BottomBarDestinations.ProductOverviewScreen
                route.contains(BottomBarDestinations.CartScreen.screen.toString()) -> BottomBarDestinations.CartScreen
                route.contains(BottomBarDestinations.NotificationsScreen.screen.toString()) -> BottomBarDestinations.NotificationsScreen
                route.contains(BottomBarDestinations.CategoriesScreen.screen.toString()) -> BottomBarDestinations.CategoriesScreen
                else -> BottomBarDestinations.ProductOverviewScreen
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Scaffold() { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                NavHost(
                    modifier = Modifier.weight(1f),
                    navController = navController,
                    startDestination = Screens.ProductOverviewScreen,
                )
                {
                    composable<Screens.ProductOverviewScreen> {}
                    composable<Screens.CartScreen> {}
                    composable<Screens.NotificationsScreen> {}
                    composable<Screens.CategoriesScreen> {}
                }
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier.padding(12.dp),
                ) {
                    AppBottomBar(
                        selected = selectedDestination,
                        onSelected = { destinations ->
                            navController.navigate(destinations.screen) {
                                launchSingleTop = true
                                popUpTo<Screens.ProductOverviewScreen> {
                                    saveState = true
                                    inclusive = false
                                }
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    }
}