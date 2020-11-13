package com.example.diccionariolsc;

import java.io.IOException;
import java.io.InputStream;
import java.lang.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import businessLogic.LectorPalabras;
import data.Palabra;
import implementacionesED.ListaPalabras;
import implementacionesED.MyTree;

public class MainActivity extends AppCompatActivity {
//    public static ListaPalabras palabras = new ListaPalabras();
    public static MyTree testTree = new MyTree();
    public Button botonInvitado;
    public Button botonRegistro;

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


}
