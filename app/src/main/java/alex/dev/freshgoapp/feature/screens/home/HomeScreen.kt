package alex.dev.freshgoapp.feature.screens.home

import alex.dev.freshgoapp.R
import alex.dev.freshgoapp.feature.components.AppBottomBar
import alex.dev.freshgoapp.feature.components.AppDrawer
import alex.dev.freshgoapp.feature.navigation.Screens
import alex.dev.freshgoapp.ui.theme.FontSize
import alex.dev.freshgoapp.app.feature.theme.Resources
import alex.dev.freshgoapp.ui.theme.oswaldVariableFont
import alex.dev.freshgoapp.ui.theme.BrandBrown
import alex.dev.freshgoapp.ui.theme.IconPrimary
import alex.dev.freshgoapp.ui.theme.Surface
import alex.dev.freshgoapp.ui.theme.TextPrimary
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToAuth: () -> Unit,
    navigateToProfile: () -> Unit,
) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState()
    val homeViewModel: HomeViewModel = koinViewModel()
    val context = LocalContext.current
    val errorMessage = stringResource(R.string.error_while_signing_out)
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
    val screenWidth = remember { getScreenWidth() }
    var drawerState by remember { mutableStateOf(DrawerState.Closed) }
    val offsetValue by remember { derivedStateOf { (screenWidth / 1.8).dp } }
    val animatedOffset by animateDpAsState(targetValue = if (drawerState.isOpened()) offsetValue else 0.dp)
    val animatedScale by animateFloatAsState(targetValue = if (drawerState.isOpened()) 0.9f else 1f)
    val animatedBackground by animateColorAsState(targetValue = if (drawerState.isOpened()) BrandBrown else Surface)
    val animatedRadius by animateDpAsState(targetValue = if (drawerState.isOpened()) 20.dp else 0.dp)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(animatedBackground)
            .systemBarsPadding()
    ) {
        AppDrawer(
            onProfileClick = navigateToProfile,
            onContactUsClick = { },
            onSignOutClick = {
                homeViewModel.signOut(
                    onSuccess = navigateToAuth,
                    onError = {
                        Toast.makeText(
                            context,
                            errorMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                )
            },
            onAdminPanelClick = {}
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(animatedOffset)
                .scale(animatedScale)
                .clip(RoundedCornerShape(animatedRadius))
                .shadow(
                    elevation = 20.dp,
                    shape = RoundedCornerShape(animatedRadius),
                    ambientColor = Color.Black.copy(0.6f),
                    spotColor = Color.Black.copy(0.6f)
                )
        ) {
            Scaffold(
                containerColor = Surface,
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            AnimatedContent(
                                targetState = selectedDestination
                            ) { destination ->
                                Text(
                                    text = stringResource(destination.titleRes),
                                    fontFamily = oswaldVariableFont(),
                                    fontSize = FontSize.LARGE,
                                    color = TextPrimary
                                )
                            }
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    drawerState =
                                        drawerState.reverse()
                                }
                            ) {
                                AnimatedContent(targetState = drawerState) { drawer ->
                                    if (!drawer.isOpened()) Icon(
                                        painter = painterResource(Resources.Icon.Menu),
                                        contentDescription = "Menu",
                                        tint = IconPrimary
                                    ) else {
                                        Icon(
                                            painter = painterResource(Resources.Icon.Close),
                                            contentDescription = "Menu",
                                            tint = IconPrimary
                                        )
                                    }
                                }
                            }
                        },
                        colors =
                            TopAppBarDefaults.topAppBarColors(
                                containerColor = Surface,
                                scrolledContainerColor = Surface,
                                navigationIconContentColor = IconPrimary,
                                titleContentColor = TextPrimary,
                                actionIconContentColor = IconPrimary
                            )
                    )
                }
            ) { paddingValues ->
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
}

fun getScreenWidth(): Float {
    return android.content.res.Resources.getSystem().displayMetrics.widthPixels / android.content.res.Resources.getSystem().displayMetrics.density
}

