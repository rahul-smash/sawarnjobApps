package com.sarwarajobsapp.base

import com.sarwarajobsapp.modelattend.AttendanceModell
import com.sarwarajobsapp.modelattend.CanddiateAttendanceModell
import com.sarwarajobsapp.modelattend.CanddiateEditProfileModell
import com.sarwarajobsapp.modelattend.NewPostionExperience
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

        @Part("full_name") first_name: RequestBody?,
      //  @Part("last_name") last_name: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part("phone") phone: RequestBody?,
        @Part("dob") dob: RequestBody?,
        @Part("looking_job_type") looking_job_type: RequestBody?,

        @Part("address") address: RequestBody?,





        @Part aadhar: MultipartBody.Part?,
        @Part upload_file: MultipartBody.Part?,
        @Part upload_files: MultipartBody.Part?,
        ): Observable<AttendanceModell>?



    @POST("candidate/education/add")
    @Multipart
    fun  candidateeducationeadd(
        @Part("user_id") user_id: RequestBody?,
        @Part("school") school: RequestBody?,
        @Part("specialized") specialized: RequestBody?,
   /*     @Part("started_at") started_at: RequestBody?,
        @Part("ended_at") ended_at: RequestBody?,*/
        @Part("description") description: RequestBody?,

      //  @Part upload_file: MultipartBody.Part?,


        ): Observable<CanddiateAttendanceModell>?
    @POST("candidate/edit")
    @Multipart

    fun candidateedit(
        @Part("user_id") admin_user_id: RequestBody?,

        @Part("full_name") first_name: RequestBody?,
      //  @Part("last_name") last_name: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part("phone") phone: RequestBody?,
        @Part("dob") dob: RequestBody?,
        @Part("address") address: RequestBody?,
        @Part("looking_job_type") looking_job_type: RequestBody?,
        @Part("description") description: RequestBody?,


        @Part aadhar: MultipartBody.Part?,
        @Part upload_file: MultipartBody.Part?,
        @Part upload_files: MultipartBody.Part?,
        ): Observable<CanddiateEditProfileModell>?

    @POST("candidate/experience/add")
    @Multipart

    fun candidateExperienceAdd(
        @Part("user_id") user_id: RequestBody?,

        @Part("company") company: RequestBody?,
        @Part("position") position: RequestBody?,
    //    @Part("started_at") started_at: RequestBody?,
     //   @Part("ended_at") ended_at: RequestBody?,
        @Part("description") description: RequestBody?,

     //   @Part upload_file: MultipartBody.Part?,

        ): Observable<NewPostionExperience>?
}