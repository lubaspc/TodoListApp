package com.lubinpc.retrofit;

import com.lubinpc.retrofit.api.entities.GenericResponse;
import com.lubinpc.retrofit.models.NoteWS;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TodoListInterface {

    @GET(ApiConstans.wsPath + " notes")
    Call<List<NoteWS>> notes(@Query("programed") Boolean programed);

    @POST(ApiConstans.wsPath +"note/create")
    Call<GenericResponse> createNote(@Body NoteWS note);

    @POST(ApiConstans.wsPath +"note/{noteId}/update")
    Call<GenericResponse> updateNote(@Path("noteId") long noteId,@Body NoteWS note);

    @GET(ApiConstans.wsPath + "note/{noteId}/destroy")
    Call<GenericResponse> destroyNote(@Path("noteId") long noteId);

}
