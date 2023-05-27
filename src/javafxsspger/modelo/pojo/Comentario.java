/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 24/05/2023
*Fecha de modificación: 24/05/2023
*Descripción: POJO del comentario de un anteproyecto
*/
package javafxsspger.modelo.pojo;

import java.sql.Date;


public class Comentario {
    
    private int idComentario;
    private String texto;
    private Date fechaCreacion;
    
    private int idAcademico;
    private String nombreAcademico;
    
    private int idAnteproyecto;

    public Comentario() {
    }

    public int getIdComentario() {
        return idComentario;
    }

    public String getTexto() {
        return texto;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public int getIdAcademico() {
        return idAcademico;
    }

    public String getNombreAcademico() {
        return nombreAcademico;
    }

    public int getIdAnteproyecto() {
        return idAnteproyecto;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setIdAcademico(int idAcademico) {
        this.idAcademico = idAcademico;
    }

    public void setNombreAcademico(String nombreAcademico) {
        this.nombreAcademico = nombreAcademico;
    }

    public void setIdAnteproyecto(int idAnteproyecto) {
        this.idAnteproyecto = idAnteproyecto;
    }
    
    
}
