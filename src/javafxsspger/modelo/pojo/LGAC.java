/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 16/05/2023
*Descripción: POJO de una LGAC
*/
package javafxsspger.modelo.pojo;


public class LGAC {
    private int idLGAC;
    private String nombreLGAC;
    private String descripcion;
    private int idCuerpoAcademico;

    public LGAC(int idLGAC, String nombreLGAC, String descripcion, int idCuerpoAcademico) {
        this.idLGAC = idLGAC;
        this.nombreLGAC = nombreLGAC;
        this.descripcion = descripcion;
        this.idCuerpoAcademico = idCuerpoAcademico;
    }

    public int getIdCuerpoAcademico() {
        return idCuerpoAcademico;
    }

    public void setIdCuerpoAcademico(int idCuerpoAcademico) {
        this.idCuerpoAcademico = idCuerpoAcademico;
    }
    
    public LGAC() {
    }

    public int getIdLGAC() {
        return idLGAC;
    }

    public String getNombreLGAC() {
        return nombreLGAC;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setIdLGAC(int idLGAC) {
        this.idLGAC = idLGAC;
    }

    public void setNombreLGAC(String nombreLGAC) {
        this.nombreLGAC = nombreLGAC;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return nombreLGAC;
    }
    
    
}
