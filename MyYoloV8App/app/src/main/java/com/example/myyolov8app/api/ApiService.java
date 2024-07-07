package com.example.myyolov8app.api;

import com.example.myyolov8app.Constants;
import com.example.myyolov8app.model.AdminUser;
import com.example.myyolov8app.model.AllUsers;
import com.example.myyolov8app.model.ChangePassword;
import com.example.myyolov8app.model.Classify;
import com.example.myyolov8app.model.DeleteHis;
import com.example.myyolov8app.model.HistoryData;
import com.example.myyolov8app.model.Users;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    Gson gson =new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl(Constants.URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);
    @POST("/register")
    Call<Users> registerUser(@Body Users user);
    @POST("/login")
    Call<Users> loginUser(@Body Users user);
    @POST("/delete_data")
    Call<DeleteHis> deleteHistory(@Body DeleteHis delete);
    @Multipart
    @POST("/classify")
    Call<Classify> classify(@Header("Authorization") String token,
                            @Part MultipartBody.Part File);
    @Multipart
    @POST("/changeUsername")
    Call<Users> changeProfile(@Part MultipartBody.Part File,@Part("email") RequestBody email,
                              @Part("username") RequestBody username);
    @POST("/change-password")
    Call<ChangePassword> changePassword(@Header("Authorization") String token,
                                        @Body ChangePassword changePassword);
    @GET("/history")
    Call<HistoryData> getHistory(@Header("Authorization") String token);
    @GET("/getAllUsers")
    Call<AllUsers> getAllUser();
    @GET("/detailUser/{email}")
    Call<AdminUser> getDetailUser(@Path("email") String email);

}
