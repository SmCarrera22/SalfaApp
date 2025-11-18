package com.example.salfaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.salfaapp.domain.model.EstadoVehiculo
import com.example.salfaapp.domain.model.data.config.AppDatabase
import com.example.salfaapp.domain.model.data.entities.VehiculoEntity
import com.example.salfaapp.domain.model.data.entities.VehiculoMovimientoEntity
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
    val movimientoDao = remember { db.movimientoDao() }

    var vehiculo by remember { mutableStateOf<VehiculoEntity?>(null) }
    var historial by remember { mutableStateOf<List<VehiculoMovimientoEntity>>(emptyList()) }

    val scope = rememberCoroutineScope()

    // Estado seleccionado en el dropdown
    var estadoSeleccionado by remember { mutableStateOf<EstadoVehiculo?>(null) }
    var menuExpandido by remember { mutableStateOf(false) }
    var mensajeActualizado by remember { mutableStateOf(false) }

    // ============================
    // Cargar vehículo y su historial
    // ============================
    LaunchedEffect(vehiculoId) {
        if (vehiculoId != null) {

            vehiculo = vehiculoDao.getVehiculoById(vehiculoId)
            estadoSeleccionado = vehiculo?.estado

            // RECOLECTAR FLOW DE HISTORIAL
            launch {
                movimientoDao.getMovimientosByVehiculo(vehiculoId).collect { lista ->
                    historial = lista
                }
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
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // =========================
                // === CARD: FICHA BÁSICA ===
                // =========================
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

                // =========================
                // === CARD: FICHA COMPLETA ==
                // =========================
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

                Spacer(modifier = Modifier.height(24.dp))

                // ================================================
                // ===== CARD: CAMBIO DE ESTADO DEL VEHÍCULO ======
                // ================================================
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Estado del Vehículo",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold, fontSize = 20.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Dropdown
                        Box {
                            OutlinedButton(
                                onClick = { menuExpandido = true },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(text = estadoSeleccionado?.name ?: "Seleccionar Estado")
                            }

                            DropdownMenu(
                                expanded = menuExpandido,
                                onDismissRequest = { menuExpandido = false }
                            ) {
                                EstadoVehiculo.values().forEach { estado ->
                                    DropdownMenuItem(
                                        text = { Text(estado.name) },
                                        onClick = {
                                            estadoSeleccionado = estado
                                            menuExpandido = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Botón Actualizar
                        Button(
                            onClick = {
                                scope.launch {
                                    if (estadoSeleccionado != null) {

                                        // 1) Actualizamos vehículo
                                        val actualizado = v.copy(estado = estadoSeleccionado!!)
                                        vehiculoDao.updateVehiculo(actualizado)
                                        vehiculo = actualizado

                                        // 2) Registramos movimiento
                                        val movimiento = VehiculoMovimientoEntity(
                                            vehiculoId = v.id,
                                            estadoAnterior = v.estado,
                                            estadoNuevo = estadoSeleccionado!!,
                                            fechaHora = System.currentTimeMillis()
                                        )
                                        movimientoDao.insertMovimiento(movimiento)

                                        mensajeActualizado = true
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Actualizar")
                        }

                        if (mensajeActualizado) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                "Estado actualizado correctamente",
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // =================================
                // === CARD: HISTORIAL MOVIMIENTOS ==
                // =================================
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Historial de Movimientos",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        if (historial.isEmpty()) {
                            Text("Sin movimientos registrados.")
                        } else {
                            historial.forEach { mov ->
                                Column(modifier = Modifier.padding(vertical = 6.dp)) {
                                    Text("• Estado: ${mov.estadoNuevo}")
                                    Text(
                                        "Fecha: ${
                                            java.text.SimpleDateFormat("dd/MM/yyyy HH:mm")
                                                .format(mov.fechaHora)
                                        }",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                Divider()
                            }
                        }
                    }
                }
            }
        }
            ?: Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Cargando información del vehículo…")
            }
    }
}

@Composable
fun VehicleDetailItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium
            )
        )
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}