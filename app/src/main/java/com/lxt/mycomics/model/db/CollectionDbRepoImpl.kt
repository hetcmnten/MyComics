package com.lxt.mycomics.model.db

import kotlinx.coroutines.flow.Flow

class CollectionDbRepoImpl(private val characterDao: CharacterDao, private val noteDao: NoteDao) :
    CollectionDbRepo {
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

    override suspend fun getAllNotes(): Flow<List<DbNote>> = noteDao.getAllNotes()

    override suspend fun getNoteFromRepo(characterId: Int) =
        noteDao.getNotes(characterId)

    override suspend fun addNoteToRepo(note: DbNote) = noteDao.addNote(note)

    override suspend fun updateNoteInRepo(note: DbNote) = noteDao.updateNote(note)

    override suspend fun deleteNoteFromRepo(note: DbNote) = noteDao.deleteNote(note)

    override suspend fun deleteAllNotes(character: DbCharacter) =
        noteDao.deleteAllNotes(character.id)
}