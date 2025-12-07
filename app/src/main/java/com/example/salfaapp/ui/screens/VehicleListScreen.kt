package com.example.salfaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.salfaapp.ui.viewModel.VehiculoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleListScreen(
    navController: NavController,
    viewModel: VehiculoViewModel
) {
    val vehiculos by viewModel.vehiculos.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Listado de Vehículos") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(
                        NavRoutes.VehicleForm.createRoute(-1L)  // crear vehículo
                    )
                },
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
                contentAlignment = Alignment.Center
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
                            navController.navigate(
                                NavRoutes.CarProfile.createRoute(vehiculo.id)
                            )
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