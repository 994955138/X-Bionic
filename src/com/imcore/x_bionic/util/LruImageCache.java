package com.imcore.x_bionic.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class LruImageCache implements ImageCache {
	private LruCache<String, Bitmap> mCache;
	
	public LruImageCache(Context context) {
		mCache = new LruCache<String, Bitmap>(getCacheSize(context));
	}
	
	@Override
	public Bitmap getBitmap(String url) {
		return mCache.get(url);
	}
	
	@Override
	public void putBitmap(String url, Bitmap bitmap) {
	      mCache.put(url, bitmap);		
	}
	
	// Returns a cache size equal to approximately three screens worth of images.
    private int getCacheSize(Context ctx) {
        final DisplayMetrics displayMetrics = ctx.getResources().
                getDisplayMetrics();
        final int screenWidth = displayMetrics.widthPixels;
        final int screenHeight = displayMetrics.heightPixels;
        // 4 bytes per pixel
        final int screenBytes = screenWidth * screenHeight * 4;

        return screenBytes * 3;
    }
}
