package com.example.diccionariolsc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

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

        if(ID != null){
            descargarPalabras();
        }

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
}