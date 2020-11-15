package com.example.diccionariolsc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

import data.Palabra;
import implementacionesED.MyTree;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

//    public static ListaPalabras palabras = new ListaPalabras();
    public static MyTree testTree = new MyTree();
    public Button botonInvitado;
    public Button botonRegistro;
    private Button botonLogin;

    public EditText txt_correo, txt_cont;

    private String correo, cont, ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        parseXML();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonInvitado = (Button) findViewById(R.id.botonInvitado);
        botonInvitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Aprender.class);
                intent.putExtra("Tipo", true);
                startActivity(intent);
            }
        });

        botonRegistro = (Button) findViewById(R.id.botonRegistro);
        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registro_Usuario.class);
                startActivity(intent);
            }
        });

        txt_correo = (EditText) findViewById(R.id.nombre_usuario);
        txt_cont = (EditText) findViewById(R.id.contraseña2);
        botonLogin = (Button) findViewById(R.id.Registro);
        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correo = txt_correo.getText().toString();
                cont = txt_cont.getText().toString();

                if(!correo.isEmpty() && !cont.isEmpty()){ //Verificar que no hayan campos vacios
                    verificarIngreso(correo, cont);
                }else{
                    Toast.makeText(MainActivity.this, "Debe ingresar" +
                            " contraseña y correo para ingresar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void parseXML() {
        System.out.println("Begin");
        long time_start, time_end;
        time_start = System.currentTimeMillis();
        XmlPullParserFactory parserF;
        try {
            parserF = XmlPullParserFactory.newInstance();
            XmlPullParser parser2 = parserF.newPullParser();
            InputStream is = getAssets().open("base_palabras2.xml");
            parser2.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser2.setInput(is, null);
            ProcessParsing(parser2);
        } catch (Exception e) {
            System.out.println("Error en 1");
        }
        System.out.println("End");
        time_end = System.currentTimeMillis();
        System.out.println("the task has taken "+ ( time_end - time_start ) +" milliseconds");
        testTree.print();
    }

    private void ProcessParsing(XmlPullParser parser) throws XmlPullParserException, IOException {
        Palabra nueva = null;
        int tipoEvento = parser.getEventType();
        while (tipoEvento != XmlPullParser.END_DOCUMENT) {
            String etiqueta = null;
            if (tipoEvento == XmlPullParser.START_TAG) {
                etiqueta = parser.getName();
                if (etiqueta.equals("palabra")) {
                    nueva = new Palabra("", "", "");
//                    palabras.push(nueva);
                } else if (nueva != null) {
                    if (etiqueta.equals("id")) {
                        nueva.setId(parser.nextText());
//                        System.out.println(nueva.getId());
                    } else if ("contenido".equals(etiqueta)) {
                        nueva.setContenido(parser.nextText());
//                        System.out.println(nueva.getContenido());
                    } else if (etiqueta.equals("url")){
                        nueva.setUrl(parser.nextText());
                        testTree.add(nueva);
                    }

                }
            }
            tipoEvento = parser.next();
        }

    }

    private void verificarIngreso(String correo, String password) {
        mAuth.signInWithEmailAndPassword(correo,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task){
                if(task.isSuccessful()){
                    ID = mAuth.getUid().toString();
                    iniciarAprender(ID);
                    Toast.makeText(MainActivity.this, "Ingreso exitoso",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Ingreso fallido, verifique que el usuario" +
                            " o contraseña estén bien digitados",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void iniciarAprender(String ID) {
        Intent intent = new Intent(MainActivity.this, Aprender.class);
        startActivity(intent);
        finish();
    }
}
