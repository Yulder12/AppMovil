package com.example.appmovil.utils

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.appmovil.R

class ColorUtils {
    private fun getComplementaryTextColor(backgroundColorRes: Int): Int {
        return when (backgroundColorRes) {
            R.color.theme_dark_gray, R.color.theme_navy, R.color.theme_green -> R.color.white
            else -> R.color.black
        }
    }

    private fun setTextColorRecursively(view: View, textColor: Int) {
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                setTextColorRecursively(view.getChildAt(i), textColor)
            }
        } else if (view is TextView) {
            view.setTextColor(ContextCompat.getColor(view.context, textColor))
        }
    }
}