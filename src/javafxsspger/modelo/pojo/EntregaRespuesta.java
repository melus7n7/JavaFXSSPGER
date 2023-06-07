/*
*Autor: Montiel Salas Jesús Jacob
*Fecha de creación: 06/05/2023
*Fecha de modificación: 06/05/2023
*Descripción: POJO de la entrega respuesta
*/
package javafxsspger.modelo.pojo;

import java.util.ArrayList;


public class EntregaRespuesta {
    
    private ArrayList<Entrega> entregas;
    private int codigoRespuesta;

    public EntregaRespuesta() {
    }

    public EntregaRespuesta(ArrayList<Entrega> entregas, int codigoRespuesta) {
        this.entregas = entregas;
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Entrega> getEntregas() {
        return entregas;
    }

    public void setEntregas(ArrayList<Entrega> entregas) {
        this.entregas = entregas;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }
    
}
