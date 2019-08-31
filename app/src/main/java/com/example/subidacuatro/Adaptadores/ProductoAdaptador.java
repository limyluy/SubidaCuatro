package com.example.subidacuatro.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.subidacuatro.Entidades.Productos;
import com.example.subidacuatro.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductoAdaptador extends FirestoreRecyclerAdapter<Productos, ProductoAdaptador.ProductosViewHolder> {

  private Context context;

    public ProductoAdaptador(@NonNull FirestoreRecyclerOptions<Productos> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductosViewHolder holder, int position, @NonNull Productos model) {

        ArrayList<String> etiquetas = model.getCategorias();
        String resultados = "";
       for (int i = 0; i < etiquetas.size(); i++)
            if (i + 1 < etiquetas.size()) {
                resultados += etiquetas.get(i) + " | ";
            }else{
                resultados += etiquetas.get(i);
                holder.categorias.setText(resultados);
            }


        holder.nombre.setText(model.getNombre());
        holder.descripcion.setText(model.getDescripcion());
        holder.marca.setText(model.getMarca());
        holder.desAdional.setText(model.getDesAdicional());
        holder.infoAdicional.setText(model.getInfAdicional());
        holder.numVesesBuscado.setText(String.valueOf(model.getVesesBuscado()));
        holder.numLocales.setText(String.valueOf(model.getLocalesTiene().size()));
        holder.chbOferta.setChecked(model.getOferta());
        Picasso.with(context).load(model.getImgProducto()).into(holder.imgProducto);


    }

    @NonNull
    @Override
    public ProductosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_ver_productos,viewGroup,false);
        return new ProductosViewHolder(v);
    }

    class ProductosViewHolder extends RecyclerView.ViewHolder{

        TextView nombre;
        TextView descripcion;
        TextView marca;
        TextView infoAdicional;
        TextView desAdional;
        TextView numVesesBuscado;
        TextView categorias;
        TextView numLocales;
        ImageView imgProducto;
        CheckBox chbOferta;

        public ProductosViewHolder(@NonNull View itemView) {
            super(itemView);


            nombre = itemView.findViewById(R.id.txt_nom_producto_card);
            descripcion = itemView.findViewById(R.id.txt_des_producto_card);
            marca = itemView.findViewById(R.id.txt_marca_producto_card);
            infoAdicional = itemView.findViewById(R.id.txt_infoadicional_producto_card);
            desAdional = itemView.findViewById(R.id.txt_desadicional_producto_card);
            numVesesBuscado = itemView.findViewById(R.id.txt_veses_bus_producto_card);
            categorias = itemView.findViewById(R.id.txt_cat_producto_card);
            numLocales = itemView.findViewById(R.id.txt_num_locales_producto_card);
            imgProducto = itemView.findViewById(R.id.img_producto_card);
            chbOferta = itemView.findViewById(R.id.chb_oferta_productos_card);
        }
    }
}
