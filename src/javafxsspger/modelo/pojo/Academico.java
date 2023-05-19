/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 05/05/2023
*Descripción: POJO del director
*/
package javafxsspger.modelo.pojo;

public class Academico extends Usuario{
    
    private int idAcademico;
    private String noPersonal;
    private int idCuerpoAcademico;

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
    

    public void setIdAcademico(int idAcademico) {
        this.idAcademico = idAcademico;
    }

    public void setNoPersonal(String noPersonal) {
        this.noPersonal = noPersonal;
    }

    public void setIdCuerpoAcademico(int idCuerpoAcademico) {
        this.idCuerpoAcademico = idCuerpoAcademico;
    }

    

}
