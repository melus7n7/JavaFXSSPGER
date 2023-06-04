/*
*Autor: Montiel Salas Jesús Jacob
*Fecha de creación: 03/06/2023
*Fecha de modificación: 03/06/2023
*Descripción: Controlador de la vista de la entrega de una actividad
*/
package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author monti
 */
public class FXMLDetallesEntregaController implements Initializable {

    @FXML
    private Label idTitulo;
    @FXML
    private TextArea txtAreaDescripcion;
    @FXML
    private TextArea txtAreaRetroalimentacion;
    @FXML
    private Button bttEntregar;
    @FXML
    private Button bttEliminarEntrega;
    @FXML
    private Button bttCalificarEntregar;
    @FXML
    private Button bttEliminarCalificacion;

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
    private void clicEditar(ActionEvent event) {
    }
    
}
