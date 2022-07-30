package com.example.githubapp

import android.app.Application
import com.example.githubapp.di.ApplicationComponent
import com.example.githubapp.di.DaggerApplicationComponent

class MyApplication: Application() {
    lateinit var applicationComponent: ApplicationComponent
    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}