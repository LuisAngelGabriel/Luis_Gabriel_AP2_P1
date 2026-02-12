package edu.ucne.luis_gabriel_ap2_p1.presentation.edit

sealed interface EditCervezaUiEvent {
    data class Load(val id: Int?) : EditCervezaUiEvent
    data class NombreChanged(val value: String) : EditCervezaUiEvent
    data class MarcaChanged(val value: String) : EditCervezaUiEvent
    data class PuntuacionChanged(val value: String) : EditCervezaUiEvent
    data object Save : EditCervezaUiEvent
    data object Delete : EditCervezaUiEvent
}