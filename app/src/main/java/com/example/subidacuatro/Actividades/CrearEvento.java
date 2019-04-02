package com.example.subidacuatro.Actividades;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subidacuatro.R;
import com.google.firebase.firestore.GeoPoint;

import java.util.List;

public class CrearEvento extends AppCompatActivity {


    private EditText edtNombre;
    private EditText edtDescripcion;
    private EditText edtDesAdicional;
    private EditText edtespecificaicones;
    private EditText edtCategoria;
    private ImageView imgUno;
    private ImageView imgDos;
    private ImageView imgTres;
    private Button btnFecha;
    private Button btnUbicacion;
    private Button btnAddCategoria;
    private TextView txtCategorias;
    private Button btnCrear;
    private Button btnCancelar;

    private GeoPoint geoPoint;


    private Context context;
    private LocationManager locManager;
    private Location loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_evento);

        edtNombre = findViewById(R.id.edt_nom_evento);
        edtDescripcion = findViewById(R.id.edt_des_evento);
        edtDesAdicional = findViewById(R.id.edt_desadicional_evento);
        edtespecificaicones = findViewById(R.id.edt_especificaciones_evento);
        edtCategoria = findViewById(R.id.edt_etiquetas_evento);
        imgUno = findViewById(R.id.img_evento);
        imgDos = findViewById(R.id.img_evento1);
        imgTres = findViewById(R.id.img_evento2);
        btnFecha = findViewById(R.id.btn_fecha_evento);
        btnUbicacion = findViewById(R.id.btn_ubicacion_evento);
        btnAddCategoria = findViewById(R.id.btn_add_categoria);
        txtCategorias = findViewById(R.id.txt_categorias_evento);
        btnCrear = findViewById(R.id.btn_crear_nuevoevento);
        btnCancelar = findViewById(R.id.btn_cancelar_nuevoevento);

        context = getApplicationContext();

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearNuevoEvento();
            }
        });

    }

    private void crearNuevoEvento() {

        if (edtNombre.getText().toString().isEmpty() || edtDescripcion.getText().toString().isEmpty()) {
            Toast.makeText(context, "Datos insuficientes", Toast.LENGTH_SHORT).show();
            return;
        }


        String nombre = edtNombre.getText().toString();
        String descripcion = edtDescripcion.getText().toString();
        GeoPoint ubicacion = null;
        String fecha;
        String desAdicional;
        String especificaciones;
        List<String> categoria;
        List<String> fotos;
        List<String> locales;

        if (geoPoint == null){
            Toast.makeText(context, "Ubicacion no indicada", Toast.LENGTH_SHORT).show();
            return;
        }else{
            ubicacion = geoPoint;
        }




    }

    private void obtenerUbicacion() {
        ActivityCompat.requestPermissions(CrearEvento.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Permiso de Ubicaion no Activado", Toast.LENGTH_SHORT).show();

            return;
        } else {

            locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            geoPoint = new GeoPoint(loc.getLatitude(),loc.getLongitude());

        }
    }
}
