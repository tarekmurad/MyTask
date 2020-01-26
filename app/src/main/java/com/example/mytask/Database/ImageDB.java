package com.example.mytask.Database;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Images")
public class ImageDB {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "largeImageURL")
    private String largeImageURL;


}