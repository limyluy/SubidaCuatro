package com.example.subidacuatro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.subidacuatro.Actividades.CrearCliente;
import com.example.subidacuatro.Actividades.VerClientes;
import com.example.subidacuatro.Actividades.VerEvento;
import com.example.subidacuatro.Actividades.VerLocales;
import com.example.subidacuatro.Actividades.VerProductos;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private Button btnNuevoCliente;
    private Button btnNuevoLocal;
    private Button btnNuevoProducto;
    private Button btnNuevoEvento;
    private CardView crvClientes;
    private CardView crvLocales;
    private CardView crvProductos;
    private CardView crvEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNuevoCliente = findViewById(R.id.btn_main_nuevo_cliente);
        btnNuevoLocal = findViewById(R.id.btn_main_nuevo_locales);
        btnNuevoProducto = findViewById(R.id.btn_main_nuevo_productos);
        btnNuevoEvento = findViewById(R.id.btn_main_nuevo_eventos);
        crvClientes = findViewById(R.id.crv_main_clientes);
        crvLocales = findViewById(R.id.crv_main_locales);
        crvProductos = findViewById(R.id.crv_main_productos);
        crvEventos = findViewById(R.id.crv_main_eventos);


        btnNuevoCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CrearCliente.class));
            }
        });

        crvClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VerClientes.class));
            }
        });

        btnNuevoLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this,VerClientes.class);
               intent.putExtra("local",true);
               startActivity(intent);
            }
        });

        crvLocales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VerLocales.class));
            }
        });

        btnNuevoProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,VerLocales.class);
                intent.putExtra("producto",true);
                startActivity(intent);
            }
        });

        crvProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VerProductos.class);
                startActivity(intent);
            }
        });

        btnNuevoEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,VerLocales.class);
                intent.putExtra("evento",true);
                startActivity(intent);
            }
        });

        crvEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VerEvento.class));
            }
        });

    }

}
