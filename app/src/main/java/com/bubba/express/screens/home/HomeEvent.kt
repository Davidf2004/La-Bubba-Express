package com.bubba.express.screens.home

sealed class HomeEvent {
    object LoadProducts : HomeEvent()
    data class OnProductClick(val productId: Int) : HomeEvent()

}