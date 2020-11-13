package data;

public class Palabra {
    private String id;
    private String contenido;
    private String url;

    public Palabra(String id, String contenido, String url) {
        this.id = id;
        this.contenido = contenido;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
