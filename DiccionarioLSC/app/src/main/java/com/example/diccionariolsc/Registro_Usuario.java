package com.example.diccionariolsc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class Registro_Usuario extends AppCompatActivity {

    EditText nombreIngresado;
    EditText contraseñaIngresada;
    EditText contraseña2Ingresada;
    Button botonRegistro;
    Switch switchAdmin;
    String nombreUsuario, contraseña, contraseña2, administrador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_usuario);

        nombreIngresado = (EditText) findViewById(R.id.nombre_usuario);
        contraseñaIngresada = (EditText) findViewById(R.id.contraseña_usuario);
        contraseña2Ingresada = (EditText) findViewById(R.id.contraseña2);
        switchAdmin = findViewById(R.id.switch_Administrador);

        //Switch para crear un usuario Administrador
        switchAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchAdmin.isChecked()){
                    administrador = "true";
                    Toast.makeText(Registro_Usuario.this, "Modo Administrador!", Toast.LENGTH_SHORT).show();
                }else{
                    administrador = "false";
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
                mensajeEmergente(nombreUsuario, contraseña, contraseña2);
            }
        });
    }
    private void mensajeEmergente (String nombreUsuario, String contraseña, String contraseña2){
        if(contraseña.equals(contraseña2)){
            Toast.makeText(Registro_Usuario.this, "Registro completo!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Registro_Usuario.this, "La contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }
    }
}