package edu.ucne.luis_gabriel_ap2_p1.presentation.edit

data class EditCervezaUiState(
    val IdCerveza: Int? = null,
    val nombre: String = "",
    val marca: String = "",
    val puntuacion: String = "",
    val nombreError: String? = null,
    val marcaError: String? = null,
    val puntuacionError: String? = null,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isNew: Boolean = true,
    val saved: Boolean = false,
    val deleted: Boolean = false,
    val errorMessage: String? = null
)