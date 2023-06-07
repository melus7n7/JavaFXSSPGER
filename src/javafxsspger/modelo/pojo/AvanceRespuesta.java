/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 03/06/2023
*Fecha de modificación: 03/06/2023
*Descripción: POJO que contiene varios avances
*/
package javafxsspger.modelo.pojo;

import java.util.ArrayList;


public class AvanceRespuesta {
    ArrayList<Avance> avances;
    int codigoRespuesta;

    public AvanceRespuesta() {
    }

    public AvanceRespuesta(ArrayList<Avance> avances, int codigoRespuesta) {
        this.avances = avances;
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Avance> getAvances() {
        return avances;
    }
    
    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setAvances(ArrayList<Avance> avances) {
        this.avances = avances;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }
    
}
