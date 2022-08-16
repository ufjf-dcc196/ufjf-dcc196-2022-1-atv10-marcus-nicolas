package br.ufjf.dcc196.marcusviniciux1.listadecompras;

import android.content.Context;

import android.graphics.Paint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ufjf.dcc196.marcusviniciux1.listadecompras.bd.Itens;

public class ListaItensAdapter extends RecyclerView.Adapter<ListaItensAdapter.MyViewHolder> {

    private Context context;
    private List<Itens> listaDeItens;
    private HandleItemClick clickListener;

    public ListaItensAdapter(Context context, HandleItemClick clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    public void setListaItens(List<Itens> listaDeItens) {
        this.listaDeItens = listaDeItens;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListaItensAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaItensAdapter.MyViewHolder holder, int position) {
        holder.tvNomeDoItem.setText(this.listaDeItens.get(position).nomeItem);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.itemClick(listaDeItens.get(position));
            }
        });

        holder.editarItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.editarItem(listaDeItens.get(position));
            }
        });

        holder.removerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.removerItem(listaDeItens.get(position));
            }
        });

        if (this.listaDeItens.get(position).completo) {
            holder.tvNomeDoItem.setPaintFlags(holder.tvNomeDoItem.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.tvNomeDoItem.setPaintFlags(0);
        }
    }

    @Override
    public int getItemCount() {
        if (listaDeItens == null || listaDeItens.size() == 0)
            return 0;
        else
            return listaDeItens.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomeDoItem;
        ImageView removerItem;
        ImageView editarItem;

        public MyViewHolder(View view) {
            super(view);
            tvNomeDoItem = view.findViewById(R.id.tvNomeDaCategoria);
            removerItem = view.findViewById(R.id.removerCategoria);
            editarItem = view.findViewById(R.id.editarCategoria);

        }
    }

    public interface HandleItemClick {
        void itemClick(Itens item);

        void removerItem(Itens item);

        void editarItem(Itens item);
    }
}