package com.lubinpc.retrofit.models;

import com.google.gson.annotations.SerializedName;

public class NoteWS {
    private long id;
    private String title;
    private String text;
    private String programed;
    @SerializedName("programed_time")
    private Long programedTime;

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    private boolean complete;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getProgramed() {
        return programed;
    }

    public void setProgramed(String programed) {
        this.programed = programed;
    }

    public Long getProgramedTime() {
        return programedTime;
    }

    public void setProgramedTime(Long programedTime) {
        this.programedTime = programedTime;
    }

}
