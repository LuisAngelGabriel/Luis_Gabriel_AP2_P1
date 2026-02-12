package edu.ucne.luis_gabriel_ap2_p1.domain.repository

import edu.ucne.luis_gabriel_ap2_p1.domain.model.Cerveza
import kotlinx.coroutines.flow.Flow

interface CervezaRepository {
    fun observeAll(): Flow<List<Cerveza>>
    suspend fun upsert(cerveza: Cerveza): Int
    suspend fun getById(id: Int): Cerveza?
    suspend fun delete(id: Int)
    fun observeFiltered(nombre: String?): Flow<List<Cerveza>>
}