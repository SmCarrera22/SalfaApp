package com.example.salfaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.rememberNavController
import com.example.salfaapp.domain.model.EstadoVehiculo
import com.example.salfaapp.domain.model.Sucursal
import com.example.salfaapp.domain.model.TipoVehiculo
import com.example.salfaapp.domain.model.Vehiculo
import com.example.salfaapp.domain.usecase.*
import com.example.salfaapp.ui.navigation.AppNavHost
import com.example.salfaapp.ui.screens.LoginScreen
import com.example.salfaapp.ui.theme.SalfaAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ---- Datos iniciales para pruebas ----
        val listaVehiculos = mutableListOf<Vehiculo>()

        val registrarVehiculo = RegistrarVehiculoUseCase(listaVehiculos)
        val actualizarEstado = ActualizarEstadoVehiculoUseCase(listaVehiculos)
        val asignarTaller = AsignarTallerUseCase(listaVehiculos)
        val listarPorEstado = ListarVehiculosPorEstadoUseCase(listaVehiculos)

        val v1 = Vehiculo(
            id = 1,
            marca = "Volkswagen",
            modelo = "Golf MK7",
            anio = 2018,
            tipo = TipoVehiculo.HATCHBACK,
            patente = "KVVZ63",
            estado = EstadoVehiculo.Nuevo_Ingreso,
            sucursal = Sucursal.Autopark,
            observaciones = "Nuevo ingreso"
        )

        val v2 = Vehiculo(
            id = 2,
            marca = "Chery",
            modelo = "Tiggo 7 Pro",
            anio = 2022,
            tipo = TipoVehiculo.SUV,
            patente = "RRLX22",
            estado = EstadoVehiculo.Pendiente_Revision,
            sucursal = Sucursal.Movicenter,
            observaciones = "Nuevo Ingreso"
        )

        registrarVehiculo(v1)
        registrarVehiculo(v2)

        enableEdgeToEdge()
        setContent {
            SalfaAppTheme {
                SalfaAppApp(listaVehiculos)
            }
        }
    }
}

@Composable
fun SalfaAppApp(listaVehiculos: List<Vehiculo>) {
    var isLoggedIn by rememberSaveable { mutableStateOf(false) }

    if (!isLoggedIn) {
        // Pantalla de Login
        LoginScreen(
            onLoginSuccess = { isLoggedIn = true }
        )
    } else {
        // Navegación entre pantallas después del login
        val navController = rememberNavController()

        AppNavHost(
            navController = navController,
            onLogout = { isLoggedIn = false },
            vehiculos = listaVehiculos
        )
    }
}
