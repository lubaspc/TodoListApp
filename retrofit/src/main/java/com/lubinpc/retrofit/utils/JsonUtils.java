package com.lubinpc.retrofit.utils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    public static JSONObject toJSON(Object obj){
        try {
            return new JSONObject(
                    new Gson().toJsonTree(obj).toString()
            );
        } catch (JSONException e) {
            return null;
        }
    }

    public static <T> T toModel(String json, Class<T> model){
        return new Gson().fromJson(json,model);
    }
}
