package com.lxt.mycomics.model.db

import kotlinx.coroutines.flow.Flow

interface CollectionDbRepo {
    suspend fun getCharactersFromRepo(): Flow<List<DbCharacter>>

    suspend fun getCharacterFromRepo(characterId: Int): Flow<DbCharacter>

    suspend fun addCharacterToRepo(character: DbCharacter)

    suspend fun updateCharacterInRepo(character: DbCharacter)

    suspend fun deleteCharacterInRepo(character: DbCharacter)
}