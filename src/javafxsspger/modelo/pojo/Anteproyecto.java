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
    private int noEstudiantesAsignados;
    private byte[] documento;
    private String nombreDocumento;
    private int idTrabajoRecepcional;
    
    private int idCuerpoAcademico;
    private String nombreCuerpoAcademico;
    private int idTipoAnteproyecto;
    private String tipoAnteproyecto;
    private int idLGAC;
    private String nombreLGAC;
    private int idEstado;
    
    private String nombreDirector;
    private int idDirector;
    
    private int codigoRespuesta;
    private ArrayList<Academico> codirectores;
    private ArrayList<Estudiante> estudiantes;

    public Anteproyecto(int idAnteproyecto, String titulo, String descripcion, String fechaCreacion, String fechaAprobacion, int noEstudiantesMaximo, int noEstudiantesAsignados, byte[] documento, String nombreDocumento, int idTrabajoRecepcional, int idCuerpoAcademico, String nombreCuerpoAcademico, int idTipoAnteproyecto, String tipoAnteproyecto, int idLGAC, String nombreLGAC, int idEstado, String nombreDirector, int idDirector, int codigoRespuesta, ArrayList<Academico> codirectores, ArrayList<Estudiante> estudiantes) {
        this.idAnteproyecto = idAnteproyecto;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.fechaAprobacion = fechaAprobacion;
        this.noEstudiantesMaximo = noEstudiantesMaximo;
        this.noEstudiantesAsignados = noEstudiantesAsignados;
        this.documento = documento;
        this.nombreDocumento = nombreDocumento;
        this.idTrabajoRecepcional = idTrabajoRecepcional;
        this.idCuerpoAcademico = idCuerpoAcademico;
        this.nombreCuerpoAcademico = nombreCuerpoAcademico;
        this.idTipoAnteproyecto = idTipoAnteproyecto;
        this.tipoAnteproyecto = tipoAnteproyecto;
        this.idLGAC = idLGAC;
        this.nombreLGAC = nombreLGAC;
        this.idEstado = idEstado;
        this.nombreDirector = nombreDirector;
        this.idDirector = idDirector;
        this.codigoRespuesta = codigoRespuesta;
        this.codirectores = codirectores;
        this.estudiantes = estudiantes;
    }
    
    public Anteproyecto() {
    }
    
    public String getTitulo() {
        return titulo;
    }

    public int getIdDirector() {
        return idDirector;
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

    public int getIdEstado() {
        return idEstado;
    }

    public ArrayList<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public int getNoEstudiantesAsignados() {
        return noEstudiantesAsignados;
    }

    public int getIdTrabajoRecepcional() {
        return idTrabajoRecepcional;
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

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public void setIdDirector(int idDirector) {
        this.idDirector = idDirector;
    }

    public void setEstudiantes(ArrayList<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public void setNoEstudiantesAsignados(int noEstudiantesAsignados) {
        this.noEstudiantesAsignados = noEstudiantesAsignados;
    }

    public void setIdTrabajoRecepcional(int idTrabajoRecepcional) {
        this.idTrabajoRecepcional = idTrabajoRecepcional;
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
