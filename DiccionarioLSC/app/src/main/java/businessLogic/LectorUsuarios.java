package businessLogic;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import data.Usuario;
import estructuras.LinkedListGeneric;

public class LectorUsuarios {
    private String fileName;
    private LinkedListGeneric<Usuario> usuarios;
    private JSONParser parser;

    public LectorUsuarios(String fileName) {
        this.fileName = fileName;
        this.usuarios = new LinkedListGeneric<Usuario>();
        this.parser = new JSONParser();
        this.leerArchivo();
    }

    public void leerArchivo() {
        fileName = checkfileName(fileName);
        try {
            JSONObject jsonObject = parser.parse(new FileReader(fileName));
            Usuario u = procesarUsuario(jsonObject);
            usuarios.pushFront(u);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Usuario procesarUsuario(JSONObject jsonObject) throws JSONException {
        boolean esAdmin = (boolean) jsonObject.get("esAdmin");
        String username = (String) jsonObject.get("username");
        String clave = (String) jsonObject.get("clave");
        String celular = (String) jsonObject.get("celular");
        Usuario u = new Usuario(username, clave, celular, esAdmin);
        return u;
    }

    public String checkfileName(@NotNull String fileName) {
        if (!fileName.contains("."))
            fileName = fileName + ".txt";
        else if (!fileName.contains(".txt"))
            fileName = fileName.substring(0, fileName.indexOf('.')) + ".txt";
        return fileName;
    }

    public LinkedListGeneric<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(LinkedListGeneric<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
