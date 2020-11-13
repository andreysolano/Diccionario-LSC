package com.example.diccionariolsc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import businessLogic.LectorPalabras;
import data.Palabra;
import implementacionesED.ListaPalabras;
import implementacionesED.MyTree;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;

//    public static ListaPalabras palabras = new ListaPalabras();
    public static MyTree testTree = new MyTree();
    public Button botonInvitado;
    public Button botonRegistro;
    private Button botonLogin;

    public EditText txt_correo, txt_cont;

    private String correo, cont, ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mStorageRef = FirebaseStorage.getInstance().getReference();

         try {
            crearXML();//Esto es para la prueba mientras aparece los archivos descargados
            leerXml();
            testTree.print();
        } catch (ParserConfigurationException | FileNotFoundException e) {
            e.printStackTrace();
        }
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
                mAuth = FirebaseAuth.getInstance();
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

        //Descarga del XML de Firebase
        StorageReference referencia = mStorageRef.child("base_palabras2.xml");
        try {
            File file = File.createTempFile("base_Palabras2","xml");
            referencia.getFile(file)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(MainActivity.this,"Archivo descargado",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(MainActivity.this,"Archivo no descargado",Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            Toast.makeText(MainActivity.this,"Fallo en recuperar el XML",Toast.LENGTH_SHORT).show();
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
        intent.putExtra("Estado",false);
        startActivity(intent);
        finish();
    }
    public void leerXml() throws ParserConfigurationException {
        DocumentBuilderFactory DBF= DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = DBF.newDocumentBuilder();

        Document doc = null;
        String path = getFilesDir().getAbsolutePath();
        try {
            FileInputStream fis=openFileInput("basePalabras.xml");//como se vaya a llamar tho
            doc = documentBuilder.parse(fis);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        Node principal=doc.getElementsByTagName("base_palabras").item(0);
        NodeList lista=principal.getChildNodes();
        for(int i=0;i<lista.getLength();i++){
            Node elemento=lista.item(i);
            Palabra nueva=new Palabra(elemento.getFirstChild().getTextContent(),elemento.getLastChild().getTextContent(), i+"");
            testTree.add(nueva);

        }
    }
    public void crearXML() throws FileNotFoundException {//Esto se podra borar despues
        String res="<?xml version='1.0' encoding='UTF-8'?>" +
                "<base_palabras>" +
                "<palabra><id>papa</id><contenido>African darter</contenido></palabra><palabra><id>comida</id><contenido>Thomson's gazelle</contenido></palabra><palabra><id>Hombre</id><contenido>Eastern cottontail rabbit</contenido></palabra><palabra><id>omfg</id><contenido>Smith's bush squirrel</contenido></palabra><palabra><id>Over</id><contenido>Crab, red lava</contenido></palabra><palabra><id>Larvae</id><contenido>Moose</contenido></palabra><palabra><id>Freud</id><contenido>Steller's sea lion</contenido></palabra><palabra><id>Coral</id><contenido>Northern fur seal</contenido></palabra><palabra><id>RainbowShrimp</id><contenido>Otter, cape clawless</contenido></palabra><palabra><id>Octopus</id><contenido>Blue-faced booby</contenido></palabra></base_palabras>";
        FileOutputStream FOS= openFileOutput("basePalabras.xml", Context.MODE_PRIVATE);
        try {
            FOS.write(res.getBytes(),0,res.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FOS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
