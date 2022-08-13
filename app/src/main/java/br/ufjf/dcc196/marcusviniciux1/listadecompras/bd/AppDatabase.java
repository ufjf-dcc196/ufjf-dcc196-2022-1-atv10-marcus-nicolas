package br.ufjf.dcc196.marcusviniciux1.listadecompras.bd;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Categoria.class, Itens.class}, version = 1)

public abstract class AppDatabase extends RoomDatabase {
    public static AppDatabase INSTANCIA;

    public static AppDatabase getDBinstancia(Context contexto) {
        if (INSTANCIA == null) {
            INSTANCIA = Room.databaseBuilder(contexto.getApplicationContext(),
                            AppDatabase.class, "AppBD").
                    allowMainThreadQueries().build();
        }
        return INSTANCIA;
    }
}