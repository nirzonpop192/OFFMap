package com.example.pop.offmap.controller.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by pop on 7/8/2016.
 * <p>
 *
 */
public class NetConnectionAdapter {


    private Context mContext;


   // private static NetConnectionAdapter netConnectInstance;

    public NetConnectionAdapter( Context context) {
        this.mContext=context;
    }

 /*   public static NetConnectionAdapter getNetConInstance() {
        return netConnectInstance;
    }
*/
    /**
     *
     *
     * @return
     */

    public  boolean isConnectingToInternet() {

        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo[] info = manager.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }

        }
        return false;
    }
}
