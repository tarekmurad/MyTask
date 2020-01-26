package com.example.mytask;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytask.Utils.EndlessRecyclerViewScrollListener;
import com.example.mytask.Utils.SessionManager;
import com.example.mytask.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity implements ImageDataAdapter.ImageAdapterListener {

    private MainViewModel mainViewModel;
    private ImageDataAdapter imageDataAdapter;
    private ActivityMainBinding activityMainBinding;
    SessionManager sessionManager;

    EndlessRecyclerViewScrollListener scrollListener;
    boolean isLoading = false;
    int currentPage = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        String EMAIL = user.get(SessionManager.EMAIL);
        String PASSWORD = user.get(SessionManager.PASSWORD);

        mainViewModel = ViewModelProviders.of(this, new MainViewModel(this.getApplication(), this)).get(MainViewModel.class);

        activityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        activityMainBinding.setLifecycleOwner(this);
        activityMainBinding.setMainViewModel(mainViewModel);

        // bind RecyclerView
        RecyclerView recyclerView = activityMainBinding.viewImages;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        imageDataAdapter = new ImageDataAdapter(this);
        recyclerView.setAdapter(imageDataAdapter);

//        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                // Triggered only when new data needs to be appended to the list
//                // Add whatever code is needed to append new items to the bottom of the list
//                // loadNextDataFromApi(page);
//                isLoading = true;
//                currentPage = currentPage + 1;
//                getImagesFromDatabase(currentPage);
//
//            }
//        };
//        // Adds the scroll listener to RecyclerView
//        recyclerView.addOnScrollListener(scrollListener);

        getImages(currentPage);
    }

    @Override
    public void onPostClicked(Image image, int postion) {
        Toast.makeText(getApplicationContext(), "Image " + postion + " clicked!", Toast.LENGTH_SHORT).show();
    }

    private void getImages(int currentPage) {
        mainViewModel.getImages(currentPage).observe(this, new Observer<List<Image>>() {
            @Override
            public void onChanged(@Nullable List<Image> images) {
                imageDataAdapter.setImageList((ArrayList<Image>) images);
                ProgressBar progressBar = activityMainBinding.progressBar;
                progressBar.setVisibility(View.GONE);
            }
        });
    }


}
