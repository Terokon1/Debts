package com.chaev.debts.domain.models.base

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


@JsonClass(generateAdapter = true)
data class User(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "username")
    val username: String
)
