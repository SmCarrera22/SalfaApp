package com.example.salfaapp.domain.model

class Empleado (
    val id: Int,
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val correo: String,
    val numero: Int,
    val cargo: String,
    val rol: Rol
) {
}