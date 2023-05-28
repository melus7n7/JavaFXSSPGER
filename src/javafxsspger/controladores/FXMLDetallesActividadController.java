/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxsspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Actividad;
import javafxsspger.modelo.pojo.Estudiante;

/**
 * FXML Controller class
 *
 * @author monti
 */
public class FXMLDetallesActividadController implements Initializable {

    private Academico usuarioAcademico;
    private Actividad actividad;
    @FXML
    private TextArea txtAreaActividad;
    @FXML
    private Label lblTituloActividad;
    @FXML
    private Label lblNombreEstudiante;
    @FXML
    private Label lblFechaCreacion;
    @FXML
    private Label lblFechaInicio;
    @FXML
    private Label lblFechaFinal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 
    }    

    @FXML
    private void clicRegresarAnteproyectos(MouseEvent event) {
    }
    
    public void inicializarInformacion(Academico usuarioAcademico, Actividad actividad){
       this.usuarioAcademico = usuarioAcademico;
       this.actividad=actividad;
       cargarInformacionActividad();
    }
    
    public void cargarInformacionActividad(){
        lblTituloActividad.setText(actividad.getTitulo());
        lblNombreEstudiante.setText(actividad.getNombreEstudiante()+" "+actividad.getApellidoPaternoEstudiante()+" "+actividad.getApellidoMaternoEstudiante());
        txtAreaActividad.setText(actividad.getDescripcion());
        txtAreaActividad.setEditable(false);
        lblFechaInicio.setText(actividad.getFechaInicio());
        lblFechaFinal.setText(actividad.getFechaFinal());
        lblFechaCreacion.setText(actividad.getFechaFinal());
        
    }

    @FXML
    private void clicVerEntrega(ActionEvent event) {
        
    }
    
    private Estudiante usuarioEstudiante;//MODIFICAR
    @FXML
    private void clicEditar(ActionEvent event) {
        Stage escenarioBase = (Stage)lblTituloActividad.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLCreacionActividad.fxml"));
            Parent vista = accesoControlador.load();
            FXMLCreacionActividadController creacionActividad = accesoControlador.getController();
            boolean esEdicion=true;        
            creacionActividad.inicializarInformacion(usuarioEstudiante, esEdicion, actividad);
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Creacion de Actividad");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
