package com.eventplanner.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.eventplanner.application.EventApp
import com.eventplanner.database.local.EventDao
import com.eventplanner.database.local.EventDb
import com.eventplanner.database.remote.api.RetrofitService
import com.eventplanner.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesApplication(
        @ApplicationContext app: Context
    ): EventApp {
        return app as EventApp
    }

    @Singleton
    @Provides
    fun providesAppDatabase(app: Application): EventDb {
        return Room.databaseBuilder(
            app,
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
    fun provideRetrofitService(retrofit: Retrofit): RetrofitService {
        return retrofit.create(RetrofitService::class.java)
    }
    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
