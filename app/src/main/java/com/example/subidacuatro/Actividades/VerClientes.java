package com.example.subidacuatro.Actividades;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.subidacuatro.Adaptadores.ClienteAdaptador;
import com.example.subidacuatro.Entidades.Cliente;
import com.example.subidacuatro.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class VerClientes extends AppCompatActivity {

    private static final String CLIENTES = "clientes";
    private RecyclerView rcvClientes;
    private ClienteAdaptador adaptador;
    private Context context;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_clientes);

        rcvClientes = findViewById(R.id.rcv_ver_clientes);

        db = FirebaseFirestore.getInstance();
        context = this;

        llenarRecycler();
    }

    private void llenarRecycler() {


        CollectionReference ref = db.collection(CLIENTES);
        Query query = ref.orderBy("nombre", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Cliente> options =new FirestoreRecyclerOptions.Builder<Cliente>()
                .setQuery(query,Cliente.class)
                .build();

        adaptador = new ClienteAdaptador(options,context);

        rcvClientes.setHasFixedSize(true);
        rcvClientes.setLayoutManager(new LinearLayoutManager(this));
        rcvClientes.setAdapter(adaptador);
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
