package businessLogic;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

import data.Palabra;
import implementacionesED.ListaPalabras;

public class LectorPalabras {
    private String fileName;
    private ListaPalabras palabras;

    public LectorPalabras(String fileName) {
        this.fileName = fileName;
        this.palabras = new ListaPalabras();
        this.leerArchivo();
    }

    public void leerArchivo(){
        //fileName = revisarExtension(fileName);
//        fileName = "userdata.xml";
        try{  //Inicializa el lector (Se usa JDOM)
            File inputFile = new File(fileName);
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(inputFile);

            System.out.println("Root element: " + document.getRootElement().getName());
            Element classElement = document.getRootElement();

            List<Element> userList = classElement.getChildren();
            System.out.println("----------------------------");

            for(int i = 0; i < userList.size(); i++){  //Cicla por cada usuario en el archivo, lo procesa y lo adrega a la lista
                Element palabra = userList.get(i);
                Palabra u = procesarPalabra(palabra);
                palabras.push(u);

//                System.out.println("\nCurrent type: " + palabra.getName());
//
//                Attribute identificacion = palabra.getAttribute("id");
//                System.out.println("ID: " + identificacion.getValue());
//
//                System.out.println("Palabra: " + palabra.getChild("contenido").getText());

            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Palabra procesarPalabra(Element palabra){
        Attribute idA = palabra.getAttribute("id");
        String id = idA.getValue();
        String contenido = palabra.getChild("contenido").getText();
        String url = palabra.getChild("url").getText();
        String significado = palabra.getChild("significado").getText();
        Palabra p = new Palabra();
        p.setId("1");
        p.setContenido(contenido);
        p.setUrl(url);
        p.setSignificado(significado);
        return p;
    }

    public String revisarExtension(String fileName) {  //Revisa que el nombre del archivo tenga la extension .xml
        if (!fileName.contains("."))
            fileName = fileName + ".xml";
        else if (!fileName.contains(".xml"))
            fileName = fileName.substring(0, fileName.indexOf('.')) + ".xml";
        return fileName;
    }

    public ListaPalabras getPalabras() {
        return palabras;
    }
}
