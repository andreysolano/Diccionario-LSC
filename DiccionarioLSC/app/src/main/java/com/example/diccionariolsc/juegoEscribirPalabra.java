package com.example.diccionariolsc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import java.text.Normalizer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import data.Palabra;

public class juegoEscribirPalabra extends AppCompatActivity {

    public int count;
    public String aleatorio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_escribir_palabra);


        final ImageView gifView = findViewById(R.id.iv_gif3);
        final EditText respuesta = findViewById(R.id.ingresoRespuesta);
        final Button btnVerificar = findViewById(R.id.btn_verificarRta);

            buscarAl();
            final Palabra NodoBuscado = buscar(buscarAl1());

            String url = NodoBuscado.getUrl();
            Glide.with(this).load(url).into(gifView);
            //Toast.makeText(juegoEscribirPalabra.this, aleatorio, Toast.LENGTH_SHORT).show();

            btnVerificar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String palabraIngresada = respuesta.getText().toString();
                    String rta = NodoBuscado.getContenido().toLowerCase();
                    if (palabraIngresada.equals(rta)) {
                        buscarAl();
                        respuesta.setBackgroundColor(Color.GREEN);
                        btnVerificar.setText("Siguiente");
                        initCounter();
                        //Una pausa para evitar errores
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        btnVerificar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                btnVerificar.setText("Verificar");
                                restartActivity();
                            }
                        });
                    } else {
                        plusCounter();
                        respuesta.setBackgroundColor(Color.RED);
                        Toast.makeText(juegoEscribirPalabra.this, count + " intento ", Toast.LENGTH_SHORT).show();
                    }
                    if (count >= 3) {
                        Toast.makeText(juegoEscribirPalabra.this, "La rta es:" + rta, Toast.LENGTH_SHORT).show();
                    }
                }
            });


    }
    public Palabra buscar(String data){
        return MainActivity.testTree.find(data);
    }

    public void buscarAl(){
        aleatorio =  MainActivity.testTree.getPalAleatoria();
    }
    public String buscarAl1(){
        return MainActivity.testTree.getPalAleatoria();
    }

    public void initCounter(){
        count = 0;
    }
    public void plusCounter(){
        count ++;
    }
    public void restartActivity(){
        Intent mIntent = getIntent();
        finish();
        overridePendingTransition(0,0);
        startActivity(mIntent);
        overridePendingTransition(0,0);
    }



}