package com.eventplanner.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.eventplanner.di.DaggerAppComponent
import com.eventplanner.view.navigation.NavigationAppHost
import com.eventplanner.view.theme.EventPlannerTheme
import com.eventplanner.viewmodel.SharedViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appComponent by lazy {
            DaggerAppComponent.builder()
                .application(this.application)
                .context(this.baseContext)
                .build()
        }

        setContent {
            EventPlannerTheme {
                Surface(modifier = Modifier.fillMaxSize()) {

                    val viewModel: SharedViewModel = daggerViewModel {
                       appComponent.getViewModel()
                    }

                    val navController = rememberNavController()

                    NavigationAppHost(navController = navController, viewModel)

                }
            }
        }

    }

}

@Composable
inline fun <reified T : ViewModel> daggerViewModel(
    key: String? = null,
    crossinline viewModelInstanceCreator: () -> T
): T =
        androidx.lifecycle.viewmodel.compose.viewModel(
            modelClass = T::class.java,
            key = key,
            factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return viewModelInstanceCreator() as T
                }
            }
        )
