package com.bubba.express.screens.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bubba.express.ui.theme.CoffeeAccent
import com.bubba.express.ui.theme.CoffeePrimary
import com.bubba.express.ui.theme.ErrorRed
import com.bubba.express.ui.theme.WhitePure

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    // Cuando el login sea exitoso:
    if (state.isLoggedIn) {
        onLoginSuccess()
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
        //nuevo titulo
            Text(
                text = "Bienvenido a\nLa Bubba Express",
                style = MaterialTheme.typography.headlineMedium,
                color = CoffeePrimary
            )

            Spacer(modifier = Modifier.height(32.dp))
            // Campo de correo
            LoginTextField(
                value = state.email,
                onValueChange = { viewModel.onEvent(LoginEvent.EmailChanged(it)) },
                label = "Correo",
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            LoginTextField(
                value = state.password,
                onValueChange = { viewModel.onEvent(LoginEvent.PasswordChanged(it)) },
                label = "Contraseña",
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                visualTransformation = PasswordVisualTransformation()
            )

            AnimatedVisibility(visible = state.error != null) {
                Text(
                    text = state.error ?: "",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { viewModel.onEvent(LoginEvent.Submit) },
                enabled = !state.isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CoffeePrimary,
                    contentColor = WhitePure
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        color = WhitePure,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text(
                        text = "Iniciar Sesión",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            TextButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = onNavigateToRegister
            ) {
                Text(
                    "¿No tienes cuenta? Regístrate aquí",
                    color = CoffeeAccent,
                    style = MaterialTheme.typography.bodyMedium
                )
            }


        }
    }
}
