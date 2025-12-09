package com.bubba.express.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bubba.express.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.NombreChanged ->
                _uiState.value = _uiState.value.copy(nombre = event.value)

            is RegisterEvent.EmailChanged ->
                _uiState.value = _uiState.value.copy(email = event.value)

            is RegisterEvent.PasswordChanged ->
                _uiState.value = _uiState.value.copy(password = event.value)

            RegisterEvent.Submit -> register()
        }
    }

    private fun register() {
        val state = _uiState.value

        if (state.nombre.isBlank() || state.email.isBlank() || state.password.isBlank()) {
            _uiState.value = state.copy(error = "Completa todos los campos")
            return
        }

        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true, error = null)

            val result = repository.register(
                nombre = state.nombre,
                email = state.email,
                password = state.password
            )

            result.onSuccess {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isSuccess = true
                )
            }

            result.onFailure { e ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error al registrar"
                )
            }
        }
    }
}
