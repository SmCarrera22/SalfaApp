package com.example.salfaapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.salfaapp.domain.model.Vehiculo
import com.example.salfaapp.ui.components.SalfaScaffold
import com.example.salfaapp.ui.screens.CarProfileScreen
import com.example.salfaapp.ui.screens.DashboardScreen
import com.example.salfaapp.ui.screens.VehicleFormScreen
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
            SalfaScaffold(
                title = "Dashboard Salfa",
                navController = navController,
                onLogout = onLogout
            ) { innerPadding ->
                // Llamada sin pasar modifier
                DashboardScreen(
                    onNavigateToVehicles = { navController.navigate(NavRoutes.VehicleList.route) },
                    onLogout = onLogout
                )
            }
        }

        composable(NavRoutes.VehicleList.route) {
            SalfaScaffold(
                title = "Vehículos",
                navController = navController,
                onLogout = onLogout
            ) { innerPadding ->
                VehicleListScreen(navController = navController)
            }
        }

        composable(NavRoutes.CarProfile.route) {
            SalfaScaffold(
                title = "Ficha del Vehículo",
                navController = navController,
                onLogout = onLogout
            ) { innerPadding ->
                // Llamada sin pasar modifier
                CarProfileScreen(navController = navController)
            }
        }

        composable("vehicleForm") {
            VehicleFormScreen(
                navController = navController,
                onLogout = {},
                onSubmit = { marca, modelo, anio, tipo, patente, estado, sucursal, taller, obs ->
                    // Aquí puedes manejar el guardado o navegación de regreso
                }
            )
        }

    }
}
