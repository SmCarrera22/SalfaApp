package com.example.salfaapp.ui.screens
import com.example.salfaapp.ui.viewModel.TallerViewModel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.salfaapp.domain.model.data.entities.TallerEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun TallerListScreen(
    viewModel: TallerViewModel,
    onAddTaller: () -> Unit,
    onTallerSelected: (Int) -> Unit // para editar/ver detalle
) {
    val talleres by viewModel.talleres.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTaller) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Taller")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {

            Text(
                text = "Talleres Registrados",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp)
            )

            if (talleres.isEmpty()) {
                Text(
                    text = "No hay talleres registrados",
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(talleres) { taller ->
                        TallerItem(
                            taller = taller,
                            onClick = { onTallerSelected(taller.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TallerItem(
    taller: TallerEntity,
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

            Text(text = taller.nombre, style = MaterialTheme.typography.titleMedium)
            Text(text = "Rut: ${taller.rut}-${taller.codigoVerificador}")
            Text(text = "Tipo: ${taller.tipo}")
            Text(text = "Encargado: ${taller.encargado}")
            Text(text = "Dirección: ${taller.direccion}")
        }
    }
}