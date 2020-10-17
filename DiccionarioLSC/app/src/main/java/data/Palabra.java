package data;

public class Palabra {
    private String id;
    private String contenido;

    public Palabra(String id, String contenido) {
        this.id = id;
        this.contenido = contenido;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
