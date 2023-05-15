/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 05/05/2023
*Descripción: POJO del anteproyecto
*/
package javafxsspger.modelo.pojo;

import java.util.ArrayList;

public class Anteproyecto {
    
    private int idAnteproyecto;
    private String titulo;
    private String descripcion;
    private String nombreDirector;
    private String cuerpoAcademico;
    private String areaDisciplinar;
    private String fechaPublicacion;
    private int noEstudiantesMaximo;
    private int codigoRespuesta;
    private ArrayList<Academico> codirectores;

    public Anteproyecto() {
    }

    public String getTitulo() {
        return titulo;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
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

    public int getNoEstudiantesMaximo() {
        return noEstudiantesMaximo;
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

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public void setNoEstudiantesMaximo(int noEstudiantesMaximo) {
        this.noEstudiantesMaximo = noEstudiantesMaximo;
    }

    public ArrayList<Academico> getCodirectores() {
        return codirectores;
    }

    public void setCodirectores(ArrayList<Academico> codirectores) {
        this.codirectores = codirectores;
    }
    
    public String getNombreCodirectores (){
        String nombres = "";
        for(int i = 0; i < codirectores.size(); i++){
            nombres += codirectores.get(i).getNombre();
            if(i != (codirectores.size() - 1)){
                nombres += ", ";
            }
        }
        return nombres;
    }
    
}
