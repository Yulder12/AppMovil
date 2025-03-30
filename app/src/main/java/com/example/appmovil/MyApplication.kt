package com.example.appmovil

import android.app.Application
import com.example.appmovil.util.ThemeManager

class MyApplication : Application() {

    lateinit var themeManager: ThemeManager

    override fun onCreate() {
        super.onCreate()
        // Inicializar ThemeManager
        themeManager = ThemeManager.getInstance(this)
    }
}