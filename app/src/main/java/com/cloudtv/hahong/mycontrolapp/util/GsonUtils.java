package com.cloudtv.hahong.mycontrolapp.util;

import com.google.gson.Gson;

public class GsonUtils {

    public static <T> T fromJson(String json, Class<T> classOfT) {
        Gson gson = new Gson();
        return gson.fromJson(json, classOfT);
    }

    public static String toJson(String json, Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

}
