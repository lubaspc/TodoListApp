package com.lubinpc.retrofit.volley;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.lubinpc.retrofit.ApiConstans;
import com.lubinpc.retrofit.api.CBGeneric;
import com.lubinpc.retrofit.api.CBSuccess;
import com.lubinpc.retrofit.api.entities.GenericResponse;
import com.lubinpc.retrofit.models.UserWS;
import com.lubinpc.retrofit.utils.JsonUtils;

import org.json.JSONException;


public class RequestVolley {

    private static final int MY_DEFAULT_TIMEOUT = 15000;
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
        gr.setMessage("Ocurrió un error en la solicitud, verifica tu conexión a internet");
        JsonObjectRequest task = new JsonObjectRequest(Request.Method.POST, url + "login", JsonUtils.toJSON(user), response -> {
            try {
                if (!response.getBoolean("success")){
                    gr.setMessage(response.getString("message"));
                }else {
                    gr.setSuccess(true);
                    gr.setData(JsonUtils.toModel(response.getJSONObject("data").toString(),UserWS.class));
                }
                cb.onResult(gr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            cb.onResult(gr);
        });
        configRequest(task);
        queue.add(task);
    }


    public void register(UserWS user, CBSuccess<String> cb){
        JsonObjectRequest task = new JsonObjectRequest(Request.Method.POST, url + "register", JsonUtils.toJSON(user), response -> {
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
            cb.onResponse(false,"Ocurrió un error en la solicitud, verifica tu conexión a internet");
        });
        configRequest(task);
        queue.add(task);
    }

    private static <T> void configRequest(JsonRequest<T> request){
        request.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }



}
