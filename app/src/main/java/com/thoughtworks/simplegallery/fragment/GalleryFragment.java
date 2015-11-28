package com.thoughtworks.simplegallery.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtworks.simplegallery.R;
import com.thoughtworks.simplegallery.adapter.GalleryAdapter;
import com.thoughtworks.simplegallery.model.Photo;
import com.thoughtworks.simplegallery.model.PhotoDirectory;
import com.thoughtworks.simplegallery.utils.PhotoLoader;
import com.thoughtworks.simplegallery.utils.PhotosLoadedCallback;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private GalleryAdapter galleryGridAdapter;

    private List<PhotoDirectory> data = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setRetainInstance(true);

        final View rootView = inflater.inflate(R.layout.fragment_gallery_gird, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.gallery);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        galleryGridAdapter = new GalleryAdapter(getActivity());
        recyclerView.setAdapter(galleryGridAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadPhotos();
    }

    private void loadPhotos() {
        getActivity().getSupportLoaderManager().initLoader(0, null, new PhotoLoader(getActivity(), new PhotosLoadedCallback() {
            @Override
            public void onLoadFinished(List<PhotoDirectory> directories) {
                data.clear();
                data.addAll(directories);

                List<Photo> photos = new ArrayList<>();
                for (PhotoDirectory dir : data) {
                    photos.addAll(dir.getPhotos());
                }

                galleryGridAdapter.setData(photos);
            }
        }));
    }
}
