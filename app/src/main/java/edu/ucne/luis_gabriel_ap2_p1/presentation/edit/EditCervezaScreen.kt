package edu.ucne.luis_gabriel_ap2_p1.presentation.edit
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EditCervezaScreen(
    cervezaId: Int?,
    onDrawer: () -> Unit,
    goBack: () -> Unit,
    viewModel: EditCervezaViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(cervezaId, state.saved, state.deleted) {
        if (state.saved || state.deleted) {
            goBack()
        } else {
            viewModel.onEvent(EditCervezaUiEvent.Load(cervezaId))
        }
    }

    EditCervezaBody(state, viewModel::onEvent, onDrawer, goBack)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditCervezaBody(
    state: EditCervezaUiState,
    onEvent: (EditCervezaUiEvent) -> Unit,
    onDrawer: () -> Unit,
    goBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.isNew) "Nueva Cerveza" else "Modificar Cerveza") },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = onDrawer) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menú")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = state.nombre,
                onValueChange = { onEvent(EditCervezaUiEvent.NombreChanged(it)) },
                label = { Text("Nombre de la Cerveza") },
                isError = state.nombreError != null,
                modifier = Modifier.fillMaxWidth()
            )
            state.nombreError?.let {
                Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.marca,
                onValueChange = { onEvent(EditCervezaUiEvent.MarcaChanged(it)) },
                label = { Text("Marca / Origen") },
                isError = state.marcaError != null,
                modifier = Modifier.fillMaxWidth()
            )
            state.marcaError?.let {
                Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.puntuacion,
                onValueChange = { onEvent(EditCervezaUiEvent.PuntuacionChanged(it)) },
                label = { Text("Puntuación (0-5)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = state.puntuacionError != null,
                modifier = Modifier.fillMaxWidth()
            )
            state.puntuacionError?.let {
                Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onEvent(EditCervezaUiEvent.Save) },
                    enabled = !state.isSaving,
                    modifier = Modifier.weight(1f)
                ) {
                    if (state.isSaving) CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                    else Text("Guardar")
                }

                if (!state.isNew) {
                    OutlinedButton(
                        onClick = { onEvent(EditCervezaUiEvent.Delete) },
                        enabled = !state.isDeleting,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Eliminar")
                    }
                }
            }

            state.errorMessage?.let {
                Spacer(Modifier.height(16.dp))
                Text(it, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}