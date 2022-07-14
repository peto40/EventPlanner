package com.eventplanner

import android.app.Application
import com.eventplanner.di.AppComponent
import com.eventplanner.di.DaggerAppComponent


class MyApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .context(this)
            .build()
    }

    fun getRetrofitComponent(): AppComponent {
        return appComponent
    }
}