package com.example.salfaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.salfaapp.domain.model.data.entities.TallerEntity
import com.example.salfaapp.ui.viewModel.TallerViewModel


@Composable
fun TallerFormScreen(
    viewModel: TallerViewModel,
    onSaved: () -> Unit,
    paddingValues: PaddingValues
) {
    var nombre by remember { mutableStateOf("") }
    var rut by remember { mutableStateOf("") }
    var dv by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var encargado by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre Taller") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = rut,
            onValueChange = { rut = it },
            label = { Text("RUT") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = dv,
            onValueChange = { dv = it },
            label = { Text("DV") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = tipo,
            onValueChange = { tipo = it },
            label = { Text("Tipo Taller") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = direccion,
            onValueChange = { direccion = it },
            label = { Text("Dirección") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = encargado,
            onValueChange = { encargado = it },
            label = { Text("Encargado") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.agregarTaller(
                    TallerEntity(
                        nombre = nombre,
                        rut = rut.toIntOrNull() ?: 0,
                        codigoVerificador = dv.toIntOrNull() ?: 0,
                        tipo = tipo,
                        direccion = direccion,
                        encargado = encargado
                    )
                )
                onSaved()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Taller")
        }
    }
}