/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 05/05/2023
*Descripción: POJO del usuario
*/
package javafxsspger.modelo.pojo;


public class Usuario {
    
    protected int idUsuario;
    protected String nombre;
    protected int idTipoUsuario;
    protected int codigoRespuesta;
    protected String nombreTipoUsuario;
    protected String usuario;
    protected String contrasena;

    public Usuario(int idUsuario, String nombre, int tipoUsuario) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.idTipoUsuario = tipoUsuario;
    }
    
    public Usuario(){
        
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public String getNombreTipoUsuario() {
        return nombreTipoUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }
    

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setIdTipoUsuario(int idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public void setNombreTipoUsuario(String nombreTipoUsuario) {
        this.nombreTipoUsuario = nombreTipoUsuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    
    
}


