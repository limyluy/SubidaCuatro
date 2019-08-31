package com.example.subidacuatro.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.subidacuatro.Entidades.Local;
import com.example.subidacuatro.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class AdaptadorSeleccionarProducto extends FirestoreRecyclerAdapter<Local, AdaptadorSeleccionarProducto.LocalSeleccionarViewHolder> {
     Context context;


    public AdaptadorSeleccionarProducto(@NonNull FirestoreRecyclerOptions<Local> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull LocalSeleccionarViewHolder holder, int position, @NonNull Local model) {

        holder.nombre.setText(model.getNombre());
        holder.cliente.setText(model.getIdCliente());

    }

    @NonNull
    @Override
    public LocalSeleccionarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_seleccionar_locales,viewGroup,false);
        return new LocalSeleccionarViewHolder(v);
    }

    class LocalSeleccionarViewHolder extends RecyclerView.ViewHolder{

        TextView nombre;
        TextView cliente;


        public LocalSeleccionarViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txt_nombre_seleccionar_locales_card);
            cliente = itemView.findViewById(R.id.txt_cliente_seleccionar_locales_card);
        }
    }
}
