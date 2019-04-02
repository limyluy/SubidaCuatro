package com.example.subidacuatro.Actividades;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.subidacuatro.Adaptadores.ClienteAdaptador;
import com.example.subidacuatro.Adaptadores.LocalesAdaptador;
import com.example.subidacuatro.Entidades.Cliente;
import com.example.subidacuatro.Entidades.Local;
import com.example.subidacuatro.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class VerLocales extends AppCompatActivity {

    private static final String LOCALES = "locales";

    private CardView crvVerLocales;
    private RecyclerView rcvLocales;
    private Toolbar tooVerLocales;
    private CardView crvNoLocal;

    private LocalesAdaptador adaptador;
    private FirebaseFirestore db;
    private  Context context;
    private Boolean vieneLocales;
    private Boolean vieneEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_locales);

        crvVerLocales = findViewById(R.id.crv_item_locales);
        rcvLocales = findViewById(R.id.rcv_ver_locales);
        tooVerLocales = findViewById(R.id.too_ver_locales);
        crvNoLocal = findViewById(R.id.crv_nolocal_ver_locales);

        vieneLocales = getIntent().getBooleanExtra("producto",false);
        vieneEventos = getIntent().getBooleanExtra("evento",false);

        context = this;
        db = FirebaseFirestore.getInstance();
        llenarRecyclerLocales();
        if (vieneEventos){
            cargarIntergasSeleccionEventos();
        }

        if (vieneLocales){
            cargarInterfasSelccion();
        }
    }

    private void cargarIntergasSeleccionEventos() {
        crvNoLocal.setVisibility(View.VISIBLE);
        crvNoLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerLocales.this, CrearEvento.class);
                intent.putExtra("nombre", "no cliente");
                intent.putExtra("id", "no id");
                startActivity(intent);
            }
        });

        adaptador.setOnItemClickListener(new ClienteAdaptador.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                Local local = documentSnapshot.toObject(Local.class);
                Intent intent = new Intent(VerLocales.this, CrearEvento.class);
                intent.putExtra("nombre", local.getNombre());
                intent.putExtra("id", local.getId());

                startActivity(intent);
            }
        });
    }

    private void cargarInterfasSelccion()
         {
            crvNoLocal.setVisibility(View.VISIBLE);
            crvNoLocal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(VerLocales.this, CrearProducto.class);
                    intent.putExtra("nombre", "no cliente");
                    intent.putExtra("id", "no id");
                    startActivity(intent);
                }
            });

            adaptador.setOnItemClickListener(new ClienteAdaptador.OnItemClickListener() {
                @Override
                public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                    Local local = documentSnapshot.toObject(Local.class);
                    Intent intent = new Intent(VerLocales.this, CrearProducto.class);
                    intent.putExtra("nombre", local.getNombre());
                    intent.putExtra("id", local.getId());

                    startActivity(intent);
                }
            });


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
