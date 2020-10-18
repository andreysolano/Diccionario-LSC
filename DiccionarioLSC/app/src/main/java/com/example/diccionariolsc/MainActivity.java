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

public class MainActivity extends AppCompatActivity {
    public static ListaPalabras palabras=new ListaPalabras();
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
                Intent intent = new Intent(MainActivity.this,Aprender.class);
                intent.putExtra("Tipo",true);
                startActivity(intent);
            }
        });

        botonRegistro = (Button) findViewById(R.id.botonRegistro);
        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Registro_Usuario.class);
                startActivity(intent);
            }
        });
    }
    public void parseXML(){
        XmlPullParserFactory parserF;
        try{
            parserF=XmlPullParserFactory.newInstance();
            XmlPullParser parser2=parserF.newPullParser();
            InputStream is=getAssets().open("Datos/base_palabras.xml");
            parser2.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
            parser2.setInput(is,null);
            ProcessParsing(parser2);
        }
        catch(Exception e){
            System.out.println("Error en 1");
        }
    }
    private void ProcessParsing(XmlPullParser parser) throws XmlPullParserException, IOException {
        Palabra nueva=null;
        int tipoEvento=parser.getEventType();
        while(tipoEvento!=XmlPullParser.END_DOCUMENT){
            String etiqueta=null;
            switch(tipoEvento){
                case XmlPullParser.START_TAG:
                    etiqueta=parser.getName();
                    if(etiqueta.equals("Palabra")){
                        nueva=new Palabra("","");
                    }
                    else if(nueva!=null){
                        if("id".equals(etiqueta)){
                            nueva.setId(parser.nextText());
                        }
                        else if("contenido".equals(etiqueta)){
                            nueva.setContenido(parser.nextText());
                        }
                    }
            }
            tipoEvento=parser.next();
            palabras.push(nueva);
            nueva=null;
        }

    }


}
