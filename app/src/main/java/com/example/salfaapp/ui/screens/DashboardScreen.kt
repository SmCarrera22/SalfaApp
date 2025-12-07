package com.example.salfaapp.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.salfaapp.location.LocationService
import com.example.salfaapp.ui.theme.Blue20
import com.example.salfaapp.ui.viewModel.DashboardViewModel
import com.example.salfaapp.ui.weather.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    weatherViewModel: WeatherViewModel,
    onLogout: () -> Unit = {},
    onNavigateToVehicles: () -> Unit = {}
) {
    val talleresDyP by viewModel.totalTalleresDyP.collectAsState(initial = 0)
    val talleresMecanica by viewModel.totalTalleresMecanica.collectAsState(initial = 0)

    val totalStock by viewModel.totalStock.collectAsState(initial = 0)
    val disponibles by viewModel.vehiculosDisponibles.collectAsState(initial = 0)
    val pap by viewModel.vehiculosPAP.collectAsState(initial = 0)
    val nuevosIngresos by viewModel.vehiculosNuevos.collectAsState(initial = 0)

    val weather by weatherViewModel.weather.collectAsState()
    val isLoadingWeather by weatherViewModel.isLoading.collectAsState()

    val context = LocalContext.current

    var locationPermissionGranted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        locationPermissionGranted = granted
    }

    LaunchedEffect(locationPermissionGranted) {
        if (locationPermissionGranted) {
            val locationService = LocationService(context)
            locationService.obtenerUbicacion { lat, lon ->
                weatherViewModel.fetchWeather(lat, lon)
            }
        } else {
            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Menú", modifier = Modifier.padding(16.dp))
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Dashboard Salfa") }
                )
            }
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {

                // --- CLIMA ---
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(Color(0xFF64B5F6)),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text("Clima actual", style = MaterialTheme.typography.titleLarge, color = Color.White)
                        Spacer(Modifier.height(10.dp))

                        if (isLoadingWeather) {
                            CircularProgressIndicator(color = Color.White)
                        }

                        weather?.let { w ->
                            Text("Ubicación: ${w.name}", color = Color.White)
                            Text("Temperatura: ${w.main.temp}°C", color = Color.White)
                            Text("Humedad: ${w.main.humidity}%", color = Color.White)
                            Text("Clima: ${w.weather.firstOrNull()?.description ?: "-"}", color = Color.White)
                        } ?: run {
                            Text("Cargando clima...", color = Color.White)
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))

                // --- TALLERES ---
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(Blue20),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Talleres", style = MaterialTheme.typography.titleLarge, color = Color.White)
                        Spacer(Modifier.height(10.dp))
                        Text("DyP: $talleresDyP", color = Color.White)
                        Text("Mecánica: $talleresMecanica", color = Color.White)
                    }
                }

                Spacer(Modifier.height(20.dp))

                // --- VEHÍCULOS ---
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(Color(0xFF1565C0)),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Vehículos", style = MaterialTheme.typography.titleLarge, color = Color.White)
                        Spacer(Modifier.height(10.dp))
                        Text("Stock total: $totalStock", color = Color.White)
                        Text("Disponibles: $disponibles", color = Color.White)
                        Text("En PAP: $pap", color = Color.White)
                        Text("Nuevos ingresados: $nuevosIngresos", color = Color.White)
                    }
                }
            }
        }
    }
}