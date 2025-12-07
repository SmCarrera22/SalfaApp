package com.example.salfaapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.salfaapp.domain.model.data.entities.VehiculoEntity
import com.example.salfaapp.ui.viewModel.VehiculoViewModel

@Composable
fun VehicleListScreen(
    viewModel: VehiculoViewModel,
    onAddVehicle: () -> Unit,
    onVehicleSelected: (Long) -> Unit
) {
    val vehiculos by viewModel.vehiculos.collectAsState(initial = emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddVehicle) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Vehículo")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {

            Text(
                text = "Vehículos Registrados",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp)
            )

            if (vehiculos.isEmpty()) {
                Text(
                    text = "No hay vehículos registrados",
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 12.dp)
                ) {
                    items(vehiculos) { vehiculo ->
                        VehicleItem(
                            vehiculo = vehiculo,
                            onClick = { onVehicleSelected(vehiculo.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun VehicleItem(
    vehiculo: VehiculoEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() }
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "Patente: ${vehiculo.patente}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = "${vehiculo.marca} ${vehiculo.modelo}")
            Text(text = "Sucursal: ${vehiculo.sucursal}")
            Text(text = "Estado: ${vehiculo.estado}")
        }
    }
}