package edu.ucne.luis_gabriel_ap2_p1.domain.usecase

import javax.inject.Inject

data class ValidationResult(
    val isValid: Boolean,
    val error: String? = null
)

class ValidateCervezaUseCase @Inject constructor() {

    fun validateNombre(value: String): ValidationResult {
        if (value.isBlank()) return ValidationResult(false, "El nombre es obligatorio")
        return ValidationResult(true)
    }

    fun validateMarca(value: String): ValidationResult {
        if (value.isBlank()) return ValidationResult(false, "La marca es obligatoria")
        return ValidationResult(true)
    }

    fun validatePuntuacion(value: String): ValidationResult {
        if (value.isBlank()) return ValidationResult(false, "La puntuación es obligatoria")
        val puntuacion = value.toIntOrNull()
        if (puntuacion == null || puntuacion !in 0..5) {
            return ValidationResult(false, "La puntuación debe ser un número entre 0 y 5")
        }
        return ValidationResult(true)
    }

    fun validateCervezaDuplicada(nombre: String, nombresExistentes: List<String>): ValidationResult {
        if (nombresExistentes.any { it.equals(nombre, ignoreCase = true) }) {
            return ValidationResult(false, "Esta cerveza ya está registrada")
        }
        return ValidationResult(true)
    }
}