/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 06/06/2023
*Fecha de modificación: 06/06/2023
*Descripción: POJO de una Experiencia Educativa
*/
package javafxsspger.modelo.pojo;

/**
 *
 * @author danie
 */
public class ExperienciaEducativa {
    private int idExperienciaEducativa;
    private String NRC;
    private String fechaComienzo;
    private String fechaFin;
    private String nombrePeriodoEscolar;
    private int idMateria;
    private int idCoordinadorAcademia;
    private int idAcademico;

    public ExperienciaEducativa(int idExperienciaEducativa, String NRC, String fechaComienzo, String fechaFin, String nombrePeriodoEscolar, int idMateria, int idCoordinadorAcademia, int idAcademico) {
        this.idExperienciaEducativa = idExperienciaEducativa;
        this.NRC = NRC;
        this.fechaComienzo = fechaComienzo;
        this.fechaFin = fechaFin;
        this.nombrePeriodoEscolar = nombrePeriodoEscolar;
        this.idMateria = idMateria;
        this.idCoordinadorAcademia = idCoordinadorAcademia;
        this.idAcademico = idAcademico;
    }

    public ExperienciaEducativa() {
    }
    
    public int getIdExperienciaEducativa() {
        return idExperienciaEducativa;
    }

    public void setIdExperienciaEducativa(int idExperienciaEducativa) {
        this.idExperienciaEducativa = idExperienciaEducativa;
    }

    public String getNRC() {
        return NRC;
    }

    public void setNRC(String NRC) {
        this.NRC = NRC;
    }

    public String getFechaComienzo() {
        return fechaComienzo;
    }

    public void setFechaComienzo(String fechaComienzo) {
        this.fechaComienzo = fechaComienzo;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getNombrePeriodoEscolar() {
        return nombrePeriodoEscolar;
    }

    public void setNombrePeriodoEscolar(String nombrePeriodoEscolar) {
        this.nombrePeriodoEscolar = nombrePeriodoEscolar;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public int getIdCoordinadorAcademia() {
        return idCoordinadorAcademia;
    }

    public void setIdCoordinadorAcademia(int idCoordinadorAcademia) {
        this.idCoordinadorAcademia = idCoordinadorAcademia;
    }

    public int getIdAcademico() {
        return idAcademico;
    }

    public void setIdAcademico(int idAcademico) {
        this.idAcademico = idAcademico;
    }
}
