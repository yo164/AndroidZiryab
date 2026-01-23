import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp



@Composable
fun DashboardScreen() {
    val menuItems = listOf(
        "Programación" to { println("Opción Programación seleccionada") },
        "Base de datos" to { println("Opción Base de datos seleccionada") },
        "Lenguaje de marcas" to { println("Opción Lenguaje de marcas seleccionada") }
        // Agrega más elementos si quieres
    )

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val maxVisible = 4
    val visibleItems = menuItems.size.coerceAtMost(maxVisible)

    // Espaciado entre cards y margen externo
    val spacing = 16.dp
    val horizontalPadding = 16.dp
    val totalSpacing = spacing * (visibleItems - 1)
    val itemHeight = (screenHeight - totalSpacing - spacing * 2) / visibleItems

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = horizontalPadding, vertical = spacing),
        verticalArrangement = Arrangement.spacedBy(spacing),
        userScrollEnabled = menuItems.size > maxVisible
    ) {
        items(menuItems.size) { index ->
            val (title, action) = menuItems[index]
            MenuOption(title = title, onClick = action, height = itemHeight)
        }
    }
}

@Composable
fun MenuOption(title: String, onClick: () -> Unit, height: Dp) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.medium // Bordes redondeados visibles
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

