package com.example.diccionariolsc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        EditText Titulo=(EditText) findViewById(R.id.NombrePalabra); 
        Titulo.setText(Palabra);
        Button Editar=(Button) findViewById(R.id.botonEditar);
        if(Tipo){
            Editar.setVisibility(View.INVISIBLE);
            Instrucciones.setEnabled(false);
            Instrucciones.setFocusable(false);
            Titulo.setEnabled(false);
            Titulo.setFocusable(false);
        }
        /*if(Boton.equals("Crear")){
            Editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Nodo alfinal=Lista.tail;
                    alfinal.next=new Nodo(Instrucciones.getText().toString(),Titulo.getText().toString());
                }
            });    
        }
        else{//cuando es para editar
            NodoaEditar=buscar(Palabra);
            Instrucciones.setText(NodoaEditar.palabra);
            Editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NodoaEditar.palabra=Instrucciones.getText().toString();
                }
            });
        }*/
        
    }
    /*public Nodo buscar(String buscado) { //Seria la busqueda de las instrucciones de la palabra, depronto ya va hecha en la clase, idk
        Nodo cabeza = Lista.head;
        while (cabeza.next != null) {
            if (cabeza.palabra == buscado) {
                return cabeza;
            }
            cabeza = cabeza.next;
        }
        return null;
    }   */
}
