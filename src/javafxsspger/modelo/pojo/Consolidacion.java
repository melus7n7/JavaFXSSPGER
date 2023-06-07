/*
*Autor: Montiel Salas Jesús Jacob
*Fecha de creación: 06/06/2023
*Fecha de modificación: 06/06/2023
*Descripción: POJO de la consolidacion de un cuerpo academico
*/
package javafxsspger.modelo.pojo;

/**
 *
 * @author monti
 */
public class Consolidacion {
    
    private int idConsolidacion;
    private String nivelConsolidacion;

    public Consolidacion() {
    }

    public Consolidacion(int idConsolidacion, String nivelConsolidacion) {
        this.idConsolidacion = idConsolidacion;
        this.nivelConsolidacion = nivelConsolidacion;
    }

    public int getIdConsolidacion() {
        return idConsolidacion;
    }

    public void setIdConsolidacion(int idConsolidacion) {
        this.idConsolidacion = idConsolidacion;
    }

    public String getNivelConsolidacion() {
        return nivelConsolidacion;
    }

    public void setNivelConsolidacion(String nivelConsolidacion) {
        this.nivelConsolidacion = nivelConsolidacion;
    }

    @Override
    public String toString() {
        return  nivelConsolidacion;
    }
      
}
