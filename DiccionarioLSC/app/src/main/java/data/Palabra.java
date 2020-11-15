package data;

public class Palabra {
    private String id;  //Este es un número identificador de cada palabra (es un String porque no hacemos operaciones básicas con él)
    private String contenido;  //Esta es la palabra como tal
    private String url;  //Este es el Gif/Img que tiene cada palabra
    private String significado;  //Este es el significado que tiene la palabra

    public Palabra(String id, String contenido, String url, String significado) {
        this.id = id;
        this.contenido = contenido;
        this.url = url;
        this.significado = significado;
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

    public String getSignificado() { return significado; }

    public void setSignificado(String significado) { this.significado = significado; }
}
