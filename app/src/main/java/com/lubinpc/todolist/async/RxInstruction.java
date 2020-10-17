package com.lubinpc.todolist.async;

import android.annotation.SuppressLint;
import android.util.Log;

import com.lubinpc.retrofit.api.CBDone;
import com.lubinpc.retrofit.api.CBGeneric;

import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;


public class RxInstruction {

    private static final String TAG = "Rx_instruction";

    private static Action action;
    private static Callable callable;


    public RxInstruction completable(Action action) {
        this.action = action;
        return this;
    }

    public RxInstruction single(Callable callable) {
        this.callable = callable;
        return this;
    }


    @SuppressLint("CheckResult")
    public void exec(CBDone onDone) {
        Completable
                .fromAction(action)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(onDone::done, throwable -> Log.e(
                        TAG,
                        "Error: " + throwable.getMessage()
                ));
    }

    @SuppressWarnings({"unchecked", "ResultOfMethodCallIgnored"})
    @SuppressLint("CheckResult")
    public void exec(CBGeneric cb) {
        Single.fromCallable(callable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cb::onResult, throwable -> cb.onResult(null));
    }
}
