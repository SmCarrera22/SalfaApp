package com.example.salfaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
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
                onValueChange = { input ->

                    // Filtrar solo letras y números
                    val filtered = input
                        .uppercase()
                        .filter { it.isLetterOrDigit() }

                    // Limitar a 6 caracteres
                    if (filtered.length <= 6) {
                        patente = filtered
                    }
                },
                label = { Text("Patente") },
                modifier = Modifier.fillMaxWidth(),
                supportingText = {
                    if (patente.length < 6)
                        Text("Debe tener 6 caracteres alfanuméricos")
                }
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
                    // --- VALIDACIÓN DE PATENTE ---
                    val patenteRegex = Regex("^[A-Z0-9]{1,6}$")

                    if (!patenteRegex.matches(patente)) {
                        // Puedes usar Snackbar, diálogo o imprimir error.
                        // Ejemplo con Log:
                        println("Patente inválida: Debe tener máx 6 caracteres, solo letras y números.")
                        return@Button // ❌ Detiene el proceso y no guarda
                    }

                    // --- VALIDACIÓN DE AÑO ---
                    val anioInt = anio.toIntOrNull()
                    if (anio.isNotBlank() && anioInt == null) {
                        println("El año debe ser numérico.")
                        return@Button
                    }

                    // --- SI TODO OK, PROCEDER A GUARDAR ---
                    val db = AppDatabase.getDatabase(navController.context)
                    val vehiculoDao = db.vehiculoDao()

                    val nuevoVehiculo = VehiculoEntity(
                        marca = marca,
                        modelo = modelo,
                        anio = anioInt,
                        tipo = tipo,
                        patente = patente,
                        estado = estado,
                        sucursal = sucursal,
                        tallerAsignado = tallerAsignado.ifBlank { null },
                        observaciones = observaciones.ifBlank { null }
                    )

                    coroutineScope.launch {
                        vehiculoDao.insertVehiculo(nuevoVehiculo)
                        navController.popBackStack()
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
