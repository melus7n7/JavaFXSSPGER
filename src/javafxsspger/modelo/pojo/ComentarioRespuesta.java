/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 29/05/2023
*Fecha de modificación: 29/05/2023
*Descripción: POJO de un conjunto de comentarios
*/
package javafxsspger.modelo.pojo;

import java.util.ArrayList;


public class ComentarioRespuesta {
    private ArrayList <Comentario> comentarios;
    private int codigoRespuesta;

    public ComentarioRespuesta() {
    }

    public ArrayList<Comentario> getComentarios() {
        return comentarios;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setComentarios(ArrayList<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }
    
    
}
