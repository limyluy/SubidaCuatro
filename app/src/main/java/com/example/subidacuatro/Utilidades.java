package com.example.subidacuatro;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subidacuatro.Entidades.Cliente;
import com.example.subidacuatro.Entidades.Historial;
import com.example.subidacuatro.Entidades.Local;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.Format;
import java.util.Calendar;
import java.util.zip.DataFormatException;


public class Utilidades {

    private static final String CLIENTES = "clientes";
    private static final String HISTORIAL = "historial";
    private static final String LOCALES = "locales";

    private FirebaseFirestore db;
    private StorageReference storage;

   private Context context;

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

    public void getNumeroDatos(final TextView textView, String camino) {

        final DocumentReference ref = db.collection("numeros").document(camino);
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                long i = (long) documentSnapshot.get("cantidad");
                textView.setText(String.valueOf(i));
            }
        });


    }

    public String llenarLocal(final Local local) {
        db.collection(LOCALES).document().set(local).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, local.getId(), Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Local no Creado", Toast.LENGTH_SHORT).show();
            }
        });

        return local.getId();
    }

    public String subirImagen(String carpeta, Uri uri, final Context context){
        storage = FirebaseStorage.getInstance().getReference(carpeta);
        String direccion = null;

        StorageReference reference = storage.child(System.currentTimeMillis()+"."
        +getFileExtencion(uri));

        reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task task = taskSnapshot.getStorage().getDownloadUrl();
                while (!task.isSuccessful());
                Uri uri1 = (Uri) task.getResult();

                String direccion = uri1.toString();



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Error al Cargar", Toast.LENGTH_SHORT).show();

            }
        });

        return direccion;
    }
    private String getFileExtencion( Uri uri){
        ContentResolver cR = context.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

}


