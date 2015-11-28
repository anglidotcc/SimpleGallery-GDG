package com.thoughtworks.simplegallery.utils;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.thoughtworks.simplegallery.model.PhotoDirectory;

import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static android.provider.MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME;
import static android.provider.MediaStore.Images.ImageColumns.BUCKET_ID;
import static android.provider.MediaStore.MediaColumns.DATA;
import static android.provider.MediaStore.MediaColumns.DATE_ADDED;
import static android.provider.MediaStore.MediaColumns.MIME_TYPE;

public class PhotoLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context context;
    private PhotosLoadedCallback callback;

    public PhotoLoader(Context context, PhotosLoadedCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyLoader(context);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) {

            List<PhotoDirectory> directories = new ArrayList<>();

            while (data.moveToNext()) {

                int imageId = data.getInt(data.getColumnIndexOrThrow(_ID));
                String bucketId = data.getString(data.getColumnIndexOrThrow(BUCKET_ID));
                String name = data.getString(data.getColumnIndexOrThrow(BUCKET_DISPLAY_NAME));
                String path = data.getString(data.getColumnIndexOrThrow(DATA));

                PhotoDirectory photoDirectory = new PhotoDirectory();
                photoDirectory.setId(bucketId);
                photoDirectory.setName(name);

                if (!directories.contains(photoDirectory)) {
                    photoDirectory.setCoverPath(path);
                    photoDirectory.addPhoto(imageId, path);
                    photoDirectory.setDateAdded(data.getLong(data.getColumnIndexOrThrow(DATE_ADDED)));
                    directories.add(photoDirectory);
                } else {
                    directories.get(directories.indexOf(photoDirectory)).addPhoto(imageId, path);
                }
            }

            if (callback != null) {
                callback.onLoadFinished(directories);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

class MyLoader extends CursorLoader {

    final String[] IMAGE_PROJECTION = {
            Media._ID,
            Media.DATA,
            Media.BUCKET_ID,
            Media.BUCKET_DISPLAY_NAME,
            Media.DATE_ADDED
    };

    MyLoader(Context context) {
        super(context);
        setProjection(IMAGE_PROJECTION);
        setUri(Media.EXTERNAL_CONTENT_URI);
        setSortOrder(Media.DATE_ADDED + " DESC");
        setSelection(MIME_TYPE + "=? or " + MIME_TYPE + "=?");
        String[] selectionArgs = new String[]{"image/jpeg", "image/png"};
        setSelectionArgs(selectionArgs);
    }
}