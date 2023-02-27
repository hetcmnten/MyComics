package com.lxt.mycomics.model.db

import kotlinx.coroutines.flow.Flow

class CollectionDbRepoImpl(private val characterDao: CharacterDao) : CollectionDbRepo {
    override suspend fun getCharactersFromRepo(): Flow<List<DbCharacter>> {
        return characterDao.getCharacters()
    }

    override suspend fun getCharacterFromRepo(characterId: Int): Flow<DbCharacter> {
        return characterDao.getCharacter(characterId)
    }

    override suspend fun addCharacterToRepo(character: DbCharacter) {
        return characterDao.addCharacter(character)
    }

    override suspend fun updateCharacterInRepo(character: DbCharacter) {
        return characterDao.updateCharacter(character)
    }

    override suspend fun deleteCharacterInRepo(character: DbCharacter) {
        return characterDao.deleteCharacter(character)
    }
}