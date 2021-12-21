package com.chaev.debts.data.models.request


import com.squareup.moshi.Json

data class FriendReqPatch (
    @field:Json(name = "id")
    val id: String,
    @field:Json (name = "status")
    val status: String
)