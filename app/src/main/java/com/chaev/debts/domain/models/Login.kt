package com.chaev.debts.domain.models


data class Login (
    val accessToken: String,
    val refreshToken: String
)