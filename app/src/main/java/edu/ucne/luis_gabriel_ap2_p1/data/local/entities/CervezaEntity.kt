package edu.ucne.luis_gabriel_ap2_p1.data.local.entities
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cerveza")
data class CervezaEntity(
    @PrimaryKey(autoGenerate = true)
    val IdCerveza: Int=0,
    val nombre: String,
    val marca: String,
    val puntuacion: Int,
)