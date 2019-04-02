package com.example.subidacuatro.Actividades;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.subidacuatro.Entidades.Evento;
import com.example.subidacuatro.MainActivity;
import com.example.subidacuatro.R;
import com.example.subidacuatro.Utilidades;
import com.google.firebase.firestore.GeoPoint;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CrearEvento extends AppCompatActivity {

    private static final int IMAGEN_UNO = 1;
    private static final int IMAGEN_DOS = 2;
    private static final int IMAGEN_TRES = 3;
    private static final String LOCALES = "locales";


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
    private Button btnSubirImgs;
    private TextView txtUno;
    private TextView txtDos;
    private TextView txtTres;
    private Toolbar tooCrearEvento;

    private GeoPoint geoPoint;
    private Uri uriUno;
    private Uri uriDos;
    private Uri uriTres;


    private Context context;
    private LocationManager locManager;
    private Location loc;
    private Utilidades utilidades;

    private List<String> arrayCategorias = new ArrayList<>();
    private List<String> arratFotos = new ArrayList<>();
    private String idLocal;
    private String nomLocal;

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
        btnSubirImgs = findViewById(R.id.btn_subir_imgs_evento);
        txtUno = findViewById(R.id.txt_img_uno_eventos);
        txtDos = findViewById(R.id.txt_img_dos_eventos);
        txtTres = findViewById(R.id.txt_img_tres_eventos);
        tooCrearEvento = findViewById(R.id.too_crear_evento);

        context = getApplicationContext();
        utilidades = new Utilidades(context);

        idLocal = getIntent().getStringExtra("id");
        nomLocal = getIntent().getStringExtra("nombre");
        tooCrearEvento.setTitle(nomLocal);

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearNuevoEvento();
            }
        });

        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerUbicacion();
            }
        });

        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha();
            }
        });

        btnAddCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llenarEtiquetas();
            }
        });

        imgUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscadorImagen(IMAGEN_UNO);
            }
        });

        imgDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscadorImagen(IMAGEN_DOS);
            }
        });

        imgTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscadorImagen(IMAGEN_TRES);
            }
        });

        btnSubirImgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uriUno == null || uriDos == null || uriTres == null){
                    Toast.makeText(context, "No imagenes seleccionadas", Toast.LENGTH_SHORT).show();
                }
                utilidades.subirImagen(LOCALES,uriUno,context,txtUno);
                utilidades.subirImagen(LOCALES,uriDos,context,txtDos);
                utilidades.subirImagen(LOCALES,uriTres,context,txtTres);
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
        String desAdicional = "";
        String especificaciones = "";
        List<String> categoria = arrayCategorias;
        List<String> fotos = new ArrayList<>();
        List<String> locales = new ArrayList<>();

        if (geoPoint == null) {
            Toast.makeText(context, "Ubicacion no indicada", Toast.LENGTH_SHORT).show();
            return;
        } else {
            ubicacion = geoPoint;
        }

        fecha = btnFecha.getText().toString();

        if (!(edtDesAdicional.toString().isEmpty()))
            desAdicional = edtDesAdicional.getText().toString();
        if (!(edtespecificaicones.toString().isEmpty()))
            especificaciones = edtespecificaicones.getText().toString();

        fotos.add(txtUno.getText().toString());
        fotos.add(txtDos.getText().toString());
        fotos.add(txtTres.getText().toString());

        locales.add(idLocal);

        Evento evento = new Evento(nombre,descripcion,ubicacion,fecha,desAdicional,especificaciones,categoria,fotos,locales);
        utilidades.agregarEventoLocal(evento,idLocal);
        startActivity(new Intent(CrearEvento.this, MainActivity.class));



    }

    private void obtenerUbicacion() {
        ActivityCompat.requestPermissions(CrearEvento.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Permiso de Ubicaion no Activado", Toast.LENGTH_SHORT).show();

            return;
        } else {

            locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            geoPoint = new GeoPoint(loc.getLatitude(), loc.getLongitude());

            btnUbicacion.setText(String.valueOf(geoPoint.getLatitude()));

        }
    }

    private void obtenerFecha() {


        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        btnFecha.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void llenarEtiquetas() {
        if (edtCategoria.getText().toString().isEmpty()) {
            Toast.makeText(this, "Campo basio", Toast.LENGTH_SHORT).show();
        } else {
            arrayCategorias.add(edtCategoria.getText().toString());
            mostrarEtiquetas();
            edtCategoria.setText("");
        }
    }

    private void mostrarEtiquetas() {

        String resultados = "";
        for (int i = 0; i < arrayCategorias.size(); i++)
            if (i + 1 < arrayCategorias.size())
                resultados += arrayCategorias.get(i) + " | ";
            else
                resultados += arrayCategorias.get(i);

        txtCategorias.setText(resultados);
    }

    private void buscadorImagen(int codImagen) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, codImagen);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGEN_UNO && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            uriUno = data.getData();


            Picasso.with(this).load(uriUno).into(imgUno);

        }

        if (requestCode == IMAGEN_DOS && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            uriDos = data.getData();


            Picasso.with(this).load(uriDos).into(imgDos);

        }

        if (requestCode == IMAGEN_TRES && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            uriTres = data.getData();


            Picasso.with(this).load(uriTres).into(imgTres);

        }


    }
}
