package com.lubinpc.retrofit.api;

public interface CBSuccess<T>{
    void onResponse(boolean success, T result);
}
