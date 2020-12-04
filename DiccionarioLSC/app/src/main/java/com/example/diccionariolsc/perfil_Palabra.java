package com.example.diccionariolsc;

import androidx.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;

import data.Palabra;
import implementacionesED.DoubleLinkedNodePalabra;
import implementacionesED.MyTree;

public class perfil_Palabra extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference();

    boolean Tipo;
    String Palabra, ID;
    boolean modoBusqueda; // Estos datos se obtienen de la activity Diccionario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil__palabra);

        recuperacionParametros();

        final EditText Instrucciones=(EditText) findViewById(R.id.TextViewInstrucciones);
        final EditText Titulo=(EditText) findViewById(R.id.NombrePalabra);
        final EditText urlTextGif =(EditText) findViewById(R.id.textURL);
        final Button Editar=(Button) findViewById(R.id.botonEditar);
        final ImageView gifView = findViewById(R.id.gifView);

        Instrucciones.setEnabled(false);
        Titulo.setEnabled(false);
        urlTextGif.setEnabled(false);
        Titulo.setText(Palabra);

        if(modoBusqueda) {

            urlTextGif.setVisibility(View.INVISIBLE);
            final Palabra NodoBuscado = buscar(Palabra);
            // Comprueba si esta la palabra en el Arbol
            if (NodoBuscado != null) {

                String url = NodoBuscado.getUrl();
                urlTextGif.setText(url);
                Instrucciones.setText(NodoBuscado.getSignificado());
                Toast.makeText(perfil_Palabra.this, "¡Palabra encontrada!", Toast.LENGTH_SHORT).show();


                // Comprueba si la variable url es una url valida
                if (URLUtil.isValidUrl(url)) {
                    Glide.with(this).load(url).into(gifView);
                } else {
                    Toast.makeText(perfil_Palabra.this, "¡URL Inválido!", Toast.LENGTH_SHORT).show();
                    Glide.with(this).load("https://media.giphy.com/media/3o6MbnTkJL5TW9Djm8/giphy.gif").into(gifView);
                }

                //Agrega la palabra buscada al usuario que la busco
                if(ID != null){
                    ref.child("Usuarios").child(ID).child("Palabras").child(Palabra).setValue("");
                }else{
                    ref.child("Usuarios").child("Invitado").child("Palabras").child(Palabra).setValue("");
                }

            } else {

                Toast.makeText(perfil_Palabra.this, "No se encuentra la palabra", Toast.LENGTH_SHORT).show();
                Instrucciones.setText("Error. Palabra No encontrada! ");
                Glide.with(this).load("https://media.giphy.com/media/3o6MbnTkJL5TW9Djm8/giphy.gif").into(gifView);

                urlTextGif.setVisibility(View.INVISIBLE);
                Toast.makeText(perfil_Palabra.this, "No se encuentra la palabra", Toast.LENGTH_SHORT).show();
                Instrucciones.setText("Error. Palabra No encontrada! ");
                Glide.with(this).load("https://media.giphy.com/media/3o6MbnTkJL5TW9Djm8/giphy.gif").into(gifView);

            }


            //Si el usuario que entra no es ADMIN, el botón Editar no es visible
            if (!Tipo) {
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


                    if (NodoBuscado != null) {
                        Editar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                NodoBuscado.setSignificado(Instrucciones.getText().toString());
                                NodoBuscado.setUrl(urlTextGif.getText().toString());
                                Toast.makeText(perfil_Palabra.this, " ** ¡Cambios Guardados! ** ", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),Diccionario.class));
                            }
                        });
                    }

                }
            });
        } else if(modoBusqueda == false) {
            gifView.setVisibility(View.INVISIBLE);
            Instrucciones.setText("Agregue las instrucciones");
            urlTextGif.setText("Agruegue el URL de su gif");
            Editar.setText("Agregar");

            Instrucciones.setEnabled(true);
            urlTextGif.setEnabled(true);


            Editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final String palabra = Titulo.getText().toString();
                    final String URL = urlTextGif.getText().toString();
                    final String instrucciones = Instrucciones.getText().toString();
                    final Palabra nueva = new Palabra();
                    nueva.setId("1");
                    nueva.setContenido(palabra);
                    nueva.setUrl(URL);
                    nueva.setSignificado(instrucciones);
                    final int[] NN2 = {0};

                    ref.child("NNNN").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int NUM = Integer.parseInt(snapshot.getValue().toString());
                            NN2[0] = NUM + 1;

                            //Agrega la palabra a FB
                            Map<String,Object> datosPalabra = new HashMap<>(); //Map que contiene los hijos de FB
                            datosPalabra.put("contenido",palabra);
                            String pal = (NN2[1]) + "";
                            datosPalabra.put("id",pal);
                            datosPalabra.put("significado", instrucciones);
                            datosPalabra.put("url", URL);
                            ref.child("Palabras").child(palabra).setValue(datosPalabra);

                            Toast.makeText(perfil_Palabra.this, " ** ¡Palabra Agregada! ** ", Toast.LENGTH_SHORT).show();
                            MainActivity.testTree.add(nueva);
                            startActivity(new Intent(getApplicationContext(),Diccionario.class));
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                    ref.child("NNNN").setValue(NN2[0]);
                }
            });


        }
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
        */


    }

    private void recuperacionParametros() {
        Intent previo=getIntent();
        Tipo=(boolean) previo.getBooleanExtra("Tipo",true);
        Palabra=(String) previo.getStringExtra("Palabra");
        modoBusqueda =(boolean) previo.getBooleanExtra("modoBusqueda",true);
        ID = (String) previo.getStringExtra("ID");
    }

    public Palabra buscar(String data){
        return MainActivity.testTree.find(data);
    }
}
