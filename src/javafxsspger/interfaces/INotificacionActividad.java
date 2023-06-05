/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 04/06/2023
*Fecha de modificación: 04/06/2023
*Descripción: Interfaz para comunicar cuando se ha seleccionado o deseleccionado una actividad en un avance
*/
package javafxsspger.interfaces;

import javafxsspger.modelo.pojo.Actividad;

public interface INotificacionActividad {
    
    public void notificarAñadirActividad(Actividad actividad);
    public void notificarEliminarActividad(Actividad actividad);
}
