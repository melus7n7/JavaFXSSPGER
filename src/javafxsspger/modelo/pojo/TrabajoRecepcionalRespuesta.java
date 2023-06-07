/*
*Autor: Montiel Salas Jesús Jacob
*Fecha de creación: 21/05/2023
*Fecha de modificación: 21/05/2023
*Descripción: POJO que contiene varios Trabajos Recepcionales
*/
package javafxsspger.modelo.pojo;

import java.util.ArrayList;

public class TrabajoRecepcionalRespuesta {
    
    private int codigoRespuesta;
    private ArrayList <TrabajoRecepcional> trabajosRecepcionales;

    public TrabajoRecepcionalRespuesta() {
    }

    public TrabajoRecepcionalRespuesta(int codigoRespuesta, ArrayList<TrabajoRecepcional> trabajosRecepcionales) {
        this.codigoRespuesta = codigoRespuesta;
        this.trabajosRecepcionales = trabajosRecepcionales;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<TrabajoRecepcional> getTrabajosRecepcionales() {
        return trabajosRecepcionales;
    }

    public void setTrabajosRecepcionales(ArrayList<TrabajoRecepcional> trabajosRecepcionales) {
        this.trabajosRecepcionales = trabajosRecepcionales;
    }
    
}
