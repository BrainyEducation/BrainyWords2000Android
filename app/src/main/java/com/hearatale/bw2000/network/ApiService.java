package com.hearatale.bw2000.network;

import com.hearatale.bw2000.data.model.Analytic.BaseResponseAnalytic;
import com.hearatale.bw2000.data.model.Analytic.PayLoadAnalytic;
import com.hearatale.bw2000.data.model.BaseResponseGetAllStudent;
import com.hearatale.bw2000.data.model.student.BaseResponseCreateStudent;
import com.hearatale.bw2000.data.model.student.PayloadStudent;
import com.hearatale.bw2000.data.model.student.BaseResponseStudentLogin;
import com.hearatale.bw2000.data.model.teacher_login.BaseResponseTeacherLogin;
import com.hearatale.bw2000.data.model.teacher_login.PayLoadLogin;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

//    @GET("students")
//    Call<BaseRespone> getAllStudent(@HeaderMap Map<String,String> headers);

    @GET("students")
    Single<BaseResponseGetAllStudent> getAllStudents(@Header("Authorization") String key);


    @POST("session/login")
    @Headers("{Content-Type:application/json}")
    Single<BaseResponseTeacherLogin> startTeacherLogin(@Body PayLoadLogin payLoadLogin);

    @POST("session/student")
    Single<BaseResponseStudentLogin> startStudentLogin(@Body Map<String,String> payload);



    @POST("analytics/application")
    Single<BaseResponseAnalytic> startPostAnalytics(@HeaderMap Map<String,String> header, @Body PayLoadAnalytic payLoadAnalytic);

    @POST("student")
    Single<BaseResponseCreateStudent> createStudent(@HeaderMap Map<String,String> header, @Body PayloadStudent payloadStudent);

}
