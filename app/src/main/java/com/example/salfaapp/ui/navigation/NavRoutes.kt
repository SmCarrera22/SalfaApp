sealed class NavRoutes(val route: String) {

    object Dashboard : NavRoutes("dashboard")

    // Lista de vehículos
    object VehicleList : NavRoutes("vehicle_list")

    // Perfil del vehiculo
    object CarProfile : NavRoutes("car_profile/{vehiculoId}") {
        fun createRoute(vehiculoId: Long) = "car_profile/$vehiculoId"
    }

    // Lista de talleres
    object TallerList : NavRoutes("taller_list")

    // Perfil del taller
    object TallerProfile : NavRoutes("taller_profile/{tallerId}") {
        fun createRoute(tallerId: Int) = "taller_profile/$tallerId"
    }

    // Formularios
    object VehicleForm : NavRoutes("vehicle_form/{id}") {
        fun createRoute(id: Long?) = "vehicle_form/${id ?: -1}"
    }
    object TallerForm : NavRoutes("tallerForm")
}