package com.example.diccionariolsc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.concurrent.ThreadLocalRandom;

import data.Palabra;

public class Aprender extends AppCompatActivity {

    private StorageReference mStorageRef;


    String ID;
    boolean estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprender);
        ImageButton btnlvl1 = findViewById(R.id.botonlvl1);
        ImageButton  btnlvl2 = findViewById(R.id.botonlvl2);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        btnlvl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),JuegoQuiz.class));
            }
        });

        btnlvl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),juegoEscribirPalabra.class));

            }
        });




        //Barra inferior de navegación
        BottomNavigationView barraNavegacion = (BottomNavigationView) findViewById(R.id.navigation);
        barraNavegacion.setSelectedItemId(R.id.aprender);
        barraNavegacion.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.diccionario:
                        Intent intento = new Intent(getApplicationContext(), Diccionario.class);
                        intento.putExtra("Tipo", estado);
                        intento.putExtra("ID", ID);
                        startActivity(intento);
                        finish();
                        overridePendingTransition(0, 0);
                        return;

                    case R.id.aprender:

                    case R.id.perfil:
                        Intent intento2 = new Intent(getApplicationContext(), Perfil.class);
                        intento2.putExtra("Tipo", estado);
                        intento2.putExtra("ID", ID);
                        startActivity(intento2);
                        finish();
                        overridePendingTransition(0, 0);
                        return;
                }
            }
        });




    }


    private void recuperacionParametros() {
        Intent actual = getIntent();
        estado = (boolean) actual.getBooleanExtra("Estado", false);
        ID = (String) actual.getStringExtra("ID");
    }
}
