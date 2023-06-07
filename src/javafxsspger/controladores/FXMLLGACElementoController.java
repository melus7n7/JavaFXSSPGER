/*
*Autor: Montiel Salas Jesús Jacob
*Fecha de creación: 06/06/2023
*Fecha de modificación: 06/06/2023
*Descripción: Controlador de la vista de un elemento en la lista de lgacs
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.interfaces.INotificacionLGAC;
import javafxsspger.modelo.pojo.LGAC;
import javafxsspger.modelo.pojo.Usuario;

public class FXMLLGACElementoController implements Initializable {
    
    
    @FXML
    private CheckBox chcBoxLGAC;
    @FXML
    private Label lblNombreLGAC;
    private Usuario usuarioAdmin;
    private LGAC lgacElemento;
    private INotificacionLGAC interfazNotificacion;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void inicializarElementoLGAC(LGAC lgacElemento, Usuario usuarioAdmin){
        this.lgacElemento = lgacElemento;
        this.usuarioAdmin = usuarioAdmin;
        lblNombreLGAC.setText(lgacElemento.getNombreLGAC());
        
    }

    public void setElementos (LGAC lgac, INotificacionLGAC interfazNotificacion, boolean estaSeleccionado){
        this.interfazNotificacion = interfazNotificacion;
        this.lgacElemento = lgac;
        lblNombreLGAC.setText(lgac.getNombreLGAC());
        chcBoxLGAC.setSelected(estaSeleccionado);
    }
    
    @FXML
    private void clicSeleccionarLGAC(ActionEvent event) {
        if(chcBoxLGAC.isSelected()){
            interfazNotificacion.notificarAñadirLGAC(lgacElemento);
        }else{
            interfazNotificacion.notificarEliminarLGAC(lgacElemento);
        }
    }

}
