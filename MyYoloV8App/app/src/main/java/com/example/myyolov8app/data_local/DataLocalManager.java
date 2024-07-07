package com.example.myyolov8app.data_local;

import android.content.Context;

import com.example.myyolov8app.model.Users;
import com.google.gson.Gson;

public class DataLocalManager {
    private static final String PREF_OBJECT_USER = "PREF_OBJECT_USER";
    private static  DataLocalManager instance;
private MySharePreferences mySharePreferences;

    public static void init(Context context) {
        instance = new DataLocalManager();
        instance.mySharePreferences = new MySharePreferences(context);
    }
    public static DataLocalManager getInstance(){
        if(instance==null){
            instance = new DataLocalManager();
        }
        return  instance;
    }

    public static void setUser(Users user) {
        Gson gson = new Gson();
        String strJsonUser = gson.toJson(user);
        DataLocalManager.getInstance().mySharePreferences.putStringValue(PREF_OBJECT_USER,strJsonUser);
    }
    public static Users getUser() {
        String strJsonUser = DataLocalManager.getInstance().mySharePreferences.getStringValue(PREF_OBJECT_USER);
       Gson gson = new Gson();
       Users user = gson.fromJson(strJsonUser,Users.class);
       return user;
    }
}
