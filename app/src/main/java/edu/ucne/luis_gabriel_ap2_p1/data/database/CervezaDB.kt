package edu.ucne.luis_gabriel_ap2_p1.data.database
import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.luis_gabriel_ap2_p1.data.local.dao.CervezaDao
import edu.ucne.luis_gabriel_ap2_p1.data.local.entities.CervezaEntity



    @Database(
        entities = [CervezaEntity::class],
        version = 1,
        exportSchema = false)
   abstract class CervezaDB: RoomDatabase(){

        abstract fun cervezaDao(): CervezaDao
    }