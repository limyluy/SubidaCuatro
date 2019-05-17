package com.example.subidacuatro.Actividades;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.text.MessagePattern;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.graphics.drawable.ArgbEvaluator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.subidacuatro.Entidades.Local;
import com.example.subidacuatro.MainActivity;
import com.example.subidacuatro.R;
import com.example.subidacuatro.Utilidades;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;

public class CrearLocal extends AppCompatActivity {

    private static final int IMAGEN_PUESTA = 1;
    private static final int IMAGEN_PUESTA_LOGO = 2;
    private static final String LOCALES = "locales";

    private EditText edtNombre;
    private EditText edtDescripcion;
    private EditText edtTelefono;
    private EditText edtDireccion;
    private CheckBox chbGarage;
    private CheckBox chbTarjeta;
    private CheckBox chbGarantia;
    private Button btnUbicacion;
    private Button btnImgLocal;
    private Button btnImgLogo;
    private EditText edtEtiquetas;
    private Button btnAddEtiquetas;
    private TextView txtEtiquetas;
    private ImageView imgLocal;
    private ImageView imgLogo;
    private Button imgChooseColor;
    private Button btnSubirImagenes;
    private TextView link;
    private TextView linkDos;
    private TextView linkTres;

    private double longitud;
    private double latitud;
    private Uri imgLocalUri;
    private Uri imgLogoUri;

    private List<String> arrayEtiquetas = new ArrayList<>();
    private boolean trazado;
    private Button btnCrear;
    private Button btnCancelar;
    private Toolbar tooCrearLocal;


    private StorageReference mStoraRef;
    private StorageTask mUploadTask;

    private Context context;
    private Utilidades utilidades;
    private GeoPoint geoPoint;

    private int mdefaultColor;
    private String idCliente;

    private String logo;
    private String local;

    private LocationManager locManager;
    private Location loc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_local);
        getSupportActionBar();


        edtNombre = findViewById(R.id.edt_nom_local);
        edtDescripcion = findViewById(R.id.edt_des_local);
        edtTelefono = findViewById(R.id.edt_tel_local);
        edtDireccion = findViewById(R.id.edt_dir_local);
        chbGarage = findViewById(R.id.chb_garage_local);
        chbTarjeta = findViewById(R.id.chb_tarjeta_local);
        chbGarantia = findViewById(R.id.chb_garantia_local);
        btnUbicacion = findViewById(R.id.btn_ubicacion_local);
        btnImgLocal = findViewById(R.id.btn_img_local);
        btnImgLogo = findViewById(R.id.btn_img_logo);
        edtEtiquetas = findViewById(R.id.edt_etiquetas_local);
        btnAddEtiquetas = findViewById(R.id.btn_add_etiquetas);
        txtEtiquetas = findViewById(R.id.txt_etiquetas);
        btnCrear = findViewById(R.id.btn_crear_nuevolocal);
        btnCancelar = findViewById(R.id.btn_cancelar_nuevolocal);
        imgLocal = findViewById(R.id.img_local);
        imgLogo = findViewById(R.id.img_logo);
        imgChooseColor = findViewById(R.id.img_choose_color_crear_local);
        tooCrearLocal = findViewById(R.id.too_crear_local);
        btnSubirImagenes = findViewById(R.id.btn_subir_img_local);
        link = findViewById(R.id.txt_link);
        linkDos = findViewById(R.id.txt_link_dos);
        linkTres = findViewById(R.id.txt_link_tres);

        context = this;
        utilidades = new Utilidades(context);

        mdefaultColor = ContextCompat.getColor(CrearLocal.this, R.color.colorAccent);
        idCliente = getIntent().getStringExtra("id");

        obtenerUbicacion();


            btnCrear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    crearLocalBd(idCliente);

                }
            });
            btnAddEtiquetas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    llenarEtiquetas();
                }
            });
            btnImgLocal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buscadorImagen(IMAGEN_PUESTA);
                }
            });
            btnImgLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buscadorImagen(IMAGEN_PUESTA_LOGO);
                }
            });
            btnUbicacion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    obtenerUbicacion();
                    btnUbicacion.setText(String.valueOf(geoPoint.getLatitude()));
                }
            });
            imgChooseColor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    escogerColor();
                }
            });
            btnSubirImagenes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    utilidades.subirImagen(LOCALES, imgLocalUri, context, link);
                                    utilidades.subirImagen(LOCALES, imgLogoUri, context, linkDos);
                                }
                            }).start();


                        }
                    }).start();

                    btnSubirImagenes.setVisibility(View.INVISIBLE);
                    btnCrear.setVisibility(View.VISIBLE);
                }
            });

        }

    private void obtenerUbicacion() {
        ActivityCompat.requestPermissions(CrearLocal.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Permiso de Ubicaion no Activado", Toast.LENGTH_SHORT).show();

            return;
        } else {

            locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            geoPoint = new GeoPoint(loc.getLatitude(),loc.getLongitude());

        }
    }

    private void escogerColor () {
            AmbilWarnaDialog colorPiker = new AmbilWarnaDialog(this, mdefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                @Override
                public void onCancel(AmbilWarnaDialog dialog) {
                    Toast.makeText(context, "No se elegio color", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onOk(AmbilWarnaDialog dialog, int color) {
                    mdefaultColor = color;
                    imgChooseColor.setBackgroundColor(mdefaultColor);

                }
            });
            colorPiker.show();
        }

        private void llenarEtiquetas () {
            if (edtEtiquetas.getText().toString().isEmpty()) {
                Toast.makeText(this, "Campo basio", Toast.LENGTH_SHORT).show();
            } else {
                arrayEtiquetas.add(edtEtiquetas.getText().toString());
                mostrarEtiquetas();
                edtEtiquetas.setText("");
            }
        }

        private void mostrarEtiquetas () {

            String resultados = "";
            for (int i = 0; i < arrayEtiquetas.size(); i++)
                if (i + 1 < arrayEtiquetas.size())
                    resultados += arrayEtiquetas.get(i) + " | ";
                else
                    resultados += arrayEtiquetas.get(i);

            txtEtiquetas.setText(resultados);
        }

        private void buscadorImagen ( int codImagen){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, codImagen);
        }


        private void crearLocalBd (String id){

            if (edtNombre.getText().toString().isEmpty() || edtDescripcion.getText().toString().isEmpty() || geoPoint == null || edtTelefono.getText().toString().isEmpty()) {
                Toast.makeText(context, "Datos insuficientes", Toast.LENGTH_SHORT).show();
                return;
            }

            String idCli = idCliente;
            String nombre = edtNombre.getText().toString();
            String direccion = "no direccion";
            String telefono = edtTelefono.getText().toString();
            String descripcion = edtDescripcion.getText().toString();
            GeoPoint ubicacion = geoPoint;
            int atencion = 0;
            int calidad = 0;
            int precio = 0;
            boolean tarjeta =  chbTarjeta.isChecked();
            boolean garaje = chbGarage.isChecked();
            boolean garantia = chbGarantia.isChecked();
            String imgLocal = link.getText().toString();
            String imgLogo = linkDos.getText().toString();
            long numRecomendado = 0;
            Boolean actualizado = true;
            List<String> productos = new ArrayList<>();
            List<String> etiquetas = arrayEtiquetas;
            String color;


            if (!(edtDireccion.getText().toString().isEmpty())) {
                direccion = edtDireccion.getText().toString();
            }


            if (imgLocalUri == null || imgLogoUri == null) {
                Toast.makeText(this, "Imagenes no Seleccionadas", Toast.LENGTH_SHORT).show();
                return;
            }

            color = String.valueOf(mdefaultColor);
            productos.add(nombre);



            Local local = new Local(idCli, nombre, direccion, telefono, descripcion, ubicacion, atencion, calidad, precio, tarjeta, garaje, garantia, imgLocal, imgLogo, numRecomendado, actualizado, productos, etiquetas, color,false);


          String idLocal = utilidades.llenarLocal(local);
          if (idLocal != null){
              utilidades.agregarlocalCliente(idLocal,idCliente);
          }

            String tangs = "";
            for (int i = 0; i < etiquetas.size(); i++)
                if (i + 1 < etiquetas.size())
                    tangs += etiquetas.get(i) +" ";
                else
                    tangs += etiquetas.get(i);



          utilidades.subirAlgolia(nombre,direccion,descripcion,"Local Comercial",tangs);

            Toast.makeText(this, "Local Creado", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CrearLocal.this, MainActivity.class));




        }

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == IMAGEN_PUESTA && resultCode == RESULT_OK
                    && data != null && data.getData() != null) {

                imgLocalUri = data.getData();

                Picasso.with(this).load(imgLocalUri).into(imgLocal);

            }

            if (requestCode == IMAGEN_PUESTA_LOGO && resultCode == RESULT_OK
                    && data != null && data.getData() != null) {

                imgLogoUri = data.getData();
                Picasso.with(this).load(imgLogoUri).into(imgLogo);
            }
        }
    }
