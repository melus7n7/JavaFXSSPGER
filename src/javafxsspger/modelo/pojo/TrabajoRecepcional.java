/*
*Autor: Montiel Salas Jesús Jacob 
*Fecha de creación: 21/05/2023
*Fecha de modificación: 21/05/2023
*Descripción: POJO del Trabajo Recepcional
*/
package javafxsspger.modelo.pojo;

/**
 *
 * @author monti
 */
public class TrabajoRecepcional {
    
    
    private int idTrabajoRecepcional;
    private String titulo; 
    private String descripcion; 
    private String fechaCreacion; 
    private String fechaAprobacion; 
    private int noEstudiantesMaximos; 
    private int noEstudiantesAsignados; 
    private byte[] documento; 
    private String nombreDocumento;
    private int idAnteproyecto; 
    private int idTipoAnteproyecto; 
    private int idCuerpoAcademico; 
    private int idEstado; 
    private int idLGAC;
    private int codigoRespuesta;

    public TrabajoRecepcional() {
    }

    public TrabajoRecepcional(int idTrabajoRecepcional, String titulo, String descripcion, String fechaCreacion, String fechaAprobacion, int noEstudiantesMaximos, int noEstudiantesAsignados, byte[] documento, String nombreDocumento, int idAnteproyecto, int idTipoAnteproyecto, int idCuerpoAcademico, int idEstado, int idLGAC) {
        this.idTrabajoRecepcional = idTrabajoRecepcional;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.fechaAprobacion = fechaAprobacion;
        this.noEstudiantesMaximos = noEstudiantesMaximos;
        this.noEstudiantesAsignados = noEstudiantesAsignados;
        this.documento = documento;
        this.nombreDocumento = nombreDocumento;
        this.idAnteproyecto = idAnteproyecto;
        this.idTipoAnteproyecto = idTipoAnteproyecto;
        this.idCuerpoAcademico = idCuerpoAcademico;
        this.idEstado = idEstado;
        this.idLGAC = idLGAC;
    }

    public int getIdTrabajoRecepcional() {
        return idTrabajoRecepcional;
    }

    public void setIdTrabajoRecepcional(int idTrabajoRecepcional) {
        this.idTrabajoRecepcional = idTrabajoRecepcional;
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

    public String getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(String fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public int getNoEstudiantesMaximos() {
        return noEstudiantesMaximos;
    }

    public void setNoEstudiantesMaximos(int noEstudiantesMaximos) {
        this.noEstudiantesMaximos = noEstudiantesMaximos;
    }

    public int getNoEstudiantesAsignados() {
        return noEstudiantesAsignados;
    }

    public void setNoEstudiantesAsignados(int noEstudiantesAsignados) {
        this.noEstudiantesAsignados = noEstudiantesAsignados;
    }

    public byte[] getDocumento() {
        return documento;
    }

    public void setDocumento(byte[] documento) {
        this.documento = documento;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public int getIdAnteproyecto() {
        return idAnteproyecto;
    }

    public void setIdAnteproyecto(int idAnteproyecto) {
        this.idAnteproyecto = idAnteproyecto;
    }

    public int getIdTipoAnteproyecto() {
        return idTipoAnteproyecto;
    }

    public void setIdTipoAnteproyecto(int idTipoAnteproyecto) {
        this.idTipoAnteproyecto = idTipoAnteproyecto;
    }

    public int getIdCuerpoAcademico() {
        return idCuerpoAcademico;
    }

    public void setIdCuerpoAcademico(int idCuerpoAcademico) {
        this.idCuerpoAcademico = idCuerpoAcademico;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getIdLGAC() {
        return idLGAC;
    }

    public void setIdLGAC(int idLGAC) {
        this.idLGAC = idLGAC;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }
    
    @Override
    public String toString(){
        return titulo;
    }
    
 }
