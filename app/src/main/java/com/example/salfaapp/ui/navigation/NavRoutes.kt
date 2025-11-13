package com.example.salfaapp.ui.navigation

sealed class NavRoutes(val route: String) {
    object Dashboard : NavRoutes("dashboard")
    object VehicleList : NavRoutes("vehicle_list")
    object CarProfile : NavRoutes("car_profile")
    object VehicleForm : NavRoutes("vehicleForm")
}
