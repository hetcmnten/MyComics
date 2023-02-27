package com.lxt.mycomics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lxt.mycomics.model.CharacterResult
import com.lxt.mycomics.model.db.CollectionDbRepo
import com.lxt.mycomics.model.db.DbCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val repo: CollectionDbRepo
) : ViewModel() {
    val currentCharacter = MutableStateFlow<DbCharacter?>(null)
    val collection = MutableStateFlow<List<DbCharacter>>(listOf())

    init {
        getCollection()
    }

    private fun getCollection() {
        viewModelScope.launch {
            repo.getCharactersFromRepo().collect {
                collection.value = it
            }
        }
    }

    fun setCurrentCharacterId(characterId: Int?) {
        characterId?.let {
            viewModelScope.launch {
                repo.getCharacterFromRepo(it).collect {
                    currentCharacter.value = it
                }
            }
        }
    }

    fun addCharacter(character: CharacterResult) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addCharacterToRepo(DbCharacter.fromCharacter(character))
        }
    }

    fun deleteCharacter(character: DbCharacter) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteCharacterInRepo(character)
        }
    }
}