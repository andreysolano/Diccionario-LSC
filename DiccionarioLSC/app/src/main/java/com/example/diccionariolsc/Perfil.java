package com.example.diccionariolsc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Perfil extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference();

    String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //if(ID != null){
           // descargarPalabras();
        //}
        String lista[]=new String[10];
        lista[0]="surprise";
        lista[1]="Dear Person";
        lista[2]="I";
        lista[3]="Bet";
        lista[7]="Saw";
        lista[8]="The";
        lista[9]="Last";
        prepararLista1(lista);
        String lista2[]=new String [1];
        lista2[0]="Palabra del dia";
        prepararLista2(lista2);
        //Barra inferior de navegación
        BottomNavigationView barraNavegacion = (BottomNavigationView)findViewById(R.id.navigation);
        barraNavegacion.setSelectedItemId(R.id.perfil);

        barraNavegacion.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.diccionario:
                        startActivity(new Intent(getApplicationContext(),Diccionario.class));
                        finish();
                        overridePendingTransition(0,0);
                        return;

                    case R.id.aprender:
                        startActivity(new Intent(getApplicationContext(),Aprender.class));
                        finish();
                        overridePendingTransition(0,0);
                        return;

                    case R.id.perfil:
                }
            }
        });
    }

    private void descargarPalabras(){
        final ArrayList<String> lista = new ArrayList<String>();
        ref.child("Usuarios").child(ID).child("Palabras").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot objSnap : snapshot.getChildren()){
                    String pal = objSnap.getKey().toString();
                    lista.add(pal);
                }
                // Aqui se debe llamar el metodo que cree la lista para ver las palabras buscadas
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
    public void prepararLista1(final String arr[]){
        ListView listV = (ListView) findViewById(R.id.listaPalabras);
        listV.setVerticalScrollBarEnabled(true);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arr);
        listV.setAdapter(adapter);
        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String temporal=arr[position];
                Toast.makeText(Perfil.this, "Imprime: "+temporal, Toast.LENGTH_SHORT).show();
                //Intent intento = new Intent(getApplicationContext(), Diccionario.class);

            }
        });

    }
    public void prepararLista2(final String arr[]){
        ListView listV = (ListView) findViewById(R.id.DescubrePalabra);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arr);
        listV.setAdapter(adapter);
        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String temporal=arr[position];
                Toast.makeText(Perfil.this, "Imprime: "+temporal, Toast.LENGTH_SHORT).show();
                //Intent intento = new Intent(getApplicationContext(), Diccionario.class);

            }
        });

    }
}
