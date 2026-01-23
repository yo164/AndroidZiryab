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
fun DashboardProfesorScreen() {
    // Lista de clases/grupos que imparte el profesor
    val gruposProfe = listOf(
        "1º DAM - Programación",
        "2º DAM - Acceso a Datos",
        "1º DAW - Base de Datos",
        "2º DAW - Desarrollo Web",
        "1º ASIR - Lenguaje de Marcas",
        "Tutoria - 2º DAM"
    )

    // LazyColumn gestiona el scroll automáticamente:
    // Si hay pocos grupos, no habrá scroll. Si hay muchos, se activará.
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            Text(
                text = "Mis Clases",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        items(gruposProfe) { grupo ->
            GrupoItem(
                nombreGrupo = grupo,
                onClick = { println("Entrando a la gestión de: $grupo") }
            )
        }
    }
}

@Composable
fun GrupoItem(nombreGrupo: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp) // Altura fija para que no ocupen toda la pantalla
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant // Color ligeramente distinto para diferenciar
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = nombreGrupo,
                    style = MaterialTheme.typography.titleLarge
                )

            }
        }
    }
}