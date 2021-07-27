package com.sberkozd.lettervault.di

import android.content.Context
import androidx.room.Room
import com.sberkozd.lettervault.db.LetterDao
import com.sberkozd.lettervault.db.LetterDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): LetterDatabase {
        return Room.databaseBuilder(context, LetterDatabase::class.java, "my_database.db")
            .allowMainThreadQueries()
            .createFromAsset("database/my_database.db")
            .build()
    }


    @Provides
    @Singleton
    fun provideLetterDao(database: LetterDatabase): LetterDao {
        return database.letterDao()
    }


}