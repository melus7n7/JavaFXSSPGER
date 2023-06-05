/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 04/06/2023
*Fecha de modificación: 04/06/2023
*Descripción: POJO de la calificación para entregas y avances
*/
package javafxsspger.modelo.pojo;


public class Calificacion {
    
    private int idRubricaCalificacion;
    private String nivelSatisfaccion;
    private int puntajeCalificacion;

    public Calificacion() {
    }

    public Calificacion(int idRubricaCalificacion, String nivelSatisfaccion, int puntajeCalificacion) {
        this.idRubricaCalificacion = idRubricaCalificacion;
        this.nivelSatisfaccion = nivelSatisfaccion;
        this.puntajeCalificacion = puntajeCalificacion;
    }

    public int getIdRubricaCalificacion() {
        return idRubricaCalificacion;
    }

    public String getNivelSatisfaccion() {
        return nivelSatisfaccion;
    }

    public int getPuntajeCalificacion() {
        return puntajeCalificacion;
    }

    public void setIdRubricaCalificacion(int idRubricaCalificacion) {
        this.idRubricaCalificacion = idRubricaCalificacion;
    }

    public void setNivelSatisfaccion(String nivelSatisfaccion) {
        this.nivelSatisfaccion = nivelSatisfaccion;
    }

    public void setPuntajeCalificacion(int puntajeCalificacion) {
        this.puntajeCalificacion = puntajeCalificacion;
    }

    @Override
    public String toString() {
        return nivelSatisfaccion;
    }
    
    
}
