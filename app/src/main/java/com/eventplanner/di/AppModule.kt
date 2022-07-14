package com.eventplanner.di

import android.app.Application
import androidx.room.Room
import com.eventplanner.database.EventDao
import com.eventplanner.database.EventDb
import com.eventplanner.repositiry.EventRepository
import com.eventplanner.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [RetrofitModule::class])
class AppModule {

    @Singleton
    @Provides
    fun providesAppDatabase(application: Application): EventDb {
        return Room.databaseBuilder(
            application,
            EventDb::class.java,
            "event_database")
            .build()
    }

    @Singleton
    @Provides
    fun providesEventDao(eventDb: EventDb): EventDao {
        return eventDb.eventDao()
    }

    @Singleton
    @Provides
    fun providesRepository(eventDao: EventDao): EventRepository {
        return EventRepository(eventDao)
    }


}