/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 23/05/2023
*Fecha de modificación: 23/05/2023
*Descripción: Controlador de la vista de Validación 
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.pojo.Anteproyecto;


public class FXMLValidacionAnteproyectoController implements Initializable {

    private Anteproyecto anteproyectoValidacion;
    
    @FXML
    private TextArea txtAreaDescripcion;
    @FXML
    private Label lblLGAC;
    @FXML
    private Label lblTipoAnteproyecto;
    @FXML
    private Label lblNombreDocumento;
    @FXML
    private Label lblFechaCreacion;
    @FXML
    private Label lblCodirectores;
    @FXML
    private Label lblNoEstudiantesMaximo;
    @FXML
    private Label lblCuerpoAcademico;
    @FXML
    private Label lblNombreDirector;
    @FXML
    private Label lblNombreAnteproyecto;

        @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicDescargarDocumento(ActionEvent event) {
    }

    @FXML
    private void clicDevolverParaCorreccion(ActionEvent event) {
    }

    @FXML
    private void clicAprobarParaPublicar(ActionEvent event) {
        
    }
    
    public void inicializarInformacion (Anteproyecto anteproyectoValidacion){
        this.anteproyectoValidacion = anteproyectoValidacion;
        cargarDatos();
    }
    
    private void cargarDatos(){
        lblNombreAnteproyecto.setText(anteproyectoValidacion.getTitulo());
        txtAreaDescripcion.setText(anteproyectoValidacion.getDescripcion());
        lblNombreDirector.setText(anteproyectoValidacion.getNombreDirector());
        lblCodirectores.setText(anteproyectoValidacion.getNombreCodirectores());
        lblCuerpoAcademico.setText(anteproyectoValidacion.getNombreCuerpoAcademico());
        lblLGAC.setText(anteproyectoValidacion.getNombreLGAC());
        lblTipoAnteproyecto.setText(anteproyectoValidacion.getTipoAnteproyecto());
        lblNoEstudiantesMaximo.setText(""+anteproyectoValidacion.getNoEstudiantesMaximo());
        lblNombreDocumento.setText("Documento jiji");
        lblFechaCreacion.setText(anteproyectoValidacion.getFechaCreacion());
    }

    @FXML
    private void clicRegresar(MouseEvent event) {
        Stage escenarioBase = (Stage) lblNombreAnteproyecto.getScene().getWindow();
        escenarioBase.close();
    }
}
