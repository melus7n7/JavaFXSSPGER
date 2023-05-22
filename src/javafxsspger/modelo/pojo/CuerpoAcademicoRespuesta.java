/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 20/05/2023
*Fecha de modificación: 20/05/2023
*Descripción: POJO de un conjunto de cuerpos académicos
*/
package javafxsspger.modelo.pojo;

import java.util.ArrayList;

public class CuerpoAcademicoRespuesta {
    
    private ArrayList <CuerpoAcademico> cuerposAcademicos;
    private int codigoRespuesta;

    public CuerpoAcademicoRespuesta() {
    }
    
    public ArrayList<CuerpoAcademico> getCuerposAcademicos() {
        return cuerposAcademicos;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCuerposAcademicos(ArrayList<CuerpoAcademico> cuerposAcademicos) {
        this.cuerposAcademicos = cuerposAcademicos;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }
    
    
}
