package com.example.mytask;


import androidx.lifecycle.MutableLiveData;

import com.example.mytask.Utils.ImageDataService;
import com.example.mytask.Utils.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageRepository {
    private static final String TAG = "ImageRepository";

    private ArrayList<Image> images = new ArrayList<>();
    private MutableLiveData<List<Image>> mutableLiveData = new MutableLiveData<>();

    public ImageRepository() {
    }

    public MutableLiveData<List<Image>> getMutableLiveData(int currentPage) {
        final ImageDataService userDataService = RetrofitClient.getService();
        Call<ImageDBResponse> call = userDataService.getImages(RetrofitClient.API_KEY, currentPage, 10);

        call.enqueue(new Callback<ImageDBResponse>() {
            @Override
            public void onResponse(Call<ImageDBResponse> call, Response<ImageDBResponse> response) {
                ImageDBResponse imageDBResponse = response.body();
                if (imageDBResponse != null && imageDBResponse.getTotalHits() != null) {
                    images = (ArrayList<Image>) imageDBResponse.getHits();
                    mutableLiveData.setValue(images);
                }
            }

            @Override
            public void onFailure(Call<ImageDBResponse> call, Throwable t) {
            }
        });
        return mutableLiveData;
    }
}