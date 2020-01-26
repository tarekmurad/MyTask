package com.example.mytask;


import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.example.mytask.Database.ImageDao;
import com.example.mytask.Database.ImageRoomDatabase;
import com.example.mytask.Utils.ImageDataService;
import com.example.mytask.Utils.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageRepository {

    private ArrayList<Image> images = new ArrayList<>();
    private MutableLiveData<List<Image>> mutableLiveData = new MutableLiveData<>();
    private ImageDao imageDao;
    private ImageRoomDatabase db;


    public ImageRepository(Context context) {
        db = ImageRoomDatabase.getDatabase(context);
        imageDao = db.imageDao();
    }

    public MutableLiveData<List<Image>> getMutableLiveData(int currentPage) {
        final ImageDataService userDataService = RetrofitClient.getService();
        Call<ImageResponse> call = userDataService.getImages(RetrofitClient.API_KEY, currentPage, 10);

        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                ImageResponse imageDBResponse = response.body();
                if (imageDBResponse != null && imageDBResponse.getTotalHits() != null) {
                    images = (ArrayList<Image>) imageDBResponse.getHits();
                    updateDatabase(images);
                    mutableLiveData.setValue(images);
                }
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                getImagesFromDatabase();
                mutableLiveData.setValue(images);
            }
        });
        return mutableLiveData;
    }

    public void updateDatabase(ArrayList<Image> images) {
        deleteImagesFromDatabase();
        for (Image image : images) {
            insertImageIntoDatabase(image);
        }
    }


    ////////////////////////////////////////////////////////////////////

    public void deleteImagesFromDatabase() {
        DeleteAsyncTask task = new DeleteAsyncTask(imageDao);
        task.execute();
    }

    public void insertImageIntoDatabase(Image newImage) {
        InsertAsyncTask task = new InsertAsyncTask(imageDao);
        task.execute(newImage);
    }

    public void getImagesFromDatabase() {
        QueryAsyncTask task = new QueryAsyncTask(imageDao);
        task.delegate = this;
        task.execute();
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    private static class DeleteAsyncTask extends AsyncTask<String, Void, Void> {

        private ImageDao asyncTaskDao;

        DeleteAsyncTask(ImageDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            asyncTaskDao.delete();
            return null;
        }
    }

    private static class InsertAsyncTask extends AsyncTask<Image, Void, Void> {

        private ImageDao asyncTaskDao;

        InsertAsyncTask(ImageDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Image... params) {
            asyncTaskDao.insertAll(params);
            return null;
        }
    }

    private static class QueryAsyncTask extends AsyncTask<String, Void, List<Image>> {

        private ImageDao asyncTaskDao;
        private ImageRepository delegate = null;

        QueryAsyncTask(ImageDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected List<Image> doInBackground(final String... params) {
            return asyncTaskDao.getAll();
        }

        @Override
        protected void onPostExecute(List<Image> result) {
            delegate.asyncFinished(result);
        }
    }

    private List<Image> asyncFinished(List<Image> results) {
        mutableLiveData.setValue(results);
        return results;
    }
}