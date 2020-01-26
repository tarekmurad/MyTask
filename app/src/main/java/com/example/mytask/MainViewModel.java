package com.example.mytask;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;


public class MainViewModel extends AndroidViewModel implements ViewModelProvider.Factory {

    private ImageRepository ImageRepository;
    private Application mApplication;
    private Context mContext;

    public MainViewModel(@NonNull Application application,  Context context) {
        super(application);
        ImageRepository = new ImageRepository(context);
        mApplication = application;
        mContext = context;

    }

    public LiveData<List<Image>> getImages(int currentPage) {
        return ImageRepository.getMutableLiveData(currentPage);
    }

    public void onClick(View view) {
        Intent intent = new Intent(mContext, CompressVideoActivity.class);
        mContext.startActivity(intent);

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MainViewModel(mApplication, mContext);
    }
}