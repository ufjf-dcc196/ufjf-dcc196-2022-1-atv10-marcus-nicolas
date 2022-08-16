package br.ufjf.dcc196.marcusviniciux1.listadecompras.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.ufjf.dcc196.marcusviniciux1.listadecompras.bd.AppDatabase;
import br.ufjf.dcc196.marcusviniciux1.listadecompras.bd.Itens;

public class ListaDeItensActivityViewModel extends AndroidViewModel {

    private MutableLiveData<List<Itens>> listaDeItens;
    private AppDatabase appDatabase;

    public ListaDeItensActivityViewModel(Application application) {
        super(application);
        listaDeItens = new MutableLiveData<>();

        appDatabase = AppDatabase.getDBinstancia(getApplication().getApplicationContext());
    }

    public MutableLiveData<List<Itens>> getObserverListaItens() {
        return listaDeItens;
    }

    public void getTodasListasDeItens(int IDCategoria) {
        List<Itens> listaItens = appDatabase.listaDeComprasDAO().getTodasListasDeItens(IDCategoria);
        if (listaItens.size() > 0) {
            listaDeItens.postValue(listaItens);
        } else {
            listaDeItens.postValue(null);
        }
    }

    public void inserirItem(Itens item) {
        appDatabase.listaDeComprasDAO().inserirItens(item);
        getTodasListasDeItens(item.idCategoria);
    }

    public void atualizarItem(Itens item) {
        appDatabase.listaDeComprasDAO().atualizarItens(item);
        getTodasListasDeItens(item.idCategoria);
    }

    public void deletarItem(Itens item) {
        appDatabase.listaDeComprasDAO().deletarItens(item);
        getTodasListasDeItens(item.idCategoria);
    }
}