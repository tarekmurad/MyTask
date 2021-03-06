package com.example.mytask.Utils;

import com.example.mytask.ImageResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ImageDataService {

    @GET("?q=flower&image_type=photo")
    Call<ImageResponse> getImages(@Query("key") String key, @Query("page") int page, @Query("per_page") int per_page);
}
