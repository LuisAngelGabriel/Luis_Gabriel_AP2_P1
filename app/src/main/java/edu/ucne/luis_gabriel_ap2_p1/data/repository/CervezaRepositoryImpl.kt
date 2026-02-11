package edu.ucne.luis_gabriel_ap2_p1.data.repository
import edu.ucne.luis_gabriel_ap2_p1.data.local.dao.CervezaDao
import edu.ucne.luis_gabriel_ap2_p1.domain.model.Cerveza
import edu.ucne.luis_gabriel_ap2_p1.domain.repository.CervezaRepository
import edu.ucne.luis_gabriel_ap2_p1.data.mapper.toEntity
import edu.ucne.luis_gabriel_ap2_p1.data.mapper.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CervezaRepositoryImpl (
    private val dao: CervezaDao
) : CervezaRepository {

    override fun observeAll(): Flow<List<Cerveza>> {} = dao.observeAll().map {list ->
    list.map { it.toDomain()  }

        override fun observeFiltered(nombre: String?): Flow<List<Cerveza>> =
            dao.observeFiltered(nombre).map {list ->
                list.map { it.toDomain() }

         override suspend fun getById(id: Int): Cerveza? = dao.getById(id)?.toDomain()

                override suspend fun upsert(cerveza: Cerveza): Int {
                    return dao.upsert(cerveza = cerveza.toEntity()). toInt()
                }

                override suspend fun delete(id: Int){
                    val entity = dao.getById(id)
                    entity?.let{
                        dao.delete(cerveza = it)
                    }
                }
}
    }
}





