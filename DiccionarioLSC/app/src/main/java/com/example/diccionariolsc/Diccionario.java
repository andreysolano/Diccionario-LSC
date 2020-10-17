package com.example.diccionariolsc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Diccionario extends AppCompatActivity {
    boolean Tipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diccionario);
        BottomNavigationView barraNavegacion = (BottomNavigationView)findViewById(R.id.navigation);
        barraNavegacion.setSelectedItemId(R.id.diccionario);
        Button Buscar=(Button) findViewById(R.id.botonBuscar);
        Button Eliminar=(Button) findViewById(R.id.botonEliminar);
        Button Agregar=(Button) findViewById(R.id.botonIngresar);
        final EditText ingreso=(EditText) findViewById(R.id.ingresoPalabra);
        String palabra= ingreso.getText().toString();
        Intent anterior=getIntent();
        Tipo= (boolean) anterior.getBooleanExtra("Tipo",true);
        if(true){
            Eliminar.setVisibility(View.INVISIBLE);
            Agregar.setVisibility(View.INVISIBLE);
        }
        Buscar.setOnClickListener(new View.OnClickListener() {//Boton Buscar
            @Override
            public void onClick(View v) {
                String palabra=ingreso.getText().toString();
                //String instrucciones=buscar(palabra);
                Intent intento=new Intent(Diccionario.this,perfil_Palabra.class);
                intento.putExtra("Palabra",palabra);
                intento.putExtra("Instrucciones","");
                intento.putExtra("Tipo",Tipo);
                intento.putExtra("Boton","Guardar");
            }
        });
        Eliminar.setOnClickListener(new View.OnClickListener() {//boton eliminar
            @Override
            public void onClick(View v) {
                String mensaje=eliminar(ingreso.getText().toString());
                Toast.makeText(Diccionario.this, mensaje, Toast.LENGTH_SHORT).show();
            }
        });
        Agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento=new Intent(Diccionario.this,perfil_Palabra.class);
                intento.putExtra("Palabra","");
                intento.putExtra("Instrucciones","");
                intento.putExtra("Tipo",Tipo);
                intento.putExtra("Boton","Crear");
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
    /*public String buscar(String buscado){ //Seria la busqueda de las instrucciones de la palabra, depronto ya va hecha en la clase, idk
        Nodo cabeza=Lista.head;
        String intrucciones="-1";
        while(cabeza!=null){
            if(cabeza.palabra==buscado){
                instrucciones=cabeza.inst;
            }
            cabeza=cabeza.next;
        }
        return instrucciones;
    }*/
    public String eliminar(String buscado){
        String mensaje="La palabra no fue encontrada, no fue posible eliminarla";
        /*Nodo cabeza=Lista.head;
        Nodo anterior;
        while(cabeza.next!=null){
            anterior=cabeza;
            cabeza=cabeza.next;
            if(cabeza.palabra==buscado) {
                anterior.next = cabeza.next;
                mensaje = "Palabra eliminada";
            }
        }*/
        return mensaje;
    }

}
