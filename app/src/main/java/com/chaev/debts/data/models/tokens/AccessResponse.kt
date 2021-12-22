package com.chaev.debts.data.models.tokens

import com.squareup.moshi.Json

data class AccessResponse(
    @field:Json(name = "access")
    val access: String?,
    @field:Json(name = "refresh")
    val refresh: String?
)
