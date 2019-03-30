package com.example.subidacuatro.Actividades;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.subidacuatro.Adaptadores.ClienteAdaptador;
import com.example.subidacuatro.Entidades.Cliente;
import com.example.subidacuatro.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class VerClientes extends AppCompatActivity {

    private static final String CLIENTES = "clientes";
    private RecyclerView rcvClientes;
    private CardView crvLocalSinCliente;
    private Toolbar tooVerCLiente;
    private ClienteAdaptador adaptador;
    private Context context;
    private FirebaseFirestore db;
    private boolean nuevoLocal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_clientes);



        rcvClientes = findViewById(R.id.rcv_ver_clientes);
        crvLocalSinCliente = findViewById(R.id.crv_ver_nocliente);
        tooVerCLiente = findViewById(R.id.too_ver_cliente);

        tooVerCLiente.setTitle("Todos los Clientes");
        nuevoLocal = getIntent().getBooleanExtra("local", false);

        db = FirebaseFirestore.getInstance();
        context = this;

        llenarRecycler();

        if (nuevoLocal) {
            cargarInterfazSeleccion();
            tooVerCLiente.setTitle("Escoger Cliente");
        }


    }

    private void cargarInterfazSeleccion() {
        crvLocalSinCliente.setVisibility(View.VISIBLE);
        crvLocalSinCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerClientes.this, CrearLocal.class);
                intent.putExtra("nombre", "no cliente");
                intent.putExtra("id", "no id");
                startActivity(intent);
            }
        });

        adaptador.setOnItemClickListener(new ClienteAdaptador.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                Cliente cliente = documentSnapshot.toObject(Cliente.class);
                Intent intent = new Intent(VerClientes.this, CrearLocal.class);
                intent.putExtra("nombre", cliente.getNombre());
                intent.putExtra("id", cliente.getId());

                startActivity(intent);
            }
        });

    }

    private void llenarRecycler() {


        CollectionReference ref = db.collection(CLIENTES);
        Query query = ref.orderBy("nombre", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Cliente> options = new FirestoreRecyclerOptions.Builder<Cliente>()
                .setQuery(query, Cliente.class)
                .build();

        adaptador = new ClienteAdaptador(options, context);

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
