package com.example.appmovil.util

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ThemeManager(context: Context) {

    enum class ThemeMode {
        LIGHT, DARK, BLUE, GREEN
    }

    companion object {
        private const val PREFS_NAME = "app_preferences"
        private const val KEY_BACKGROUND_COLOR = "background_color"
        private const val KEY_THEME_MODE = "theme_mode"
        private const val KEY_COMPACT_VIEW = "compact_view"

        @Volatile
        private var INSTANCE: ThemeManager? = null

        fun getInstance(context: Context): ThemeManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ThemeManager(context.applicationContext).also {
                    INSTANCE = it
                }
            }
        }
    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val _backgroundColor = MutableLiveData<Int>()
    val backgroundColor: LiveData<Int> = _backgroundColor

    private val _themeMode = MutableLiveData<ThemeMode>()
    val themeMode: LiveData<ThemeMode> = _themeMode

    private val _compactViewMode = MutableLiveData<Boolean>()
    val compactViewMode: LiveData<Boolean> = _compactViewMode

    init {
        // Cargar el color de fondo guardado o usar el predeterminado (blanco)
        val savedColor = sharedPreferences.getInt(KEY_BACKGROUND_COLOR, android.graphics.Color.WHITE)
        _backgroundColor.value = savedColor

        // Cargar el modo de tema
        val savedThemeMode = sharedPreferences.getString(KEY_THEME_MODE, ThemeMode.LIGHT.name)
        _themeMode.value = ThemeMode.valueOf(savedThemeMode ?: ThemeMode.LIGHT.name)

        // Cargar preferencia de vista compacta
        val compactView = sharedPreferences.getBoolean(KEY_COMPACT_VIEW, false)
        _compactViewMode.value = compactView
    }

    fun setBackgroundColor(colorValue: Int) {
        sharedPreferences.edit().putInt(KEY_BACKGROUND_COLOR, colorValue).apply()
        _backgroundColor.value = colorValue
    }

    fun setThemeMode(mode: ThemeMode) {
        sharedPreferences.edit().putString(KEY_THEME_MODE, mode.name).apply()
        _themeMode.value = mode
    }

    fun setCompactViewMode(isCompact: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_COMPACT_VIEW, isCompact).apply()
        _compactViewMode.value = isCompact
    }

    fun getCompactViewMode(): Boolean {
        return sharedPreferences.getBoolean(KEY_COMPACT_VIEW, false)
    }
}