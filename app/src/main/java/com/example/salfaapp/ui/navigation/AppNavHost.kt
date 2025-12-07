package com.example.salfaapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.salfaapp.domain.model.Vehiculo
import com.example.salfaapp.domain.model.data.config.AppDatabase
import com.example.salfaapp.network.RetrofitWeatherClient
import com.example.salfaapp.network.api.remote.WeatherApi
import com.example.salfaapp.network.repository.WeatherRepository
import com.example.salfaapp.ui.components.SalfaScaffold
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
import com.example.salfaapp.ui.weather.WeatherViewModel
import com.example.salfaapp.ui.weather.WeatherViewModelFactory

@Composable
fun AppNavHost(
    navController: NavHostController,
    onLogout: () -> Unit,
    vehiculos: List<Vehiculo> = emptyList()
) {

    // ---------------------------------------------
    // 🔵 1) Crear Retrofit + DAOs + Repos globales
    // ---------------------------------------------
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)

    // --- Retrofit APIs ---
    val vehiculoApi = com.example.salfaapp.network.RetrofitVehicleClient.instance
        .create(com.example.salfaapp.network.api.VehiculoApi::class.java)

    val tallerApi = com.example.salfaapp.network.RetrofitTallerClient.instance
        .create(com.example.salfaapp.network.api.TallerApi::class.java)

    // --- Remote repos (Retrofit + DAOs) ---
    val vehiculoRemoteRepo = com.example.salfaapp.network.repository.VehiculoRemoteRepository(
        vehiculoApi,
        db.vehiculoDao()
    )

    val tallerRemoteRepo = com.example.salfaapp.network.repository.TallerRemoteRepository(
        tallerApi,
        db.tallerDao()
    )

    // --- Local repos (solo Room) ---
    val vehiculoLocalRepo = com.example.salfaapp.domain.model.data.repository.VehiculoRepository(
        db.vehiculoDao()
    )
    val tallerLocalRepo = com.example.salfaapp.domain.model.data.repository.TallerRepository(
        db.tallerDao()
    )

    val weatherApi = RetrofitWeatherClient.instance
        .create(WeatherApi::class.java)

    val weatherRepository = WeatherRepository(weatherApi, "8f547d1e534a7186dd9fc31a158afe74")

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Dashboard.route
    ) {

        // ---------- DASHBOARD ----------
        composable(NavRoutes.Dashboard.route) {

            // --- ViewModels principales ---
            val dashboardViewModel: DashboardViewModel = viewModel(
                factory = DashboardViewModelFactory(
                    vehiculoLocalRepo,
                    tallerLocalRepo,
                    vehiculoRemoteRepo,
                    tallerRemoteRepo
                )
            )

            // --- WeatherViewModel para clima ---
            val weatherViewModel: WeatherViewModel = viewModel(
                factory = WeatherViewModelFactory(weatherRepository)
            )

            SalfaScaffold(
                title = "Dashboard Salfa",
                navController = navController,
                onLogout = onLogout
            ) { innerPadding ->
                DashboardScreen(
                    viewModel = dashboardViewModel,
                    weatherViewModel = weatherViewModel,
                    onNavigateToVehicles = { navController.navigate(NavRoutes.VehicleList.route) },
                    onLogout = onLogout
                )
            }
        }

        // ---------- VEHICLE LIST ----------
        composable(NavRoutes.VehicleList.route) {

            val vehiculoViewModel: VehiculoViewModel =
                viewModel(
                    factory = VehiculoViewModelFactory(
                        vehiculoLocalRepo,
                        vehiculoRemoteRepo    // 👈 AGREGADO
                    )
                )

            SalfaScaffold(
                title = "Vehículos",
                navController = navController,
                onLogout = onLogout
            ) {
                VehicleListScreen(
                    viewModel = vehiculoViewModel,
                    onAddVehicle = {
                        navController.navigate(NavRoutes.VehicleForm.createRoute(-1))
                    },
                    onVehicleSelected = { id ->
                        navController.navigate(NavRoutes.CarProfile.createRoute(id))
                    }
                )
            }
        }

        // ---------- VEHICLE FORM ----------
        composable(NavRoutes.VehicleForm.route) { backStackEntry ->

            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()

            val viewModel: VehiculoViewModel =
                viewModel(
                    factory = VehiculoViewModelFactory(
                        vehiculoLocalRepo,
                        vehiculoRemoteRepo
                    )
                )

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

        // ---------- TALLER LIST ----------
        composable(NavRoutes.TallerList.route) {

            val tallerViewModel: TallerViewModel =
                viewModel(factory = TallerViewModelFactory(tallerLocalRepo, tallerRemoteRepo))

            SalfaScaffold(
                title = "Talleres",
                navController = navController,
                onLogout = onLogout
            ) {
                TallerListScreen(
                    viewModel = tallerViewModel,
                    onAddTaller = { navController.navigate(NavRoutes.TallerForm.route) },
                    onTallerSelected = { id ->
                        navController.navigate(NavRoutes.TallerProfile.createRoute(id))
                    }
                )
            }
        }

        // ---------- TALLER FORM ----------
        composable(NavRoutes.TallerForm.route) {

            val tallerViewModel: TallerViewModel =
                viewModel(factory = TallerViewModelFactory(tallerLocalRepo, tallerRemoteRepo))

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

        // ---------- TALLER PROFILE ----------
        composable(NavRoutes.TallerProfile.route) { backStackEntry ->

            val id = backStackEntry.arguments?.getString("tallerId")?.toIntOrNull()

            val tallerViewModel: TallerViewModel =
                viewModel(factory = TallerViewModelFactory(tallerLocalRepo, tallerRemoteRepo))

            SalfaScaffold(
                title = "Ficha Taller",
                navController = navController,
                onLogout = onLogout
            ) { padding ->
                TallerProfileScreen(
                    navController = navController,
                    viewModel = tallerViewModel,
                    tallerId = id,
                    onLogout = onLogout
                )
            }
        }
    }
}