package edu.ucne.luis_gabriel_ap2_p1.presentation.list

sealed interface ListCervezaUiEvent {
    data object Load : ListCervezaUiEvent
    data class Delete(val id: Int) : ListCervezaUiEvent
    data object CreateNew : ListCervezaUiEvent
    data class Edit(val id: Int) : ListCervezaUiEvent
    data class ShowMessage(val message: String) : ListCervezaUiEvent
    data class Filter(val query: String) : ListCervezaUiEvent
}