package com.example.myyolov8app.data_local;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharePreferences {
    private static final String MY_SHARE_PREFERENCES ="MY_SHARE_PREFERENCES";
    private Context context;

    public MySharePreferences(Context context) {
        this.context = context;
    }
    public void putStringValue(String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARE_PREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public  String getStringValue(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences("MY_SHARE_PREFERENCES",Context.MODE_PRIVATE);
        return  sharedPreferences.getString(key,"");
    }
}
