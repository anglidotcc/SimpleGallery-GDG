package com.thoughtworks.simplegallery.utils;

import com.thoughtworks.simplegallery.model.PhotoDirectory;

import java.util.List;

public interface PhotosLoadedCallback {
    void onLoadFinished(List<PhotoDirectory> directories);
}
