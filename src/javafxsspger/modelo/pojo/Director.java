/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 05/05/2023
*Descripción: POJO del director
*/
package javafxsspger.modelo.pojo;

public class Director extends Usuario{
    
    private int idDirector;
    private String noPersonal;

    public Director(int idUsuario, String nombre, int tipoUsuario) {
        super(idUsuario, nombre, tipoUsuario);
    }
    
    public Director (){
        
    }

    public int getIdDirector() {
        return idDirector;
    }

    public String getNoPersonal() {
        return noPersonal;
    }

    public void setIdDirector(int idDirector) {
        this.idDirector = idDirector;
    }

    public void setNoPersonal(String noPersonal) {
        this.noPersonal = noPersonal;
    }

    

}
