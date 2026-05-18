package com.gramavasathi.app.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gramavasathi.app.viewmodel.GramaVasathiViewModel

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Home : Screen("home")
    object Explore : Screen("explore")
    object FarmStayDetail : Screen("farmstay_detail")
    object Booking : Screen("booking")
    object BookingConfirm : Screen("booking_confirm")
    object HostTraining : Screen("host_training")
    object CulturalGuide : Screen("cultural_guide")
}

@Composable
fun GramaVasathiApp() {
    val navController = rememberNavController()
    val viewModel: GramaVasathiViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screen.Explore.route) {
            ExploreScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screen.FarmStayDetail.route) {
            FarmStayDetailScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screen.Booking.route) {
            BookingScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screen.BookingConfirm.route) {
            BookingConfirmScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screen.HostTraining.route) {
            HostTrainingScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screen.CulturalGuide.route) {
            CulturalGuideScreen(navController = navController)
        }
    }
}
