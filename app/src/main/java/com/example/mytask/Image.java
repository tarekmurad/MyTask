package com.example.mytask;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mytask.R;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("largeImageURL")
    private String largeImageURL;

    public String getLargeImageURL() {
        return largeImageURL;
    }

    public void setLargeImageURL(String largeImageURL) {
        this.largeImageURL = largeImageURL;
    }

    @BindingAdapter({"avatar"})
    public static void loadImage(ImageView imageView, String imageURL) {

        Glide.with(imageView.getContext())
                .load(imageURL)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(imageView);


    }
}