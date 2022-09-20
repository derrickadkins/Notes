package com.demo.notes;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.List;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class DB extends RoomDatabase {
    public abstract NoteDao noteDao();

    public List<Note> notes;

    public interface DbListener{
        void onDbReady(DB db);
    }

    synchronized static void InitDbSync(Context context, DbListener dbListener){
        DB db = Room.databaseBuilder(context,
                        DB.class, "Notes Database")
                .fallbackToDestructiveMigration()
                .build();

        db.notes = db.noteDao().getAll();

        if(dbListener != null) new Handler(Looper.getMainLooper()).post(() -> dbListener.onDbReady(db));
    }

    public static void InitDB(Context context){
        DbListener dbListener = null;
        if(context instanceof DbListener)
            dbListener = (DbListener) context;
        InitDB(context, dbListener);
    }

    public static void InitDB(Context context, DbListener dbListener){
        new Thread(() -> InitDbSync(context, dbListener)).start();
    }

    @Dao
    public interface NoteDao{
        @Query("select * from Note")
        List<Note> getAll();

        @Insert
        long insert(Note note);

        @Query("update Note set title = :title, content = :content where id = :id")
        void update(long id, String title, String content);

        @Query("delete from Note where id = :id")
        void delete(long id);
    }
}
