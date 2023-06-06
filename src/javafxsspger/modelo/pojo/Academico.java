/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 20/05/2023
*Descripción: POJO del director
*/
package javafxsspger.modelo.pojo;

public class Academico extends Usuario{
    
    private int idAcademico;
    private String noPersonal;
    private int idCuerpoAcademico;
    private int idAnteproyecto;
    private boolean esDirector;
    private boolean esProfesor;
    private boolean esResponsableCA;
    private boolean esCoordinador;
    private String nombreCompleto;
    private String puesto;

    

    public Academico(int idAcademico, String noPersonal, int idCuerpoAcademico, int idAnteproyecto, boolean esDirector, boolean esProfesor, boolean esResponsableCA, boolean esCoordinador, String nombreCompleto, String puesto) {
        this.idAcademico = idAcademico;
        this.noPersonal = noPersonal;
        this.idCuerpoAcademico = idCuerpoAcademico;
        this.idAnteproyecto = idAnteproyecto;
        this.esDirector = esDirector;
        this.esProfesor = esProfesor;
        this.esResponsableCA = esResponsableCA;
        this.esCoordinador = esCoordinador;
        this.nombreCompleto = nombreCompleto;
        this.puesto = puesto;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
    
    public Academico(int idUsuario, String nombre, int tipoUsuario) {
        super(idUsuario, nombre, tipoUsuario);
    }
    
    public Academico (){
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public int getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(int idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public String getNombreTipoUsuario() {
        return nombreTipoUsuario;
    }

    public void setNombreTipoUsuario(String nombreTipoUsuario) {
        this.nombreTipoUsuario = nombreTipoUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    
    public int getIdAcademico() {
        return idAcademico;
    }

    public String getNoPersonal() {
        return noPersonal;
    }

    public int getIdCuerpoAcademico() {
        return idCuerpoAcademico;
    }

    public int getIdAnteproyecto() {
        return idAnteproyecto;
    }

    public boolean isEsDirector() {
        return esDirector;
    }

    public boolean isEsProfesor() {
        return esProfesor;
    }

    public boolean isEsResponsableCA() {
        return esResponsableCA;
    }

    public boolean isEsCoordinador() {
        return esCoordinador;
    }
    

    public void setIdAcademico(int idAcademico) {
        this.idAcademico = idAcademico;
    }

    public void setNoPersonal(String noPersonal) {
        this.noPersonal = noPersonal;
    }

    public void setIdCuerpoAcademico(int idCuerpoAcademico) {
        this.idCuerpoAcademico = idCuerpoAcademico;
    }

    public void setIdAnteproyecto(int idAnteproyecto) {
        this.idAnteproyecto = idAnteproyecto;
    }

    public void setEsDirector(boolean esDirector) {
        this.esDirector = esDirector;
    }

    public void setEsProfesor(boolean esProfesor) {
        this.esProfesor = esProfesor;
    }

    public void setEsResponsableCA(boolean esResponsableCA) {
        this.esResponsableCA = esResponsableCA;
    }

    public void setEsCoordinador(boolean esCoordinador) {
        this.esCoordinador = esCoordinador;
    }

    

}
