package com.bubba.express.data.remote.dto

data class PedidoItemDto(
    val id_detalle_pedido: Int,
    val id_pedido: Int,
    val id_producto: Int,
    val cantidad: Int,
    val nombre_producto: String,
    val precio_unitario: Double,
    val subtotal: Double
)