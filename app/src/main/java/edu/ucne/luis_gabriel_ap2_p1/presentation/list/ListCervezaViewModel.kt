package edu.ucne.luis_gabriel_ap2_p1.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.luis_gabriel_ap2_p1.domain.usecase.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListCervezaViewModel @Inject constructor(
    private val observeCervezaUsecase: ObserveCervezaUseCase,
    private val deleteCervezaUsecase: DeleteCervezaUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ListCervezaUiState())
    val state: StateFlow<ListCervezaUiState> = _state.asStateFlow()

    init {
        onLoad()
    }

    fun onEvent(event: ListCervezaUiEvent) {
        when (event) {
            ListCervezaUiEvent.Load -> onLoad()
            is ListCervezaUiEvent.Delete -> onDelete(event.id)
            ListCervezaUiEvent.CreateNew -> _state.update { it.copy(navigateToCreate = true) }
            is ListCervezaUiEvent.Edit -> _state.update { it.copy(navigateToEditId = event.id) }
            is ListCervezaUiEvent.ShowMessage -> _state.update { it.copy(message = event.message) }
            is ListCervezaUiEvent.Filter -> onFilterChanged(event.query)
        }
    }

    private fun onLoad() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            observeCervezaUsecase(_state.value.filtro).collectLatest { cervezas ->
                val promedio = if (cervezas.isNotEmpty()) {
                    cervezas.map { it.puntuacion }.average()
                } else 0.0

                _state.update {
                    it.copy(
                        isLoading = false,
                        cervezas = cervezas,
                        conteo = cervezas.size,
                        promedioPuntuacion = promedio
                    )
                }
            }
        }
    }

    private fun onFilterChanged(nuevoFiltro: String) {
        _state.update { it.copy(filtro = nuevoFiltro) }
        onLoad()
    }

    private fun onDelete(id: Int) {
        viewModelScope.launch {
            deleteCervezaUsecase(id)
        }
    }
}