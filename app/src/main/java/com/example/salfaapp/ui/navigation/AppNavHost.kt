package com.example.salfaapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.salfaapp.domain.model.Vehiculo
import com.example.salfaapp.domain.model.data.config.AppDatabase
import com.example.salfaapp.domain.model.data.repository.TallerRepository
import com.example.salfaapp.ui.components.SalfaScaffold
import com.example.salfaapp.ui.screens.CarProfileScreen
import com.example.salfaapp.ui.screens.DashboardScreen
import com.example.salfaapp.ui.screens.TallerFormScreen
import com.example.salfaapp.ui.screens.TallerListScreen
import com.example.salfaapp.ui.screens.TallerProfileScreen
import com.example.salfaapp.ui.screens.VehicleFormScreen
import com.example.salfaapp.ui.screens.VehicleListScreen
import com.example.salfaapp.ui.viewModel.TallerViewModel
import com.example.salfaapp.ui.viewModel.TallerViewModelFactory

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
            ) {
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
            ) {
                VehicleListScreen(navController = navController)
            }
        }

        composable(route = NavRoutes.CarProfile.route) { backStackEntry ->
            val vehiculoId = backStackEntry.arguments?.getString("vehiculoId")?.toLongOrNull()

            SalfaScaffold(
                title = "Ficha del Vehículo",
                navController = navController,
                onLogout = onLogout
            ) {
                CarProfileScreen(
                    navController = navController,
                    vehiculoId = vehiculoId
                )
            }
        }

        composable("vehicleForm") {
            VehicleFormScreen(
                navController = navController,
                onLogout = {},
                onSubmit = { marca, modelo, anio, tipo, patente, estado, sucursal, taller, obs ->
                }
            )
        }

        // ---------- TALLER FORM ----------
        composable(NavRoutes.TallerForm.route) {

            val context = LocalContext.current
            val db = AppDatabase.getDatabase(context)
            val repo = TallerRepository(db.tallerDao())
            val tallerViewModel: TallerViewModel =
                viewModel(factory = TallerViewModelFactory(repo))

            SalfaScaffold(
                title = "Registrar Taller",
                navController = navController,
                onLogout = onLogout
            ) { padding ->
                TallerFormScreen(
                    viewModel = tallerViewModel,
                    onSaved = { navController.popBackStack() },
                    paddingValues = padding
                )
            }
        }

        // ---------- TALLER LIST ----------
        composable(NavRoutes.TallerList.route) {

            val context = LocalContext.current
            val db = AppDatabase.getDatabase(context)
            val repo = TallerRepository(db.tallerDao())
            val tallerViewModel: TallerViewModel =
                viewModel(factory = TallerViewModelFactory(repo))

            SalfaScaffold(
                title = "Talleres",
                navController = navController,
                onLogout = onLogout
            ) {
                TallerListScreen(
                    viewModel = tallerViewModel,
                    onAddTaller = { navController.navigate(NavRoutes.TallerForm.route) },
                    onTallerSelected = { tallerId ->
                        navController.navigate(NavRoutes.TallerProfile.createRoute(tallerId))
                    }
                )
            }
        }

        // ---------- TALLER PROFILE ----------
        composable(
            route = NavRoutes.TallerProfile.route
        ) { backStackEntry ->

            val tallerId = backStackEntry.arguments
                ?.getString("tallerId")
                ?.toIntOrNull()

            SalfaScaffold(
                title = "Ficha del Taller",
                navController = navController,
                onLogout = onLogout
            ) {
                TallerProfileScreen(
                    navController = navController,
                    tallerId = tallerId,
                    onLogout = onLogout
                )
            }
        }
    }
}