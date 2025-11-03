package com.example.salfaapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.salfaapp.ui.components.SalfaScaffold

@Composable
fun CarProfileScreen(
    patente: String = "KVVZ63",
    sucursal: String = "Santiago",
    marca: String = "Volkswagen",
    modelo: String = "Golf MK7",
    tipo: String = "Hatchback",
    anio: Int = 2018,
    version: String = "1.4 Sport Plus",
    transmision: String = "Automática",
    traccion: String = "Delantera",
    regimen: String = "Gasolina",
    fechaIngreso: String = "12/03/2023",
    onLogout: () -> Unit = {}
) {
    SalfaScaffold(title = "Salfa") { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Tarjeta superior con datos básicos ---
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "$patente / $sucursal",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$marca $modelo",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- Tarjeta inferior con ficha del vehículo ---
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
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

                    VehicleDetailItem("Tipo", tipo)
                    VehicleDetailItem("Año", anio.toString())
                    VehicleDetailItem("Versión", version)
                    VehicleDetailItem("Transmisión", transmision)
                    VehicleDetailItem("Tracción", traccion)
                    VehicleDetailItem("Régimen", regimen)
                    VehicleDetailItem("Fecha Ingreso SAP", fechaIngreso)
                }
            }
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

@Preview(showBackground = true)
@Composable
fun CarProfileScreenPreview() {
    CarProfileScreen()
}
