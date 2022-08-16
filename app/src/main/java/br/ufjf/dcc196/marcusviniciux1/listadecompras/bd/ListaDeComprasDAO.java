package br.ufjf.dcc196.marcusviniciux1.listadecompras.bd;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ListaDeComprasDAO {
    @Query("Select * from Categoria")
    List<Categoria> getTodasListasDeCategorias();

    @Insert
    void inserirCategoria(Categoria...categorias);

    @Update
    void atualizarCategoria(Categoria...categorias);

    @Delete
    void deletarCategoria(Categoria...categorias);

    @Query("Select * from Itens where idCategoria = :idCat")
    List<Itens> getTodasListasDeItens(int idCat);

    @Insert
    void inserirItens(Itens itens);

    @Update
    void atualizarItens(Itens itens);

    @Delete
    void deletarItens(Itens itens);
}