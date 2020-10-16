package com.lubinpc.retrofit;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lubinpc.retrofit.api.CBSuccess;
import com.lubinpc.retrofit.api.entities.GenericResponse;
import com.lubinpc.retrofit.api.serializers.BooleanDeserializer;
import com.lubinpc.retrofit.api.serializers.BooleanSerializer;
import com.lubinpc.retrofit.api.serializers.DateDeserializaer;
import com.lubinpc.retrofit.api.serializers.DateSerializer;
import com.lubinpc.retrofit.models.NoteWS;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.Callback;

public class ApiTodoList {
    private static final String TAG = "API_SANTAFIX";
    private static ApiTodoList instance;
    private TodoListInterface apiService;
    private String apiToken;

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public static ApiTodoList getInstance() {
        if (instance == null) instance = new ApiTodoList();
        return instance;
    }

    public ApiTodoList() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Prepare http client
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request newRequest = chain.request().newBuilder()
                                .addHeader("Authorization", "Bearer " + apiToken)
                                .build();
                        return chain.proceed(newRequest);
                    }
                });

        // Prepare Gson instance
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Boolean.class, new BooleanSerializer())
                .registerTypeAdapter(Boolean.class, new BooleanDeserializer())
                .registerTypeAdapter(boolean.class, new BooleanSerializer())
                .registerTypeAdapter(boolean.class, new BooleanDeserializer())
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Date.class, new DateDeserializaer())
                .create();

        // Prepare retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstans.serverPath)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClientBuilder.build())
                .build();

        apiService = retrofit.create(TodoListInterface.class);
    }

    @SuppressWarnings("unchecked")
    private void doRequest(final String operation, Call call, final CBSuccess cb) {
        call.enqueue(new Callback() {
            @SuppressWarnings("NullableProblems")
            @Override
            public void onResponse(Call call, retrofit2.Response response) {
                if (response.isSuccessful()) {
                    cb.onResponse(true, response.body());
                } else {
                    handleUnsuccessful(operation, cb);
                }
            }

            @SuppressWarnings("NullableProblems")
            @Override
            public void onFailure(Call call, Throwable t) {
                handleFailure(operation, t, cb);
            }
        });
    }


    @SuppressWarnings("unchecked")
    private void handleUnsuccessful(String operation, CBSuccess callback) {
        Log.w(TAG, operation + " was unsuccessful");
        callback.onResponse(false, null);
    }

    @SuppressWarnings("unchecked")
    private void handleFailure(String operation, Throwable t,
                               CBSuccess callback) {
        Log.e(TAG, operation + " has failed");
        Log.e(TAG, "Message is: " + t.getMessage());

        callback.onResponse(false, null);
    }

    public void notes(Boolean programed, CBSuccess<List<NoteWS>> cb){
        doRequest(
                "get all notes",
                apiService.notes(programed),
                cb
        );
    }

    public void create(NoteWS note, CBSuccess<GenericResponse> cb){
        doRequest(
                "create note",
                apiService.createNote(note),
                cb
        );
    }

    public void update(long noteId,NoteWS note, CBSuccess<GenericResponse> cb) {
        doRequest(
                "update note",
                apiService.updateNote(noteId, note),
                cb
        );
    }

    public void destroy(long noteId, CBSuccess<GenericResponse> cb){
        doRequest(
                "destroy note",
                apiService.destroyNote(noteId),
                cb
        );
    }
}
