package data;

public class Usuario {
    private String username;
    private String clave;
    private String celular;
    private boolean esAdmin;

    public Usuario(String username, String clave, String celular, boolean esAdmin) {
        this.username = username;
        this.clave = clave;
        this.celular = celular;
        this.esAdmin = esAdmin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public boolean isEsAdmin() {
        return esAdmin;
    }

    public void setEsAdmin(boolean esAdmin) {
        this.esAdmin = esAdmin;
    }
}
