package com.example.salfaapp.domain.usecase

class ValidarLoginUseCase {

    private val validEmail = "scarrerao@salfa.cl"
    private val validPassword = "Onix.2025"

    fun execute(email: String, password: String): Boolean {
        return email == validEmail && password == validPassword
    }

}