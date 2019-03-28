package com.example.subidacuatro;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subidacuatro.Entidades.Cliente;
import com.example.subidacuatro.Entidades.Historial;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.Format;
import java.util.Calendar;
import java.util.zip.DataFormatException;


public class Utilidades {

    private static final String CLIENTES = "clientes";
    private static final String HISTORIAL = "historial";

    FirebaseFirestore db;
    Context context;

    public Utilidades(Context context) {
        db = FirebaseFirestore.getInstance();
        this.context = context;
    }

    public String llenarCliente(final Cliente cliente) {

        db.collection(CLIENTES).document().set(cliente).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Cliete " + cliente.getId() + "creado", Toast.LENGTH_SHORT).show();
                Calendar c = Calendar.getInstance();
                c.getTimeInMillis();
                Historial historial = new Historial("Ingreso cliente", cliente.getNombre(), c.toString(), cliente.getId());
                llenarHistorial(historial);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Fallo en subida", Toast.LENGTH_SHORT).show();

            }
        });
        return cliente.getId();
    }

    public void llenarHistorial(Historial historial) {

        db.collection(HISTORIAL).document().set(historial).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Historial no llenado", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getNumeroDatos(final TextView textView,String camino) {

        final DocumentReference ref = db.collection("numeros").document(camino);
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
               long i = (long) documentSnapshot.get("cantidad");
               textView.setText(String.valueOf(i));
            }
        });


    }


}


