/*
*Autor: Montiel Salas Jesús Jacob
*Fecha de creación: 20/05/2023
*Fecha de modificación: 30/05/2023
*Descripción: Controlador de la vista para la Creacion de Actividades para el Estudiante
*/
package javafxsspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import static javafxsspger.modelo.dao.ActividadDAO.guardarActividad;
import static javafxsspger.modelo.dao.ActividadDAO.modificarActividad;
import javafxsspger.modelo.pojo.Actividad;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;

public class FXMLCreacionActividadController implements Initializable {
    
    
    private boolean esEdicion;
    private Estudiante usuarioEstudiante;
    private Actividad actividad;
    @FXML
    private Label lblTitulo;
    @FXML
    private TextArea txtAreaDescripcionActividad;
    @FXML
    private TextArea txtAreaTituloActividad;
    @FXML
    private DatePicker dtPickerFechaInicio;
    @FXML
    private DatePicker dtPickerFechaFin;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dtPickerFechaFin.setEditable(false);
        dtPickerFechaInicio.setEditable(false);
    }   
    
    public void inicializarInformacionEstudiante(Estudiante usuarioEstudiante, boolean esEdicion, Actividad actividadEdicion){
        this.usuarioEstudiante = usuarioEstudiante;
        this.esEdicion = esEdicion;
        this.actividad = actividadEdicion;
        dtPickerFechaFin.setEditable(false);
        dtPickerFechaInicio.setEditable(false);
        if(esEdicion){
            inicializarActividadEdicion();
        }
    }
    
    private void inicializarActividadEdicion(){
        txtAreaTituloActividad.setText(actividad.getTitulo());
        txtAreaDescripcionActividad.setText(actividad.getDescripcion());
        String fechaInicioString = actividad.getFechaInicio();
        LocalDate fechaI = LocalDate.parse(fechaInicioString);
        dtPickerFechaInicio.setValue(fechaI);
        String fechaFinalString = actividad.getFechaFinal();
        LocalDate fechaF = LocalDate.parse(fechaFinalString);
        dtPickerFechaFin.setValue(fechaF);
        lblTitulo.setText("Modificacíon de Actividad");
    }

    @FXML
    private void clicRegresarActividades(MouseEvent event) {
        cerrarVentana();
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage)lblTitulo.getScene().getWindow();
        try {            
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLActividades.fxml"));
            Parent vista = accesoControlador.load();
            FXMLActividadesController Actividades = accesoControlador.getController();
            Actividades.inicializarInformacionEstudiante(usuarioEstudiante); 
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Actividades");
            escenarioBase.show();            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicGuardarActividad(ActionEvent event) {
        validarCamposRegistro();
    
    }
    
    private void validarCamposRegistro(){
        if(txtAreaTituloActividad.getText().isEmpty() || txtAreaDescripcionActividad.getText().isEmpty() || dtPickerFechaInicio.getValue() == null || dtPickerFechaFin.getValue() == null ){
            Utilidades.mostrarDialogoSimple("Campos invalidos","Error. Hay campos inválidos. Complételos o cámbielos para continuar", Alert.AlertType.ERROR);
        }else{
            String titulo = txtAreaTituloActividad.getText();
            String descripcion = txtAreaDescripcionActividad.getText();
            String fechaInicio = dtPickerFechaInicio.getValue().toString();
            String fechaFinal = dtPickerFechaFin.getValue().toString();
            int idEstudiante = usuarioEstudiante.getIdEstudiante(); 
            int idTrabajoRecepcional = usuarioEstudiante.getIdTrabajoRecepcional(); 
            
                LocalDate fechaI = dtPickerFechaInicio.getValue();
                LocalDate fechaF = dtPickerFechaFin.getValue();
                Actividad actividadValidada = new Actividad();
                if(esEdicion){
                    actividadValidada = actividad;
                }
                actividadValidada.setTitulo(titulo);
                actividadValidada.setDescripcion(descripcion);
                actividadValidada.setFechaInicio(fechaInicio);
                actividadValidada.setFechaFinal(fechaFinal);
                actividadValidada.setIdEstudiante(idEstudiante); 
                actividadValidada.setIdTrabajoRecepcional(idTrabajoRecepcional);
                if (fechaI.compareTo(fechaF) > 0) {
                    Utilidades.mostrarDialogoSimple("Fechas Invalidas", "La fecha ingresada no es válida. Ingrese una nueva fecha.", Alert.AlertType.WARNING);
                }else {
                    if(esEdicion){  
                        actualizarActividad(actividadValidada);
                    }else{
                        registrarActividad(actividadValidada);
                    }
                }
            
        }
    }
    
    private void actualizarActividad(Actividad actividadActualizada){
        int codigoRespuesta = modificarActividad(actividadActualizada);                
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion", "No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error cargar los datos", "Intentelo despues", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Actividad actualizada", "La actividad fue actualizada correctamente", Alert.AlertType.INFORMATION);        
                cerrarVentana();
                break;
        }    
    }
    
    private void registrarActividad(Actividad actividadNueva){
        int codigoRespuesta = guardarActividad(actividadNueva);                
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion", "No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error cargar los datos", "Intentelo mas tarde", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Actividad agregada", "La actividad ha sido añadida correctamente", Alert.AlertType.INFORMATION);        
                cerrarVentana();
                break;
        }
    }
    
}
