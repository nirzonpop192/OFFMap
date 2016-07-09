package com.example.pop.offmap.controller.appconfig;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.pop.offmap.controller.appconfig.utill.LruBitmapCache;

/**
 * @author: faisal
 * @date: 7/5/2016.
 * This is the Singleton  class
 * which has only one object.
 * <p>
 * <p>The recommended approach is to implement a singleton class that encapsulates RequestQueue
 * and other Volley functionality. Another approach is to subclass Application and set up the
 * RequestQueue in Application.onCreate().
 * But this approach is discouraged; a static singleton can provide the same functionality
 * in a more modular way.
 * A key concept is that the RequestQueue must be instantiated with the Application context,
 * not an Activity context. This ensures that the RequestQueue will last for the lifetime of
 * your app, instead of being recreated every time the activity is recreated (for example,
 * when the user rotates the device).</p>
 */
public class AppController extends Application {
    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;

    private ImageLoader mImageLoader;


    /**
     * create class static  object
     */
    private static AppController mInstance;

    private boolean status = false;

    /**
     * total_record: will show toll record to be upload
     * to serve db
     */

    private int total_record = 0;

    private int current_record = 0;

    private Context main_view;


    boolean getAsyStatus() {
        return this.status;
    }

    public int getTotal_record() {
        return total_record;
    }

    public void setTotal_record(int total_record) {
        this.total_record = total_record;
    }

    public int getCurrent_record() {
        return current_record;
    }

    public void setDefalut_record() {
        this.current_record = 0;
    }

    public Context getMain_view() {
        return main_view;
    }

    public void setMain_view(View view) {
        this.main_view = view.getContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }


    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getmRequestQueue() {

        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return this.mRequestQueue;
    }

    /**
     * Online image loader Image Loader
     */

    public ImageLoader getmImageLoader() {


        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(getmRequestQueue(), new LruBitmapCache());
        }

        return this.mImageLoader;

    }


    public <T> void addToRequestQueue(Request<T> request, String tag) {
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

        // 6o second

        int socketTimeOut = 60000;

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        request.setRetryPolicy(retryPolicy);
        getmRequestQueue().add(request);
    }


    public <T>void addToRequestQueue(Request request){

        request.setTag(TAG);
        getmRequestQueue().add(request);
    }

    public void cancelPendingRequests(Object tag){

        if (mRequestQueue!=null){
            mRequestQueue.cancelAll(tag);
        }
    }


    public void cleanCacheMemory(){
        getInstance().getmRequestQueue().getCache().clear();
    }
}
