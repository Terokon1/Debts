package com.chaev.debts.data.models.request

import com.google.gson.annotations.SerializedName

data class DebtRequest(
    @SerializedName("money")
    val money: String?,
    @SerializedName("creditor")
    val creditor: String?,
    @SerializedName("debtor")
    val debtor: String?,
    @SerializedName("description")
    val description: String?
)

