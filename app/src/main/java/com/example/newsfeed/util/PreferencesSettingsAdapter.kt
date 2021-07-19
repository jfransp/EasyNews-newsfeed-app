package com.example.newsfeed.util

import com.example.newsfeed.R

//Adapts strings to Ids(Int) for setting the switches at the settings screen according to user preferences
// (and vice-versa for setting preferences according to switch clicks).
fun adaptPreferenceFromDataStore(data: String): Int {

    return when (data) {
        "general" -> R.id.general
        "business" -> R.id.business
        "entertainment" -> R.id.entertainment
        "health" -> R.id.health
        "science" -> R.id.science
        "sports" -> R.id.sports
        "technology" -> R.id.technology
        "us" -> R.id.us
        "gb" -> R.id.gb
        "ru" -> R.id.ru
        "br" -> R.id.br
        "de" -> R.id.de
        "au" -> R.id.au
        "ca" -> R.id.ca
        "in" -> R.id.`in`
        "it" -> R.id.it
        "fr" -> R.id.fr
        "cn" -> R.id.cn
        "es" -> R.id.es
        else -> R.id.us
    }
}

fun adaptPreferenceToDataStore(id: Int): String {

    return when (id) {
        R.id.general -> "general"
        R.id.technology -> "technology"
        R.id.business -> "business"
        R.id.entertainment -> "entertainment"
        R.id.health -> "health"
        R.id.science -> "science"
        R.id.sports -> "sports"
        R.id.us -> "us"
        R.id.gb -> "gb"
        R.id.ru -> "ru"
        R.id.br -> "br"
        R.id.de -> "de"
        R.id.au -> "au"
        R.id.ca -> "ca"
        R.id.`in` -> "in"
        R.id.it -> "it"
        R.id.fr -> "fr"
        R.id.cn -> "cn"
        R.id.es -> "es"
        else -> "us"
    }
}
