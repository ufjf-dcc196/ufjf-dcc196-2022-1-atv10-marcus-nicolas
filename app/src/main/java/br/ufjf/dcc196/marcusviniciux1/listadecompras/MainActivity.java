package br.ufjf.dcc196.marcusviniciux1.listadecompras;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;


import java.util.List;

import br.ufjf.dcc196.marcusviniciux1.listadecompras.bd.Categoria;
import br.ufjf.dcc196.marcusviniciux1.listadecompras.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity implements ListaCategoriasAdapter.HandleCategoryClick {

    private MainActivityViewModel viewModel;
    private TextView semCategoriaTextView;
    private RecyclerView recyclerView;
    private ListaCategoriasAdapter listaCategoriasAdapter;
    private Categoria categoriaParaEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Lista de Compras");
        semCategoriaTextView = findViewById(R.id.semCategoriaTextView);
        recyclerView = findViewById(R.id.recyclerView);
        ImageView addNew = findViewById(R.id.adicionarNovaCategoriaImageView);
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialogoCategoria(false);
            }
        });
        iniciarViewModel();
        iniciarRecyclerView();
        viewModel.getTodasListasDeCategoria();
    }

    private void iniciarRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listaCategoriasAdapter = new ListaCategoriasAdapter(this, this);
        recyclerView.setAdapter(listaCategoriasAdapter);
    }

    private void iniciarViewModel() {
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel.getObserverListaCategoria().observe(this, new Observer<List<Categoria>>() {
            @Override
            public void onChanged(List<Categoria> categorias) {
                if (categorias == null) {
                    semCategoriaTextView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    listaCategoriasAdapter.setListaCategoria(categorias);
                    recyclerView.setVisibility(View.VISIBLE);
                    semCategoriaTextView.setVisibility(View.GONE);
                }
            }
        });
    }


    private void showAddDialogoCategoria(boolean paraEditar) {
        AlertDialog dialogoBuilder = new AlertDialog.Builder(this).create();
        View dialogView = getLayoutInflater().inflate(R.layout.add_categoria_layout, null);
        EditText categoriaInput = dialogView.findViewById(R.id.categoriaInput);
        TextView criarButton = dialogView.findViewById(R.id.criarButton);
        TextView cancelarButton = dialogView.findViewById(R.id.cancelarButton);

        if(paraEditar){
            criarButton.setText("Atualizar");
            categoriaInput.setText(categoriaParaEditar.nomeCategoria);
        }

        cancelarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoBuilder.dismiss();
            }
        });
        criarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = categoriaInput.getText().toString();
                if (TextUtils.isEmpty(nome)) {
                    Toast.makeText(MainActivity.this, "Digitar o nome da categoria", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (paraEditar) {
                    categoriaParaEditar.nomeCategoria = nome;
                    viewModel.atualizarCategoria(categoriaParaEditar);
                } else {
                    viewModel.inserirCategoria(nome);
                }
                dialogoBuilder.dismiss();
            }
        });
        dialogoBuilder.setView(dialogView);
        dialogoBuilder.show();
    }

    @Override
    public void itemClick(Categoria categoria) {
        Intent intent = new Intent(MainActivity.this, ListaDeItens.class);
        intent.putExtra("id_categoria", categoria.uid);
        intent.putExtra("nome_categoria", categoria.nomeCategoria);

        startActivity(intent);
    }

    @Override
    public void removerItem(Categoria categoria) {
        viewModel.deletarCategoria(categoria);
    }

    @Override
    public void editarItem(Categoria categoria) {
        this.categoriaParaEditar = categoria;
        showAddDialogoCategoria(true);
    }
}