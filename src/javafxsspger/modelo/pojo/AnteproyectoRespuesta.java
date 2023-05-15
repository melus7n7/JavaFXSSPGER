/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 14/05/2023
*Descripción: POJO que contiene varios anteproyectos
*/
package javafxsspger.modelo.pojo;

import java.util.ArrayList;

public class AnteproyectoRespuesta {
    
    private int codigoRespuesta;
    private ArrayList <Anteproyecto> anteproyectos;

    public AnteproyectoRespuesta() {
    }

    public AnteproyectoRespuesta(int codigoRespuesta, ArrayList<Anteproyecto> anteproyectos) {
        this.codigoRespuesta = codigoRespuesta;
        this.anteproyectos = anteproyectos;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public ArrayList<Anteproyecto> getAnteproyectos() {
        return anteproyectos;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public void setAnteproyectos(ArrayList<Anteproyecto> anteproyectos) {
        this.anteproyectos = anteproyectos;
    }
    
    
}
