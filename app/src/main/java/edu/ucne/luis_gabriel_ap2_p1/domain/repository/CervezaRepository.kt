package edu.ucne.luis_gabriel_ap2_p1.domain.repository

import edu.ucne.luis_gabriel_ap2_p1.data.local.entities.CervezaEntity
import kotlinx.coroutines.flow.Flow
interface CervezaRepository {
    fun observeAll(): Flow<List<CervezaEntity>>
    suspend fun upsert(cerveza: CervezaEntity): Long
    suspend fun getById(id: Int): CervezaEntity?
    suspend fun delete(cerveza: CervezaEntity)
    fun observeFiltered(nombre: String?): Flow<List<CervezaEntity>>
}


