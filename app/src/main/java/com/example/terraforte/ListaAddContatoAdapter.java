package com.example.terraforte;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListaAddContatoAdapter extends RecyclerView.Adapter<ListaAddContatoAdapter.myViewHolder>  {
    List<Usuario> datalist;
    String nomeUsuario;
    private Context context;

    public ListaAddContatoAdapter(ArrayList<Usuario> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addcontato_item, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.nome_usuario.setText( datalist.get(position).getNome_usuario() );
        holder.apelido.setText(datalist.get(position).getApelido());
        //Picasso.get().load(datalist.get(position).getImage_url()).into(holder.place_image);
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        TextView nome_usuario, apelido, select, UsuarioId;
        //ImageView place_image;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            nome_usuario = itemView.findViewById(R.id.id_nomeUsuario_addContato);
            apelido = itemView.findViewById(R.id.id_apelido_addContato);
            //place_image = itemView.findViewById(R.id.place_image);
            select = itemView.findViewById(R.id.select);
            context = itemView.getContext();

            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String idContato = "";

                    int pos = getAdapterPosition();

                    if (pos!=RecyclerView.NO_POSITION){

                        idContato = datalist.get(pos).getId_usuario();

                        Intent intent = new Intent(v.getContext(), PerfilContato.class);
                        Bundle b = new Bundle();
                        b.putString("IdContato", idContato);
                        intent.putExtras(b);
                        v.getContext().startActivity(intent);

                    }
                }
            });
        }
    }
}
