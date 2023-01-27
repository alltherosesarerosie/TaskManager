package com.geektech.taskmanager.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences


class Pref(private val context: Context) {
    private  val pref = context.getSharedPreferences(PREF_NAME,  MODE_PRIVATE)
    private lateinit var name: String


    fun isUserSeen():Boolean{
        return pref.getBoolean(SEEN_KEY, false)
    }

    fun saveSeen(){
        pref.edit().putBoolean(SEEN_KEY, true).apply()
    }

    fun saveName(name: String){
        pref.edit().putString(NAME_KEY, name).apply()
    }

    fun getName():String{
        return pref.getString(NAME_KEY,"hellor").toString()
    }

















    companion object{
        const val PREF_NAME = "Task.pref"
        const val SEEN_KEY = "seen.key"
        const val NAME_KEY = "name.key"
    }
}