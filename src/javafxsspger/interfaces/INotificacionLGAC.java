/*
*Autor: Montiel Salas Jesús Jacob
*Fecha de creación: 06/06/2023
*Fecha de modificación: 06/06/2023
*Descripción: Interfaz para comunicar cuáles LGAC han sido seleccionadas
*/
package javafxsspger.interfaces;

import javafxsspger.modelo.pojo.LGAC;

public interface INotificacionLGAC {
    
    
    public void notificarAñadirLGAC(LGAC lgac);
    public void notificarEliminarLGAC(LGAC lgac);
    
}
