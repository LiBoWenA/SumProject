package com.example.tiamo.sumproject.callback;

import android.app.Activity;
import android.widget.ImageView;

public interface ImageLoader {
    void displayImage(Activity activity, String path, ImageView imageView, int width, int height);
    void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height);
    void clearMemoryCache();
}
