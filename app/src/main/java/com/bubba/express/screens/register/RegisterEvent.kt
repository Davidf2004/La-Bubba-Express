package com.bubba.express.screens.register

sealed class RegisterEvent {
    data class NombreChanged(val value: String) : RegisterEvent()
    data class EmailChanged(val value: String) : RegisterEvent()
    data class PasswordChanged(val value: String) : RegisterEvent()
    object Submit : RegisterEvent()
}
