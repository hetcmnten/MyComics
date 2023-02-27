package com.lxt.mycomics.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lxt.mycomics.comicsToString
import com.lxt.mycomics.model.CharacterResult
import com.lxt.mycomics.model.db.Constants.CHARACTER_TABLE

@Entity(tableName = CHARACTER_TABLE)
data class DbCharacter(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val apiId: Int?,
    val name: String?,
    val thumbnail: String?,
    val comics: String?,
    val description: String?
) {
    companion object {
        fun fromCharacter(character: CharacterResult) =
            DbCharacter(
                id = 0,
                apiId = character.id,
                name = character.name,
                thumbnail = character.thumbnail?.path + "." + character.thumbnail?.extension,
                description = character.description,
                comics = character.comics?.items?.mapNotNull { it.name }?.comicsToString()
                    ?: "no comics"
            )
    }
}