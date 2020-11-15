package com.example.diccionariolsc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.function.DoubleUnaryOperator;

import data.Palabra;
import implementacionesED.DoubleLinkedNodePalabra;

public class perfil_Palabra extends AppCompatActivity {
    boolean Tipo;
    String Palabra;
    String Boton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil__palabra);

        Intent previo=getIntent();
        Toast.makeText(perfil_Palabra.this, "Entro", Toast.LENGTH_SHORT).show();
        Tipo=(boolean) previo.getBooleanExtra("Tipo",true);
        Palabra=(String) previo.getStringExtra("Palabra");
        Boton=(String) previo.getStringExtra("Boton");

        final EditText Instrucciones=(EditText) findViewById(R.id.TextViewInstrucciones);
        final EditText Titulo=(EditText) findViewById(R.id.NombrePalabra);
        Titulo.setText(Palabra);
        Button Editar=(Button) findViewById(R.id.botonEditar);

        ImageView gifView = findViewById(R.id.gifView);
        Glide.with(this).load("http://educativo.insor.gov.co/diccionario/wp-content/uploads/2019/08/1.-ANTENA-PARABOLICO-SE%C3%91A.mp4?_=1").into(gifView);

        Toast.makeText(perfil_Palabra.this, "Supero parte 1", Toast.LENGTH_SHORT).show();



        if(Tipo){
            Toast.makeText(perfil_Palabra.this, "Hace invisible", Toast.LENGTH_SHORT).show();
            Editar.setVisibility(View.INVISIBLE);
            Instrucciones.setEnabled(false);
            Instrucciones.setFocusable(false);
            Titulo.setEnabled(false);
            Titulo.setFocusable(false);
        }
        if(Boton.equals("Crear")){
            System.out.println("6");
            Editar.setText("Crear");
            Editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.Palabra nueva=new Palabra(Titulo.getText().toString(),Instrucciones.getText().toString(),Instrucciones.getText().toString(), Instrucciones.getText().toString());
                    //linea anterior MAL IMPLEMENTADA por Andrey
//                    MainActivity.palabras.push(nueva);
                    MainActivity.testTree.add(nueva);
                }
            });    
        }
        else{//cuando es para editar
            Toast.makeText(perfil_Palabra.this, "Guardar cambios", Toast.LENGTH_SHORT).show();
            Editar.setText("Guardar Cambios");
            final Palabra NodoaEditar=buscar(Palabra);
            if(NodoaEditar!=null){
                Instrucciones.setText(NodoaEditar.getContenido());
                String url = NodoaEditar.getUrl();

                Glide.with(this).load(url).into(gifView);

                Editar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NodoaEditar.setContenido(Instrucciones.getText().toString());
                        NodoaEditar.setId(Titulo.getText().toString());
                    }
                });
            }
            else{
                Toast.makeText(perfil_Palabra.this, "No se encuentra la palabra", Toast.LENGTH_SHORT).show();
                Instrucciones.setText("Error. Palabra No encontrada! ");
                Glide.with(this).load("https://media.giphy.com/media/3o6MbnTkJL5TW9Djm8/giphy.gif").into(gifView);
            }

        }
        
    }
//    public DoubleLinkedNodePalabra buscar(String buscado) { //Seria la busqueda de las instrucciones de la palabra, depronto ya va hecha en la clase, idk
//        System.out.println("8");
//        DoubleLinkedNodePalabra cabeza = MainActivity.palabras.head;
//        System.out.println("9");
//        while (cabeza.getNext() != null) {
//            System.out.println("10");
//            if (cabeza.getData().getId()== buscado) {
//                System.out.println("12");
//                return cabeza;
//            }
//            System.out.println("11");
//            cabeza = cabeza.getNext();
//        }
//        return null;
//    }
    public Palabra buscar(String data){
        return MainActivity.testTree.find(data);
    }
}
