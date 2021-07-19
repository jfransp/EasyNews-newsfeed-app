package com.example.newsfeed.util

fun convertToISO(language: String): String {
    return when (language) {
        "English" -> "en"
        "Portuguese" -> "pt"
        "French" -> "fr"
        "Russian" -> "ru"
        "Spanish" -> "es"
        "Italian" -> "it"
        "Dutch" -> "nl"
        "German" -> "de"
        "Northern Sami" -> "se"
        "Chinese" -> "zh"
        "Arabic" -> "ar"
        "Hebrew" -> "he"
        "Norwegian" -> "no"
        "ud" -> "ud"
        else -> ""
    }
}
