package com.bubba.express.screens.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bubba.express.data.repository.OrderRepository
import com.bubba.express.domain.model.CartItem
import com.bubba.express.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : ViewModel() {

    private val _state = MutableStateFlow(OrderState())
    val state: StateFlow<OrderState> = _state.asStateFlow()

    fun onEvent(event: OrderEvent) {
        when (event) {
            is OrderEvent.AddToCart -> addToCart(event.product)
            is OrderEvent.RemoveFromCart -> removeFromCart(event.item)
            is OrderEvent.UpdateQuantity -> updateQuantity(event.item, event.newQuantity)
            is OrderEvent.ConfirmOrder -> confirmOrder(event.usuarioId)
            is OrderEvent.ClearCart -> clearCart()
        }
    }
