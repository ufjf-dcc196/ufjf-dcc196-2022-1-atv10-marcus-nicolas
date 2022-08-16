package br.ufjf.dcc196.marcusviniciux1.listadecompras;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.text.TextUtils;

import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import br.ufjf.dcc196.marcusviniciux1.listadecompras.bd.Itens;
import br.ufjf.dcc196.marcusviniciux1.listadecompras.viewmodel.ListaDeItensActivityViewModel;

public class ListaDeItens extends AppCompatActivity implements ListaItensAdapter.HandleItemClick {

    private int id_categoria;
    private ListaItensAdapter listaItensAdapter;
    private ListaDeItensActivityViewModel viewModel;
    private RecyclerView recyclerView;
    private Itens itemParaAtualizar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_itens);

        id_categoria = getIntent().getIntExtra("id_categoria", 0);
        String categoryName = getIntent().getStringExtra("nome_categoria");

        getSupportActionBar().setTitle(categoryName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText addNewItemInput = findViewById(R.id.adicionarNovoItem);
        ImageView saveButton = findViewById(R.id.salvarButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = addNewItemInput.getText().toString();
                if (TextUtils.isEmpty(itemName)) {
                    Toast.makeText(ListaDeItens.this, "Digite o nome do item", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (itemParaAtualizar == null)
                    salvarNovoItem(itemName);
                else
                    atualizarNovoItem(itemName);
            }
        });
        iniciarRecyclerView();
        iniciarViewModel();
        viewModel.getTodasListasDeItens(id_categoria);
    }

    private void iniciarViewModel() {
        viewModel = new ViewModelProvider(this).get(ListaDeItensActivityViewModel.class);
        viewModel.getObserverListaItens().observe(this, new Observer<List<Itens>>() {
            @Override
            public void onChanged(List<Itens> itens) {
                if (itens == null) {
                    recyclerView.setVisibility(View.GONE);
                    findViewById(R.id.semResultado).setVisibility(View.VISIBLE);
                } else {
                    listaItensAdapter.setListaItens(itens);
                    findViewById(R.id.semResultado).setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void iniciarRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaItensAdapter = new ListaItensAdapter(this, this);
        recyclerView.setAdapter(listaItensAdapter);
    }

    private void salvarNovoItem(String nomeItem) {
        Itens item = new Itens();
        item.nomeItem = nomeItem;
        item.idCategoria = id_categoria;
        viewModel.inserirItem(item);
        ((EditText) findViewById(R.id.adicionarNovoItem)).setText("");
    }

    @Override
    public void itemClick(Itens item) {
        if (item.completo) {
            item.completo = false;
        } else {
            item.completo = true;
        }
        viewModel.atualizarItem(item);
    }

    @Override
    public void removerItem(Itens item) {
        viewModel.deletarItem(item);
    }

    @Override
    public void editarItem(Itens item) {
        this.itemParaAtualizar = item;
        ((EditText) findViewById(R.id.adicionarNovoItem)).setText(item.nomeItem);
    }

    private void atualizarNovoItem(String novoNome) {
        itemParaAtualizar.nomeItem = novoNome;
        viewModel.atualizarItem(itemParaAtualizar);
        ((EditText) findViewById(R.id.adicionarNovoItem)).setText("");
        itemParaAtualizar = null;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
