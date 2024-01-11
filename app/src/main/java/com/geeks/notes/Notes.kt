package com.geeks.notes

import java.io.Serializable

data class Notes(
    val title: String? = null,
    val desc: String? = null,
    val date: String? = null,
    val image: String? = null
) : Serializable
