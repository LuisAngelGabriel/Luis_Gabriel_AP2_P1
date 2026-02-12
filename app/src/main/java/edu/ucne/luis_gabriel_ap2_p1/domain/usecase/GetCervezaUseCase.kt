package edu.ucne.luis_gabriel_ap2_p1.domain.usecase

import edu.ucne.luis_gabriel_ap2_p1.domain.repository.CervezaRepository
import javax.inject.Inject

class GetCervezaUseCase @Inject constructor(
    private val repository: CervezaRepository
) {
    suspend operator fun invoke(id: Int) = repository.getById(id)


}





