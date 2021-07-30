package com.example.terraforte;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListaAddContatoAdapter extends RecyclerView.Adapter<ListaAddContatoAdapter.myViewHolder>  {
    List<Usuario> datalist;

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

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView nome_usuario, apelido, select;
        //ImageView place_image;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            nome_usuario = itemView.findViewById(R.id.id_nomeUsuario_addContato);
            apelido = itemView.findViewById(R.id.id_apelido_addContato);
            //place_image = itemView.findViewById(R.id.place_image);
            select = itemView.findViewById(R.id.select);

            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
