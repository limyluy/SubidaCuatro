package com.example.subidacuatro.Adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;

public class EventoAdaptador extends FirestoreRecyclerAdapter {

    class EvetoViewHolder extends RecyclerView.ViewHolder{
        {}
        public EvetoViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
