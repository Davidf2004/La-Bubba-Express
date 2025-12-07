package com.bubba.express.data.remote.dto

data class UsuarioDto(
    val id_usuario: Int,
    val nombre: String,
    val email: String,
    val contrasenia: String?
)