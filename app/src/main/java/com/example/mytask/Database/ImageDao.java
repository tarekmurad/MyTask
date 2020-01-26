package com.example.mytask.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mytask.Image;

import java.util.List;

@Dao
public interface ImageDao {

    @Query("SELECT * FROM image")
    List<Image> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Image... images);

    @Insert
    void insert(Image images);

    @Query("DELETE FROM  image")
    void delete();

}