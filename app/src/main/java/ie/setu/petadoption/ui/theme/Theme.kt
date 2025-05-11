package ie.setu.petadoption.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
//    primary = Purple40,
//    secondary = PurpleGrey40,
//    tertiary = Pink40

    primary = Color(0xFF2E7D32),        // Green 800 – primary actions
    onPrimary = Color.White,

    secondary = Color(0xFFA5D6A7),      // Light Green 300 – secondary accents
    onSecondary = Color.Black,

    tertiary = Color(0xFFFFCC80),       // Soft orange – for warm call-to-action highlights
    onTertiary = Color.Black,

    background = Color(0xFFF1F8F4),     // Very light green background
    onBackground = Color(0xFF1B1F1C),

    surface = Color(0xFFFFFFFF),        // Clean white surface
    onSurface = Color(0xFF1B1F1C)
    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

val startGradientColor = Color(0xFFA5D6A7) // Light Mint Green (matches secondary)
val endGradientColor = Color(0xFF66BB6A)   // Medium Leafy Green (midpoint between secondary and primary)


val gStartGradientColor = Color(0xFF013B6E)
val gEndGradientColor = Color(0xFF2189EB)

@Composable
fun PetAdoptionTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}