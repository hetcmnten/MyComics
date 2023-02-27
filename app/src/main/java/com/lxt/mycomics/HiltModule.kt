package com.lxt.mycomics

import android.content.Context
import androidx.room.Room
import com.lxt.mycomics.model.api.ApiService
import com.lxt.mycomics.model.api.MarvelApiRepo
import com.lxt.mycomics.model.db.CharacterDao
import com.lxt.mycomics.model.db.CollectionDb
import com.lxt.mycomics.model.db.CollectionDbRepo
import com.lxt.mycomics.model.db.CollectionDbRepoImpl
import com.lxt.mycomics.model.db.Constants.DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {
    @Provides
    fun provideApiRepo() = MarvelApiRepo(ApiService.api)

    @Provides
    fun provideCollectionDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CollectionDb::class.java, DB).build()

    @Provides
    fun provideCharacterDao(collectionDb: CollectionDb) = collectionDb.characaterDao()

    @Provides
    fun provideDbRepoImpl(characterDao: CharacterDao): CollectionDbRepo =
        CollectionDbRepoImpl(characterDao)
}