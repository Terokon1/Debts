package com.chaev.debts.domain.models

import com.google.gson.annotations.SerializedName

data class Login (
    val accessToken: String,
    val refreshToken: String
        )