/*
*Autor: Montiel Salas Jesús Jacob
*Fecha de creación: 05/06/2023
*Fecha de modificación: 05/06/2023
*Descripción: POJO de la Entrega de una Actividad
*/
package javafxsspger.modelo.pojo;

public class Entrega {
    
    private int idEntrega;
    private String tituloActividad;
    private String fechaEntrega;
    private String descripcion;
    private String areaConocimiento;
    private String retroalimentacion;
    private int idRubrica;
    private String nivelSatisfaccion;
    private int puntajeSatisfaccion;
    private String nombreEstudiante;
    private String apellidoPaternoEstudiante;
    private String apellidoMaternoEstudiante;
    //private byte[] documento; //modificar
    //private File archivoFoto;
    //private ArrayList<File> Array;

    public Entrega() {
    }

    public Entrega(int idEntrega, String tituloActividad, String fechaEntrega, String descripcion, String areaConocimiento, String retroalimentacion, int idRubrica, String nivelSatisfaccion, int puntajeSatisfaccion, String nombreEstudiante, String apellidoPaternoEstudiante, String apellidoMaternoEstudiante) {
        this.idEntrega = idEntrega;
        this.tituloActividad = tituloActividad;
        this.fechaEntrega = fechaEntrega;
        this.descripcion = descripcion;
        this.areaConocimiento = areaConocimiento;
        this.retroalimentacion = retroalimentacion;
        this.idRubrica = idRubrica;
        this.nivelSatisfaccion = nivelSatisfaccion;
        this.puntajeSatisfaccion = puntajeSatisfaccion;
        this.nombreEstudiante = nombreEstudiante;
        this.apellidoPaternoEstudiante = apellidoPaternoEstudiante;
        this.apellidoMaternoEstudiante = apellidoMaternoEstudiante;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getApellidoPaternoEstudiante() {
        return apellidoPaternoEstudiante;
    }

    public void setApellidoPaternoEstudiante(String apellidoPaternoEstudiante) {
        this.apellidoPaternoEstudiante = apellidoPaternoEstudiante;
    }

    public String getApellidoMaternoEstudiante() {
        return apellidoMaternoEstudiante;
    }

    public void setApellidoMaternoEstudiante(String apellidoMaternoEstudiante) {
        this.apellidoMaternoEstudiante = apellidoMaternoEstudiante;
    }

    

    public String getTituloActividad() {
        return tituloActividad;
    }

    public void setTituloActividad(String tituloActividad) {
        this.tituloActividad = tituloActividad;
    }

    

    public int getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(int idEntrega) {
        this.idEntrega = idEntrega;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAreaConocimiento() {
        return areaConocimiento;
    }

    public void setAreaConocimiento(String areaConocimiento) {
        this.areaConocimiento = areaConocimiento;
    }

    public String getRetroalimentacion() {
        return retroalimentacion;
    }

    public void setRetroalimentacion(String retroalimentacion) {
        this.retroalimentacion = retroalimentacion;
    }

    public int getIdRubrica() {
        return idRubrica;
    }

    public void setIdRubrica(int idRubrica) {
        this.idRubrica = idRubrica;
    }

    public String getNivelSatisfaccion() {
        return nivelSatisfaccion;
    }

    public void setNivelSatisfaccion(String nivelSatisfaccion) {
        this.nivelSatisfaccion = nivelSatisfaccion;
    }

    public int getPuntajeSatisfaccion() {
        return puntajeSatisfaccion;
    }

    public void setPuntajeSatisfaccion(int puntajeSatisfaccion) {
        this.puntajeSatisfaccion = puntajeSatisfaccion;
    }
    
    
    
    
    
}
