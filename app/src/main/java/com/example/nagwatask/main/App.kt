package com.example.nagwatask.main

import android.app.Application
import com.example.nagwatask.main.dagger2.ApplicationComponent
import com.example.nagwatask.main.dagger2.DaggerApplicationComponent

class App : Application()
{
    lateinit var appComponent : ApplicationComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.create()
    }
}