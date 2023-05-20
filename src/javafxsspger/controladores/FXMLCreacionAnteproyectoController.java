/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 05/05/2023
*Descripción: Controlador de la vista de la creación y edición de los anteproyectos
*/
package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.LGAC;


public class FXMLCreacionAnteproyectoController implements Initializable {

    @FXML
    private TextField txtFieldEstudiantesMaximos;
    @FXML
    private ComboBox<String> cmbBoxTipoAnteproyecto;
    @FXML
    private ComboBox<LGAC> cmbBoxLGAC;
    @FXML
    private Button btnGuardarCambios;
    @FXML
    private Label lblNombreDocumento;
    @FXML
    private TextArea txtAreaNombreAnteproyecto;
    @FXML
    private TextArea txtAreaDescripcionAnteproyecto;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void inicializarCodirectores (Academico academicoCreacion){
        
    }

    @FXML
    private void clicGuardarCambios(ActionEvent event) {
    }

    @FXML
    private void clicAdjuntarCambios(ActionEvent event) {
    }
    
}
