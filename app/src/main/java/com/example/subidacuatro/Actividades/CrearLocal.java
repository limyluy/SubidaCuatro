package com.example.subidacuatro.Actividades;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subidacuatro.Entidades.Local;
import com.example.subidacuatro.R;
import com.example.subidacuatro.Utilidades;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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

    private double longitud;
    private double latitud;
    private Uri imgLocalUri;
    private Uri imgLogoUri;

    private List<String> arrayEtiquetas = new ArrayList<>();
    private boolean locationPrendida;
    private boolean trazado;
    private Button btnCrear;
    private Button btnCancelar;
    LocationManager locationManager;

    private StorageReference mStoraRef;
    private StorageTask mUploadTask;

    private Context context;
    private Utilidades utilidades;
    private GeoPoint geoPoint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_local);



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

        context = this;
        utilidades = new Utilidades(context);

        final String idCliente = getIntent().getStringExtra("id");

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
            public void onClick(View v) {               buscadorImagen(IMAGEN_PUESTA_LOGO);            }
        });
        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLocationEnabled()) {
                    showAlert(); return;
                }else{geoPoint = obtenerGeoPoint();}
            }
        });
    }

    private void llenarEtiquetas() {
        if (edtEtiquetas.getText().toString().isEmpty()){
            Toast.makeText(this, "Campo basio", Toast.LENGTH_SHORT).show();
        }else{
            arrayEtiquetas.add(edtEtiquetas.getText().toString());
            mostrarEtiquetas();
            edtEtiquetas.setText("");
        }
    }

    private void mostrarEtiquetas() {

        String resultados = "";
        for (int i = 0; i < arrayEtiquetas.size(); i++)
            if(i + 1 < arrayEtiquetas.size())
                resultados += arrayEtiquetas.get(i) + " | ";
            else
                resultados += arrayEtiquetas.get(i);

        txtEtiquetas.setText(resultados);
    }

    private void buscadorImagen(int codImagen) {
        Intent intent =  new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,codImagen);
    }

    private GeoPoint obtenerGeoPoint() {
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                longitud = location.getLongitude();
                latitud = location.getLatitude();

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        GeoPoint geoPoint = new GeoPoint(latitud, longitud);


        return geoPoint;
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Su ubicación esta desactivada.\npor favor active su ubicación " +
                        "usa esta app")
                .setPositiveButton("Configuración de ubicación", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void crearLocalBd(String id) {

        if (edtNombre.getText().toString().isEmpty() || edtDescripcion.getText().toString().isEmpty() || geoPoint == null || edtTelefono.getText().toString().isEmpty()){
            Toast.makeText(context, "Datos insuficientes", Toast.LENGTH_SHORT).show();
            return;
        }

         String nombre = edtNombre.getText().toString();
         String direccion = "no direccion";
         String telefono = edtTelefono.getText().toString();
         String descripcion = edtDescripcion.getText().toString();
         GeoPoint ubicacion = geoPoint;
         int atencion = 0;
         int calidad = 0;
         int precio = 0;
         boolean tarjeta = chbTarjeta.isActivated();
         boolean garaje = chbGarage.isActivated();
         boolean garantia = chbGarantia.isActivated();
         String imgLocal;
         String imgLogo;
         long numRecomendado = 0;
         Boolean actualizado = true;
         List<String> clientes = new ArrayList<>() ;
         List<String> etiquetas = arrayEtiquetas;


        if (!(edtDireccion.getText().toString().isEmpty())){
            direccion = edtDireccion.getText().toString();
        }

        clientes.add(id);

        if (imgLocalUri == null || imgLogoUri == null){
            Toast.makeText(this, "Imagenes no Seleccionadas", Toast.LENGTH_SHORT).show();
            return;
        }
        imgLocal = utilidades.subirImagen(LOCALES,imgLocalUri,context);
        imgLogo = utilidades.subirImagen(LOCALES,imgLogoUri,context);




        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGEN_PUESTA && resultCode ==   RESULT_OK
                && data != null && data.getData() != null){

            imgLocalUri =data.getData();

            Picasso.with(this).load(imgLocalUri).into(imgLocal);

        }

        if (requestCode == IMAGEN_PUESTA_LOGO && resultCode == RESULT_OK
                && data != null && data.getData() != null){

            imgLogoUri = data.getData();

            Picasso.with(this).load(imgLogoUri).into(imgLogo);
        }
    }
}
