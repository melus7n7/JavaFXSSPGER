/*
*Autor: Montiel Salas Jesús Jacob
*Fecha de creación: 06/06/2023
*Fecha de modificación: 06/06/2023
*Descripción: POJO de un conjunto de consolidaciones
*/
package javafxsspger.modelo.pojo;

import java.util.ArrayList;

/**
 *
 * @author monti
 */
public class ConsolidacionRespuesta {
    
    private ArrayList <Consolidacion> consolidaciones;
    private int codigoRespuesta;

    public ConsolidacionRespuesta() {
    }

    public ConsolidacionRespuesta(ArrayList<Consolidacion> consolidaciones, int codigoRespuesta) {
        this.consolidaciones = consolidaciones;
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Consolidacion> getConsolidaciones() {
        return consolidaciones;
    }

    public void setConsolidaciones(ArrayList<Consolidacion> consolidaciones) {
        this.consolidaciones = consolidaciones;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }
    
    

}
