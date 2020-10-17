package com.lubinpc.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.lubinpc.db.models.NoteDB;

import java.util.Date;
import java.util.List;

import io.reactivex.Maybe;

@Dao
public abstract class NoteDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void upsert(List<NoteDB> notes);

    @Query("SELECT * FROM note")
    public abstract List<NoteDB> getAll();

    @Transaction
    @Query("DELETE FROM note")
    public abstract void deleteAll();

    @Transaction
    public void refresh(List<NoteDB> notes){
        deleteAll();
        upsert(notes);
    }

    @Query("SELECT * FROM note WHERE programed BETWEEN :from AND :to")
    public abstract List<NoteDB> findProgramed(Date from, Date to);


}
