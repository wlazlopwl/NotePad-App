package com.appdevpwl.notes

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences (val context: Context){
val PREFS_NAME = "note_settings"
    val FONT_SIZE="Font size"

private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun getFontSize():Int{
        return sharedPreferences.getInt(FONT_SIZE,1)
    }

    fun setFontSize(size:Int){
        editor.putInt(FONT_SIZE,size)
        editor.apply()
    }




}