package com.example.salfaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.salfaapp.domain.model.Vehiculo
import com.example.salfaapp.ui.navigation.NavRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleListScreen(
    vehiculos: List<Vehiculo>,
    onVehiculoClick: (Vehiculo) -> Unit,
    navController: NavController
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Listado de Vehículos") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("vehicleForm") }, // 👈 ruta hacia el formulario
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

@Preview(showBackground = true)
@Composable
fun VehicleListScreenPreview() {
    // Simulamos un NavController
    val navController = androidx.navigation.compose.rememberNavController()

    // Lista de prueba
    val sampleVehiculos = listOf(
        Vehiculo(
            id = 1,
            marca = "Volkswagen",
            modelo = "Golf",
            anio = 2018,
            tipo = com.example.salfaapp.domain.model.TipoVehiculo.HATCHBACK,
            patente = "KVVZ63",
            estado = com.example.salfaapp.domain.model.EstadoVehiculo.Disponible,
            sucursal = com.example.salfaapp.domain.model.Sucursal.Movicenter,
            tallerAsignado = "Taller Central",
            observaciones = "Listo para entrega"
        ),
        Vehiculo(
            id = 2,
            marca = "Toyota",
            modelo = "Corolla",
            anio = 2021,
            tipo = com.example.salfaapp.domain.model.TipoVehiculo.SEDAN,
            patente = "ABC123",
            estado = com.example.salfaapp.domain.model.EstadoVehiculo.Pendiente_Revision,
            sucursal = com.example.salfaapp.domain.model.Sucursal.Concepcion,
            tallerAsignado = null,
            observaciones = null
        )
    )

    VehicleListScreen(
        vehiculos = sampleVehiculos,
        onVehiculoClick = {},
        navController = navController
    )
}
