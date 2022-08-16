package br.ufjf.dcc196.marcusviniciux1.listadecompras.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.ufjf.dcc196.marcusviniciux1.listadecompras.bd.AppDatabase;
import br.ufjf.dcc196.marcusviniciux1.listadecompras.bd.Categoria;

public class MainActivityViewModel extends AndroidViewModel {

    private MutableLiveData<List<Categoria>> listaDeCategorias;
    private AppDatabase appDatabase;

    public MainActivityViewModel(Application application) {
        super(application);
        listaDeCategorias = new MutableLiveData<>();

        appDatabase = AppDatabase.getDBinstancia(getApplication().getApplicationContext());
    }

    public MutableLiveData<List<Categoria>> getObserverListaCategoria() {
        return listaDeCategorias;
    }

    public void getTodasListasDeCategoria() {
        List<Categoria> listaCategoria = appDatabase.listaDeComprasDAO().getTodasListasDeCategorias();
        if (listaCategoria.size() > 0) {
            listaDeCategorias.postValue(listaCategoria);
        } else {
            listaDeCategorias.postValue(null);
        }
    }

    public void inserirCategoria(String catName) {
        Categoria categoria = new Categoria();
        categoria.nomeCategoria = catName;
        appDatabase.listaDeComprasDAO().inserirCategoria(categoria);
        getTodasListasDeCategoria();
    }

    public void atualizarCategoria(Categoria category) {
        appDatabase.listaDeComprasDAO().atualizarCategoria(category);
        getTodasListasDeCategoria();
    }

    public void deletarCategoria(Categoria category) {
        appDatabase.listaDeComprasDAO().deletarCategoria(category);
        getTodasListasDeCategoria();
    }
}
