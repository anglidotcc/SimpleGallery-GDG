package com.thoughtworks.simplegallery;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.thoughtworks.simplegallery.fragment.GalleryFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_FRAGMENT = "gallery_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        GalleryFragment galleryFragment = (GalleryFragment) fm.findFragmentByTag(TAG_FRAGMENT);

        if (galleryFragment == null) {
            galleryFragment = new GalleryFragment();
            fm.beginTransaction().add(galleryFragment, TAG_FRAGMENT).commit();
        }
    }
}
