/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 05/05/2023
*Descripción: Controlador de la vista del inicio de sesión
*/
package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxsspger.modelo.pojo.Usuario;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;


public class FXMLInicioSesionController implements Initializable {

    @FXML
    private TextField txtFieldUsuario;
    @FXML
    private TextField txtFieldContrasena;
    @FXML
    private Label lblCamposVacios;
    
    private static Usuario usuarioLogin;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicIngresar(ActionEvent event) {
        lblCamposVacios.setVisible(false);
        String usuario = txtFieldUsuario.getText();
        String password = txtFieldContrasena.getText();
        if(usuario.isEmpty() || password.isEmpty()){
            lblCamposVacios.setVisible(true);
        }else{
            validarCredenciales(usuario, password);
        }
    }
    
    private void validarCredenciales (String usuario, String password){
        //TO DO
        usuarioLogin = new Usuario ();
        usuarioLogin.setTipoUsuario(Constantes.DIRECTOR);
        irPantallaPrincipal(usuarioLogin);
        
    }
    
    private void irPantallaPrincipal (Usuario usuarioLogin){
        Stage escenarioBase = (Stage) txtFieldUsuario.getScene().getWindow();
        
        switch (usuarioLogin.getTipoUsuario()){
                case Constantes.DIRECTOR:
                    escenarioBase.setScene(Utilidades.inicializarEscena("vistas/FXMLMenuPrincipalAcademico.fxml"));
                    break;
                default:
                    System.out.print ("ERROR");
        }
        escenarioBase.setTitle("Home");
        escenarioBase.show();
    }
    
    public static Usuario getUsuarioLogin (){
        return usuarioLogin;
    }
    
}
