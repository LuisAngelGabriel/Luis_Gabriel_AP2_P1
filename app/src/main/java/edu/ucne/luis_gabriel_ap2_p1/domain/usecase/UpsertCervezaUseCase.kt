package edu.ucne.luis_gabriel_ap2_p1.domain.usecase

import edu.ucne.luis_gabriel_ap2_p1.domain.model.Cerveza
import edu.ucne.luis_gabriel_ap2_p1.domain.repository.CervezaRepository
import javax.inject.Inject


class UpsertCervezaUseCase @Inject constructor(
    private val repository: CervezaRepository
) {
    suspend operator fun invoke(
        nombre: Cerveza,
        marca: List<String>
    ): Result<Int> {
        val vNombre = validateNombreCerveza(entrada.nombre)
        if (!vNombre.isValid) return Result.failure(IllegalArgumentException(vNombre.error))

        val vMarca = validateMarcaCerveza(entrada.marca)
        if (!vNombre.isValid) return Result.failure(IllegalArgumentException(vMarca.error))

        val vPuntuacion = validatePuntuacionCerveza(entrada.puntuacion)
        if(entrada.puntuacion > 5)
            return Result.failure(IllegalArgumentException(vPuntuacion.error))
    }

}

