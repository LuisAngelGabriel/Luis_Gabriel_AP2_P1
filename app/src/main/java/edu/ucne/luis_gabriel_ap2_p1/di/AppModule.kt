package edu.ucne.luis_gabriel_ap2_p1.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.luis_gabriel_ap2_p1.data.database.CervezaDB
import edu.ucne.luis_gabriel_ap2_p1.data.local.dao.CervezaDao
import edu.ucne.luis_gabriel_ap2_p1.data.repository.CervezaRepositoryImpl
import edu.ucne.luis_gabriel_ap2_p1.domain.repository.CervezaRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideCervezaDb(@ApplicationContext appContext: Context): CervezaDB =
        Room.databaseBuilder(
            appContext,
            CervezaDB::class.java,
            "Cerveza.db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideCervezaDao(db: CervezaDB): CervezaDao =
        db.cervezaDao()

    @Provides
    @Singleton
    fun provideCervezaRepository(dao: CervezaDao): CervezaRepository =
        CervezaRepositoryImpl(dao)
}