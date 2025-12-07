package com.bubba.express.data.remote.dto

data class ProductoDto(
    val id_producto: Int,
    val nombre: String,
    val descripcion: String?,
    val precio: Double,
    val disponible: Int,
    val categoria: String?
)