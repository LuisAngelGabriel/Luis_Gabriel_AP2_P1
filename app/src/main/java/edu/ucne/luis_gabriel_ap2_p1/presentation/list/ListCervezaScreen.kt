package edu.ucne.luis_gabriel_ap2_p1.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.luis_gabriel_ap2_p1.domain.model.Cerveza

@Composable
fun ListCervezaScreen(
    viewModel: ListCervezaViewModel = hiltViewModel(),
    onDrawer: () -> Unit,
    onCreate: () -> Unit,
    onEdit: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ListCervezaBody(
        state = state,
        onDrawer = onDrawer,
        onCreate = onCreate,
        onEdit = onEdit,
        onDelete = { viewModel.onEvent(ListCervezaUiEvent.Delete(it)) },
        onFilterChanged = { viewModel.onEvent(ListCervezaUiEvent.Filter(it)) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListCervezaBody(
    state: ListCervezaUiState,
    onDrawer: () -> Unit,
    onCreate: () -> Unit,
    onEdit: (Int) -> Unit,
    onDelete: (Int) -> Unit,
    onFilterChanged: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Historial de Cervezas") },
                navigationIcon = {
                    IconButton(onClick = onDrawer) { Icon(Icons.Filled.Menu, null) }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreate,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.padding(bottom = 80.dp)
            ) {
                Icon(Icons.Default.Add, null)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = !expanded },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Filtros", style = MaterialTheme.typography.titleMedium)
                        Icon(
                            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.FilterList,
                            contentDescription = null
                        )
                    }

                    if (expanded) {
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = state.filtro,
                            onValueChange = onFilterChanged,
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Buscar por nombre o marca...") },
                            leadingIcon = { Icon(Icons.Default.Search, null) },
                            singleLine = true
                        )
                    }
                }
            }

            if (state.isLoading) {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(state.cervezas) { cerveza ->
                        CervezaCard(
                            cerveza = cerveza,
                            onClick = { onEdit(cerveza.IdCerveza) },
                            onDelete = { onDelete(cerveza.IdCerveza) }
                        )
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, top = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1E1E2C)
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total: ${state.conteo}",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Promedio: ${String.format("%.1f", state.promedioPuntuacion)}",
                        color = Color(0xFF8C9EFF),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }
}

@Composable
fun CervezaCard(
    cerveza: Cerveza,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = cerveza.nombre,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = cerveza.marca,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${cerveza.puntuacion}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.width(8.dp))
                    IconButton(onClick = onDelete) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }

}
@Preview(showBackground = true)
@Composable
fun ListCervezaPreview() {
    MaterialTheme {
        ListCervezaBody(
            state = ListCervezaUiState(
                cervezas = listOf(
                    Cerveza(IdCerveza = 1, nombre = "Presidente", marca = "Nacional", puntuacion = 5),
                    Cerveza(IdCerveza = 2, nombre = "Corona", marca = "Modelo", puntuacion = 4)
                ),
                conteo = 2,
                promedioPuntuacion = 4.5
            ),
            onDrawer = {},
            onCreate = {},
            onEdit = {},
            onDelete = {},
            onFilterChanged = {}
        )
    }
}