/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 27/05/2023
*Fecha de modificación: 27/05/2023
*Descripción: Controlador de la vista del menú principal del administrador
*/
package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafxsspger.modelo.pojo.Usuario;


public class FXMLMenuPrincipalAdministradorController implements Initializable {

    private Usuario usuarioAdmin;
    
    @FXML
    private Label lblSaludo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void inicializarInformacion(Usuario usuarioAdmin){
        this.usuarioAdmin = usuarioAdmin;
        lblSaludo.setText("Hola administrador " + this.usuarioAdmin.getNombre());
    }
    
}
