/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 05/05/2023
*Descripción: POJO del TipoAnteproyecto
*/
package javafxsspger.modelo.pojo;

public class TipoAnteproyecto {
    private int idTipoAnteproyecto;
    private String tipoAnteproyecto;

    public TipoAnteproyecto() {
    }

    public TipoAnteproyecto(int idTipoAnteproyecto, String tipoAnteproyecto) {
        this.idTipoAnteproyecto = idTipoAnteproyecto;
        this.tipoAnteproyecto = tipoAnteproyecto;
    }

    public int getIdTipoAnteproyecto() {
        return idTipoAnteproyecto;
    }

    public String getTipoAnteproyecto() {
        return tipoAnteproyecto;
    }

    public void setIdTipoAnteproyecto(int idTipoAnteproyecto) {
        this.idTipoAnteproyecto = idTipoAnteproyecto;
    }

    public void setTipoAnteproyecto(String tipoAnteproyecto) {
        this.tipoAnteproyecto = tipoAnteproyecto;
    }

    @Override
    public String toString() {
        return tipoAnteproyecto;
    }
    
    
}
