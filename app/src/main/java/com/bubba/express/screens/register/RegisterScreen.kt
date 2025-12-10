package com.bubba.express.screens.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bubba.express.screens.login.LoginTextField
import com.bubba.express.screens.login.PrimaryButton
import com.bubba.express.ui.theme.CoffeePrimary
import com.bubba.express.ui.theme.CoffeeAccent
import com.bubba.express.ui.theme.ErrorRed

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onRegisterSuccess: () -> Unit,
    onBackToLogin: () -> Unit
) {
    val state = viewModel.uiState.collectAsState().value

    if (state.isSuccess) {
        onRegisterSuccess()
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(32.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Crear Cuenta",
                style = MaterialTheme.typography.headlineMedium,
                color = CoffeePrimary
            )

            Spacer(Modifier.height(32.dp))

            // Campo Nombre
            LoginTextField(
                value = state.nombre,
                onValueChange = { viewModel.onEvent(RegisterEvent.NombreChanged(it)) },
                label = "Nombre"
            )

            Spacer(Modifier.height(16.dp))

            LoginTextField(
                value = state.email,
                onValueChange = { viewModel.onEvent(RegisterEvent.EmailChanged(it)) },
                label = "Correo"
            )

            Spacer(Modifier.height(16.dp))


            LoginTextField(
                value = state.password,
                onValueChange = { viewModel.onEvent(RegisterEvent.PasswordChanged(it)) },
                label = "Contraseña"
            )


            if (state.error != null) {
                Text(
                    text = state.error!!,
                    color = ErrorRed,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(Modifier.height(32.dp))


            PrimaryButton(
                text = "Registrarme",
                loading = state.isLoading,
                onClick = { viewModel.onEvent(RegisterEvent.Submit) }
            )

            Spacer(Modifier.height(16.dp))


            TextButton(onClick = onBackToLogin) {
                Text(
                    "¿Ya tienes cuenta? Inicia sesión",
                    color = CoffeeAccent,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}