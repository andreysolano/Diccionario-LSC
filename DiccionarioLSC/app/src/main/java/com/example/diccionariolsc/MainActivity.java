package com.example.diccionariolsc;


import java.io.IOException;
import java.io.InputStream;
import java.lang.*;
import java.util.concurrent.ThreadLocalRandom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


import data.Palabra;
import implementacionesED.MyTree;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference();

    //    public static ListaPalabras palabras = new ListaPalabras();
    public static MyTree testTree = new MyTree();
    public Button botonInvitado;
    public Button botonRegistro;
    private Button botonLogin;

    public EditText txt_correo, txt_cont;

    private String correo, cont;
    private String ID = "";
    private boolean administrador = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        crearXML();

        botonInvitado = (Button) findViewById(R.id.botonInvitado);
        botonInvitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarAprender();
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

                if (!correo.isEmpty() && !cont.isEmpty()) { //Verificar que no hayan campos vacios
                    verificarIngreso(correo, cont);
                } else {
                    Toast.makeText(MainActivity.this, "Debe ingresar" +
                            " contraseña y correo para ingresar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Descarga del XML de Firebase
//        System.out.println("AJa 1");
//        StorageReference referencia = mStorageRef.child("base_palabras2.xml");
//        try {
//            File file = new File(getFilesDir(), "base_palabras2.xml");
//            System.out.println(file.getAbsolutePath());
//            System.out.println(file.getAbsolutePath());
//            referencia.getFile(file)
//                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                            Toast.makeText(MainActivity.this, "Archivo descargado", Toast.LENGTH_SHORT).show();
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                    Toast.makeText(MainActivity.this, "Archivo no descargado", Toast.LENGTH_SHORT).show();
//                }
//            });
//        } catch (Exception e) {
//            Toast.makeText(MainActivity.this, "Fallo en recuperar el XML", Toast.LENGTH_SHORT).show();
//        }
        //leerXml();
    }

    private void crearXML(){
        ref.child("NNNN").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final int numAleatorio = ThreadLocalRandom.current().nextInt(0,  Integer.parseInt(snapshot.getValue().toString())+ 1);
                ref.child("Palabras").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int contador = 0;
                        for(DataSnapshot objSnap : snapshot.getChildren()){
                            Palabra temp = objSnap.getValue(Palabra.class);
                            testTree.add(temp);
                            System.out.println("El numero que va es" + contador);
                            if(contador == numAleatorio){
                                testTree.setPalAleatoria(temp.getContenido());
                            }
                            contador++;
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });


    }

    private void verificarIngreso(String correo, String password) {
        mAuth.signInWithEmailAndPassword(correo, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    ID = mAuth.getUid().toString();
                    verifAdmin();
                    Toast.makeText(MainActivity.this, "Ingreso exitoso", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Ingreso fallido, verifique que el usuario" +
                            " o contraseña estén bien digitados", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void leerXml() {
        long time_start, time_end;
        time_start = System.currentTimeMillis();
        XmlPullParserFactory parserF;
        try {
            parserF = XmlPullParserFactory.newInstance();
            XmlPullParser parser2 = parserF.newPullParser();
            InputStream is = getAssets().open("base_actualizada.xml");
            parser2.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser2.setInput(is, null);
            ProcessParsing(parser2);
        } catch (Exception e) {
            System.out.println("No se encuentra el archivo (creo)");
        }
        time_end = System.currentTimeMillis();
        System.out.println("Task 'add' has taken " + (time_end - time_start) + " milliseconds--------------------------------------------------------");
        System.out.println(testTree.capacity);
//        testTree.print();
    }

    private void ProcessParsing(XmlPullParser parser) throws XmlPullParserException, IOException {
        Palabra nueva = null;
        int tipoEvento = parser.getEventType();
        while (tipoEvento != XmlPullParser.END_DOCUMENT) {
            String etiqueta = null;
            if (tipoEvento == XmlPullParser.START_TAG) {
                etiqueta = parser.getName();
                if (etiqueta.equals("palabra")) {
                    nueva = new Palabra();
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

                    } else if(etiqueta.equals("significado")){
                        nueva.setSignificado(parser.nextText());
                        testTree.add(nueva);
                    }
                }
            }
            tipoEvento = parser.next();
        }
    }

    private void verifAdmin(){
        System.out.println(ID);
        ref.child("Usuarios").child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario objUsuario = snapshot.getValue(Usuario.class);
                String IDusuario = objUsuario.getAdministrador();
                if(IDusuario.equals("SI")){
                    administrador = true;
                }
                iniciarAprender();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
    private void iniciarAprender() {
        Intent intent = new Intent(MainActivity.this, Aprender.class);
        intent.putExtra("Estado", administrador);
        intent.putExtra("ID", ID);
        startActivity(intent);
        finish();
    }

//    public void leerXml() throws ParserConfigurationException {
//        DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();
//        DocumentBuilder documentBuilder = null;
//        try {
//            documentBuilder = DBF.newDocumentBuilder();
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        }
//
//        Document doc = null;
//        String path = getFilesDir().getAbsolutePath();
//        try {
//            System.out.println("Esta leyendo y es algo nuevo en intento 3...................................");
//            System.out.println("......................................................................");
//            InputStream fis = getAssets().open("base_actualizada.xml");//como se vaya a llamar tho
//            doc = documentBuilder.parse(fis);
//        } catch (IOException | SAXException e) {
//            e.printStackTrace();
//        }
//        Node principal = doc.getElementsByTagName("base_palabras").item(0);
//        NodeList lista = principal.getChildNodes();
//        for (int i = 0; i < lista.getLength(); i++) {
//            Node elemento = lista.item(i);
//            Palabra nueva = new Palabra(elemento.getFirstChild().getTextContent(), elemento.getChildNodes().item(1).getTextContent(), elemento.getChildNodes().item(2).getTextContent(), elemento.getLastChild().getTextContent());
//            testTree.add(nueva);
//
//        }
//    }
    /*public void crearXML() throws FileNotFoundException {//Esto se podra borar despues
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
    }*/
}
