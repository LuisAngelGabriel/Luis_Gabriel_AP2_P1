package edu.ucne.luis_gabriel_ap2_p1.domain.usecase

import edu.ucne.luis_gabriel_ap2_p1.domain.repository.CervezaRepository
import jakarta.inject.Inject

class ObserveCervezaUseCase @Inject constructor(
    private val repository: CervezaRepository
) {
    operator fun invoke(nombre: String? = null) = repository.observeFiltered(nombre)
}