package com.example.myfaith.utils

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.example.mynavigationapp.R

object WallpaperUtil {
    fun applyBackground(view: View, context: Context) {
        val sharedPref = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val wallpaperResId = sharedPref.getInt("wallpaper", R.color.zikr_green)

        view.setBackgroundResource(wallpaperResId)
    }
}
