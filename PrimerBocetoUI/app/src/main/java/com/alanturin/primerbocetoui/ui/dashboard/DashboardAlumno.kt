import android.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp


import androidx.compose.ui.unit.dp



@Composable
fun DashboardScreen() {
    val menuItems = listOf(
        "Programación" to { println("Opción Programación seleccionada") },
        "Base de datos" to { println("Opción Base de datos seleccionada") },
        "Lenguaje de marcas" to { println("Opción Lenguaje de marcas seleccionada") },
        "Ipe" to { println("Opción ipe seleccionada") },
        "Ipe" to { println("Opción ipe seleccionada") },
        "Ipe" to { println("Opción ipe seleccionada") }
        // Agrega más elementos si quieres
    )

    val spacing = 16.dp
    val horizontalPadding = 16.dp
    val maxVisible = 5
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = horizontalPadding, vertical = spacing)
    ) {
        val boxHeight = maxHeight
        val visibleItems = menuItems.size.coerceAtMost(maxVisible)
        val totalSpacing = spacing * (visibleItems - 1)
        val contentPaddingVertical = spacing * 2
        val itemHeight = (boxHeight - totalSpacing - contentPaddingVertical) / visibleItems

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(spacing),
            contentPadding = PaddingValues(vertical = spacing)
        ) {
            items(menuItems.size) { index ->
                val (title, action) = menuItems[index]
                MenuOption(
                    title = title,
                    onClick = action,
                    height = itemHeight
                )
            }
        }
    }
}

    @Composable
    fun MenuOption(title: String, onClick: () -> Unit, height: Dp? = null) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .then(height?.let { Modifier.height(it) } ?: Modifier) // Si height es null, usa altura automática
                .clickable { onClick() },
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp), // espacio entre los textos
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Nombre Profesor",
                    style = MaterialTheme.typography.bodyMedium, // un poco más pequeño que el título
                    color = MaterialTheme.colorScheme.onSurfaceVariant // opcional, para diferenciarlo
                )
            }
        }
    }