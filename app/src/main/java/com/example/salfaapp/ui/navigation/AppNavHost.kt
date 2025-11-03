package com.example.salfaapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.salfaapp.domain.model.Vehiculo
import com.example.salfaapp.ui.screens.CarProfileScreen
import com.example.salfaapp.ui.screens.DashboardScreen
import com.example.salfaapp.ui.screens.VehicleListScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    onLogout: () -> Unit,
    vehiculos: List<Vehiculo>
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Dashboard.route
    ) {
        composable(NavRoutes.Dashboard.route) {
            DashboardScreen(
                onLogout = onLogout,
                onNavigateToVehicles = {
                    navController.navigate(NavRoutes.VehicleList.route)
                }
            )
        }

        composable(NavRoutes.VehicleList.route) {
            VehicleListScreen(
                vehiculos = vehiculos,
                onVehiculoClick = { navController.navigate(NavRoutes.CarProfile.route) }
            )
        }

        composable(NavRoutes.CarProfile.route) {
            CarProfileScreen()
        }
    }
}
