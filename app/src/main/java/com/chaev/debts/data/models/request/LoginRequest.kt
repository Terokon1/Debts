package com.chaev.debts.data.models.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("username")
    val username: String?,
    @SerializedName("password")
    val password: String?
)