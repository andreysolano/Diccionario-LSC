package data;

public class Usuario {
    private int id = 10;
    private String nombre;
    private String contraseña;
    private String administrador;

    public Usuario(int id, String nombre, String contraseña, String administrador) {
        //this.id = id;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.administrador = administrador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        id +=1;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String apellido) {
        this.contraseña = contraseña;
    }

    public String getAdministrador() {
        return administrador;
    }

    public void setAdministrador(String genero) {
        this.administrador = administrador;
    }
}
