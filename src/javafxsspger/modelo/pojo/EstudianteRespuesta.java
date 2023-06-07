/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 16/05/2023
*Descripción: POJO que contiene múltiples estudiantes
*/
package javafxsspger.modelo.pojo;

import java.util.ArrayList;

public class EstudianteRespuesta {
    
    private ArrayList<Estudiante> estudiantes;
    private int codigoRespuesta;

    public EstudianteRespuesta() {
    }

    public ArrayList<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setEstudiantes(ArrayList<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }
    
}
