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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import data.Palabra;

public class Diccionario extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference();

    private boolean Tipo;
    private Boolean modoBusqueda = true; // Si modoTipo == True , se busca y visualiza la palabra. Si modoTipo ==  False, se agrega la palabra

    private String ID;

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

        recuperacionParametros();

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
                intento.putExtra("ID", ID);
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
                intento.putExtra("ID", ID);
                startActivity(intento);
            }
        });

        barraNavegacion.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.diccionario:

                    case R.id.aprender:
                        Intent intent=new Intent(getApplicationContext(),Aprender.class);
                        intent.putExtra("Estado", Tipo);
                        intent.putExtra("ID", ID);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return;

                    case R.id.perfil:
                        Intent intento2 = new Intent(getApplicationContext(),Perfil.class);
                        intento2.putExtra("Tipo",Tipo);
                        intento2.putExtra("ID",ID);
                        finish();
                        overridePendingTransition(0,0);
                        return;

                }
            }
        });
    }

    private void recuperacionParametros() {
        Intent anterior = getIntent();
        Tipo= (boolean) anterior.getBooleanExtra("Tipo",true);
        ID = anterior.getStringExtra("ID");
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
