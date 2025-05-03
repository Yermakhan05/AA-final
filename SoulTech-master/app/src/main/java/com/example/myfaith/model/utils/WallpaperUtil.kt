package com.example.myfaith.model.utils

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.example.myfaith.R

object WallpaperUtil {
    fun applyBackground(view: View, context: Context) {
        val sharedPref = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val wallpaperResId = sharedPref.getInt("wallpaper", R.color.zikr_green)

        view.setBackgroundResource(wallpaperResId)
    }
}
