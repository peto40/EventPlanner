package com.eventplanner.di

import android.app.Application
import android.content.Context
import com.eventplanner.MyApplication
import com.eventplanner.repositiry.EventRepository
import com.eventplanner.utils.ViewModelProviderFactory
import com.eventplanner.viewmodel.SharedViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules =[
    AppModule::class,
    ViewModelFactoryModule::class,
    ViewModelModule::class

])
interface AppComponent {

    @Component.Builder
    interface Builder {
        fun build(): AppComponent


        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun context(context: Context): Builder

    }
    @Singleton
    fun viewModelFactory(): ViewModelProviderFactory

    fun inject(sharedViewModel: SharedViewModel)

    fun getViewModel(): SharedViewModel

}