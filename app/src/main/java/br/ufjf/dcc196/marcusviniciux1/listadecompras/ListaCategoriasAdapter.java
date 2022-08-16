package br.ufjf.dcc196.marcusviniciux1.listadecompras;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import br.ufjf.dcc196.marcusviniciux1.listadecompras.bd.Categoria;

public class ListaCategoriasAdapter extends RecyclerView.Adapter<ListaCategoriasAdapter.MyViewHolder> {

    private Context context;
    private List<Categoria> listaDeCategoria;
    private HandleCategoryClick clickListener;

    public ListaCategoriasAdapter(Context context, HandleCategoryClick clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    public void setListaCategoria(List<Categoria> categoryList) {
        this.listaDeCategoria = categoryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListaCategoriasAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaCategoriasAdapter.MyViewHolder holder, int position) {
        holder.tvNomeDaCategoria.setText(this.listaDeCategoria.get(position).nomeCategoria);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.itemClick(listaDeCategoria.get(position));
            }
        });

        holder.editarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.editarItem(listaDeCategoria.get(position));
            }
        });

        holder.removerCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.removerItem(listaDeCategoria.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listaDeCategoria == null || listaDeCategoria.size() == 0)
            return 0;
        else
            return listaDeCategoria.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomeDaCategoria;
        ImageView removerCategoria;
        ImageView editarCategoria;

        public MyViewHolder(View view) {
            super(view);
            tvNomeDaCategoria = view.findViewById(R.id.tvNomeDaCategoria);
            removerCategoria = view.findViewById(R.id.removerCategoria);
            editarCategoria = view.findViewById(R.id.editarCategoria);

        }
    }

    public interface HandleCategoryClick {
        void itemClick(Categoria category);

        void removerItem(Categoria category);

        void editarItem(Categoria category);
    }
}
