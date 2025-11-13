package com.example.salfaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.salfaapp.domain.model.data.config.AppDatabase
import com.example.salfaapp.domain.model.data.entities.VehiculoEntity
import com.example.salfaapp.ui.components.SalfaScaffold
import kotlinx.coroutines.launch

@Composable
fun CarProfileScreen(
    navController: NavHostController,
    vehiculoId: Long?,
    onLogout: () -> Unit = {}
) {
    val context = navController.context
    val db = remember { AppDatabase.getDatabase(context) }
    val vehiculoDao = remember { db.vehiculoDao() }
    var vehiculo by remember { mutableStateOf<VehiculoEntity?>(null) }
    val scope = rememberCoroutineScope()

    // Cargar el vehículo desde la base de datos
    LaunchedEffect(vehiculoId) {
        if (vehiculoId != null) {
            scope.launch {
                vehiculo = vehiculoDao.getVehiculoById(vehiculoId)
            }
        }
    }

    SalfaScaffold(
        title = "Ficha del Vehículo",
        navController = navController,
        onLogout = onLogout
    ) { innerPadding ->
        vehiculo?.let { v ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Patente: ${v.patente} / ${v.sucursal}",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "${v.marca} ${v.modelo}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Ficha Vehículo",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        VehicleDetailItem("Año", v.anio?.toString() ?: "—")
                        VehicleDetailItem("Tipo", v.tipo.name)
                        VehicleDetailItem("Estado", v.estado.name)
                        VehicleDetailItem("Sucursal", v.sucursal.name)
                        VehicleDetailItem("Taller", v.tallerAsignado ?: "—")
                        VehicleDetailItem("Observaciones", v.observaciones ?: "—")
                    }
                }
            }
        } ?: Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text("Cargando información del vehículo…")
        }
    }
}

@Composable
fun VehicleDetailItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

