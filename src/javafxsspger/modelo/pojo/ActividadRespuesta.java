/*
*Autor: Montiel Salas Jesús Jacob 
*Fecha de creación: 20/05/2023
*Fecha de modificación: 20/05/2023
*Descripción: POJO que contiene varias actividades
*/
package javafxsspger.modelo.pojo;

import java.util.ArrayList;

/**
 *
 * @author monti
 */
public class ActividadRespuesta {
    
    
    private ArrayList<Actividad> actividades;
    private int codigoRespuesta;

    public ActividadRespuesta() {
    }

    public ActividadRespuesta(ArrayList<Actividad> actividades, int codigoRespuesta) {
        this.actividades = actividades;
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(ArrayList<Actividad> actividades) {
        this.actividades = actividades;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }
    
}
