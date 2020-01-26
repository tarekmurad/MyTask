package com.example.mytask;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.mytask.Utils.TaskItemNavigator;
import com.example.mytask.databinding.ActivityCompressVideoBinding;
import com.github.tcking.giraffecompressor.GiraffeCompressor;

import java.io.File;

public class CompressVideoActivity extends AppCompatActivity implements TaskItemNavigator {

    private CompressVideoViewModel compressVideoViewModel;
    private ActivityCompressVideoBinding activityCompressVideoBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compress_video);

        compressVideoViewModel = ViewModelProviders.of(this, new CompressVideoViewModel(this.getApplication(), this, this)).get(CompressVideoViewModel.class);

        activityCompressVideoBinding = DataBindingUtil.setContentView(CompressVideoActivity.this, R.layout.activity_compress_video);
        activityCompressVideoBinding.setLifecycleOwner(this);
        activityCompressVideoBinding.setCompressVideoViewModel(compressVideoViewModel);

        GiraffeCompressor.init(this);
    }

    @Override
    public void openTaskDetails() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 4);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            compressVideoViewModel.prepareFiles(data);
        }
    }


}
