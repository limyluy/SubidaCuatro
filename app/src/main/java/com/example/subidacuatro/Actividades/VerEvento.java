package com.example.subidacuatro.Actividades;

import android.content.Context;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.subidacuatro.Adaptadores.EventoAdaptador;
import com.example.subidacuatro.Entidades.Evento;
import com.example.subidacuatro.Entidades.Local;
import com.example.subidacuatro.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class VerEvento extends AppCompatActivity {

    private static final String EVENTOS = "eventos";

    private RecyclerView rcvVerEventos;
    private Toolbar tooVerEventos;
    private CardView crvVerEventos;

    private FirebaseFirestore db;
    private EventoAdaptador adaptador;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_evento);

        rcvVerEventos = findViewById(R.id.rcv_ver_eventos);
        tooVerEventos = findViewById(R.id.too_ver_eventos);
        crvVerEventos = findViewById(R.id.crv_ver_eventos);

        context = getApplicationContext();
        db = FirebaseFirestore.getInstance();


        llenarRecycler();


    }

    private void llenarRecycler() {
        rcvVerEventos.setHasFixedSize(true);
        rcvVerEventos.setLayoutManager(new LinearLayoutManager(this));

        CollectionReference ref = db.collection(EVENTOS);
        Query query = ref.orderBy("nombre", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Evento> options = new FirestoreRecyclerOptions.Builder<Evento>()
                .setQuery(query, Evento.class)
                .build();

        adaptador =  adaptador = new EventoAdaptador(options,context);
        rcvVerEventos.setAdapter(adaptador);

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
