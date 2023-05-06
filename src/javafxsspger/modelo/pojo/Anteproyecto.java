/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 05/05/2023
*Descripción: POJO del anteproyecto
*/
package javafxsspger.modelo.pojo;

public class Anteproyecto {
    
    private int idAnteproyecto;
    private String titulo;
    private String descripcion;
    private String nombreDirector;
    private String cuerpoAcademico;
    private String areaDisciplinar;
    private String fechaPublicacion;

    public Anteproyecto() {
    }

    public Anteproyecto(int idAnteproyecto, String titulo, String descripcion, String nombreDirector, String cuerpoAcademico, String areaDisciplinar) {
        this.idAnteproyecto = idAnteproyecto;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.nombreDirector = nombreDirector;
        this.cuerpoAcademico = cuerpoAcademico;
        this.areaDisciplinar = areaDisciplinar;
    }
    
    

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNombreDirector() {
        return nombreDirector;
    }

    public String getCuerpoAcademico() {
        return cuerpoAcademico;
    }

    public String getAreaDisciplinar() {
        return areaDisciplinar;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setNombreDirector(String nombreDirector) {
        this.nombreDirector = nombreDirector;
    }

    public void setCuerpoAcademico(String cuerpoAcademico) {
        this.cuerpoAcademico = cuerpoAcademico;
    }

    public void setAreaDisciplinar(String areaDisciplinar) {
        this.areaDisciplinar = areaDisciplinar;
    }

    public void setIdAnteproyecto(int idAnteproyecto) {
        this.idAnteproyecto = idAnteproyecto;
    }

    public int getIdAnteproyecto() {
        return idAnteproyecto;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
    
    
    
}
