package com.chaev.debts.data.models.response

import com.squareup.moshi.Json

data class AccessResponse(
    @field:Json(name = "access")
    val access: String?
)
