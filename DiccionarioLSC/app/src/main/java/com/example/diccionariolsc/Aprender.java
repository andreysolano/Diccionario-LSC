package com.example.diccionariolsc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Aprender extends AppCompatActivity {
    boolean estado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprender);
        Intent actual=getIntent();
        estado=(boolean) actual.getBooleanExtra("Estado", true);
        //Barra inferior de navegación
        BottomNavigationView barraNavegacion = (BottomNavigationView)findViewById(R.id.navigation);
        barraNavegacion.setSelectedItemId(R.id.aprender);

        barraNavegacion.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.diccionario:
                        Intent intento=new Intent(getApplicationContext(),Diccionario.class);
                        intento.putExtra("Tipo",estado);
                        startActivity(intento);
                        finish();
                        overridePendingTransition(0,0);
                        return;

                    case R.id.aprender:

                    case R.id.perfil:
                        startActivity(new Intent(getApplicationContext(),Perfil.class));
                        finish();
                        overridePendingTransition(0,0);
                        return;



                }
            }
        });


    }
}
