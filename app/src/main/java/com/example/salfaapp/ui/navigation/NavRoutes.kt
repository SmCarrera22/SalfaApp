sealed class NavRoutes(val route: String) {
    object Dashboard : NavRoutes("dashboard")
    object VehicleList : NavRoutes("vehicle_list")
    object CarProfile : NavRoutes("car_profile/{vehiculoId}") {
        fun createRoute(vehiculoId: Long) = "car_profile/$vehiculoId"
    }
    object VehicleForm : NavRoutes("vehicleForm")
}
