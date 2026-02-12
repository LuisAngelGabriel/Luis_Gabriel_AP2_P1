package edu.ucne.luis_gabriel_ap2_p1.presentation.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.luis_gabriel_ap2_p1.domain.model.Cerveza
import edu.ucne.luis_gabriel_ap2_p1.domain.usecase.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditCervezaViewModel @Inject constructor(
    private val getCervezaUsecase: GetCervezaUseCase,
    private val observeCervezaUsecase: ObserveCervezaUseCase,
    private val upsertCervezaUseCase: UpsertCervezaUseCase,
    private val deleteCervezaUsecase: DeleteCervezaUseCase,
    private val validator: ValidateCervezaUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EditCervezaUiState())
    val state: StateFlow<EditCervezaUiState> = _state.asStateFlow()

    fun onEvent(event: EditCervezaUiEvent) {
        when (event) {
            is EditCervezaUiEvent.Load -> onLoad(event.id)
            is EditCervezaUiEvent.NombreChanged -> _state.update {
                it.copy(nombre = event.value, nombreError = null)
            }
            is EditCervezaUiEvent.MarcaChanged -> _state.update {
                it.copy(marca = event.value, marcaError = null)
            }
            is EditCervezaUiEvent.PuntuacionChanged -> _state.update {
                it.copy(puntuacion = event.value, puntuacionError = null)
            }
            EditCervezaUiEvent.Save -> onSave()
            EditCervezaUiEvent.Delete -> onDelete()
        }
    }

    private fun onLoad(id: Int?) {
        if (id == null || id == 0) {
            _state.update { it.copy(isNew = true, IdCerveza = null) }
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val cerveza = getCervezaUsecase(id)
            cerveza?.let { item ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        isNew = false,
                        IdCerveza = item.IdCerveza,
                        nombre = item.nombre,
                        marca = item.marca,
                        puntuacion = item.puntuacion.toString()
                    )
                }
            }
        }
    }

    private fun onSave() {
        val uiState = state.value

        val vNombre = validator.validateNombre(uiState.nombre)
        val vMarca = validator.validateMarca(uiState.marca)
        val vPuntuacion = validator.validatePuntuacion(uiState.puntuacion)

        if (!vNombre.isValid || !vMarca.isValid || !vPuntuacion.isValid) {
            _state.update {
                it.copy(
                    nombreError = vNombre.error,
                    marcaError = vMarca.error,
                    puntuacionError = vPuntuacion.error
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }

            val id = uiState.IdCerveza ?: 0
            val cerveza = Cerveza(
                IdCerveza = id,
                nombre = uiState.nombre,
                marca = uiState.marca,
                puntuacion = uiState.puntuacion.toIntOrNull() ?: 0
            )

            val cervezasExistentes = observeCervezaUsecase().first()
                .filter { it.IdCerveza != id }
                .map { it.nombre }

            val result = upsertCervezaUseCase(cerveza, cervezasExistentes)

            result.onSuccess {
                _state.update { it.copy(isSaving = false, saved = true) }
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        isSaving = false,
                        nombreError = error.message
                    )
                }
            }
        }
    }

    private fun onDelete() {
        val id = state.value.IdCerveza ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            deleteCervezaUsecase(id)
            _state.update { it.copy(isDeleting = false, deleted = true) }
        }
    }
}
