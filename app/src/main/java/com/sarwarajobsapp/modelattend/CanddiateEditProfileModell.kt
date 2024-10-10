package com.sarwarajobsapp.modelattend

import com.google.gson.annotations.SerializedName

//data class AttendanceModell(@SerializedName("result")val result: Int ,@SerializedName("errors") val errors: String)


data class CanddiateEditProfileModell(
        @SerializedName("message") val msg: String,
        @SerializedName("data") val data: List<CandidateData>
)

data class CandidateData(
        @SerializedName("id") val id: Int,
        @SerializedName("full_name") val fullName: String,
        @SerializedName("email") val email: String?,
        @SerializedName("phone") val phone: String,
        @SerializedName("address") val address: String,
        @SerializedName("dob") val dob: String,
        @SerializedName("looking_job_type") val lookingJobType: String,
        @SerializedName("aadhar") val aadhar: String,
        @SerializedName("resume") val resume: String?,
        @SerializedName("gender") val gender: String,
        @SerializedName("state_id") val stateId: Int,
        @SerializedName("city_id") val cityId: Int,
        @SerializedName("profile_img") val profileImg: String?,
        @SerializedName("description") val description: String?
)

