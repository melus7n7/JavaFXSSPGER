/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 16/05/2023
*Descripción: POJO del Estudiante
*/
package javafxsspger.modelo.pojo;

public class Estudiante extends Usuario{
    private int idEstudiante;
    private String matricula;
    private int idAnteproyecto;
    private int idTrabajoRecepcional;

    public Estudiante() {
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public String getMatricula() {
        return matricula;
    }

    public int getIdAnteproyecto() {
        return idAnteproyecto;
    }

    public int getIdTrabajoRecepcional() {
        return idTrabajoRecepcional;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setIdAnteproyecto(int idAnteproyecto) {
        this.idAnteproyecto = idAnteproyecto;
    }

    public void setIdTrabajoRecepcional(int idTrabajoRecepcional) {
        this.idTrabajoRecepcional = idTrabajoRecepcional;
    }
    
}
