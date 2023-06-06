/*
*Autor: Montiel Salas Jesús Jacob
*Fecha de creación: 06/06/2023
*Fecha de modificación: 06/06/2023
*Descripción: Controlador de la vista de un elemento en la lista de LGACs
*/
package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafxsspger.interfaces.INotificacionLGAC;
import javafxsspger.modelo.pojo.LGAC;

/**
 * FXML Controller class
 *
 * @author monti
 */
public class FXMLLGACElementoController implements Initializable {

    private LGAC lgacElemento;
    private INotificacionLGAC interfazNotificacion;
    
    @FXML
    private CheckBox chcBoxLGAC;
    @FXML
    private Label lblNombreLGAC;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
