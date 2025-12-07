package com.example.salfaapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.salfaapp.domain.model.Vehiculo
import com.example.salfaapp.domain.model.data.config.AppDatabase
import com.example.salfaapp.domain.model.data.repository.TallerRepository
import com.example.salfaapp.domain.model.data.repository.VehiculoRepository
import com.example.salfaapp.ui.components.SalfaScaffold
import com.example.salfaapp.ui.screens.CarProfileScreen
import com.example.salfaapp.ui.screens.DashboardScreen
import com.example.salfaapp.ui.screens.TallerFormScreen
import com.example.salfaapp.ui.screens.TallerListScreen
import com.example.salfaapp.ui.screens.TallerProfileScreen
import com.example.salfaapp.ui.screens.VehicleFormScreen
import com.example.salfaapp.ui.screens.VehicleListScreen
import com.example.salfaapp.ui.viewModel.DashboardViewModel
import com.example.salfaapp.ui.viewModel.DashboardViewModelFactory
import com.example.salfaapp.ui.viewModel.TallerViewModel
import com.example.salfaapp.ui.viewModel.TallerViewModelFactory
import com.example.salfaapp.ui.viewModel.VehiculoViewModel
import com.example.salfaapp.ui.viewModel.VehiculoViewModelFactory

@Composable
fun AppNavHost(
    navController: NavHostController,
    onLogout: () -> Unit,
    vehiculos: List<Vehiculo> = emptyList()
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Dashboard.route
    ) {
        // ---------- DASHBOARD ----------
        composable(NavRoutes.Dashboard.route) {
            val context = LocalContext.current
            val db = AppDatabase.getDatabase(context)

            // Repos
            val vehiculoRepo = VehiculoRepository(db.vehiculoDao())
            val tallerRepo = TallerRepository(db.tallerDao())

            // ViewModel con factory (la factory que tengas debe aceptar (vehiculoRepo, tallerRepo))
            val dashboardViewModel: DashboardViewModel =
                viewModel(factory = DashboardViewModelFactory(vehiculoRepo, tallerRepo))

            SalfaScaffold(
                title = "Dashboard Salfa",
                navController = navController,
                onLogout = onLogout
            ) { innerPadding ->
                DashboardScreen(
                    viewModel = dashboardViewModel,
                    onNavigateToVehicles = { navController.navigate(NavRoutes.VehicleList.route) },
                    onLogout = onLogout
                )
            }
        }

        // ---------- VEHICLE LIST ----------
        composable(NavRoutes.VehicleList.route) {

            val context = LocalContext.current
            val db = AppDatabase.getDatabase(context)
            val repo = VehiculoRepository(db.vehiculoDao())

            val vehiculoViewModel: VehiculoViewModel =
                viewModel(factory = VehiculoViewModelFactory(repo))

            SalfaScaffold(
                title = "Vehículos",
                navController = navController,
                onLogout = onLogout
            ) { innerPadding ->
                VehicleListScreen(
                    navController = navController,
                    viewModel = vehiculoViewModel
                )
            }
        }

        // ---------- CAR PROFILE ----------
        composable(route = NavRoutes.CarProfile.route) { backStackEntry ->
            val vehiculoId = backStackEntry.arguments?.getString("vehiculoId")?.toLongOrNull()
            SalfaScaffold(
                title = "Ficha del Vehículo",
                navController = navController,
                onLogout = onLogout
            ) { innerPadding ->
                CarProfileScreen(
                    navController = navController,
                    vehiculoId = vehiculoId
                )
            }
        }

        // ---------- VEHICLE FORM (CREAR / EDITAR) ----------
        composable(NavRoutes.VehicleForm.route) { backStackEntry ->

            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
            val context = LocalContext.current
            val db = AppDatabase.getDatabase(context)
            val repo = VehiculoRepository(db.vehiculoDao())

            val viewModel: VehiculoViewModel =
                viewModel(factory = VehiculoViewModelFactory(repo))

            SalfaScaffold(
                title = if (id == null || id == -1L) "Nuevo Vehículo" else "Editar Vehículo",
                navController = navController,
                onLogout = onLogout
            ) { padding ->
                VehicleFormScreen(
                    navController = navController,
                    viewModel = viewModel,
                    vehiculoId = id,
                    paddingValues = padding
                )
            }
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
            ) { innerPadding ->
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
        composable(route = NavRoutes.TallerProfile.route) { backStackEntry ->

            val tallerId = backStackEntry.arguments?.getString("tallerId")?.toIntOrNull()

            val context = LocalContext.current
            val db = AppDatabase.getDatabase(context)
            val repo = TallerRepository(db.tallerDao())

            val tallerViewModel: TallerViewModel =
                viewModel(factory = TallerViewModelFactory(repo))

            SalfaScaffold(
                title = "Ficha del Taller",
                navController = navController,
                onLogout = onLogout
            ) { innerPadding ->
                TallerProfileScreen(
                    navController = navController,
                    viewModel = tallerViewModel,    // ← AGREGADO
                    tallerId = tallerId,
                    onLogout = onLogout
                )
            }
        }
    }
}