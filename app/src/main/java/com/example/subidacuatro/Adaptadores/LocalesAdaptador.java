package com.example.subidacuatro.Adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.subidacuatro.Entidades.Local;
import com.example.subidacuatro.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.protobuf.StringValue;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LocalesAdaptador extends FirestoreRecyclerAdapter<Local, LocalesAdaptador.LocalViewHolder> {

    private Context context;
    ClienteAdaptador.OnItemClickListener listener;

    public LocalesAdaptador(@NonNull FirestoreRecyclerOptions<Local> options,Context context) {
        super(options);
        this.context = context;

    }

    @Override
    protected void onBindViewHolder(@NonNull LocalViewHolder holder, int position, @NonNull Local model) {

        List<String> etiquetas = model.getEtiquetas();
        String resultados = "";
        for (int i = 0; i < etiquetas.size(); i++)
            if (i + 1 < etiquetas.size()) {
                resultados += etiquetas.get(i) + " | ";
            }else{
                resultados += etiquetas.get(i);
                holder.etiquetas.setText(resultados);
            }



        holder.nombre.setText(model.getNombre());
        holder.id.setText(model.getId());
        holder.descripcion.setText(model.getDescripcion());
        holder.direccion.setText(model.getDireccion());
        holder.telefono.setText(model.getTelefono());
        holder.longitud.setText(String.valueOf(model.getUbicacion().getLongitude()));
        holder.latitud.setText(String.valueOf(model.getUbicacion().getLatitude()));
        holder.recomendaciones.setText(String.valueOf(model.getNumRecomendado()));
        holder.atencion.setText(String.valueOf(model.getAtencion()));
        holder.precio.setText(String.valueOf(model.getPrecio()));
        holder.calidad.setText(String.valueOf(model.getCalidad()));
        holder.cliente.setText(model.getIdCliente());
        holder.garage.setChecked(model.isGaraje());
        holder.targeta.setChecked(model.isTarjeta());
        holder.garantia.setChecked(model.isGarantia());
        Picasso.with(context).load(model.getImagenesLocal().get(0)).into(holder.imgLocal);
        Picasso.with(context).load(model.getImgLogo()).into(holder.imgLogo);
        holder.imgColor.setBackgroundColor(Integer.parseInt(model.getColor()));
        holder.txtNumProductos.setText(String.valueOf( model.getEtiquetas().size()));

    }

    @NonNull
    @Override
    public LocalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_ver_locales,viewGroup,false);
        return new LocalViewHolder(v);
    }

    class LocalViewHolder extends RecyclerView.ViewHolder{

        TextView nombre;
        TextView descripcion;
        TextView id;
        TextView direccion;
        TextView telefono;
        TextView longitud;
        TextView latitud;
        TextView recomendaciones;
        TextView atencion;
        TextView precio;
        TextView calidad;
        TextView cliente;
        TextView etiquetas;
        CheckBox garage;
        CheckBox targeta;
        CheckBox garantia;
        ImageView imgLocal;
        ImageView imgLogo;
        ImageView imgColor;
        TextView txtNumProductos;



        public LocalViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.txt_nom_local_card);
            descripcion = itemView.findViewById(R.id.txt_des_local_card);
            id = itemView.findViewById(R.id.txt_id_local_card);
            direccion = itemView.findViewById(R.id.txt_dir_local_card);
            telefono = itemView.findViewById(R.id.txt_tel_local_card);
            longitud = itemView.findViewById(R.id.txt_lon_local_card);
            latitud = itemView.findViewById(R.id.txt_lat_local_card);
            recomendaciones = itemView.findViewById(R.id.txt_recom_local_card);
            atencion = itemView.findViewById(R.id.txt_num_ate_local_card);
            precio = itemView.findViewById(R.id.txt_num_pre_local_card);
            calidad = itemView.findViewById(R.id.txt_num_cal_local_card);
            atencion = itemView.findViewById(R.id.txt_num_ate_local_card);
            cliente = itemView.findViewById(R.id.txt_clientes_local_card);
            etiquetas = itemView.findViewById(R.id.txt_etiquetas_local_card);
            garage = itemView.findViewById(R.id.chb_garage_local_card);
            targeta = itemView.findViewById(R.id.chb_tarjeta_local_card);
            garantia = itemView.findViewById(R.id.chb_garantia_local_card);
            imgLocal = itemView.findViewById(R.id.img_local_card);
            imgLogo = itemView.findViewById(R.id.img_logo_card);
            imgColor = itemView.findViewById(R.id.img_color_local_card);
            txtNumProductos = itemView.findViewById(R.id.txt_num_productos_local);


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

    public void setOnItemClickListener(ClienteAdaptador.OnItemClickListener listener) {
        this.listener = listener;
    }


}
