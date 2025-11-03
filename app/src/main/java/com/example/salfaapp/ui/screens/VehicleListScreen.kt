package com.example.salfaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.salfaapp.domain.model.Vehiculo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleListScreen(
    vehiculos: List<Vehiculo>,
    onVehiculoClick: (Vehiculo) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Listado de Vehículos") }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            items(vehiculos) { vehiculo ->
                Card(
                    onClick = { onVehiculoClick(vehiculo) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Patente: ${vehiculo.patente}   |   ${vehiculo.sucursal}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${vehiculo.marca} ${vehiculo.modelo}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun VehiculoItem(x0: String, x1: String, x2: String, x3: String) {
    TODO("Not yet implemented")
}
