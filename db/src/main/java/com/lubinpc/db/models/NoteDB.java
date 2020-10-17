package com.lubinpc.db.models;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "note")
public class NoteDB {
    @PrimaryKey
    private long id;
    private String title;
    private String text;
    @Nullable
    private Date programed;

    public Date getProgramed() {
        return programed;
    }

    public void setProgramed(Date programed) {
        this.programed = programed;
    }

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


}
