package com.example.diccionariolsc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.util.Xml;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import data.Palabra;

public class Aprender extends AppCompatActivity {

    private StorageReference mStorageRef;

    private ImageView iv_gif;
    private RadioButton rb_1, rb_2, rb_3, rb_4;
    private Button btn_verificar;

    String ID;
    boolean estado;
    OpcionJuego lista[];
    OpcionJuego juego;

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

        //Juego
        iv_gif = (ImageView) findViewById(R.id.iv_gif);
        rb_1 = (RadioButton) findViewById(R.id.rb_1);
        rb_2 = (RadioButton) findViewById(R.id.rb_2);
        rb_3 = (RadioButton) findViewById(R.id.rb_3);
        rb_4 = (RadioButton) findViewById(R.id.rb_4);
        btn_verificar = (Button) findViewById(R.id.btn_verificar);

        lista = crearOpciones();
        int numero = 3; //Este debe ser generado aleatoriamente
        juego = lista[numero];
        rb_1.setText(juego.getOp1());
        rb_2.setText(juego.getOp2());
        rb_3.setText(juego.getOp3());
        rb_4.setText(juego.getOp4());
        Glide.with(this).load(juego.getURL()).into(iv_gif);
    }

    public void revisionJuego(View view) {

        String correcta = juego.getCorrecta();
        if(rb_1.isChecked() == true){
            if(juego.getOp1() == correcta){
                rb_1.setBackgroundColor(Color.GREEN);
                rb_2.setBackgroundColor(Color.RED);
                rb_3.setBackgroundColor(Color.RED);
                rb_4.setBackgroundColor(Color.RED);
                Toast.makeText(Aprender.this, "¡Respuesta correcta!", Toast.LENGTH_LONG).show();
                //#1BFF09
            }else{
                rb_1.setBackgroundColor(Color.RED);
                Toast.makeText(Aprender.this, "Respuesta incorrecta, intenta otra vez", Toast.LENGTH_SHORT).show();
            }
        }else if(rb_2.isChecked() == true) {
            if (juego.getOp2() == correcta) {
                rb_1.setBackgroundColor(Color.RED);
                rb_2.setBackgroundColor(Color.GREEN);
                rb_3.setBackgroundColor(Color.RED);
                rb_4.setBackgroundColor(Color.RED);
                Toast.makeText(Aprender.this, "¡Respuesta correcta!", Toast.LENGTH_LONG).show();
            } else {
                rb_2.setBackgroundColor(Color.RED);
                Toast.makeText(Aprender.this, "Respuesta incorrecta, intenta otra vez", Toast.LENGTH_SHORT).show();
            }
        }else if(rb_3.isChecked() == true) {
            if (juego.getOp3() == correcta) {
                rb_1.setBackgroundColor(Color.RED);
                rb_2.setBackgroundColor(Color.RED);
                rb_3.setBackgroundColor(Color.GREEN);
                rb_4.setBackgroundColor(Color.RED);
                Toast.makeText(Aprender.this, "¡Respuesta correcta!", Toast.LENGTH_LONG).show();
            } else {
                rb_3.setBackgroundColor(Color.RED);
                Toast.makeText(Aprender.this, "Respuesta incorrecta, intenta otra vez", Toast.LENGTH_SHORT).show();
            }
        }else if(rb_4.isChecked() == true) {
            if (juego.getOp4() == correcta) {
                rb_1.setBackgroundColor(Color.RED);
                rb_2.setBackgroundColor(Color.RED);
                rb_3.setBackgroundColor(Color.RED);
                rb_4.setBackgroundColor(Color.GREEN);
                Toast.makeText(Aprender.this, "¡Respuesta correcta!", Toast.LENGTH_LONG).show();
            } else {
                rb_4.setBackgroundColor(Color.RED);
                Toast.makeText(Aprender.this, "Respuesta incorrecta, intenta otra vez", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public OpcionJuego[] crearOpciones() {
        Palabra p1 = new Palabra();
        p1.setId("1");
        p1.setContenido("Aguacate");
        p1.setUrl("https://media.giphy.com/media/8Bi77uqNRQC4DBUa9S/giphy.gif");
        p1.setSignificado("Fruto comestible que tiene forma ovalada, de cáscara verde, con una sola semilla en forma de huevo y pulpa verdosa y suave.");
        String op11 ="Gato";
        String op12 = p1.getContenido();
        String op13 ="Inteligente";
        String op14 ="Hospital";
        OpcionJuego op1 = new OpcionJuego(p1, op11, op12, op13, op14);

        Palabra p2 = new Palabra();
        p2.setId("2");
        p2.setContenido("Cuchara");
        p2.setUrl("https://media.giphy.com/media/o0bgo18Ig0YQw6q8Ns/giphy.gif");
        p2.setSignificado("Utensilio de cocina formado por una paleta cóncava y un mango que sirve para llevarse a la boca líquidos o alimentos blandos.");
        String op21 ="Familia";
        String op22 ="Botella";
        String op23 = p2.getContenido();
        String op24 ="Calcualdora";
        OpcionJuego op2 = new OpcionJuego(p2, op21, op22, op23, op24);

        Palabra p3 = new Palabra();
        p3.setId("3");
        p3.setContenido("Futuro");
        p3.setUrl("https://media.giphy.com/media/IzESurMabUJhL4tbc1/giphy.gif");
        p3.setSignificado("Tiempo posterior al presente.");
        String op31 = p3.getContenido();
        String op32 ="Ducha";
        String op33 ="Celular";
        String op34 ="Defender";
        OpcionJuego op3 = new OpcionJuego(p3, op31, op32, op33, op34);

        Palabra p4 = new Palabra();
        p4.setId("4");
        p4.setContenido("Internet");
        p4.setUrl("https://media.giphy.com/media/TKnLiL4W76CrbEm09S/giphy.gif");
        p4.setSignificado("Sistema de interconexión mundial con diferentes redes de información.");
        String op41 ="Banano";
        String op42 ="Guitarra";
        String op43 ="Hotel";
        String op44 = p4.getContenido();
        OpcionJuego op4 = new OpcionJuego(p4, op41, op42, op43, op44);

        OpcionJuego lista[] = new OpcionJuego[4];
        lista[0] = op1;
        lista[1] = op2;
        lista[2] = op3;
        lista[3] = op4;

        return lista;
    }

    private void recuperacionParametros() {
        Intent actual = getIntent();
        estado = (boolean) actual.getBooleanExtra("Estado", false);
        ID = (String) actual.getStringExtra("ID");
    }
}
