package br.ufjf.dcc196.marcusviniciux1.listadecompras.bd;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Itens {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "nomeItem")
    public String nomeItem;
    @ColumnInfo(name = "idCategoria")
    public int idCategoria;
    @ColumnInfo(name = "completo")
    public boolean completo;

}