package com.example.subidacuatro.Actividades;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.subidacuatro.Adaptadores.LocalesAdaptador;
import com.example.subidacuatro.Entidades.Local;
import com.example.subidacuatro.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class VerLocales extends AppCompatActivity {

    private static final String LOCALES = "locales";

    private CardView crvVerLocales;
    private RecyclerView rcvLocales;
    private Toolbar tooVerLocales;

    private LocalesAdaptador adaptador;
    private FirebaseFirestore db;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_locales);

        crvVerLocales = findViewById(R.id.crv_item_locales);
        rcvLocales = findViewById(R.id.rcv_ver_locales);
        tooVerLocales = findViewById(R.id.too_ver_locales);

        context = this;
        db = FirebaseFirestore.getInstance();


        llenarRecyclerLocales();
    }

    private void llenarRecyclerLocales() {
        rcvLocales.setHasFixedSize(true);
        rcvLocales.setLayoutManager(new LinearLayoutManager(this));

        CollectionReference ref = db.collection(LOCALES);
        Query query = ref.orderBy("nombre", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Local> options = new FirestoreRecyclerOptions.Builder<Local>()
                .setQuery(query,Local.class)
                .build();

        adaptador = new LocalesAdaptador(options,context);

        rcvLocales.setAdapter(adaptador);

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
