/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 20/05/2023
*Fecha de modificación: 20/05/2023
*Descripción: Interfaz para comunicar cuáles codirectores han sido seleccionados
*/
package javafxsspger.interfaces;

import javafxsspger.modelo.pojo.Academico;

public interface INotificacionCodirector {
    public void notificarAñadirCodirector(Academico codirector);
    public void notificarEliminarCodirector(Academico codirector);
}
