package com.example.diccionariolsc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Diccionario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diccionario);

        //Barra inferior de navegación
        BottomNavigationView barraNavegacion = (BottomNavigationView)findViewById(R.id.navigation);
        barraNavegacion.setSelectedItemId(R.id.diccionario);

        //Esta lista es un preeliminar para probar la visualización y el scroll.
        //Creo que la mejor opción es manejar la la lsita en un xml
        ListView vistaListaDiccionario = findViewById(R.id.VistaListaDiccionario);

        ArrayList<String> listaDiccionario = new ArrayList<String>();
        listaDiccionario.add("Abrazar");
        listaDiccionario.add("Agua");
        listaDiccionario.add("Alcoba");
        listaDiccionario.add("Anciano");
        listaDiccionario.add("Nueve");
        listaDiccionario.add("Banano");
        listaDiccionario.add("Sancocho ");
        listaDiccionario.add("Universidad");
        listaDiccionario.add("Computador");
        listaDiccionario.add("Selva");
        listaDiccionario.add("Nicaragua");
        listaDiccionario.add("Audifono");
        listaDiccionario.add("Sordo");
        listaDiccionario.add("Comida");
        listaDiccionario.add("Ventana");
        listaDiccionario.add("Pobre");
        listaDiccionario.add("Primo");
        listaDiccionario.add("Sapo");
        listaDiccionario.add("Secretaria");
        listaDiccionario.add("Queso");
        listaDiccionario.add("Terco");
        listaDiccionario.add("Sofá");
        listaDiccionario.add("Yogur");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listaDiccionario);

        vistaListaDiccionario.setAdapter(arrayAdapter);


        barraNavegacion.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.diccionario:

                    case R.id.aprender:
                        startActivity(new Intent(getApplicationContext(),Aprender.class));
                        finish();
                        overridePendingTransition(0,0);
                        return;

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