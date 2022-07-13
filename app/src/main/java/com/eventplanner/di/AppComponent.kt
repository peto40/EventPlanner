package com.eventplanner.di

import android.app.Application
import com.eventplanner.MyApplication
import com.eventplanner.viewmodel.SharedViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules =[
    AppModule::class,
    ViewModelFactoryModule::class,

])
interface AppComponent {


    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(application: Application): Builder

//        @BindsInstance
//        fun context(context: Context): Builder
    }

    fun inject(sharedViewModel: SharedViewModel)
//    fun inject(mainFragment: MainFragment)
//    fun inject(addEventFragment: AddEventFragment)
//    fun inject(updateFragment: UpdateFragment)
}