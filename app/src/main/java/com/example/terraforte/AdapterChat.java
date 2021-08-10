package com.example.terraforte;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.Myholer> {

    private static final int MSG_TYPE_LEFT = 0;
    private static final int MSG_TYPE_RIHGT = 1;
    Context context;
    List<ModelChat> chatList;

    FirebaseUser fUser;

    String myUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public AdapterChat(Context context, List<ModelChat> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public Myholer onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i==MSG_TYPE_RIHGT) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_chat_right, viewGroup, false);
            return new Myholer(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.row_chat_left, viewGroup, false);
            return new Myholer(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull Myholer myholer, final int i) {
        String message = chatList.get(i).getMessage();
        String timeStamp = chatList.get(i).getTimestamp();
        String type = chatList.get(i).getType();

        //Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        //cal.setTimeInMillis(Long.parseLong(timeStamp));
        //String dateTime = DateFormat.getInstance().format(cal).toString();

        myholer.timeTv.setText("04/08/2021 12:40 PM");

        if(type.equals("text")) {
            myholer.messageTv.setVisibility(View.VISIBLE);
            myholer.messageIv.setVisibility(View.GONE);

            myholer.messageTv.setText(message);
        }else {
            myholer.messageTv.setVisibility(View.GONE);
            myholer.messageIv.setVisibility(View.VISIBLE);

            Picasso.get().load(message).placeholder(R.drawable.ic_image_black).into(myholer.messageIv);
        }

        myholer.messageTv.setText(message);
        //myholer.timeTv.setText(dateTime);

        myholer.messageLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Apagar");
                builder.setMessage("Você tem certeza que quer apagar essa mensagem?");

                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteMessage(i);
                    }
                });

                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.create().show();

                return false;
            }
        });

        myholer.messageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
                String msgTimeStamp = chatList.get(i).getTimestamp();

                BaixarImagem(reference,msgTimeStamp);
            }
        });

        if(i==chatList.size()-1) {
            if(chatList.get(i).isSeen()) {
                myholer.isSeenTv.setText("Vizualisto");
            }else {
                myholer.isSeenTv.setText("Entregue");
            }
        }else {
            myholer.isSeenTv.setVisibility(View.GONE);
        }
    }

    private void DeleteMessage(int position) {
        String msgTimeStamp = chatList.get(position).getTimestamp();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Chats");
        Query query = dbRef.orderByChild("timestamp").equalTo(msgTimeStamp);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {

                    if(ds.child("sender").getValue().equals(myUID)) {
                        ds.getRef().removeValue();

                        //HashMap<String, Object> hashMap = new HashMap<>();
                        //hashMap.put("message", "Essa mensagem foi apagada");
                        //ds.getRef().updateChildren(hashMap);

                        Toast.makeText(context, "Mensagem apagada...", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Você só pode apagar as suas mensagens", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void BaixarImagem(DatabaseReference reference , String timestamp) {
        reference.orderByChild("timestamp").equalTo(timestamp).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    String mensagem = datas.child("message").getValue().toString();
                    String tipoMensagem = datas.child("type").getValue().toString();
                    String idSender = datas.child("sender").getValue().toString();

                    if(tipoMensagem.equals("image") && !idSender.equals(myUID)) {
                        androidx.appcompat.app.AlertDialog.Builder dialog = new androidx.appcompat.app.AlertDialog.Builder(context);
                        dialog.setTitle("Baixar imagem");
                        dialog.setMessage("Deseja baixar a imagem?");
                        dialog.setIcon(android.R.drawable.ic_menu_gallery);

                        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                downloadFiles(context, "teste", ".png", DIRECTORY_DOWNLOADS, mensagem);
                            }
                        });

                        dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }else if(tipoMensagem.equals("pdf") && !idSender.equals(myUID)) {
                        androidx.appcompat.app.AlertDialog.Builder dialog = new androidx.appcompat.app.AlertDialog.Builder(context);
                        dialog.setTitle("Baixar pdf");
                        dialog.setMessage("Deseja baixar o pdf?");
                        dialog.setIcon(android.R.drawable.ic_menu_save);

                        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                downloadFiles(context, "teste", ".pdf", DIRECTORY_DOWNLOADS, mensagem);
                            }
                        });

                        dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, "Erro ao tentar baixar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void downloadFiles(Context context, String fileName, String fileExtension, String destinationDirectory, String url) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);

        downloadManager.enqueue(request);
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    @Override
    public int getItemViewType(int position) {
        fUser = FirebaseAuth.getInstance().getCurrentUser();

        if(chatList.get(position).getSender().equals(fUser.getUid())) {
            return MSG_TYPE_RIHGT;
        }else {
            return MSG_TYPE_LEFT;
        }
    }

    class Myholer extends RecyclerView.ViewHolder{

        ImageView messageIv;
        TextView messageTv, timeTv, isSeenTv;
        LinearLayout messageLayout;

        public Myholer(@NonNull View itemView) {
            super(itemView);

            messageIv = itemView.findViewById(R.id.messageIv);
            messageTv = itemView.findViewById(R.id.messageTv);
            timeTv = itemView.findViewById(R.id.timeTv);
            isSeenTv = itemView.findViewById(R.id.isSentTv);
            messageLayout = itemView.findViewById(R.id.messageLayout);
        }

        //Obs: Deixei ele para caso precise usar no futuro
        /*
        private void download() {
            //Obs: Os arquivos pdf exigem o .pdf nome nome enquanto que as imagens dão erro caso coloque o formato no child()
            storageReference = firebaseStorage.getInstance().getReference();
            ref = storageReference.child("ChatImage/post_1628387419724");

            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    String url = uri.toString();
                    downloadFiles(context, "pdf1", ".png", DIRECTORY_DOWNLOADS, url);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Erro ao baixar a imagem", Toast.LENGTH_SHORT).show();
                }
            });
        }
         */
    }
}
