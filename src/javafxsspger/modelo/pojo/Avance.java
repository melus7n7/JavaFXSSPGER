/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 03/06/2023
*Fecha de modificación: 03/06/2023
*Descripción: POJO de un avance
*/
package javafxsspger.modelo.pojo;

import java.util.ArrayList;

public class Avance {
    
    private int idAvance;
    private String titulo;
    private String descripcion;
    private String retroalimentacion;
    private String fechaCreacion;
    private String fechaInicio;
    private String fechaFin;
    private int idRubrica;
    private String nivelSatisfaccion;
    private int puntajeSatisfaccion;
    private ArrayList <Actividad> actividades;
    private int codigoRespuesta;
    private int idEstudiante;
    private ArrayList <Academico> directores;

    public Avance() {
    }

    public int getIdAvance() {
        return idAvance;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getRetroalimentacion() {
        return retroalimentacion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public int getIdRubrica() {
        return idRubrica;
    }

    public String getNivelSatisfaccion() {
        return nivelSatisfaccion;
    }

    public int getPuntajeSatisfaccion() {
        return puntajeSatisfaccion;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public ArrayList<Actividad> getActividades() {
        return actividades;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ArrayList<Academico> getDirectores() {
        return directores;
    }

    public void setIdAvance(int idAvance) {
        this.idAvance = idAvance;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setRetroalimentacion(String retroalimentacion) {
        this.retroalimentacion = retroalimentacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setIdRubrica(int idRubrica) {
        this.idRubrica = idRubrica;
    }

    public void setNivelSatisfaccion(String nivelSatisfaccion) {
        this.nivelSatisfaccion = nivelSatisfaccion;
    }

    public void setPuntajeSatisfaccion(int puntajeSatisfaccion) {
        this.puntajeSatisfaccion = puntajeSatisfaccion;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public void setActividades(ArrayList<Actividad> actividades) {
        this.actividades = actividades;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setDirectores(ArrayList<Academico> directores) {
        this.directores = directores;
    }
    
    
}
