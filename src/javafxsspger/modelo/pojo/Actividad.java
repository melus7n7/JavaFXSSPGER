/*
*Autor: Montiel Salas Jesús Jacob 
*Fecha de creación: 20/05/2023
*Fecha de modificación: 20/05/2023
*Descripción: POJO de la actividad
*/
package javafxsspger.modelo.pojo;

/**
 *
 * @author monti
 */
public class Actividad {
    
    
    private int idActividad;
    private String titulo;
    private String descripcion;
    private String fechaCreacion;
    private String fechaInicio;
    private String fechaFinal;
    private int idEstudiante;
    private int idTrabajoRecepcional;

    public Actividad() {
    }

    public Actividad(int idActividad, String titulo, String descripcion, String fechaCreacion, String fechaInicio, String fechaFinal, int idEstudiante, int idTrabajoRecepcional) {
        this.idActividad = idActividad;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.idEstudiante = idEstudiante;
        this.idTrabajoRecepcional = idTrabajoRecepcional;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public int getIdTrabajoRecepcional() {
        return idTrabajoRecepcional;
    }

    public void setIdTrabajoRecepcional(int idTrabajoRecepcional) {
        this.idTrabajoRecepcional = idTrabajoRecepcional;
    }
    
}
