package com.example.borrador;
import javax.xml.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Xml;
import android.view.View;
import android.widget.Button;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList <arraylists> lista = new ArrayList<arraylists>();
    DocumentBuilderFactory fabrica= DocumentBuilderFactory.newInstance();
    String pathabs=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("intento 2");
        System.out.println("1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            System.out.println("2");
            try {
                ponerArchivo();
                System.out.println("3");
            } catch (IOException e) {
                System.out.println("4");
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                System.out.println("5");
                e.printStackTrace();
            }
        }
        try {
            System.out.println("6");
            leer();
            System.out.println("6.1");
            editar();
            System.out.println("6.2");
            leer();
            System.out.println("6.3");
        } catch (ParserConfigurationException | FileNotFoundException e) {
            System.out.println("7");
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void ponerArchivo() throws IOException, XmlPullParserException {
        //System.out.println(getFilesDir().getAbsolutePath()+"/basePalabras.xml");

        System.out.println("8");
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter escritor=new StringWriter();
        serializer.setOutput(escritor);
        serializer.startDocument("UTF-8", true);
        System.out.println("8");
        System.out.println("9");
        serializer.startTag("", "root");
        String[] str={"Alfredo","Rodrigo","Juan","pedro","Ale ale jandro"};
        System.out.println("11");
        for(int j = 0; j < 5; j++)
        {
            System.out.println("10");
            serializer.startTag("", "record");
            serializer.text(str[j]);
            serializer.endTag("", "record");
        }
        serializer.endTag("", "root");
        serializer.endDocument();
        String res=escritor.toString();
        System.out.println(res);
        serializer.flush();
        System.out.println("12");
        FileOutputStream FOS= openFileOutput("basePalabras.xml",Context.MODE_PRIVATE);
        FOS.write(res.getBytes(),0,res.length());
        FOS.close();


    }
    public void leer() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory DBF= DocumentBuilderFactory.newInstance();
        System.out.println("13");
        DocumentBuilder documentBuilder = DBF.newDocumentBuilder();

        Document doc = null;

        System.out.println("14");
        String path = getFilesDir().getAbsolutePath();
        try {
            System.out.println("21");
            FileInputStream fis=openFileInput("basePalabras.xml");
            System.out.println("22");
            doc = documentBuilder.parse(fis);
            System.out.println("23");
            //System.out.println("idk aca");
            //File directory = getFilesDir();
            //File arch = new File(directory,"basePalabras.xml");
            //document = documentBuilder.parse(arch);
            //System.out.println("16");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        System.out.println("17");
        Node principal=doc.getElementsByTagName("root").item(0);
        NodeList lista=principal.getChildNodes();
        System.out.println("18");
        for(int i=0;i<lista.getLength();i++){
            System.out.println("19");
            Node elemento=lista.item(i);
            System.out.println(elemento.getTextContent());
        }



    }
    public void editar() throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory DBF= DocumentBuilderFactory.newInstance();
        System.out.println("31");
        DocumentBuilder documentBuilder = DBF.newDocumentBuilder();
        Document doc = null;
        System.out.println("32");
        try {
            System.out.println("33");
            FileInputStream fis=openFileInput("basePalabras.xml");
            System.out.println("34");
            doc = documentBuilder.parse(fis);
            System.out.println("35");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        Node principal=doc.getElementsByTagName("root").item(0);
        NodeList principal1=principal.getChildNodes();
        System.out.println("36");
        for(int i=0;i<principal1.getLength();i++){
            Node actual=principal1.item(i);
            actual.setTextContent(actual.getTextContent()+i);
            System.out.println("37");
        }
        Node borrar=principal1.item(3);
        principal.removeChild(borrar);
        System.out.println("38");
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        System.out.println("39");
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(doc);
        System.out.println("40");
        StreamResult streamResult = new StreamResult(new File(getFilesDir().getAbsolutePath() +"/basePalabras.xml"));
        System.out.println("41");
        transformer.transform(domSource, streamResult);
        System.out.println("42");

    }
}

