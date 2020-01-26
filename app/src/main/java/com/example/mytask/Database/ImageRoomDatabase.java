package com.example.mytask.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mytask.Image;

@Database(entities = {Image.class}, version = 1)
public abstract class ImageRoomDatabase extends RoomDatabase {

    public abstract ImageDao imageDao();

    private static ImageRoomDatabase INSTANCE;

    public static ImageRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ImageRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ImageRoomDatabase.class,
                                    "image_database").build();
                }
            }
        }
        return INSTANCE;
    }
}