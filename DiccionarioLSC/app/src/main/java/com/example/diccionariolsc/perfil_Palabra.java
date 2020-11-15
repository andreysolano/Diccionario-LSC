package com.example.diccionariolsc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;
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
        Tipo=(boolean) previo.getBooleanExtra("Tipo",true);
        Palabra=(String) previo.getStringExtra("Palabra");
        Boton=(String) previo.getStringExtra("Boton");

        final EditText Instrucciones=(EditText) findViewById(R.id.TextViewInstrucciones);
        final EditText Titulo=(EditText) findViewById(R.id.NombrePalabra);
        final EditText urlTextGif =(EditText) findViewById(R.id.textURL);
        urlTextGif.setVisibility(View.INVISIBLE);
        Titulo.setText(Palabra);

        Instrucciones.setEnabled(false);
        Titulo.setEnabled(false);
        urlTextGif.setEnabled(false);

        final Button Editar=(Button) findViewById(R.id.botonEditar);
        final ImageView gifView = findViewById(R.id.gifView);

        final Palabra NodoBuscado=buscar(Palabra);

        // Comprueba si esta la palabra en el Arbol
        if(NodoBuscado != null) {
            String url = NodoBuscado.getUrl();
            urlTextGif.setText(url);
            Instrucciones.setText(NodoBuscado.getSignificado());
            Toast.makeText(perfil_Palabra.this, "¡Palabra encontrada!", Toast.LENGTH_SHORT).show();

            // Comprueba si la variable url es una url valida
            if(URLUtil.isValidUrl(url)){
                Glide.with(this).load(url).into(gifView);
            }else{
                Toast.makeText(perfil_Palabra.this, "¡URL Inválido!", Toast.LENGTH_SHORT).show();
                Glide.with(this).load("https://media.giphy.com/media/3o6MbnTkJL5TW9Djm8/giphy.gif").into(gifView);
            }

        }else {
            Toast.makeText(perfil_Palabra.this, "No se encuentra la palabra", Toast.LENGTH_SHORT).show();
            Instrucciones.setText("Error. Palabra No encontrada! ");
            Glide.with(this).load("https://media.giphy.com/media/3o6MbnTkJL5TW9Djm8/giphy.gif").into(gifView);
        }


        //Si el usuario que entra no es ADMIN, el botón Editar no es visible
        if(!Tipo){
            Editar.setVisibility(View.INVISIBLE);
        }

        //Si se da Click en "editar", las instrucciones y el URL se pueden cambiar
        Editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(perfil_Palabra.this, " ** Modo Editor ** ", Toast.LENGTH_SHORT).show();
                Editar.setText("Guardar Cambios");
                Instrucciones.setEnabled(true);
                urlTextGif.setEnabled(true);
                gifView.setVisibility(View.INVISIBLE);
                urlTextGif.setVisibility(View.VISIBLE);


                if(NodoBuscado!=null){
                    Editar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(perfil_Palabra.this, " ** ¡Cambios Guardados! ** ", Toast.LENGTH_SHORT).show();
                            NodoBuscado.setSignificado(Instrucciones.getText().toString());
                            NodoBuscado.setUrl(urlTextGif.getText().toString());
                        }
                    });
                }

            }
        });

 /*
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
        */


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
