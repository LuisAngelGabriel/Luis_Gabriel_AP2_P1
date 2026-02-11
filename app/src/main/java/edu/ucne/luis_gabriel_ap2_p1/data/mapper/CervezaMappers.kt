package edu.ucne.luis_gabriel_ap2_p1.data.mapper
import edu.ucne.luis_gabriel_ap2_p1.data.local.entities.CervezaEntity
import edu.ucne.luis_gabriel_ap2_p1.domain.model.Cerveza

fun CervezaEntity.toDomain(): Cerveza = Cerveza(
    IdCerveza = IdCerveza,
    nombre = nombre,
    marca = marca,
    puntuacion =puntuacion
)

fun Cerveza.toEntity(): CervezaEntity = CervezaEntity(
    IdCerveza = IdCerveza,
    nombre = nombre,
    marca = marca,
    puntuacion = puntuacion
)
