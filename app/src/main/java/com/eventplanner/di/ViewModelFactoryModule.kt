package com.eventplanner.di

import androidx.lifecycle.ViewModelProvider
import com.eventplanner.utils.ViewModelProviderFactory
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelProvideFactory: ViewModelProviderFactory): ViewModelProvider.Factory

}