package com.sarwarajobsapp.modelattend

import com.google.gson.annotations.SerializedName

//data class AttendanceModell(@SerializedName("result")val result: Int ,@SerializedName("errors") val errors: String)


data class AttendanceModell(
        @SerializedName("message") val msg: String,
        @SerializedName("data") val data: AttendanceData,
)

data class AttendanceData(

    @SerializedName("user_id")
    val userID: String,






)
