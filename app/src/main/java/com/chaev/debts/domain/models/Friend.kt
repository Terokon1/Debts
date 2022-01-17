package com.chaev.debts.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Friend(
    val id: String,
    val username: String
) : Parcelable
