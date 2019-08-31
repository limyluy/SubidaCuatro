package com.example.subidacuatro.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.subidacuatro.Entidades.Evento;
import com.example.subidacuatro.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

public class EventoAdaptador extends FirestoreRecyclerAdapter<Evento, EventoAdaptador.EvetoViewHolder> {

    private Context context;
    public EventoAdaptador(@NonNull FirestoreRecyclerOptions<Evento> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull EvetoViewHolder holder, int position, @NonNull Evento model) {
        List<String> categoria = model.getTangs();
        String resultados = "";
        for (int i = 0; i < categoria.size(); i++)
            if (i + 1 < categoria.size()) {
                resultados += categoria.get(i) + " | ";
            }else{
                resultados += categoria.get(i);
                holder.cagegorias.setText(resultados);
            }


        holder.nombre.setText(model.getNombre());
        holder.descripcion.setText(model.getDescripcion());
        holder.ubicacion.setText(String.valueOf(model.getUbicacion()));
        holder.fecha.setText(model.getFecha());
        holder.descripcionAdi.setText(model.getDesAdicional());
        holder.btnFotos.setText("Fotos: " + String.valueOf(model.getFotos().size()));
        holder.btnLocales.setText("Locales: " + String.valueOf(model.getLocales().size()));



    }

    @NonNull
    @Override
    public EvetoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_ver_eventos,viewGroup,false);
        return new EvetoViewHolder(v);
    }

    class EvetoViewHolder extends RecyclerView.ViewHolder{

        TextView nombre;
        TextView descripcion;
        TextView ubicacion;
        TextView fecha;
        TextView descripcionAdi;
        TextView especificaciones;
        TextView cagegorias;
        Button btnLocales;
        Button btnFotos;

        public EvetoViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.txt_nom_evento_card);
            descripcion = itemView.findViewById(R.id.txt_des_evento_card);
            ubicacion = itemView.findViewById(R.id.txt_ubi_eventos_card);
            fecha = itemView.findViewById(R.id.txt_fec_eventos_card);
            descripcionAdi = itemView.findViewById(R.id.txt_desadi_eventos_card);
            especificaciones = itemView.findViewById(R.id.txt_esp_eventos_card);
            cagegorias = itemView.findViewById(R.id.txt_cat_eventos_card);
            btnLocales = itemView.findViewById(R.id.btn_loc_eventos_card);
            btnFotos = itemView.findViewById(R.id.btn_fot_eventos_card);
        }
    }
}
