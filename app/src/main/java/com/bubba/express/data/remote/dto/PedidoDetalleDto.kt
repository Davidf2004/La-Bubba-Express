package com.bubba.express.data.remote.dto

data class PedidoDetalleDto(
    val id_pedido: Int,
    val id_usuario: Int,
    val total: Double,
    val estado: String,
    val detalles: List<PedidoItemDto>
)