/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 20/05/2023
*Fecha de modificación: 20/05/2023
*Descripción: POJO del academico respuesta
*/
package javafxsspger.modelo.pojo;

import java.util.ArrayList;

public class AcademicoRespuesta {
    
    private ArrayList<Academico> academicos;
    private int codigoRespuesta;

    public AcademicoRespuesta(ArrayList<Academico> academicos, int codigoRespuesta) {
        this.academicos = academicos;
        this.codigoRespuesta = codigoRespuesta;
    }

    public AcademicoRespuesta() {
    }

    public ArrayList<Academico> getAcademicos() {
        return academicos;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setAcademicos(ArrayList<Academico> academicos) {
        this.academicos = academicos;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }
     
}
