package com.example.diccionariolsc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import data.Palabra;

public class Diccionario extends AppCompatActivity {
    boolean Tipo;
    Boolean modoBusqueda = true; // Si modoTipo == True , se busca y visualiza la palabra. Si modoTipo ==  False, se agrega la palabra
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diccionario);
        BottomNavigationView barraNavegacion = (BottomNavigationView)findViewById(R.id.navigation);
        barraNavegacion.setSelectedItemId(R.id.diccionario);
        Button Buscar=(Button) findViewById(R.id.botonBuscar);
        Button Eliminar=(Button) findViewById(R.id.botonEliminar);
        Button Agregar=(Button) findViewById(R.id.botonAgregar);

        final EditText ingreso=(EditText) findViewById(R.id.ingresoPalabra);
        String palabra= ingreso.getText().toString();
        Intent anterior=getIntent();

        Tipo= (boolean) anterior.getBooleanExtra("Tipo",true);

        if(Tipo){
            //Eliminar.setVisibility(View.INVISIBLE);
            //Agregar.setVisibility(View.INVISIBLE);
        }

        Buscar.setOnClickListener(new View.OnClickListener() {//Boton Buscar
            @Override
            public void onClick(View v) {
                String palabra=ingreso.getText().toString();
                String loquepasa="Guardar";
                Intent intento=new Intent(Diccionario.this,perfil_Palabra.class);
                intento.putExtra("Palabra",palabra);
                intento.putExtra("Tipo",Tipo);
                intento.putExtra("Boton",loquepasa);
                intento.putExtra("modoBusqueda", true);
                startActivity(intento);
            }
        });
        Eliminar.setOnClickListener(new View.OnClickListener() {//boton eliminar
            @Override
            public void onClick(View v) {
                String palabra=ingreso.getText().toString();
                if(buscar(palabra) != null){
                    eliminar(palabra);
                    Toast.makeText(Diccionario.this, "Palabra Eliminada", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Diccionario.this, " Palabra no existente", Toast.LENGTH_SHORT).show();
                }

            }
        });
        Agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String palabra=ingreso.getText().toString();
                modoBusqueda = false;
                Intent intento=new Intent(Diccionario.this,perfil_Palabra.class);
                intento.putExtra("Palabra",palabra);
                intento.putExtra("Tipo",Tipo);
                intento.putExtra("modoBusqueda",false);
                startActivity(intento);
            }
        });


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
//    public String eliminar(String buscado){
//        String mensaje="La palabra no fue encontrada, no fue posible eliminarla";
//        DoubleLinkedNodePalabra cabeza= MainActivity.palabras.head;
//        while(cabeza.getNext()!=null){
//            if(buscado.equals(cabeza.getData().getId())) {
//                mensaje = "Palabra eliminada";
//                cabeza.getPrev().setNext(cabeza.getNext());
//                return mensaje;
//            }
//            cabeza=cabeza.getNext();
//        }
//        return mensaje;
//    }
    public void eliminar(String data){
        long time_start, time_end;
        time_start = System.currentTimeMillis();
        MainActivity.testTree.remove(data);
        time_end = System.currentTimeMillis();
        System.out.println("Task 'Eliminar' has taken " + (time_end - time_start) + " milliseconds");
    }
    public Palabra buscar(String data){
        return MainActivity.testTree.find(data);
    }

}
