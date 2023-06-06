/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 06/06/2023
*Fecha de modificación: 06/06/2023
*Descripción: POJO que contiene varias Experiencias Educativas
*/
package javafxsspger.modelo.pojo;

import java.util.ArrayList;

/**
 *
 * @author danie
 */
public class ExperienciaEducativaRespuesta {
    private ArrayList<ExperienciaEducativa> expEducativas;
    private int codigoRespuesta;

    public ArrayList<ExperienciaEducativa> getExpEducativas() {
        return expEducativas;
    }

    public void setExpEducativas(ArrayList<ExperienciaEducativa> expEducativas) {
        this.expEducativas = expEducativas;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }
}
