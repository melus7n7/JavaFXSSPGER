/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 14/05/2023
*Descripción: POJO que contiene varias LGAC
*/
package javafxsspger.modelo.pojo;

import java.util.ArrayList;

public class LGACRespuesta {
    private ArrayList<LGAC> LGACs;
    private int codigoRespuesta;

    public ArrayList<LGAC> getLGACs() {
        return LGACs;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setLGACs(ArrayList<LGAC> LGACs) {
        this.LGACs = LGACs;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }
    
    
}
