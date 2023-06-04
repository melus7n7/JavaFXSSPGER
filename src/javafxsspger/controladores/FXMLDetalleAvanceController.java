/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 03/06/2023
*Fecha de modificación: 03/06/2023
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
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

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void clicEditar(ActionEvent event) {
    }

    @FXML
    private void clicRegresar(MouseEvent event) {
        Stage escenarioBase = (Stage) lblCalificacion.getScene().getWindow();
        escenarioBase.close();
    }

    @FXML
    private void clicEliminarAvance(ActionEvent event) {
    }

    @FXML
    private void clicCalificarAvance(ActionEvent event) {
    }
    
    public void inicializarDetalleAvanceAcademico(int idAvance, Academico usuarioAcademico){
        this.usuarioAcademico = usuarioAcademico;
        this.esAcademico = true;
        recuperarDetallesAvance(idAvance);
        bttEliminar.setVisible(false);
        bttEditar.setVisible(false);
    }
    
    public void inicializarDetalleAvanceEstudiante(int idAvance, Estudiante usuarioEstudiante){
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
        lblTitulo.setText(avanceDetalle.getTitulo());
        lblDescripcion.setText(avanceDetalle.getDescripcion());
        lblCalificacion.setText(avanceDetalle.getPuntajeSatisfaccion()+"");
        lblNivelSatisfaccion.setText(avanceDetalle.getNivelSatisfaccion());
        lblFechaInicio.setText(avanceDetalle.getFechaInicio());
        lblFechaFin.setText(avanceDetalle.getFechaFin());
        calcularPorcentajeCompletado();
        cargarActividades();
    }
    
    private void calcularPorcentajeCompletado(){
        if(!avanceDetalle.getActividades().isEmpty()){
            int actividadesCumplidas = 0;
            for(Actividad actividad: avanceDetalle.getActividades()){
                actividadesCumplidas += (actividad.isTieneEntrega()) ? 1 : 0;
            }
            String porcentaje = actividadesCumplidas/avanceDetalle.getActividades().size() + "";
            lblPorcentaje.setText(porcentaje + "%");
        }else{
            lblPorcentaje.setText("No hay actividades ...");
        }
    }
    
    private void cargarActividades(){
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
                vBoxListaActividades.getChildren().add(pane);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
