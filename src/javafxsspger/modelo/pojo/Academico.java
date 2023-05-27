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

    public Academico(int idUsuario, String nombre, int tipoUsuario) {
        super(idUsuario, nombre, tipoUsuario);
    }
    
    public Academico (){
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
