package com.example.salfaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.salfaapp.ui.theme.Blue20
import com.example.salfaapp.ui.viewModel.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onLogout: () -> Unit = {},
    onNavigateToVehicles: () -> Unit = {}
) {
    val talleresDyP by viewModel.totalTalleresDyP.collectAsState(initial = 0)
    val talleresMecanica by viewModel.totalTalleresMecanica.collectAsState(initial = 0)

    val totalStock by viewModel.totalStock.collectAsState(initial = 0)
    val disponibles by viewModel.vehiculosDisponibles.collectAsState(initial = 0)
    val pap by viewModel.vehiculosPAP.collectAsState(initial = 0)
    val nuevosIngresos by viewModel.vehiculosNuevos.collectAsState(initial = 0)

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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

//@Preview
//@Composable
//fun DashboardScreenPreview() {
//    DashboardScreen()
//}