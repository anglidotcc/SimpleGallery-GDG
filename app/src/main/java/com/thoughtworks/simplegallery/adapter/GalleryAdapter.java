package com.thoughtworks.simplegallery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.thoughtworks.simplegallery.R;
import com.thoughtworks.simplegallery.model.Photo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<PhotoViewHolder> {

    private Context context;
    private final int imageSize;
    private List<Photo> data = new ArrayList<>();

    public GalleryAdapter(Context context) {
        this.context = context;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        imageSize = metrics.widthPixels / 3;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);
        return new PhotoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        Glide.with(context)
                .load(new File(data.get(position).getPath()))
                .centerCrop()
                .dontAnimate()
                .thumbnail(0.5f)
                .override(imageSize, imageSize)
                .placeholder(R.mipmap.ic_photo_black_48dp)
                .error(R.mipmap.ic_broken_image_black_48dp)
                .into(holder.ivPhoto);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Photo> photos) {
        data.clear();
        data.addAll(photos);
        notifyDataSetChanged();
    }
}

class PhotoViewHolder extends RecyclerView.ViewHolder {
    ImageView ivPhoto;

    public PhotoViewHolder(View itemView) {
        super(itemView);
        ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
    }
}