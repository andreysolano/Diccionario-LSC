package businessLogic;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

import data.Usuario;
import implementacionesED.LinkedListGeneric;

public class LectorUsuarios {
    private String fileName; //Nombre del archivo
    private LinkedListGeneric<Usuario> usuarios;  //Lista enlazada con los usuarios ya leídos

    public LectorUsuarios(String fileName){
        this.fileName = fileName;
        this.usuarios = new LinkedListGeneric<Usuario>();
        this.leerArchivo();  //Al inicializar el lector, comienza a leer el .xml
    }

    public void leerArchivo(){
        //fileName = revisarExtension(fileName);
        fileName = "userdata.xml";
        try{  //Inicializa el lector (Se usa JDOM)
            File inputFile = new File(fileName);
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(inputFile);

            System.out.println("Root element: " + document.getRootElement().getName());
            Element classElement = document.getRootElement();

            List<Element> userList = classElement.getChildren();
            System.out.println("----------------------------");

            for(int i = 0; i < userList.size(); i++){  //Cicla por cada usuario en el archivo, lo procesa y lo adrega a la lista
                Element usuario = userList.get(i);
                Usuario u = procesarUsuario(usuario);
                usuarios.pushFront(u);

               /* System.out.println("\nCurrent User: " + usuario.getName());

                Attribute identificacion = usuario.getAttribute("id");
                System.out.println("User ID: " + identificacion.getValue());

                System.out.println("Nombre: " + usuario.getChild("first_name").getText());

                System.out.println("Apellido: " + usuario.getChild("last_name").getText());

                System.out.println("Sexo: "+ usuario.getChild("gender").getText()); */
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Usuario procesarUsuario(Element usuario){  //Obtiene los atributos y crea el usuario
        Attribute idA = usuario.getAttribute("id");
        String id = idA.getValue();
        String nombre = usuario.getChild("nombre_usuario").getText();
        String contraseña = usuario.getChild("contraseña").getText();
        String administrador = usuario.getChild("admin").getText();

        Usuario u = new Usuario(id, nombre, contraseña, administrador);
        return u;
    }

    public String revisarExtension(String fileName) {  //Revisa que el nombre del archivo tenga la extension .xml
        if (!fileName.contains("."))
            fileName = fileName + ".xml";
        else if (!fileName.contains(".xml"))
            fileName = fileName.substring(0, fileName.indexOf('.')) + ".xml";
        return fileName;
    }

    public LinkedListGeneric<Usuario> getUsuarios() {  //Getter para sacar la lista de usuarios de aca
        return usuarios;
    }
}
