package com.example.diccionariolsc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Xml;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Aprender extends AppCompatActivity {

    private StorageReference mStorageRef;

    boolean estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprender);

        mStorageRef = FirebaseStorage.getInstance().getReference();

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
        
        try {
            StorageReference referencia = mStorageRef.child("base_palabras2.xml");
            File file = File.createTempFile("base_Palabras","xml");
            referencia.getFile(file)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(Aprender.this,"Archivo descargado",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(Aprender.this,"Archivo no descargado",Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            Toast.makeText(Aprender.this,"Fallo en recuperar el XML",Toast.LENGTH_SHORT).show();
        }

    }
}
