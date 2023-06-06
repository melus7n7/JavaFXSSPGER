/*
*Autor: Martínez Aguilar Sulem, Montiel Salas Jesús Jacob
*Fecha de creación: 20/05/2023
*Fecha de modificación: 20/05/2023
*Descripción: POJO de un cuerpo académico
*/
package javafxsspger.modelo.pojo;

import java.util.ArrayList;

public class CuerpoAcademico {
    
    private int idCuerpoAcademico;
    private String nombre;
    private String claveCuerpoAcademico;
    private String descripcion;
    private String areaConocimiento;
    
    private int idConsolidacion;
    private String nivelConsolidacion;
    private int idAcademico;

    public int getIdAcademico() {
        return idAcademico;
    }

    public void setIdAcademico(int idAcademico) {
        this.idAcademico = idAcademico;
    }
    
    private ArrayList <LGAC> lgacs;
    
    private int codigoRespuesta;

    public CuerpoAcademico() {
    }
    

    public int getIdCuerpoAcademico() {
        return idCuerpoAcademico;
    }

    public String getNombre() {
        return nombre;
    }

    public String getClaveCuerpoAcademico() {
        return claveCuerpoAcademico;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getAreaConocimiento() {
        return areaConocimiento;
    }

    public int getIdConsolidacion() {
        return idConsolidacion;
    }

    public String getNivelConsolidacion() {
        return nivelConsolidacion;
    }

    public ArrayList<LGAC> getLgacs() {
        return lgacs;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setIdCuerpoAcademico(int idCuerpoAcademico) {
        this.idCuerpoAcademico = idCuerpoAcademico;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setClaveCuerpoAcademico(String claveCuerpoAcademico) {
        this.claveCuerpoAcademico = claveCuerpoAcademico;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setAreaConocimiento(String areaConocimiento) {
        this.areaConocimiento = areaConocimiento;
    }

    public void setIdConsolidacion(int idConsolidacion) {
        this.idConsolidacion = idConsolidacion;
    }

    public void setNivelConsolidacion(String nivelConsolidacion) {
        this.nivelConsolidacion = nivelConsolidacion;
    }

    public void setLgacs(ArrayList<LGAC> lgacs) {
        this.lgacs = lgacs;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    @Override
    public String toString() {
        return "[" + claveCuerpoAcademico + "] " + nombre;
    }
    
    
}
