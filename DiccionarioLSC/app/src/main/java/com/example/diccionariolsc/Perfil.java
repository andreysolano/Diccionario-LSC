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
    boolean Tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        recuperarParametros();

        if(ID != null){
            descargarPalabras(ID);
        }else{
            descargarPalabras("Invitado");
        }

        String lista2[]=new String [1];
        lista2[0]="Palabra del momento";
        prepararLista2(lista2);

        //Barra inferior de navegación
        BottomNavigationView barraNavegacion = (BottomNavigationView)findViewById(R.id.navigation);
        barraNavegacion.setSelectedItemId(R.id.perfil);
        barraNavegacion.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.diccionario:
                        Intent intento=new Intent(getApplicationContext(),Diccionario.class);
                        intento.putExtra("Tipo",Tipo);
                        intento.putExtra("ID",ID);
                        startActivity(intento);
                        finish();
                        overridePendingTransition(0,0);
                        return;

                    case R.id.aprender:
                        Intent intent=new Intent(getApplicationContext(),Aprender.class);
                        intent.putExtra("Estado", Tipo);
                        intent.putExtra("ID", ID);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return;

                    case R.id.perfil:
                }
            }
        });
    }

    private void descargarPalabras(String id){
        final ArrayList<String> lista = new ArrayList<String>();
        ref.child("Usuarios").child(id).child("Palabras").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot objSnap : snapshot.getChildren()){
                    String pal = objSnap.getKey().toString();
                    System.out.println("la palabra es: " + pal);
                    lista.add(pal);
                }
                // Aqui se debe llamar el metodo que cree la lista para ver las palabras buscadas
                prepararLista1(lista);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void prepararLista1(final ArrayList<String> lista){
        ListView listV = (ListView) findViewById(R.id.listaPalabras);
        listV.setVerticalScrollBarEnabled(true);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,lista);
        listV.setAdapter(adapter);
        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String temporal = lista.get(position);
                Toast.makeText(Perfil.this, "Buscando: "+temporal, Toast.LENGTH_SHORT).show();
                Intent intento = new Intent(getApplicationContext(), perfil_Palabra.class);
                intento.putExtra("Tipo",Tipo);
                intento.putExtra("Palabra",temporal);
                intento.putExtra("modoBusqueda",true);
                intento.putExtra("ID",ID);
                startActivity(intento);
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
                Intent intento = new Intent(getApplicationContext(), perfil_Palabra.class);
                intento.putExtra("Tipo",Tipo);
                intento.putExtra("Palabra",temporal);
                intento.putExtra("modoBusqueda",true);
                intento.putExtra("ID",ID);
                startActivity(intento);

            }
        });

    }
    public void recuperarParametros(){
        Intent previo=getIntent();
        Tipo = (boolean) previo.getBooleanExtra("Tipo",false);
        ID = (String) previo.getStringExtra("ID");
    }
}
