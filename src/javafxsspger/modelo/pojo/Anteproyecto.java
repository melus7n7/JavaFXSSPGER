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
    private String fechaCreacion;
    private String fechaAprobacion;
    private int noEstudiantesMaximo;
    private byte[] documento;
    private String nombreDocumento;
    
    private int idCuerpoAcademico;
    private String nombreCuerpoAcademico;
    private int idTipoAnteproyecto;
    private String tipoAnteproyecto;
    private int idLGAC;
    private String nombreLGAC;
 
    private String nombreDirector;
    
    private int codigoRespuesta;
    private ArrayList<Academico> codirectores;

    public Anteproyecto() {
    }
    //Getters
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

    public String getNombreCuerpoAcademico() {
        return nombreCuerpoAcademico;
    }

    public int getNoEstudiantesMaximo() {
        return noEstudiantesMaximo;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public byte[] getDocumento() {
        return documento;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public int getIdCuerpoAcademico() {
        return idCuerpoAcademico;
    }

    public int getIdTipoAnteproyecto() {
        return idTipoAnteproyecto;
    }

    public String getTipoAnteproyecto() {
        return tipoAnteproyecto;
    }

    public int getIdLGAC() {
        return idLGAC;
    }

    public String getNombreLGAC() {
        return nombreLGAC;
    }
    
    public ArrayList<Academico> getCodirectores() {
        return codirectores;
    }
    
    public int getIdAnteproyecto() {
        return idAnteproyecto;
    }

    public String getFechaAprobacion() {
        return fechaAprobacion;
    }
    
    //Setters
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setNombreDirector(String nombreDirector) {
        this.nombreDirector = nombreDirector;
    }

    public void setNombreCuerpoAcademico(String nombreCuerpoAcademico) {
        this.nombreCuerpoAcademico = nombreCuerpoAcademico;
    }

    public void setIdAnteproyecto(int idAnteproyecto) {
        this.idAnteproyecto = idAnteproyecto;
    }

    public void setFechaAprobacion(String fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public void setNoEstudiantesMaximo(int noEstudiantesMaximo) {
        this.noEstudiantesMaximo = noEstudiantesMaximo;
    }
    
    public void setCodirectores(ArrayList<Academico> codirectores) {
        this.codirectores = codirectores;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setDocumento(byte[] documento) {
        this.documento = documento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public void setIdCuerpoAcademico(int idCuerpoAcademico) {
        this.idCuerpoAcademico = idCuerpoAcademico;
    }

    public void setIdTipoAnteproyecto(int idTipoAnteproyecto) {
        this.idTipoAnteproyecto = idTipoAnteproyecto;
    }

    public void setTipoAnteproyecto(String tipoAnteproyecto) {
        this.tipoAnteproyecto = tipoAnteproyecto;
    }

    public void setIdLGAC(int idLGAC) {
        this.idLGAC = idLGAC;
    }

    public void setNombreLGAC(String nombreLGAC) {
        this.nombreLGAC = nombreLGAC;
    }
    
    public String getNombreCodirectores (){
        String nombres = "";
        for(int i = 0; i < codirectores.size(); i++){
            nombres += codirectores.get(i).getNombreCompleto();
            if(i != (codirectores.size() - 1)){
                nombres += ", ";
            }
        }
        return nombres;
    }
    
}
