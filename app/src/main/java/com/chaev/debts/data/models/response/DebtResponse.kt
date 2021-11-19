package com.chaev.debts.data.models.response

import com.google.gson.annotations.SerializedName

data class DebtResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("money")
    val money: String?,
    @SerializedName("creditor")
    val creditor: String?,
    @SerializedName("debtor")
    val debtor: String?,
    @SerializedName("created")
    val created: String?
)
