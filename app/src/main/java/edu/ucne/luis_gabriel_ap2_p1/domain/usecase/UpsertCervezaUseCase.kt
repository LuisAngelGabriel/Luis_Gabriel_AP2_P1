package edu.ucne.luis_gabriel_ap2_p1.domain.usecase

import edu.ucne.luis_gabriel_ap2_p1.domain.model.Cerveza
import edu.ucne.luis_gabriel_ap2_p1.domain.repository.CervezaRepository
import javax.inject.Inject

class UpsertCervezaUseCase @Inject constructor(
    private val repository: CervezaRepository,
    private val validator: ValidateCervezaUseCase
) {
    suspend operator fun invoke(
        cerveza: Cerveza,
        cervezasExistentes: List<String>
    ): Result<Int> {

        val vNombre = validator.validateNombre(cerveza.nombre)
        if (!vNombre.isValid) return Result.failure(IllegalArgumentException(vNombre.error))

        val vMarca = validator.validateMarca(cerveza.marca)
        if (!vMarca.isValid) return Result.failure(IllegalArgumentException(vMarca.error))

        val vPuntuacion = validator.validatePuntuacion(cerveza.puntuacion.toString())
        if (!vPuntuacion.isValid) return Result.failure(IllegalArgumentException(vPuntuacion.error))

        if (cerveza.IdCerveza == 0) {
            val vDup = validator.validateCervezaDuplicada(cerveza.nombre, cervezasExistentes)
            if (!vDup.isValid) return Result.failure(IllegalArgumentException(vDup.error))
        }
        return runCatching { repository.upsert(cerveza) }
    }
}