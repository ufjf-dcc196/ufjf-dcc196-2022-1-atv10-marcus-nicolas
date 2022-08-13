package br.ufjf.dcc196.marcusviniciux1.listadecompras.bd;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Categoria {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "nomeCategoria")
    public String nomeCategoria;

}