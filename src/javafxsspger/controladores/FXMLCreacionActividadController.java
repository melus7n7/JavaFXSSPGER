/*
*Autor: Montiel Salas Jesús Jacob
*Fecha de creación: 20/05/2023
*Fecha de modificación: 21/05/2023
*Descripción: Controlador de la vista para la Creacion de Actividades para el Estudiante
*/
package javafxsspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

/**
 * FXML Controller class
 *
 * @author monti
 */
public class FXMLCreacionActividadController implements Initializable {
    
    private boolean esEdicion;
    private Estudiante usuarioEstudiante;
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    public void inicializarInformacion(Estudiante usuarioEstudiante){
        this.usuarioEstudiante=usuarioEstudiante;
    }

    @FXML
    private void clicRegresarActividades(MouseEvent event) {
        cerrarVentana();
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage)lblTitulo.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLActividadesEstudiante.fxml"));
            Parent vista = accesoControlador.load();
            FXMLActividadesEstudianteController creacionActividad = accesoControlador.getController();
            //creacionActividad.inicializarInformacion(usuarioEstudiante);
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Actividades");
            escenarioBase.show();;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicGuardarActividad(ActionEvent event) {
        validarCamposRegistro();
    }
    
    private void validarCamposRegistro(){
        if(txtAreaTituloActividad.getText().isEmpty() || txtAreaDescripcionActividad.getText().isEmpty() || dtPickerFechaInicio.getValue()==null || dtPickerFechaFin.getValue()==null ){
            Utilidades.mostrarDialogoSimple("Campos invalidos","Error. Hay campos inválidos. Complételos o cámbielos para continuar", Alert.AlertType.ERROR);
        }else{
            String titulo = txtAreaTituloActividad.getText();
            String descripcion = txtAreaDescripcionActividad.getText();
            String fechaInicio = dtPickerFechaInicio.getValue().toString();
            String fechaFinal = dtPickerFechaFin.getValue().toString();
            //int idEstudiante=estudiante.getIdEstudiante();
            //int idTrabajoRecepcional=estudiante.getIdTrabajoRecepcional();
            try{
                SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-dd-MM");
                Date fechaI = null;
                fechaI = formatoFecha.parse(fechaInicio);
                Date fechaF = null;
                fechaF = formatoFecha.parse(fechaFinal);
                
                Actividad actividadValidada = new Actividad();
                actividadValidada.setTitulo(titulo);
                actividadValidada.setDescripcion(descripcion);
                actividadValidada.setFechaInicio(fechaInicio);
                actividadValidada.setFechaFinal(fechaFinal);
                actividadValidada.setIdEstudiante(1); //MODIFICAR
                actividadValidada.setIdTrabajoRecepcional(1); //MODIFICAR
                
                if (fechaI.compareTo(fechaF) > 0 || fechaI.compareTo(fechaF)==fechaF.compareTo(fechaI)) {
                    Utilidades.mostrarDialogoSimple("Fechas Invalidas", "La fecha ingresada no es válida. Ingrese una nueva fecha.", Alert.AlertType.WARNING);
                }else {
                    /*
                    if(esEdicion){  
                        //actividadValidada.getIdActividad(actividadEdicion.getIdActividad());
                        actualizarActividad(actividadValidada);
                    }else{
                        registrarActividad(actividadValidada);
                    }
                    */
                    registrarActividad(actividadValidada);
                }
            }catch(ParseException e){
                System.out.println("Error al convertir la fecha.");
            }
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
            Utilidades.mostrarDialogoSimple("Tarifa añadida", "La tarifa fue añadida correctamente", Alert.AlertType.WARNING);        
            cerrarVentana();
            break;
        }
    }
    
}
