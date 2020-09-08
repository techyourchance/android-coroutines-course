package com.techyourchance.coroutines

import android.app.Application
import com.techyourchance.coroutines.common.dependencyinjection.ApplicationCompositionRoot

class MyApplication : Application() {

    val applicationCompositionRoot = ApplicationCompositionRoot()

    override fun onCreate() {
        super.onCreate()
    }
}