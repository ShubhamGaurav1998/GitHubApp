package com.example.githubapp.di

import android.content.Context
//import com.example.githubapp.dataSource.DataSourceFactory
import com.example.githubapp.views.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitInstance::class])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface  Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

}