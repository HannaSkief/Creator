package com.example.ic2.retrofit;

import com.example.ic2.model.Report;
import com.example.ic2.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface IApi {

    @POST("login")
    Call<User> login(@Body User user);

    @POST("reports")
    Call<Report> sendReport(@Header("Authorization")String token,
                            @Body Report report);

}
