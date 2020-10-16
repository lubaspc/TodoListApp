package com.lubinpc.todolist.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.lubinpc.retrofit.ApiConstans;
import com.lubinpc.retrofit.api.CBGeneric;
import com.lubinpc.retrofit.api.CBSuccess;
import com.lubinpc.retrofit.api.entities.GenericResponse;
import com.lubinpc.retrofit.models.UserWS;

import org.json.JSONException;
import org.json.JSONObject;

import static com.lubinpc.todolist.utils.GsonUtilsKt.toJSON;
import static com.lubinpc.todolist.utils.GsonUtilsKt.toModel;

public class RequestVolley {

    private static RequestVolley instance;
    private RequestQueue queue;
    private String url = ApiConstans.serverPath + ApiConstans.wsPath;


    public static RequestVolley getInstance(){
        if (instance == null) instance = new RequestVolley();
        return  instance;
    }

    public static RequestVolley getInstance(Context ctx){
        if (instance == null) instance = new RequestVolley(ctx);
        return  instance;
    }

    public RequestVolley(){}

    public RequestVolley(Context ctx){
        queue = Volley.newRequestQueue(ctx);
    }

    public void login(UserWS user, CBGeneric<GenericResponse<UserWS>> cb){
        GenericResponse<UserWS> gr = new GenericResponse<>(400);
        gr.setSuccess(false);
        gr.setMessage("Ocurrió un error en la solicitud");
        JsonObjectRequest task = new JsonObjectRequest(Request.Method.POST, url + "login", toJSON(user), response -> {
            try {
                if (!response.getBoolean("success")){
                    gr.setMessage(response.getString("message"));
                }else {
                    gr.setSuccess(true);
                    gr.setData(toModel(response.getJSONObject("data").toString(),UserWS.class));
                }
                cb.onResult(gr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            cb.onResult(gr);
        });
        queue.add(task);
    }


    public void register(UserWS user, CBSuccess<String> cb){
        JsonObjectRequest task = new JsonObjectRequest(Request.Method.POST, url + "register", toJSON(user), response -> {
            try {
                if (!response.getBoolean("success")){
                  cb.onResponse(false,response.getString("message"));
                }else {
                    cb.onResponse(true,"");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            cb.onResponse(false,"Ocurrió un error en la solicitud");
        });
        queue.add(task);
    }


}
