import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

@Composable
fun DashboardScreen() {
    // Lista de asignaturas
    val asignaturas = listOf(
        "Programación",
        "Base de datos",
        "Lenguaje de marcas",
        "Entornos de Desarrollo",
        "Sistemas Informáticos",
        "Formación y Orientación Laboral"
    )

    // Si caben todas, no hace scroll. Si no caben, permite el scroll.
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(asignaturas) { asignatura ->
            AsignaturaItem(
                titulo = asignatura,
                onClick = { println("Click en $asignatura") }
            )
        }
    }
}

@Composable
fun AsignaturaItem(titulo: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}