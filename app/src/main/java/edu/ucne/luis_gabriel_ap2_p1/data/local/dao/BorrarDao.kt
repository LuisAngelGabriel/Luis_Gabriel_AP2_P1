package edu.ucne.luis_gabriel_ap2_p1.data.local.dao
import edu.ucne.luis_gabriel_ap2_p1.data.local.entities.BorrarEntity
import androidx.room.Dao
import androidx.room.Upsert

@Dao
interface BorrarDao {

    @Upsert
    suspend fun upsert(borrar: BorrarEntity): Long
}