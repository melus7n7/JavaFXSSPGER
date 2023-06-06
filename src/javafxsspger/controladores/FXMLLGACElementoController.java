/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 06/06/2023
*Fecha de modificación: 06/06/2023
*Descripción: Controlador de la vista de un elemento en la lista de LGACS
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

/**
 * FXML Controller class
 *
 * @author danie
 */
public class FXMLLGACElementoController implements Initializable {
    
    @FXML
    private CheckBox chcBoxLGAC;
    @FXML
    private Label lblNombreLGAC;
    @FXML
    private Label lblDescripcion;
    
    private Usuario usuarioAdmin;
    private LGAC lgacElemento;
    private INotificacionLGAC interfazNotificacion;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void inicializarElementoLGAC(LGAC lgacElemento, Usuario usuarioAdmin){
        this.lgacElemento=lgacElemento;
        this.usuarioAdmin=usuarioAdmin;
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

    @FXML
    private void clicModificar(ActionEvent event) {
        Stage escenarioBase = (Stage)lblNombreLGAC.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLCreacionLGAC.fxml"));
            Parent vista = accesoControlador.load();
            FXMLCreacionLGACController lgac = accesoControlador.getController();
            lgac.inicializarInformacion(usuarioAdmin);
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("");
            escenarioBase.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
