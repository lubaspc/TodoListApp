package com.lubinpc.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.lubinpc.db.models.NoteDB;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface NoteDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsert(NoteDB... notes);

    @Query("SELECT * FROM note")
    Maybe<List<NoteDB>> getAll();

    @Transaction
    @Query("DELETE FROM note")
    void deleteAll();


}
