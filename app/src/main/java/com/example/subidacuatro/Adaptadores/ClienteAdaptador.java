package com.example.subidacuatro.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.subidacuatro.Entidades.Cliente;
import com.example.subidacuatro.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class ClienteAdaptador extends FirestoreRecyclerAdapter<Cliente, ClienteAdaptador.ClienteViewHolder> {
    Context context;
    OnItemClickListener listener;


    public ClienteAdaptador(@NonNull FirestoreRecyclerOptions<Cliente> options, Context context) {
        super(options);
        this.context = context;

    }

    @Override
    protected void onBindViewHolder(@NonNull ClienteViewHolder holder, int position, @NonNull Cliente model) {

        int numero = 0;
        if (model.getLocales() != null) {
            numero = model.getLocales().size();
        }

        holder.nombre.setText(model.getNombre());
        holder.telefono.setText(model.getTelefono());
        holder.direccion.setText(model.getDireccion());
        holder.identificacion.setText(model.getIdentificacion());
        holder.numLocales.setText(String.valueOf(numero));

    }

    @NonNull
    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_ver_cliente, viewGroup, false);
        return new ClienteViewHolder(v);
    }

    class ClienteViewHolder extends RecyclerView.ViewHolder {

        TextView nombre;
        TextView telefono;
        TextView direccion;
        TextView identificacion;
        TextView numLocales;

        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.txt_nom_card_cliente);
            telefono = itemView.findViewById(R.id.txt_tel_card_cliente);
            direccion = itemView.findViewById(R.id.txt_dir_card_cliente);
            identificacion = itemView.findViewById(R.id.txt_ide_card_cliente);
            numLocales = itemView.findViewById(R.id.txt_numlocales_card_cliente);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
