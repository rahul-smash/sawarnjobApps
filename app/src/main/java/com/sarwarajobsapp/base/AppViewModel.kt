package com.sarwarajobsapp.base

import com.sarwarajobsapp.modelattend.AttendanceModell
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface AppViewModel {




    @POST("candidate/add")
    @Multipart

    fun candiateAdd(
        @Part("admin_user_id") admin_user_id: RequestBody?,

        @Part("first_name") first_name: RequestBody?,
        @Part("last_name") last_name: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part("phone") phone: RequestBody?,
        @Part("dob") dob: RequestBody?,
        @Part("looking_job_type") looking_job_type: RequestBody?,

        @Part("address") address: RequestBody?,





        @Part aadhar: MultipartBody.Part?,

        ): Observable<AttendanceModell>?


}