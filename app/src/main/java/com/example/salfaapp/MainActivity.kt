package com.example.salfaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.example.salfaapp.domain.model.EstadoVehiculo
import com.example.salfaapp.domain.model.Vehiculo
import com.example.salfaapp.domain.usecase.ActualizarEstadoVehiculoUseCase
import com.example.salfaapp.domain.usecase.AsignarTallerUseCase
import com.example.salfaapp.domain.usecase.ListarVehiculosPorEstadoUseCase
import com.example.salfaapp.domain.usecase.RegistrarVehiculoUseCase
import com.example.salfaapp.ui.theme.SalfaAppTheme
import com.example.salfaapp.ui.screens.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val listaVehiculos = mutableListOf<Vehiculo>()

        val registrarVehiculo = RegistrarVehiculoUseCase(listaVehiculos)
        val actualizarEstado = ActualizarEstadoVehiculoUseCase(listaVehiculos)
        val asignarTaller = AsignarTallerUseCase(listaVehiculos)
        val listarPorEstado = ListarVehiculosPorEstadoUseCase(listaVehiculos)

        // --- Pruebas de CRUD ---
        val v1 = Vehiculo(1, "Volkswagen", "Golf MK7", 2018, "Hatchback", "KVVZ63", EstadoVehiculo.Nuevo_Ingreso, "Nuevo ingreso")

        val v2 = Vehiculo(2, "Chery", "Tiggo 7 Pro", 2022, "Suv", "RRLX22", EstadoVehiculo.Pendiente_Revision, "Nuevo Ingreso")

        registrarVehiculo(v1)
        registrarVehiculo(v2)
        print("Vehículos registrados:")
        listaVehiculos.forEach { println(it) }

        actualizarEstado(1, EstadoVehiculo.Espera_Taller_Mecanico)
        println("Después de actualizar estado:")
        listaVehiculos.forEach { println(it) }

        asignarTaller(1, "Taller Mecánico Central")
        println("Después de asignar taller:")
        listaVehiculos.forEach { println(it) }

        val enRevision = listarPorEstado(EstadoVehiculo.Pendiente_Revision)
        println("Vehículos en revisión:")
        enRevision.forEach { println(it) }

        enableEdgeToEdge()
        setContent {
            SalfaAppTheme {
                SalfaAppApp()
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun SalfaAppApp() {
    var isLoggedIn by rememberSaveable { mutableStateOf(false) }

    if (!isLoggedIn) {
        // Mostramos la pantalla de login
        LoginScreen(
            onLoginSuccess = {
                isLoggedIn = true
            }
        )
    } else {
        // Cuando el usuario inicia sesión, mostramos el dashboard
        var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

        NavigationSuiteScaffold(
            navigationSuiteItems = {
                AppDestinations.entries.forEach {
                    item(
                        icon = {
                            Icon(
                                it.icon,
                                contentDescription = it.label
                            )
                        },
                        label = { Text(it.label) },
                        selected = it == currentDestination,
                        onClick = { currentDestination = it }
                    )
                }
            }
        ) {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Greeting(
                    name = "Android",
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}


enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
) {
    HOME("Home", Icons.Default.Home),
    FAVORITES("Favorites", Icons.Default.Favorite),
    PROFILE("Profile", Icons.Default.AccountBox),
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SalfaAppTheme {
        Greeting("Android")
    }
}