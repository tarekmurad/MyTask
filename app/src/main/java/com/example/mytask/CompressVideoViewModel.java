package com.example.mytask;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mytask.Utils.ImagePath;
import com.example.mytask.Utils.TaskItemNavigator;
import com.github.tcking.giraffecompressor.GiraffeCompressor;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class CompressVideoViewModel extends AndroidViewModel implements ViewModelProvider.Factory {

    private TaskItemNavigator mTaskItemNavigator;
    private Application mApplication;
    private Activity mActivity;

    public CompressVideoViewModel(@NonNull Application application, Activity activity, TaskItemNavigator taskItemNavigator) {
        super(application);
        mApplication = application;
        mTaskItemNavigator = taskItemNavigator;
        mActivity = activity;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new CompressVideoViewModel(mApplication, mActivity, mTaskItemNavigator);
    }

    public void onClick(View view) {
        requestStoragePermission();
    }

    private void requestStoragePermission() {
        Dexter.withActivity(mActivity)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            mTaskItemNavigator.openTaskDetails();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown
                            (List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                    }
                })
                .onSameThread()
                .check();
    }


    void prepareFiles(Intent data) {

        Uri mediaUri = data.getData();

        ImagePath imagePath = new ImagePath();
        String path = imagePath.getUriRealPath(mActivity, mediaUri);
        File inputFile = new File(path);
        File outputFile = new File(inputFile.getParent() + "/Compressed/compressed.mp4");

        if (inputFile.exists()) {

            long inputFileLength = inputFile.length();
            inputFileLength = inputFileLength / (1024 * 1024);

            if (inputFileLength > 10) {
                Compressor(inputFile, outputFile, mActivity);
            }

        }
    }

    private void Compressor(File inputFile, File outputFile, Activity mActivity) {
        GiraffeCompressor.create()
                .input(inputFile)
                .output(outputFile)
                .ready()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GiraffeCompressor.Result>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        Toast.makeText(mActivity, "Start Compressing", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(GiraffeCompressor.Result s) {
                        Toast.makeText(mActivity, "Input file size : " + Formatter.formatFileSize(mActivity, inputFile.length())
                                + "\nOutput file size : " + Formatter.formatFileSize(mActivity, new File(s.getOutput()).length()), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}