package com.example.subidacuatro;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import com.algolia.search.saas.Client;
import com.algolia.search.saas.Index;
import com.example.subidacuatro.Entidades.Cliente;
import com.example.subidacuatro.Entidades.Evento;
import com.example.subidacuatro.Entidades.Historial;
import com.example.subidacuatro.Entidades.Local;
import com.example.subidacuatro.Entidades.Productos;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Utilidades {

    private static final String CLIENTES = "clientes";
    private static final String HISTORIAL = "historial";
    private static final String LOCALES = "locales";
    private static final String PRODUCTOS = "productos";
    private static final String EVENTOS = "eventos";

    private FirebaseFirestore db;
    private StorageReference storage;

    Index index;

    private Context context;

    public Utilidades(Context context) {
        db = FirebaseFirestore.getInstance();
        this.context = context;

        Client client = new Client("LXY84IU2VY", "6c9ae679bdfb1407717fc422cb2784a5");
        index = client.getIndex("productos");
    }

    public String llenarCliente(final Cliente cliente) {

        db.collection(CLIENTES).document(cliente.getId()).set(cliente).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Cliete " + cliente.getNombre() + "creado", Toast.LENGTH_SHORT).show();
                Calendar c = Calendar.getInstance();
                c.getTimeInMillis();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Fallo ", Toast.LENGTH_LONG).show();

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
        db.collection(LOCALES).document(local.getId()).set(local).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    public void subirImagen(String carpeta, final Uri uri, final Context context, final TextView textView) {
        storage = FirebaseStorage.getInstance().getReference(carpeta);
        StorageReference reference = storage.child(System.currentTimeMillis() + "."
                + getFileExtencion(uri));


        reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {


            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task task = taskSnapshot.getStorage().getDownloadUrl();
                while (!task.isSuccessful()) ;
                Uri uri1 = (Uri) task.getResult();
                Toast.makeText(context, "Imagen Subida ", Toast.LENGTH_SHORT).show();

                textView.setText(String.valueOf(uri1));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Error al Cargar", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private String getFileExtencion(Uri uri) {
        ContentResolver cR = context.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void agregarlocalCliente(String idLocal, String idCliente) {


        DocumentReference reference = db.collection(CLIENTES).document(idCliente);

        reference.update("locales", FieldValue.arrayUnion(idLocal)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Local asignado", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public String agregarProductoLocal(final Productos productos, String idLocal) {

        db.collection(PRODUCTOS).document(productos.getId()).set(productos).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, productos.getId(), Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Fallo subida", Toast.LENGTH_SHORT).show();
            }
        });


        DocumentReference reference = db.collection(LOCALES).document(idLocal);

        reference.update("productos", FieldValue.arrayUnion(productos.getId())).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "producto asignado asignado", Toast.LENGTH_SHORT).show();
            }
        });

        return productos.getId();


    }

    public String agregarEventoLocal(final Evento evento, String idLocal) {

        db.collection(EVENTOS).document(evento.getId()).set(evento).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, evento.getId(), Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Fallo subida", Toast.LENGTH_SHORT).show();
            }
        });


        DocumentReference reference = db.collection(LOCALES).document(idLocal);

        reference.update("productos", FieldValue.arrayUnion(evento.getId())).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "evento asignado asignado", Toast.LENGTH_SHORT).show();
            }
        });


        return evento.getId();


    }

    public String subirAlgolia(String nombre, String procedencia, String descripcion, String tipo,String tangs) {

        JSONObject object = null;
        try {
            object = new JSONObject()
                    .put("nombre", nombre)
                    .put("procedencia", procedencia)
                    .put("descripcion", descripcion)
                    .put("tipo", tipo)
                    .put("tangs",tangs);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        String id = "001." + String.valueOf(System.currentTimeMillis());

        index.addObjectAsync(object, id, null);

        return id;


    }


}


