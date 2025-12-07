package com.bubba.express.data.remote.dto

data class CrearPedidoRequestDto(
    val id_usuario: Int,
    val productos: List<CrearPedidoProductoDto>
)