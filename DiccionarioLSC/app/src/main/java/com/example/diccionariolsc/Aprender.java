package com.example.diccionariolsc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Xml;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Aprender extends AppCompatActivity {

    private StorageReference mStorageRef;

    String ID;
    boolean estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprender);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        recuperacionParametros();

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
                        intento.putExtra("ID",ID);
                        startActivity(intento);
                        finish();
                        overridePendingTransition(0,0);
                        return;

                    case R.id.aprender:

                    case R.id.perfil:
                        Intent intento2 = new Intent(getApplicationContext(),Perfil.class);
                        intento2.putExtra("Tipo",estado);
                        intento2.putExtra("ID",ID);
                        startActivity(intento2);
                        finish();
                        overridePendingTransition(0,0);
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
