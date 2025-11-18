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
import com.example.salfaapp.domain.model.data.config.AppDatabase
import com.example.salfaapp.domain.model.data.entities.TallerEntity
import com.example.salfaapp.ui.components.SalfaScaffold

@Composable
fun TallerProfileScreen(
    navController: NavHostController,
    tallerId: Int?,
    onLogout: () -> Unit = {}
) {
    val context = navController.context
    val db = remember { AppDatabase.getDatabase(context) }
    val tallerDao = remember { db.tallerDao() }

    var taller by remember { mutableStateOf<TallerEntity?>(null) }

    // Cargar taller desde Room al entrar al screen
    LaunchedEffect(tallerId) {
        if (tallerId != null) {
            taller = tallerDao.getTallerById(tallerId)
        }
    }

    SalfaScaffold(
        title = "Ficha del Taller",
        navController = navController,
        onLogout = onLogout
    ) { innerPadding ->

        taller?.let { t ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
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

        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Cargando información del taller…")
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

@Preview(showBackground = true)
@Composable
fun PreviewTallerProfileScreen() {
    val nav = rememberNavController()
    val dummy = 1

    TallerProfileScreen(
        navController = nav,
        tallerId = dummy,
        onLogout = {}
    )
}