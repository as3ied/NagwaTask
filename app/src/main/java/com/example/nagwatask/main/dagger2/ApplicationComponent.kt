package com.example.nagwatask.main.dagger2

import com.example.nagwatask.main.ui.MainActivity
import dagger.Component

@Component(modules = [NetworkModule::class])
interface ApplicationComponent {
    fun inject(activity: MainActivity)

}
