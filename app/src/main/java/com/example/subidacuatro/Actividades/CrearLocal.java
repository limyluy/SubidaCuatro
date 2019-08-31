package com.example.subidacuatro.Actividades;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;

public class CrearLocal extends AppCompatActivity {

    private static final int IMAGEN_UNO = 1;
    private static final int IMAGEN_DOS = 2;
    private static final int IMAGEN_TRES = 3;
    private static final int IMAGEN_PUESTA_LOGO = 4;
    private static final String LOCALES = "locales";

    private EditText edtNombre;
    private EditText edtDescripcion;
    private EditText edtTelefono;
    private EditText edtDireccion;
    private EditText edtLogitud;
    private EditText edtLatitud;
    private CheckBox chbGarage;
    private CheckBox chbTarjeta;
    private CheckBox chbGarantia;
    private TextView txtLinkUno;
    private TextView txtLinkDos;
    private TextView txtLinkTres;
    private TextView txtLinkLogo;

    private Button btnImgLocal;
    private Button btnImgLogo;
    private EditText edtEtiquetas;
    private Button btnAddEtiquetas;
    private TextView txtEtiquetas;
    private ImageView imgLocal;
    private ImageView imgLocal2;
    private ImageView imgLocal3;
    private ImageView imgLogo;
    private Button imgChooseColor;
    private Button btnSubirImagenes;

    private double longitud;
    private double latitud;
    private Uri imgLocalUri;
    private Uri imgLocalUri2;
    private Uri imgLocalUri3;
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
        edtLogitud = findViewById(R.id.edt_long_local);
        edtLatitud = findViewById(R.id.edt_lat_local);
        chbGarage = findViewById(R.id.chb_garage_local);
        chbTarjeta = findViewById(R.id.chb_tarjeta_local);
        chbGarantia = findViewById(R.id.chb_garantia_local);

        txtLinkUno = findViewById(R.id.txt_link_local);
        txtLinkDos = findViewById(R.id.txt_linkdos_local);
        txtLinkTres = findViewById(R.id.txt_linktres_local);
        txtLinkLogo = findViewById(R.id.txt_link_logo);

        btnImgLocal = findViewById(R.id.btn_img_local);
        btnImgLogo = findViewById(R.id.btn_img_logo);
        edtEtiquetas = findViewById(R.id.edt_etiquetas_local);
        btnAddEtiquetas = findViewById(R.id.btn_add_etiquetas);
        txtEtiquetas = findViewById(R.id.txt_etiquetas);
        btnCrear = findViewById(R.id.btn_crear_nuevolocal);
        btnCancelar = findViewById(R.id.btn_cancelar_nuevolocal);
        imgLocal = findViewById(R.id.img_local);
        imgLocal2 = findViewById(R.id.img_local2);
        imgLocal3 = findViewById(R.id.img_local3);
        imgLogo = findViewById(R.id.img_logo);
        imgChooseColor = findViewById(R.id.img_choose_color_crear_local);
        tooCrearLocal = findViewById(R.id.too_crear_local);
        btnSubirImagenes = findViewById(R.id.btn_subir_img_local);


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

        btnImgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscadorImagen(IMAGEN_PUESTA_LOGO);
            }
        });

        imgLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscadorImagen(IMAGEN_UNO);
            }
        });
        imgLocal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscadorImagen(IMAGEN_DOS);
            }
        });
        imgLocal3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscadorImagen(IMAGEN_TRES);
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
                                utilidades.subirImagen(LOCALES, imgLocalUri, context, txtLinkUno);
                                utilidades.subirImagen(LOCALES, imgLocalUri2, context, txtLinkDos);
                                utilidades.subirImagen(LOCALES, imgLocalUri3, context, txtLinkTres);
                                utilidades.subirImagen(LOCALES, imgLogoUri, context, txtLinkLogo);
                            }
                        }).start();


                    }
                }).start();

                btnSubirImagenes.setVisibility(View.INVISIBLE);

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
            geoPoint = new GeoPoint(loc.getLatitude(), loc.getLongitude());

        }
    }

    private void escogerColor() {
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

    private void llenarEtiquetas() {
        if (edtEtiquetas.getText().toString().isEmpty()) {
            Toast.makeText(this, "Campo basio", Toast.LENGTH_SHORT).show();
        } else {
            arrayEtiquetas.add(edtEtiquetas.getText().toString());
            mostrarEtiquetas();
            edtEtiquetas.setText("");
        }
    }

    private void mostrarEtiquetas() {

        String resultados = "";
        for (int i = 0; i < arrayEtiquetas.size(); i++)
            if (i + 1 < arrayEtiquetas.size())
                resultados += arrayEtiquetas.get(i) + " | ";
            else
                resultados += arrayEtiquetas.get(i);

        txtEtiquetas.setText(resultados);
    }

    private void buscadorImagen(int codImagen) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, codImagen);
    }


    private void crearLocalBd(String id) {

        if (edtNombre.getText().toString().isEmpty() || edtDescripcion.getText().toString().isEmpty() || geoPoint == null || edtTelefono.getText().toString().isEmpty()) {
            Toast.makeText(context, "Datos insuficientes", Toast.LENGTH_SHORT).show();
            return;
        }

        String idCli = idCliente;
        String nombre = edtNombre.getText().toString();
        String direccion = "no direccion";
        String telefono = edtTelefono.getText().toString();
        String descripcion = edtDescripcion.getText().toString();
        Double longitud = Double.parseDouble(edtLogitud.getText().toString());
        Double latitud = Double.parseDouble(edtLatitud.getText().toString());
        int atencion = 2;
        int calidad = 2;
        int precio = 2;
        boolean tarjeta = chbTarjeta.isChecked();
        boolean garaje = chbGarage.isChecked();
        boolean garantia = chbGarantia.isChecked();
        // crear arrayList para imagenes

        ArrayList<String> imagenes = new ArrayList<>();
        String imgLocalUno = txtLinkUno.getText().toString();
        String imgLocalDos = txtLinkDos.getText().toString();
        String imgLocalTres = txtLinkTres.getText().toString();

        imagenes.add(imgLocalUno);
        imagenes.add(imgLocalDos);
        imagenes.add(imgLocalTres);

        GeoPoint ubicacion = new GeoPoint(latitud,longitud);
        String imgLogo = txtLinkLogo.getText().toString();
        long numRecomendado = 0;
        Boolean actualizado = true;
        ArrayList<String> productos = new ArrayList<>();
        ArrayList<String> tangs = (ArrayList<String>) arrayEtiquetas;
        String color;
        ArrayList<String> productosList = new ArrayList<>();


        if (!(edtDireccion.getText().toString().isEmpty())) {
            direccion = edtDireccion.getText().toString();
        }


        if (imgLocalUri == null || imgLogoUri == null) {
            Toast.makeText(this, "Imagenes no Seleccionadas", Toast.LENGTH_SHORT).show();
            return;
        }

        color = String.valueOf(mdefaultColor);
        productos.add(nombre);


        Local local = new Local(idCli,
                nombre, direccion,
                telefono, descripcion,
                ubicacion, atencion,
                calidad, precio, tarjeta,
                garaje, garantia,
                 imgLogo,imagenes,tangs,productosList,
                numRecomendado, actualizado,
                 color, false);


        String idLocal = utilidades.llenarLocal(local);
        if (idLocal != null) {
            utilidades.agregarlocalCliente(idLocal, idCliente);
        }

        String tangs1 = "";
        for (int i = 0; i < tangs.size(); i++)
            if (i + 1 < tangs.size())
                tangs1 += tangs.get(i) + " ";
            else
                tangs1 += tangs.get(i);


        utilidades.subirAlgolia(nombre, direccion, descripcion, "Local Comercial", tangs1);

        Toast.makeText(this, "Local Creado", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(CrearLocal.this, MainActivity.class));


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGEN_UNO && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            imgLocalUri = data.getData();

            Picasso.with(this).load(imgLocalUri).into(imgLocal);

        }
        if (requestCode == IMAGEN_DOS && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            imgLocalUri2 = data.getData();

            Picasso.with(this).load(imgLocalUri2).into(imgLocal2);

        }
        if (requestCode == IMAGEN_TRES && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            imgLocalUri3 = data.getData();

            Picasso.with(this).load(imgLocalUri3).into(imgLocal3);

        }

        if (requestCode == IMAGEN_PUESTA_LOGO && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            imgLogoUri = data.getData();
            Picasso.with(this).load(imgLogoUri).into(imgLogo);
        }
    }
}
