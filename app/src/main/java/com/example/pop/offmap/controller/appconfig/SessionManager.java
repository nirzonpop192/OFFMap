package com.example.pop.offmap.controller.appconfig;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * @author: pop
 * @date: 7/8/2016
 * This class will save the log in session state
 * until new user log in
 */
public class SessionManager {
    /**
     * for lagcat
     */
    private static final String TAG = "SessionManager";


    /**
     * to save the save some values in device stroage
     */
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    /**
     * to detect & get the trace who  is  call this class
     * need the Context class & also enable editor object into edite
     * mode
     */
    private Context mContext;

    /**
     * necessary Constant
     * SharedPreference mode
     */

    private static final int PRIVATE_MODE = 0;

    /**
     * USE THIS CONSTANT IN WHOLE APP
     */
    public static final String PREF_NAME = "OFFMapAppLogIn";

    /**
     *
     */
    private static final String KEY_USER_NAME = "UserName";
    private static final String KEY_USER_ID = "UserId";
    private static final String KEY_USER_PASSWORD = "UserPassword";
    private static final String KEY_IS_LOG_IN = "IsLogIn";

    String s = KEY_IS_LOG_IN;

    /**
     * In the constructor Enable the edit mode of Editor
     *
     * @param mContext
     */
    public SessionManager(Context mContext) {
        this.mContext = mContext;
        mPref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        mEditor = mPref.edit();
    }

    /**
     * @param isLogIn
     */

    public void setLogInSession(boolean isLogIn) {

        mEditor.putBoolean(KEY_IS_LOG_IN, isLogIn);
        mEditor.commit();

        Log.d(TAG, "User login session modified!");
    }

    /**
     *
     * @return boolean value from store
     */

    public boolean getLogInSession() {

        return mPref.getBoolean(KEY_IS_LOG_IN, false);
    }

    /**
     *
     * @return
     */


    public String getUserId() {
        return mPref.getString(KEY_USER_ID,"UserId");
    }

    /**
     *
     * @param userId
     */

    public void setUserId(String userId) {
        mEditor.putString(KEY_USER_ID,userId) ;
        mEditor.commit();
    }

    /**
     *
     * @return
     */

    public String getUseName() {
        return mPref.getString(KEY_USER_NAME,"UserName");
    }

    /**
     *
     * @param useName
     */

    public void setUseName(String useName) {
        mEditor.putString(KEY_USER_NAME,useName) ;
        mEditor.commit();
    }

    /**
     *
     * @return
     */

    public String getUsePass() {
        return mPref.getString(KEY_USER_PASSWORD,"UserPassword");
    }

    /**
     *
     * @param usePass
     */
    public void setUsePass(String usePass) {
        mEditor.putString(KEY_USER_PASSWORD,usePass) ;
        mEditor.commit();
    }
}
