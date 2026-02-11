package edu.ucne.luis_gabriel_ap2_p1.data.local.dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.luis_gabriel_ap2_p1.data.local.entities.CervezaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CervezaDao {
    @Upsert
    suspend fun upsert(cerveza: CervezaEntity): Long

    @Delete
    suspend fun delete(cerveza: CervezaEntity)

        @Query("SELECT * FROM cerveza WHERE IdCerveza = :id")
        suspend fun getById(id: Int): CervezaEntity?

        @Query("SELECT * FROM cerveza ORDER BY IdCerveza DESC")
        fun observeAll(): Flow<List<CervezaEntity>>

        @Query("""
        SELECT * FROM cerveza 
        WHERE (:nombre IS NULL OR marca LIKE '%' || :nombre || '%')
        ORDER BY IdCerveza DESC
    """)
        fun observeFiltered(nombre: String?): Flow<List<CervezaEntity>>

}