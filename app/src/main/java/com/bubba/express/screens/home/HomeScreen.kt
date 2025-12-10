package com.bubba.express.screens.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.LocalCafe
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bubba.express.domain.model.Product
import com.bubba.express.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onProductClick: (Int) -> Unit,
    onCartClick: () -> Unit,
    onProfileClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    var showProfileMenu by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }


    val categories = remember(state.products) {
        state.products.map { it.categoria }.distinct()
    }


    val filteredProducts = remember(state.products, selectedCategory) {
        if (selectedCategory == null) {
            state.products
        } else {
            state.products.filter { it.categoria == selectedCategory }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "La Bubba Express",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = CoffeePrimary,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            "Café de especialidad",
                            fontSize = 13.sp,
                            color = GrayMedium,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 0.3.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = WhitePure
                ),
                actions = {

                    IconButton(
                        onClick = onCartClick,
                        modifier = Modifier.padding(end = 4.dp)
                    ) {
                        BadgedBox(
                            badge = {
                                if (state.cartItemCount > 0) {
                                    Badge(
                                        containerColor = CoffeePrimary,
                                        modifier = Modifier
                                            .offset(x = (-4).dp, y = 4.dp)
                                            .animateContentSize()
                                    ) {
                                        Text(
                                            "${state.cartItemCount}",
                                            color = WhitePure,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = CoffeePrimary.copy(alpha = 0.15f),
                                modifier = Modifier.size(42.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ShoppingCart,
                                    contentDescription = "Carrito",
                                    tint = CoffeePrimary,
                                    modifier = Modifier.padding(9.dp)
                                )
                            }
                        }
                    }

                    // Perfil mejorado
                    Box(modifier = Modifier.padding(end = 8.dp)) {
                        IconButton(onClick = { showProfileMenu = true }) {
                            Surface(
                                shape = CircleShape,
                                color = CoffeePrimary,
                                modifier = Modifier.size(42.dp),
                                shadowElevation = 2.dp
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = "Perfil",
                                    tint = WhitePure,
                                    modifier = Modifier.padding(7.dp)
                                )
                            }
                        }

                        DropdownMenu(
                            expanded = showProfileMenu,
                            onDismissRequest = { showProfileMenu = false },
                            modifier = Modifier
                                .background(WhitePure, RoundedCornerShape(16.dp))
                                .width(250.dp)
                        ) {
                            // Header mejorado con más estilo
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(
                                                CoffeePrimary.copy(alpha = 0.2f),
                                                CoffeePrimary.copy(alpha = 0.05f)
                                            )
                                        )
                                    )
                                    .padding(20.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Surface(
                                        shape = CircleShape,
                                        color = CoffeePrimary,
                                        modifier = Modifier.size(48.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(
                                                text = state.currentUser?.nombre?.firstOrNull()?.uppercase() ?: "U",
                                                color = WhitePure,
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                    Column {
                                        Text(
                                            text = state.currentUser?.nombre ?: "Usuario",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = Black,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = state.currentUser?.email ?: "",
                                            fontSize = 12.sp,
                                            color = GrayMedium,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.padding(top = 2.dp)
                                        )
                                    }
                                }
                            }

                            Divider(color = GrayLight, thickness = 1.dp)

                            // Opciones del menú con más detalle
                            DropdownMenuItem(
                                text = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        Surface(
                                            shape = CircleShape,
                                            color = CoffeePrimary.copy(alpha = 0.12f),
                                            modifier = Modifier.size(40.dp)
                                        ) {
                                            Icon(
                                                Icons.Outlined.Person,
                                                contentDescription = null,
                                                tint = CoffeePrimary,
                                                modifier = Modifier.padding(10.dp)
                                            )
                                        }
                                        Column {
                                            Text(
                                                "Mi Perfil",
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = Black
                                            )
                                            Text(
                                                "Ver información personal",
                                                fontSize = 11.sp,
                                                color = GrayMedium
                                            )
                                        }
                                    }
                                },
                                onClick = {
                                    showProfileMenu = false
                                    onProfileClick()
                                },
                                modifier = Modifier.padding(vertical = 6.dp, horizontal = 8.dp)
                            )

                            DropdownMenuItem(
                                text = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        Surface(
                                            shape = CircleShape,
                                            color = CoffeePrimary.copy(alpha = 0.12f),
                                            modifier = Modifier.size(40.dp)
                                        ) {
                                            Icon(
                                                Icons.Outlined.History,
                                                contentDescription = null,
                                                tint = CoffeePrimary,
                                                modifier = Modifier.padding(10.dp)
                                            )
                                        }
                                        Column {
                                            Text(
                                                "Mis Pedidos",
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = Black
                                            )
                                            Text(
                                                "Historial de compras",
                                                fontSize = 11.sp,
                                                color = GrayMedium
                                            )
                                        }
                                    }
                                },
                                onClick = {
                                    showProfileMenu = false
                                    onHistoryClick()
                                },
                                modifier = Modifier.padding(vertical = 6.dp, horizontal = 8.dp)
                            )

                            Divider(
                                color = GrayLight,
                                thickness = 1.dp,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )

                            DropdownMenuItem(
                                text = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        Surface(
                                            shape = CircleShape,
                                            color = ErrorRed.copy(alpha = 0.12f),
                                            modifier = Modifier.size(40.dp)
                                        ) {
                                            Icon(
                                                Icons.Outlined.Logout,
                                                contentDescription = null,
                                                tint = ErrorRed,
                                                modifier = Modifier.padding(10.dp)
                                            )
                                        }
                                        Column {
                                            Text(
                                                "Cerrar Sesión",
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = ErrorRed
                                            )
                                            Text(
                                                "Salir de la cuenta",
                                                fontSize = 11.sp,
                                                color = ErrorRed.copy(alpha = 0.7f)
                                            )
                                        }
                                    }
                                },
                                onClick = {
                                    showProfileMenu = false
                                    onLogoutClick()
                                },
                                modifier = Modifier.padding(vertical = 6.dp, horizontal = 8.dp)
                            )
                        }
                    }
                }
            )
        },
        containerColor = CreamBackground
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                state.isLoading -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            color = CoffeePrimary,
                            strokeWidth = 3.dp,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Cargando productos...",
                            color = GrayMedium,
                            fontSize = 14.sp
                        )
                    }
                }
                state.error != null -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = ErrorRed.copy(alpha = 0.1f),
                            modifier = Modifier.size(100.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(text = "😔", fontSize = 56.sp)
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "Oops!",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Error al cargar productos",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = GrayDark
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = state.error ?: "",
                            color = ErrorRed,
                            textAlign = TextAlign.Center,
                            fontSize = 13.sp
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = { viewModel.onEvent(HomeEvent.LoadProducts) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CoffeePrimary
                            ),
                            shape = RoundedCornerShape(14.dp),
                            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 14.dp)
                        ) {
                            Text(
                                "Reintentar",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
                else -> {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .shadow(8.dp, RoundedCornerShape(24.dp)),
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Transparent
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        brush = Brush.linearGradient(
                                            colors = listOf(
                                                CoffeePrimary,
                                                CoffeeSecondary,
                                                CoffeePrimary.copy(alpha = 0.8f)
                                            )
                                        )
                                    )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(28.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            "¡Hola, ${state.currentUser?.nombre?.split(" ")?.firstOrNull() ?: "de nuevo"}!",
                                            fontSize = 28.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = WhitePure,
                                            letterSpacing = 0.5.sp
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            "Descubre nuestros mejores cafés",
                                            fontSize = 15.sp,
                                            color = WhitePure.copy(alpha = 0.95f),
                                            lineHeight = 20.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))

                                    }
                                    Box(
                                        modifier = Modifier
                                            .size(80.dp)
                                            .clip(CircleShape)
                                            .background(WhitePure.copy(alpha = 0.25f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("☕", fontSize = 44.sp)
                                    }
                                }
                            }
                        }

                        // Filtros de categorías
                        if (categories.isNotEmpty()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                // Botón "Todos"
                                FilterChip(
                                    selected = selectedCategory == null,
                                    onClick = { selectedCategory = null },
                                    label = {
                                        Text(
                                            "Todos",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Outlined.Fastfood,
                                            contentDescription = null,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = CoffeePrimary,
                                        selectedLabelColor = WhitePure,
                                        selectedLeadingIconColor = WhitePure
                                    )
                                )

                                categories.take(3).forEach { category ->
                                    FilterChip(
                                        selected = selectedCategory == category,
                                        onClick = {
                                            selectedCategory = if (selectedCategory == category) null else category
                                        },
                                        label = {
                                            category?.let {
                                                Text(
                                                    it,
                                                    fontSize = 13.sp,
                                                    fontWeight = FontWeight.Medium
                                                )
                                            }
                                        },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = CoffeePrimary,
                                            selectedLabelColor = WhitePure
                                        )
                                    )
                                }
                            }
                        }


                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    "Nuestro Menú",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Black,
                                    letterSpacing = 0.3.sp
                                )
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(top = 4.dp)
                                ) {
                                    Surface(
                                        shape = CircleShape,
                                        color = CoffeePrimary.copy(alpha = 0.2f)
                                    ) {
                                        Text(
                                            "${filteredProducts.size}",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = CoffeePrimary,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                        )
                                    }
                                    Text(
                                        "productos ${if (selectedCategory != null) "en $selectedCategory" else "disponibles"}",
                                        fontSize = 13.sp,
                                        color = GrayMedium,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }

                        // Grid de productos
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(filteredProducts) { product ->
                                DetailedProductCard(
                                    product = product,
                                    onClick = { onProductClick(product.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailedProductCard(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = WhitePure
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        )
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Badge de disponibilidad mejorado
                if (!product.disponible) {
                    Surface(
                        color = ErrorRed,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        shadowElevation = 2.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 6.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "⚠️",
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "AGOTADO",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = WhitePure,
                                letterSpacing = 1.sp
                            )
                        }
                    }
                }

                // Imagen del producto con decoración
                Box(
                    modifier = Modifier.size(120.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Círculo de fondo con gradiente
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        CoffeePrimary.copy(alpha = 0.15f),
                                        CreamBackground.copy(alpha = 0.8f),
                                        WhitePure
                                    )
                                )
                            )
                    )
                    // Emoji del producto
                    product.categoria?.let {
                        Text(
                            text = getEmojiForCategory(it),
                            fontSize = 64.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Badge de categoría mejorado
                Surface(
                    color = CoffeePrimary.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.shadow(1.dp, RoundedCornerShape(8.dp))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Outlined.LocalCafe,
                            contentDescription = null,
                            tint = CoffeePrimary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        product.categoria?.let {
                            Text(
                                text = it.uppercase(),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = CoffeePrimary,
                                letterSpacing = 0.8.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Nombre del producto
                Text(
                    text = product.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = Black,
                    maxLines = 2,
                    minLines = 2,
                    lineHeight = 20.sp,
                    modifier = Modifier.height(40.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Precio mejorado con sombra y efecto
                Surface(
                    color = CoffeePrimary,
                    shape = RoundedCornerShape(12.dp),
                    shadowElevation = 4.dp,
                    modifier = Modifier.fillMaxWidth(0.85f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = WhitePure.copy(alpha = 0.9f)
                        )
                        Text(
                            text = String.format("%.2f", product.precio),
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp,
                            color = WhitePure,
                            letterSpacing = 0.5.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Indicador de disponibilidad sutil
                if (product.disponible) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(SuccessGreen)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Disponible ahora",
                            fontSize = 11.sp,
                            color = SuccessGreen,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

private fun getEmojiForCategory(categoria: String): String {
    return when (categoria.lowercase()) {
        "cafe", "coffee", "café" -> "☕"
        "te", "tea", "té" -> "🍵"
        "postre", "dessert" -> "🍰"
        "pasteleria", "pastry" -> "🥐"
        "sandwich" -> "🥪"
        "bebida", "drink" -> "🥤"
        "helado", "ice cream" -> "🍦"
        "jugo", "juice" -> "🧃"
        else -> "🥐"
    }
}