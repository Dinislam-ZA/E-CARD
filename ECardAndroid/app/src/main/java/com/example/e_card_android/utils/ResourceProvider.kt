package com.example.e_card_android.utils

import android.content.Context

interface ResourceProvider {
    fun getString(resId: Int): String
    fun getString(resId: Int, fieldName: String): String
}

class DefaultResourceProvider(private val context: Context) : ResourceProvider {
    override fun getString(resId: Int): String {
        return context.getString(resId)
    }

    override fun getString(resId: Int, fieldName: String): String {
        return context.getString(resId, fieldName)
    }
}
