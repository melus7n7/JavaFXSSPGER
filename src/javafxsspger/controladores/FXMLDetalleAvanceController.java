/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 03/06/2023
*Fecha de modificación: 04/06/2023
*Descripción: Controlador de la vista del detalle de un avance
*/
package javafxsspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.interfaces.INotificacionAvances;
import javafxsspger.modelo.dao.AvanceDAO;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Actividad;
import javafxsspger.modelo.pojo.Avance;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;


public class FXMLDetalleAvanceController implements Initializable {

    private Avance avanceDetalle;
    private Estudiante usuarioEstudiante;
    private Academico usuarioAcademico;
    private boolean esAcademico;
    private INotificacionAvances notificacion;
            
    @FXML
    private Button bttEditar;
    @FXML
    private Label lblFechaInicio;
    @FXML
    private Label lblFechaEtiqueta;
    @FXML
    private Label lblCalificacion;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblFechaEtiqueta1;
    @FXML
    private Label lblFechaFin;
    @FXML
    private Label lblDescripcion;
    @FXML
    private Label lblPorcentaje;
    @FXML
    private VBox vBoxListaActividades;
    @FXML
    private Button bttEliminar;
    @FXML
    private Label lblNivelSatisfaccion;
    @FXML
    private Button bttCalificar;
    @FXML
    private ScrollPane scrPaneContenedorActividades;
    @FXML
    private TextArea txtAreaRetroalimentacion;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    private void clicEditar(ActionEvent event) {
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLCreacionAvance.fxml"));
            Parent vista = accesoControlador.load();
            FXMLCreacionAvanceController creacionAvance = accesoControlador.getController(); 
            creacionAvance.incializarPantallaEdicion(usuarioEstudiante, avanceDetalle, notificacion);
            
            Stage escenarioDetalle = (Stage) lblCalificacion.getScene().getWindow();
            escenarioDetalle.setScene(new Scene (vista));
            escenarioDetalle.setTitle("Modificación avance");
            escenarioDetalle.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicRegresar(MouseEvent event) {
        cerrarVentana();
    }

    @FXML
    private void clicEliminarAvance(ActionEvent event) {
        eliminarAvance();
        cerrarVentana();
    }

    @FXML
    private void clicCalificarAvance(ActionEvent event) {
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLCalificacionAvance.fxml"));
            Parent vista = accesoControlador.load();
            FXMLCalificacionAvanceController calificacionAvance = accesoControlador.getController(); 
            calificacionAvance.inicializarPantalla(avanceDetalle, usuarioAcademico);
            
            Stage escenarioDetalle = (Stage) lblCalificacion.getScene().getWindow();
            escenarioDetalle.setScene(new Scene (vista));
            escenarioDetalle.setTitle("Calificacion avance");
            escenarioDetalle.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void inicializarDetalleAvanceAcademico(int idAvance, Academico usuarioAcademico){
        this.usuarioAcademico = usuarioAcademico;
        this.esAcademico = true;
        recuperarDetallesAvance(idAvance);
        bttEliminar.setVisible(false);
        bttEditar.setVisible(false);
    }
    
    public void inicializarDetalleAvanceEstudiante(int idAvance, Estudiante usuarioEstudiante, INotificacionAvances notificacion){
        this.notificacion = notificacion;
        this.esAcademico = false;
        this.usuarioEstudiante = usuarioEstudiante;
        recuperarDetallesAvance(idAvance);
        bttCalificar.setVisible(false);
    }
    
    private void recuperarDetallesAvance(int idAvance){
        Avance respuesta = AvanceDAO.obtenerAvance(idAvance);
        switch(respuesta.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error de conexión", 
                            "Error en la conexión con la base de datos", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error de consulta", 
                            "Por el momento no se puede obtener información de la base de datos", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                this.avanceDetalle = respuesta;
                mostrarDetalles();
                break;
        }
    }
    
    private void mostrarDetalles(){
        if(esAcademico){
            bttCalificar.setVisible(esDirectorDelAvance());
        }
        lblTitulo.setText(avanceDetalle.getTitulo());
        lblDescripcion.setText(avanceDetalle.getDescripcion());
        lblCalificacion.setText(avanceDetalle.getPuntajeSatisfaccion()+"");
        lblNivelSatisfaccion.setText(avanceDetalle.getNivelSatisfaccion());
        lblFechaInicio.setText(avanceDetalle.getFechaInicio());
        lblFechaFin.setText(avanceDetalle.getFechaFin());
        txtAreaRetroalimentacion.setText(avanceDetalle.getRetroalimentacion());
        if(avanceDetalle.getActividades() != null && !avanceDetalle.getActividades().isEmpty()){
            cargarActividades();
            calcularPorcentajeCompletado();
        }else{
            lblPorcentaje.setText("No hay actividades ...");
            lblFechaInicio.setText("--/--/----");
            lblFechaFin.setText("--/--/----");
        }
        
    }
    
    private void calcularPorcentajeCompletado(){
        int actividadesCumplidas = 0;
        for(Actividad actividad: avanceDetalle.getActividades()){
            actividadesCumplidas += (actividad.isTieneEntrega()) ? 1 : 0;
        }
        String porcentaje = actividadesCumplidas/avanceDetalle.getActividades().size() + "";
        lblPorcentaje.setText(porcentaje + "%");
    }
    
    private boolean esDirectorDelAvance(){
        for(Academico academico: avanceDetalle.getDirectores()){
            if(academico.getIdAcademico() == usuarioAcademico.getIdAcademico()){
                return true;
            }
        }
        return false;
    }
    
    private void cargarActividades(){
        int altoVBox = 0;
        for (int i=0; i<avanceDetalle.getActividades().size(); i++){
            try{
                FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLActividadEnAvanceElemento.fxml"));
                Pane pane = accesoControlador.load();
                FXMLActividadEnAvanceElementoController actividadElementoController = accesoControlador.getController();
                if(esAcademico){
                    actividadElementoController.cargarActividadAcademico(avanceDetalle.getActividades().get(i), usuarioAcademico);
                }else{
                    actividadElementoController.cargarActividadEstudiante(avanceDetalle.getActividades().get(i), usuarioEstudiante);
                }
                altoVBox += pane.getPrefHeight();
                vBoxListaActividades.setPrefHeight(altoVBox);
                vBoxListaActividades.getChildren().add(pane);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        if(vBoxListaActividades.getPrefHeight() < scrPaneContenedorActividades.getPrefHeight()){
            vBoxListaActividades.setPrefHeight(scrPaneContenedorActividades.getPrefHeight());
        }
    }
    
    private void eliminarAvance(){
        int respuesta = AvanceDAO.eliminarAvance(avanceDetalle.getIdAvance());
        switch(respuesta){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error de conexión", 
                            "Error en la conexión con la base de datos", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error de consulta", 
                            "Por el momento no se puede obtener información de la base de datos", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Avance Eliminado", 
                            "Se ha eliminado el avance del sistema", Alert.AlertType.INFORMATION);
                notificacion.notificarCargarAvances();
                break;
        }
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) lblCalificacion.getScene().getWindow();
        escenarioBase.close();
    }
}
