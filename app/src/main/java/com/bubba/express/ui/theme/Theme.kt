package com.bubba.express.ui.theme


import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

private val DarkColorScheme = darkColorScheme(
    primary = CoffeePrimary,
    secondary = CoffeeSecondary,
    tertiary = CoffeeAccent,
    background = Black,
    surface = GrayDark,
    onPrimary = WhitePure,
    onSecondary = WhitePure,
    onTertiary = Black,
    onBackground = CreamBackground,
    onSurface = CreamBackground
)
private val LightColorScheme = lightColorScheme(
    primary = CoffeePrimary,
    secondary = CoffeeSecondary,
    tertiary = CoffeeAccent,
    background = CreamBackground,
    surface = WhitePure,
    onPrimary = WhitePure,
    onSecondary = WhitePure,
    onTertiary = Black,
    onBackground = Black,
    onSurface = Black
)


