/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 20/05/2023
*Descripción: Controlador de la vista de un codirector para colocarlo en una lista
*/
package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafxsspger.modelo.pojo.Academico;


public class FXMLCodirectorElementoController implements Initializable {

    private Academico academicoElemento;
    
    @FXML
    private Label lblNombreCodirector;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setElementos (Academico academico){
        this.academicoElemento = academico;
        lblNombreCodirector.setText(academico.getNombreCompleto());
    }

    @FXML
    private void clicSeleccionarComoCodirector(ActionEvent event) {
    }
    
}
