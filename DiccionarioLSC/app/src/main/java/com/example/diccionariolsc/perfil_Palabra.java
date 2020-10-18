package com.example.diccionariolsc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.function.DoubleUnaryOperator;

import data.Palabra;
import implementacionesED.DoubleLinkedNodePalabra;

public class perfil_Palabra extends AppCompatActivity {
    boolean Tipo;
    String Palabra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent previo=getIntent();
        Tipo=(boolean) previo.getBooleanExtra("Tipo",true);
        Palabra=(String) previo.getStringExtra("Palabra");
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil__palabra);
        String Boton=(String) previo.getStringExtra("Boton");
        final EditText Instrucciones=(EditText) findViewById(R.id.TextViewInstrucciones);
        final EditText Titulo=(EditText) findViewById(R.id.NombrePalabra);
        Titulo.setText(Palabra);
        Button Editar=(Button) findViewById(R.id.botonEditar);
        if(Tipo){
            Editar.setVisibility(View.INVISIBLE);
            Instrucciones.setEnabled(false);
            Instrucciones.setFocusable(false);
            Titulo.setEnabled(false);
            Titulo.setFocusable(false);
        }
        if(Boton.equals("Crear")){
            Editar.setText("Crear");
            Editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.Palabra nueva=new Palabra(Titulo.getText().toString(),Instrucciones.getText().toString());
                    MainActivity.Lector.palabras.push(nueva);
                }
            });    
        }
        else{//cuando es para editar
            Editar.setText("Guardar Cambios");
            final DoubleLinkedNodePalabra NodoaEditar=buscar(Palabra);
            Instrucciones.setText(NodoaEditar.getData().getId());
            Editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NodoaEditar.getData().setContenido(Instrucciones.getText().toString());
                    NodoaEditar.getData().setId(Titulo.getText().toString());
                }
            });
        }
        
    }
    public DoubleLinkedNodePalabra buscar(String buscado) { //Seria la busqueda de las instrucciones de la palabra, depronto ya va hecha en la clase, idk
        DoubleLinkedNodePalabra cabeza = MainActivity.Lector.palabras.head;
        while (cabeza.getNext() != null) {
            if (cabeza.getData().getId()== buscado) {
                return cabeza;
            }
            cabeza = cabeza.getNext();
        }
        return null;
    }
}
