package com.example.mytask;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytask.databinding.ImageItemBinding;

import java.util.ArrayList;

public class ImageDataAdapter extends RecyclerView.Adapter<ImageDataAdapter.ViewHolder> {

    private ArrayList<Image> Images;
    private ImageAdapterListener listener;

    public ImageDataAdapter(ImageAdapterListener listener) {
        this.listener = listener;
    }

    public interface ImageAdapterListener {
        void onPostClicked(Image image, int postion);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ImageItemBinding imageItemBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.image_item, viewGroup, false);

        return new ViewHolder(imageItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder ViewHolder, int i) {
        Image currentImage = Images.get(i);

        ViewHolder.imageItemBinding.setImage(currentImage);
        ViewHolder.imageItemBinding.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onPostClicked(currentImage, i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (Images != null) {
            return Images.size();
        } else {
            return 0;
        }
    }

    public void setImageList(ArrayList<Image> images) {
        if (Images != null)
            Images.addAll(images);
         else
            Images = images;

        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageItemBinding imageItemBinding;

        public ViewHolder(@NonNull ImageItemBinding imageItemBinding) {
            super(imageItemBinding.getRoot());
            this.imageItemBinding = imageItemBinding;
        }
    }
}