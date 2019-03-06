package com.dev.peralta.marqueeventos.util

import android.content.Context
import com.dev.peralta.marqueeventos.model.Local
import com.google.gson.Gson
import java.nio.charset.Charset

inline fun Context?.getLocal(gson: Gson, context: Context.(local: Local?) -> Unit) {
    try {
        val reader = this?.openFileInput("local.json")?.reader(Charset.defaultCharset())
        reader?.use {
            val local = gson.fromJson(it, Local::class.java)
            this?.context(local)
        }
    } catch (ex: Exception) {
        this?.context(null)
    }
}