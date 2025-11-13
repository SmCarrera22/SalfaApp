package com.example.salfaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.salfaapp.domain.model.EstadoVehiculo
import com.example.salfaapp.domain.model.Sucursal
import com.example.salfaapp.domain.model.TipoVehiculo
import com.example.salfaapp.domain.model.data.config.AppDatabase
import com.example.salfaapp.domain.model.data.entities.VehiculoEntity
import com.example.salfaapp.ui.components.SalfaScaffold
import com.example.salfaapp.ui.theme.SalfaAppTheme
import kotlinx.coroutines.launch

@Composable
fun VehicleFormScreen(
    navController: NavController,
    onLogout: () -> Unit = {},
    onSubmit: (
        marca: String,
        modelo: String,
        anio: Int?,
        tipo: TipoVehiculo,
        patente: String,
        estado: EstadoVehiculo,
        sucursal: Sucursal,
        taller: String?,
        observaciones: String?
    ) -> Unit = { _, _, _, _, _, _, _, _, _ -> }
) {
    var marca by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var anio by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf(TipoVehiculo.SEDAN) }
    var patente by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf(EstadoVehiculo.Nuevo_Ingreso) }
    var sucursal by remember { mutableStateOf(Sucursal.Autopark) }
    var tallerAsignado by remember { mutableStateOf("") }
    var observaciones by remember { mutableStateOf("") }

    // ✅ Scope para corrutinas
    val coroutineScope = rememberCoroutineScope()

    SalfaScaffold(
        title = "Nuevo Vehículo",
        navController = navController,
        onLogout = onLogout
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = marca,
                onValueChange = { marca = it },
                label = { Text("Marca") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = modelo,
                onValueChange = { modelo = it },
                label = { Text("Modelo") },
                modifier = Modifier.fillMaxWidth()
            )

            // Campo Año: solo permite números
            OutlinedTextField(
                value = anio,
                onValueChange = { if (it.all { c -> c.isDigit() }) anio = it },
                label = { Text("Año") },
                modifier = Modifier.fillMaxWidth()
            )

            DropdownSelector(
                label = "Tipo de Vehículo",
                options = TipoVehiculo.values().toList(),
                selectedOption = tipo,
                onOptionSelected = { tipo = it }
            )

            OutlinedTextField(
                value = patente,
                onValueChange = { patente = it.uppercase() },
                label = { Text("Patente") },
                modifier = Modifier.fillMaxWidth()
            )

            DropdownSelector(
                label = "Estado del Vehículo",
                options = EstadoVehiculo.values().toList(),
                selectedOption = estado,
                onOptionSelected = { estado = it }
            )

            DropdownSelector(
                label = "Sucursal",
                options = Sucursal.values().toList(),
                selectedOption = sucursal,
                onOptionSelected = { sucursal = it }
            )

            OutlinedTextField(
                value = tallerAsignado,
                onValueChange = { tallerAsignado = it },
                label = { Text("Taller Asignado (opcional)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = observaciones,
                onValueChange = { observaciones = it },
                label = { Text("Observaciones (opcional)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val db = AppDatabase.getDatabase(navController.context)
                    val vehiculoDao = db.vehiculoDao()

                    val nuevoVehiculo = VehiculoEntity(
                        marca = marca,
                        modelo = modelo,
                        anio = anio.toIntOrNull(),
                        tipo = tipo,
                        patente = patente,
                        estado = estado,
                        sucursal = sucursal,
                        tallerAsignado = tallerAsignado.ifBlank { null },
                        observaciones = observaciones.ifBlank { null }
                    )

                    // ✅ Usar corrutina en lugar de LaunchedEffect
                    coroutineScope.launch {
                        vehiculoDao.insertVehiculo(nuevoVehiculo)
                        navController.popBackStack() // volver a la lista
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar Vehículo")
            }
        }
    }
}

@Composable
fun <T> DropdownSelector(
    label: String,
    options: List<T>,
    selectedOption: T,
    onOptionSelected: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(text = label, style = MaterialTheme.typography.labelMedium)
        Spacer(modifier = Modifier.height(4.dp))

        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(selectedOption.toString())
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.toString()) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VehicleFormScreenPreview() {
    val navController = rememberNavController()
    SalfaAppTheme {
        VehicleFormScreen(
            navController = navController,
            onLogout = {},
            onSubmit = { _, _, _, _, _, _, _, _, _ -> }
        )
    }
}
