package com.lubinpc.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.lubinpc.db.converts.DBTypeConverts;
import com.lubinpc.db.dao.NoteDao;
import com.lubinpc.db.models.NoteDB;

@Database(
        entities = {
                NoteDB.class
        },
        version = 1,
        exportSchema = false
)
@TypeConverters({DBTypeConverts.class})
public abstract class DBTodoList  extends RoomDatabase {
    public static DBTodoList db;

    public abstract NoteDao noteDao();
}
