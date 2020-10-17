package com.example.diccionariolsc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class Registro_Usuario extends AppCompatActivity {

    EditText nombreIngresado;
    EditText contraseñaIngresada;
    EditText contraseña2Ingresada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_usuario);
    }
}