package com.example.hw6_room

import android.app.Application
import com.example.hw6_room.di.ServiceLocator

class App : Application() {

    private val serviceLocator = ServiceLocator

    override fun onCreate() {
        super.onCreate()
        serviceLocator.initDataLayerDependencies(ctx = this)
    }
}