package com.example.salfaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.salfaapp.domain.model.data.entities.TallerEntity
import com.example.salfaapp.ui.components.SalfaScaffold
import com.example.salfaapp.ui.viewModel.TallerViewModel

@Composable
fun TallerProfileScreen(
    navController: NavHostController,
    viewModel: TallerViewModel,
    tallerId: Int?,
    onLogout: () -> Unit = {}
) {
    // 1) Pedimos al ViewModel que cargue el taller según su ID
    LaunchedEffect(tallerId) {
        if (tallerId != null) {
            viewModel.cargarTaller(tallerId)
        }
    }

    // 2) Observamos el taller cargado
    val taller by viewModel.tallerSeleccionado.collectAsState()

    SalfaScaffold(
        title = "Ficha del Taller",
        navController = navController,
        onLogout = onLogout
    ) { innerPadding ->

        taller?.let { t ->
            TallerProfileContent(
                t = t,
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
            )

        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Cargando información del taller…")
        }
    }
}

@Composable
fun TallerProfileContent(
    t: TallerEntity,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        // ================================
        // CARD: INFORMACIÓN GENERAL
        // ================================
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(Modifier.padding(16.dp)) {

                Text(
                    text = t.nombre,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(Modifier.height(8.dp))

                DetailRow(label = "ID", value = t.id.toString())
                DetailRow(label = "RUT", value = "${t.rut}-${t.codigoVerificador}")
                DetailRow(label = "Tipo", value = t.tipo)
            }
        }

        Spacer(Modifier.height(24.dp))

        // ================================
        // CARD: DIRECCIÓN Y ENCARGADO
        // ================================
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(Modifier.padding(16.dp)) {

                Text(
                    text = "Información del Taller",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )

                Spacer(Modifier.height(12.dp))

                DetailRow("Dirección", t.direccion)
                DetailRow("Encargado", t.encargado)
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
        Text(value, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewTallerProfileScreen() {
    val nav = rememberNavController()

    // Se usa un objeto dummy SOLO para el preview (sin ViewModel real)
    val dummyTaller = TallerEntity(
        id = 1,
        nombre = "Taller Ejemplo",
        rut = 12345678,
        codigoVerificador = 9,
        tipo = "Mecánica",
        direccion = "Av. Siempre Viva 123",
        encargado = "Juan Pérez"
    )

    MaterialTheme {
        TallerProfileContent(t = dummyTaller)
    }
}