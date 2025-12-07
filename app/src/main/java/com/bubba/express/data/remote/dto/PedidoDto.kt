package com.bubba.express.data.remote.dto

data class PedidoDto(
    val id_pedido: Int,
    val id_usuario: Int,
    val total: Double,
    val estado: String
)