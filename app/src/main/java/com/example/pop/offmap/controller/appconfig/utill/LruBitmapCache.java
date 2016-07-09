package com.example.pop.offmap.controller.appconfig.utill;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * @autor: pop on 7/5/2016.
 * <p/>
 * This class is required to handle image cache.
 */
public class LruBitmapCache extends LruCache<String, Bitmap>
        implements ImageLoader.ImageCache {
    /**
     * <p>
     * Get max available VM memory, exceeding this amount will throw an
     * OutOfMemory exception. Stored in kilobytes as LruCache takes an
     * int in its constructor.
     * <p/>
     * </p>
     * Use 1/8th of the available memory for this memory cache.
     * <p/>
     * The cache size will be measured in kilobytes rather than
     *
     * @return
     */

    public static int getDefaultLruCacheSize() {

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        return cacheSize;
    }

    public LruBitmapCache() {

        this(getDefaultLruCacheSize());


    }

    public LruBitmapCache(int maxSizeInKiloByte) {
        super(maxSizeInKiloByte);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes()*value.getHeight()/1024;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
            put(url, bitmap);
    }
}
