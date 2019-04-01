package com.example.subidacuatro.Actividades;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.example.subidacuatro.Adaptadores.ProductoAdaptador;
import com.example.subidacuatro.Entidades.Productos;
import com.example.subidacuatro.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.PriorityQueue;

public class VerProductos extends AppCompatActivity {

    private static final String PRODUCTOS = "productos";

    private RecyclerView rcvVerProductos;
    private Toolbar tooVerProductos;

    private FirebaseFirestore db;
    private ProductoAdaptador adaptador;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_productos);

        rcvVerProductos = findViewById(R.id.rcv_ver_productos);
        tooVerProductos = findViewById(R.id.too_ver_productos);

        context = getApplicationContext();
        db = FirebaseFirestore.getInstance();

        llenarRecycler();
    }

    private void llenarRecycler() {
        rcvVerProductos.setHasFixedSize(true);
        rcvVerProductos.setLayoutManager(new LinearLayoutManager(this));

        CollectionReference ref = db.collection(PRODUCTOS);
        Query query = ref.orderBy("nombre", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Productos> options = new FirestoreRecyclerOptions.Builder<Productos>()
                .setQuery(query,Productos.class)
                .build();

        adaptador = new ProductoAdaptador(options,context);

        rcvVerProductos.setAdapter(adaptador);


    }

    @Override
    protected void onStart() {
        super.onStart();
        adaptador.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adaptador.stopListening();
    }
}
