package com.example.terraforte;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class EditarUsuario extends AppCompatActivity {
    private EditText nome_completo, nome_usuario, apelido, email, telefone, endereco;
    private Button cancelar, atualizar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_usuario);
        IniciarComponentes();

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAlert();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VoltarPerfil();
            }
        });
    }

    private void IniciarComponentes() {
        nome_completo = findViewById(R.id.idNomeCompletoEditPerfil);
        nome_usuario = findViewById(R.id.idNomeUsuarioEditPerfil);
        apelido = findViewById(R.id.idApelidoEditPerfil);
        email = findViewById(R.id.idEmailEditPerfil);
        telefone = findViewById(R.id.idTelefoneEditPerfil);
        endereco = findViewById(R.id.idEnderecoEditPerfil);
        cancelar = findViewById(R.id.btn_cancelar_editPerfil);
        atualizar = findViewById(R.id.btn_atualizar_editPerfil);
    }

    private void AtualizarPerfil(String password) {
        if(password.length() > 0) {
            DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            String edit_email_autendicar = FirebaseAuth.getInstance().getCurrentUser().getEmail();

            AuthCredential credential = EmailAuthProvider.getCredential(edit_email_autendicar, password);

            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        Log.d("Sucesso", "User re-autenticado.");

                        String edit_nome_completo = nome_completo.getText().toString();
                        String edit_usuario = nome_usuario.getText().toString();
                        String edit_apelido = apelido.getText().toString();
                        String edit_email = email.getText().toString();
                        String edit_telefone = telefone.getText().toString();
                        String edit_endereco = endereco.getText().toString();

                        user.updateEmail(edit_email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("Sucesso", "User email address updated.");
                                }else {
                                    String erro;
                                    try {
                                        throw task.getException();
                                    }catch (FirebaseAuthWeakPasswordException e) {
                                        erro = "Digite uma senha com no mínimo 6 caractéres";
                                    }catch (FirebaseAuthUserCollisionException e) {
                                        erro = "Esta conta já foi  cadastrada";
                                    }catch (FirebaseAuthInvalidCredentialsException e) {
                                        erro = "E-mail inválido";
                                    }catch (Exception e){
                                        erro = "Erro ao atualizar usuário";
                                    }
                                    Toast.makeText(EditarUsuario.this, erro, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        documentReference.update("nome_completo", edit_nome_completo,"nome_usuario", edit_usuario, "apelido", edit_apelido, "telefone", edit_telefone, "endereco", edit_endereco).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("db", "Sucesso ao atualizar os dados");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("db_error", "Erro ao atualizar os dados");
                            }
                        });

                        VoltarPerfil();

                    }else {
                        Toast.makeText(EditarUsuario.this, "Senha incorreta", Toast.LENGTH_SHORT).show();
                        DialogAlert();
                    }
                }
            });
        }else {
            Toast.makeText(EditarUsuario.this, "Você não digitou a senha", Toast.LENGTH_SHORT).show();
            DialogAlert();
        }
    }

    private void VoltarPerfil() {
        Intent intent = new Intent(this, Perfil.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        String edit_email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if(documentSnapshot != null) {
                    nome_completo.setText(documentSnapshot.getString("nome_completo"));
                    nome_usuario.setText(documentSnapshot.getString("nome_usuario"));
                    apelido.setText(documentSnapshot.getString("apelido"));
                    telefone.setText(documentSnapshot.getString("telefone"));
                    endereco.setText(documentSnapshot.getString("endereco"));

                    email.setText(edit_email);
                }
            }
        });
    }

    private void DialogAlert() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(EditarUsuario.this);

        dialog.setTitle("Atualizar dados");
        dialog.setMessage("Por favor digite a sua senha");
        final EditText edit_senha = new EditText(EditarUsuario.this);
        edit_senha.setInputType(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_VARIATION_PASSWORD);
        dialog.setView(edit_senha);

        dialog.setCancelable(false);

        dialog.setPositiveButton("Atualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String senha = edit_senha.getText().toString();
                AtualizarPerfil(senha);
            }
        });

        dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        dialog.show();
    }
}