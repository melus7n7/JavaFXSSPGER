/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 03/06/2023
*Fecha de modificación: 06/06/2023
*Descripción: Controlador de la vista de la calificación del avance
*/
package javafxsspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import javafxsspger.modelo.pojo.Avance;
import javafxsspger.modelo.pojo.Calificacion;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;


public class FXMLCalificacionAvanceController implements Initializable {

    private Academico usuarioAcademico;
    private Avance avanceACalificar;
    private ObservableList<Calificacion> calificaciones;
    private INotificacionAvances notificacionAvances;
    
    private String estiloError = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    private String estiloNormal = "-fx-border-width: 0;";
    
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblDescripcion;
    @FXML
    private Label lblCalificacion;
    @FXML
    private ComboBox<Calificacion> cmbNivelSatisfaccion;
    @FXML
    private Label lblFechaInicio;
    @FXML
    private Label lblFechaFin;
    @FXML
    private VBox vBoxListaActividades;
    @FXML
    private TextArea txtAreaRetroalimentacion;
    @FXML
    private Button bttCalificar;
    @FXML
    private ScrollPane scrPaneContenedorActividades;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    @FXML
    private void clicCalificarAvance(ActionEvent event) {
        validarCampos();
    }
    
    @FXML
    private void clicRegresar(MouseEvent event) {
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLDetalleAvance.fxml"));
            Parent vista = accesoControlador.load();
            FXMLDetalleAvanceController detalleAvance = accesoControlador.getController();
            detalleAvance.inicializarDetalleAvanceAcademico(avanceACalificar.getIdAvance(),usuarioAcademico, notificacionAvances);
            
            Stage escenarioBase = (Stage) lblCalificacion.getScene().getWindow();
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Detalles avance");
            escenarioBase.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public void inicializarPantalla(Avance avance, Academico usuarioAcademico, INotificacionAvances notificacionAvances){
        this.notificacionAvances = notificacionAvances;
        this.avanceACalificar = avance;
        this.usuarioAcademico = usuarioAcademico;
        lblTitulo.setText(avanceACalificar.getTitulo());
        lblDescripcion.setText(avanceACalificar.getDescripcion());
        lblFechaInicio.setText(Utilidades.darFormatofechas(avanceACalificar.getFechaInicio()));
        lblFechaFin.setText(Utilidades.darFormatofechas(avanceACalificar.getFechaFin()));
        txtAreaRetroalimentacion.setText(avanceACalificar.getRetroalimentacion());
        cargarActividades();
        cargarRubricaCalificaciones();
    }
    
    private void cargarActividades(){
        int altoVBox = 0;
        for (int i=0; i<avanceACalificar.getActividades().size(); i++){
            try{
                FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLActividadEnAvanceElemento.fxml"));
                Pane pane = accesoControlador.load();
                FXMLActividadEnAvanceElementoController actividadElementoController = accesoControlador.getController();
                actividadElementoController.incializarElementoParaCalificacion(avanceACalificar.getActividades().get(i));
                
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
    
    private void cargarRubricaCalificaciones(){
        calificaciones = FXCollections.observableArrayList();
        calificaciones.addAll(Utilidades.obtenerRubricaCalificacion());
        cmbNivelSatisfaccion.setItems(calificaciones);
        if(avanceACalificar.getIdRubrica() == 0){
            avanceACalificar.setIdRubrica(Constantes.ID_CALIFICACION_PENDIENTE);
        }
        lblCalificacion.setText(avanceACalificar.getPuntajeSatisfaccion() + "");
        int posicionCalificacion = obtenerPosicionComboCalificacion(avanceACalificar.getIdRubrica());
        cmbNivelSatisfaccion.getSelectionModel().select(posicionCalificacion);
        cmbNivelSatisfaccion.valueProperty().addListener(new ChangeListener<Calificacion>() {
            @Override
            public void changed(ObservableValue<? extends Calificacion> observable, Calificacion oldValue, Calificacion newValue) {
                if (newValue != null){
                    avanceACalificar.setIdRubrica(newValue.getIdRubricaCalificacion());
                    lblCalificacion.setText(newValue.getPuntajeCalificacion() + "");
                }
            }
        });
    }
        
    private int obtenerPosicionComboCalificacion(int idCalificacion){
        for(int i = 0; i < calificaciones.size() ; i++){
            if(calificaciones.get(i).getIdRubricaCalificacion()== idCalificacion){
                return i;
            }
        }
        return 0;
    }
    
    private void validarCampos(){
        boolean camposCompletos = true;
        cmbNivelSatisfaccion.setStyle(estiloNormal);
        lblCalificacion.setStyle(estiloNormal + ";-fx-background-color: white;");
        
        Calificacion calificacion = cmbNivelSatisfaccion.getSelectionModel().getSelectedItem();
        String retroalimentacion = txtAreaRetroalimentacion.getText().replaceAll("\n", System.getProperty("line.separator"));
        if(retroalimentacion.isEmpty()){
            txtAreaRetroalimentacion.setStyle(estiloError);
            camposCompletos = false;
        }
        
        if(camposCompletos){
            Avance avanceValidado = new Avance();
            avanceValidado.setIdAvance(avanceACalificar.getIdAvance());
            avanceValidado.setIdRubrica(calificacion.getIdRubricaCalificacion());
            avanceValidado.setRetroalimentacion(retroalimentacion);
            
            guardarCalificacion(avanceValidado);
        }else{
            Utilidades.mostrarDialogoSimple("Retroalimentación no especificada", 
                "El avance no ha sido retroalimentado. Debe retroalimentarlo para continuar", Alert.AlertType.ERROR);
        }
        
    }

    private void guardarCalificacion(Avance avanceValidado){
        int respuesta = AvanceDAO.guardarCalificacion(avanceValidado);
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
                Utilidades.mostrarDialogoSimple("Avance Calificado", 
                            "Se ha calificado el avance exitosamente", Alert.AlertType.INFORMATION);
                Stage escenarioBase = (Stage) lblCalificacion.getScene().getWindow();
                escenarioBase.close();
                break;
        }
    }
    
}
