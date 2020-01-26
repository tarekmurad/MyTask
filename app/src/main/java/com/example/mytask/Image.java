package com.example.mytask;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Image")
public class Image {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "largeImageURL")
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