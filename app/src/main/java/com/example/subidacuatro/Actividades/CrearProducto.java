package com.example.subidacuatro.Actividades;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.subidacuatro.Entidades.Productos;
import com.example.subidacuatro.MainActivity;
import com.example.subidacuatro.R;
import com.example.subidacuatro.Utilidades;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CrearProducto extends AppCompatActivity {


    private static final int IMAGEN_PUESTA = 1;
    private static final String PRODUCTOS = "productos";

    private EditText edtNombre;
    private EditText edtMarca;
    private EditText edtDescripcion;
    private EditText edtInfoAdicional;
    private EditText edtDesAdicional;
    private CheckBox chbOferta;
    private EditText edtEtiquetas;
    private TextView txtEtiquetas;
    private ImageView imgProducto;
    private Button btnImgProducto;
    private Button btnAddEtiquetas;
    private Button btnCrearProducto;
    private Button btnCancelar;
    private Button btnSubirImagen;
    private TextView txtImgProducto;
    private Toolbar tooCrearProducto;


    private List<String> etiquetasArreglo = new ArrayList<>();
    private Uri uriImgProducto;
    private StorageReference mREf;
    private StorageTask mUploadTask;
    private Context context;
    private Utilidades utilidades;

    private String idLocal;
    private String nomLocal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_producto);



        edtNombre = findViewById(R.id.edt_nom_producto);
        edtMarca = findViewById(R.id.edt_marca_producto);
        edtDescripcion = findViewById(R.id.edt_des_producto);
        edtInfoAdicional = findViewById(R.id.edt_infoadicional_producto);
        edtDesAdicional = findViewById(R.id.edt_desadicional_producto);
        chbOferta = findViewById(R.id.chb_oferta_producto);
        imgProducto = findViewById(R.id.img_producto);
        edtEtiquetas = findViewById(R.id.edt_etiquetas_producto);
        txtEtiquetas = findViewById(R.id.txt_etiquetas_producto);
        btnImgProducto = findViewById(R.id.btn_img_producto);
        btnAddEtiquetas = findViewById(R.id.btn_add_etiquetas);
        btnCrearProducto = findViewById(R.id.btn_crear_nuevo_producto);
        btnCancelar = findViewById(R.id.btn_cancelar_nuevoproducto);
        btnSubirImagen = findViewById(R.id.btn_subir_img_productos);
        txtImgProducto = findViewById(R.id.txt_img_producto);
        tooCrearProducto = findViewById(R.id.too_crear_producto);



        idLocal = getIntent().getStringExtra("id");
        nomLocal = getIntent().getStringExtra("nombre");

        tooCrearProducto.setTitle(nomLocal);

        context = getApplicationContext();
        mREf = FirebaseStorage.getInstance().getReference("productos");
        utilidades = new Utilidades(context);


        btnCrearProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearProducto();
            }
        });

        btnImgProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscadorImagen(IMAGEN_PUESTA);
            }
        });

        btnSubirImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utilidades.subirImagen(PRODUCTOS,uriImgProducto,context,txtImgProducto);
                btnSubirImagen.setVisibility(View.INVISIBLE);
            }
        });

        btnAddEtiquetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llenarEtiquetas();
            }
        });


    }

    private void crearProducto() {

        if (edtNombre.getText().toString().isEmpty() || edtDescripcion.getText().toString().isEmpty()){
            Toast.makeText(context, "Datos Insuficionetes", Toast.LENGTH_SHORT).show();
            return;
        }


         String nombre = edtNombre.getText().toString();
         String descripcion = edtDescripcion.getText().toString();
         String marca = "no marca";
         String imgProducto = "no imagen";
         String infAdicional = "";
         String desAdicional = "";
         Boolean oferta = chbOferta.isChecked();
         List<String> categoria = etiquetasArreglo;
         List<String> localesTiene = new ArrayList<>();
         long vesesBuscado = 0;

         if (!(edtMarca.getText().toString().isEmpty())){
             marca = edtMarca.getText().toString();
         }
         if (!(txtImgProducto.getText().toString().isEmpty())){
             imgProducto = txtImgProducto.getText().toString();
         }

         if (!(edtInfoAdicional.getText().toString().isEmpty())){
             infAdicional = edtInfoAdicional.getText().toString();
         }

        if (!(edtDesAdicional.getText().toString().isEmpty())){
            desAdicional = edtDesAdicional.getText().toString();
        }



        localesTiene.add(idLocal);
        Productos producto = new Productos(nombre,descripcion,marca,imgProducto,infAdicional,desAdicional,oferta,categoria,localesTiene,vesesBuscado);

        utilidades.agregarProductoLocal(producto,idLocal);

        String tangs = "";
        for (int i = 0; i < categoria.size(); i++)
            if (i + 1 < categoria.size())
                tangs += categoria.get(i) +" ";
            else
                tangs += categoria.get(i);

        utilidades.subirAlgolia(nombre,marca,descripcion,"Producto", tangs);
        startActivity(new Intent(CrearProducto.this, MainActivity.class));





    }

    private void buscadorImagen ( int codImagen){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, codImagen);
    }
    @Override
    protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGEN_PUESTA && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            uriImgProducto = data.getData();

            Picasso.with(this).load(uriImgProducto).into(imgProducto);

        }

    }

    private void llenarEtiquetas () {
        if (edtEtiquetas.getText().toString().isEmpty()) {
            Toast.makeText(this, "Campo basio", Toast.LENGTH_SHORT).show();
        } else {
            etiquetasArreglo.add(edtEtiquetas.getText().toString());
            mostrarEtiquetas();
            edtEtiquetas.setText("");
        }
    }

    private void mostrarEtiquetas () {

        String resultados = "";
        for (int i = 0; i < etiquetasArreglo.size(); i++)
            if (i + 1 < etiquetasArreglo.size())
                resultados += etiquetasArreglo.get(i) + " | ";
            else
                resultados += etiquetasArreglo.get(i);

        txtEtiquetas.setText(resultados);
    }
}
