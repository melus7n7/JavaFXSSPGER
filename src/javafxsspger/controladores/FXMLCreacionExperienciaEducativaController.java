/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 06/06/2023
*Fecha de modificación: 06/06/2023
*Descripción: Controlador de la vista para la creación de una experiencia educativa
*/
package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author danie
 */
public class FXMLCreacionExperienciaEducativaController implements Initializable {

    @FXML
    private ComboBox<?> cmbBoxExperienciaEducativa;
    @FXML
    private TextArea txtAreaNRCExperienciaEducativa;
    @FXML
    private DatePicker dpPeriodoEscolar;
    @FXML
    private Button btnConfirmar;
    @FXML
    private ComboBox<?> cmbBoxAcademico;
    @FXML
    private ComboBox<?> cmbBoxCoordinadorAcademia;
    @FXML
    private TextArea txtAreaPeriodoEscolar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicRegresar(MouseEvent event) {
    }

    @FXML
    private void clicConfirmar(ActionEvent event) {
    }
    
}
