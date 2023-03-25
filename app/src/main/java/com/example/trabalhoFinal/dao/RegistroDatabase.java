package com.example.trabalhoFinal.dao;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.trabalhoFinal.modelo.Filme;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Filme.class}, version = 1, exportSchema = false)
public abstract class RegistroDatabase extends RoomDatabase {

    public abstract RegistroDao registroDao();

    private static volatile RegistroDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static RegistroDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RegistroDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RegistroDatabase.class, "database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                RegistroDao dao = INSTANCE.registroDao();
                dao.deleteAll();

                Filme reg1 = new Filme(1, "Life", true, 2);
                Filme reg2 = new Filme(2, "O Farol", true, 9);
                Filme reg3 = new Filme(3, "O Âncora", true, 9);
                Filme reg4 = new Filme(4, "De repente 30", false, 0);
                Filme reg5 = new Filme(5, "Movie 43", true, 10);

                dao.insertAll(reg1, reg2, reg3, reg4, reg5);
            });
        }
    };

}
