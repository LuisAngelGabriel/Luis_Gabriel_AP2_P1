package edu.ucne.luis_gabriel_ap2_p1.presentation.list
import edu.ucne.luis_gabriel_ap2_p1.domain.model.Cerveza

data class ListCervezaUiState(
    val isLoading: Boolean = false,
    val cervezas: List<Cerveza> = emptyList(),
    val filtro: String = "",
    val message: String? = null,
    val conteo: Int = 0,
    val promedioPuntuacion: Double = 0.0,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null
)