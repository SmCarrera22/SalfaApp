package com.example.salfaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.salfaapp.domain.model.data.config.AppDatabase
import com.example.salfaapp.domain.model.data.entities.VehiculoEntity
import com.example.salfaapp.ui.navigation.NavRoutes
import com.example.salfaapp.ui.theme.SalfaAppTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleListScreen(
    navController: NavController
) {
    val context = navController.context
    val db = remember { AppDatabase.getDatabase(context) }
    val vehiculoDao = remember { db.vehiculoDao() }

    // Estado que contendrá los vehículos obtenidos desde Room
    var vehiculos by remember { mutableStateOf<List<VehiculoEntity>>(emptyList()) }

    // Corrutina para recolectar el flujo de Room (Flow)
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        scope.launch {
            vehiculoDao.getAllVehiculos().collectLatest { lista ->
                vehiculos = lista
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Listado de Vehículos") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(NavRoutes.VehicleForm.route) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar vehículo",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        if (vehiculos.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text("No hay vehículos registrados todavía.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(8.dp)
            ) {
                items(vehiculos) { vehiculo ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        onClick = {
                            // Aquí puedes navegar a un detalle o acción con el vehículo
                        }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Patente: ${vehiculo.patente}  |  ${vehiculo.sucursal}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${vehiculo.marca} ${vehiculo.modelo} (${vehiculo.anio ?: "—"})",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Estado: ${vehiculo.estado}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VehicleListScreenPreview() {
    val navController = rememberNavController()
    SalfaAppTheme {
        VehicleListScreen(navController = navController)
    }
}
