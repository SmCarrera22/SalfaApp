package com.example.salfaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.salfaapp.domain.model.EstadoVehiculo
import com.example.salfaapp.domain.model.Sucursal
import com.example.salfaapp.domain.model.TipoVehiculo
import com.example.salfaapp.domain.model.data.entities.VehiculoEntity
import com.example.salfaapp.ui.viewModel.VehiculoViewModel

@Composable
fun VehicleFormScreen(
    navController: NavController,
    viewModel: VehiculoViewModel,
    vehiculoId: Long?,
    paddingValues: PaddingValues,
    onLogout: () -> Unit = {}
) {
    val vehiculo = viewModel.vehiculoSeleccionado.collectAsState().value

    // Si es edición, cargar los datos solo una vez
    LaunchedEffect(vehiculoId) {
        if (vehiculoId != null && vehiculoId != -1L) {
            viewModel.cargarVehiculo(vehiculoId.toInt())
        }
    }

    var marca by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var anio by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf(TipoVehiculo.SEDAN) }
    var patente by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf(EstadoVehiculo.Nuevo_Ingreso) }
    var sucursal by remember { mutableStateOf(Sucursal.Autopark) }
    var tallerAsignado by remember { mutableStateOf("") }
    var observaciones by remember { mutableStateOf("") }

    // Setear valores cuando el ViewModel cargue los datos
    LaunchedEffect(vehiculo) {
        vehiculo?.let {
            marca = it.marca
            modelo = it.modelo
            anio = it.anio?.toString() ?: ""
            tipo = it.tipo
            patente = it.patente
            estado = it.estado
            sucursal = it.sucursal
            tallerAsignado = it.tallerAsignado ?: ""
            observaciones = it.observaciones ?: ""
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
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
                val filtered = input.uppercase().filter { it.isLetterOrDigit() }
                if (filtered.length <= 6) patente = filtered
            },
            label = { Text("Patente") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = {
                if (patente.length in 1..5) Text("Debe tener 6 caracteres")
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
                val anioInt = anio.toIntOrNull()

                val entity = VehiculoEntity(
                    id = vehiculo?.id ?: 0L,
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

                if (vehiculoId == null || vehiculoId == -1L)
                    viewModel.insertarVehiculo(entity)
                else
                    viewModel.actualizarVehiculo(entity)

                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (vehiculoId == null || vehiculoId == -1L) "Registrar Vehículo" else "Guardar Cambios")
        }
    }
}