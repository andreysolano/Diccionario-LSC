package com.example.diccionariolsc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class Registro_Usuario extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference();

    EditText nombreIngresado, contraseñaIngresada, contraseña2Ingresada;
    Button botonRegistro;
    Switch switchAdmin;

    String nombreUsuario, contraseña, contraseña2, ID;
    Boolean administrador = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_usuario);

        mAuth = FirebaseAuth.getInstance();

        nombreIngresado = (EditText) findViewById(R.id.nombre_usuario);
        contraseñaIngresada = (EditText) findViewById(R.id.contraseña_usuario);
        contraseña2Ingresada = (EditText) findViewById(R.id.contraseña2);
        switchAdmin = findViewById(R.id.switch_Administrador);

        //Switch para crear un usuario Administrador
        switchAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchAdmin.isChecked()){
                    administrador = true;
                    Toast.makeText(Registro_Usuario.this, "Modo Administrador!", Toast.LENGTH_SHORT).show();
                }else{
                    administrador = false;
                    Toast.makeText(Registro_Usuario.this, "Modo Usuario!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        botonRegistro = (Button) findViewById(R.id.registroUsuario);
        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nombreUsuario =nombreIngresado.getText().toString();
                contraseña = contraseñaIngresada.getText().toString();
                contraseña2 = contraseña2Ingresada.getText().toString();
                if(!nombreUsuario.isEmpty() && !contraseña.isEmpty() && !contraseña2.isEmpty()){ //Verificar que no hayan campos vacios
                    if(contraseña.length() < 6){
                        Toast.makeText(Registro_Usuario.this,"La contraseña debe" +
                                " tener por lo menos  6 caracteres",Toast.LENGTH_SHORT).show();
                    }else {
                        if(contraseña.equals(contraseña2)){
                            registrarUsuario();
                        }else{
                            Toast.makeText(Registro_Usuario.this,"Las contraseñas no coinciden",Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(Registro_Usuario.this,"Porfavor llene todos los campos de texto",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registrarUsuario() {
        mAuth.createUserWithEmailAndPassword(nombreUsuario,contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Map<String,Object> datosUsuario = new HashMap<>(); //Map que contiene los hijos de FB
                    if(administrador){
                        datosUsuario.put("Administrador","SI");
                        datosUsuario.put("Palabras","");
                    }else{
                        datosUsuario.put("Administrador","NO");
                        datosUsuario.put("Palabras","");
                    }
                    ID = mAuth.getUid().toString();
                    ref.child("Usuarios").child(ID).setValue(datosUsuario); //Inserta los datos en FB
                    iniciarAprender();
                    Toast.makeText(Registro_Usuario.this,"Registro exitoso",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Registro_Usuario.this, "Registro fallido",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void iniciarAprender() {
        Intent intent = new Intent(Registro_Usuario.this, Aprender.class);
        intent.putExtra("Estado",administrador);
        intent.putExtra("ID",ID);
        startActivity(intent);
        finish();
    }
}
