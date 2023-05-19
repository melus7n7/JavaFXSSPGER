/*
*Autor: Martínez Aguilar Sulem, Montiel Salas Jesús Jacob
*Fecha de creación: 01/05/2023
*Fecha de modificación: 05/05/2023
*Descripción: Controlador de la vista del inicio de sesión
*/
package javafxsspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.dao.UsuarioDAO;
import javafxsspger.modelo.pojo.Usuario;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;


public class FXMLInicioSesionController implements Initializable {

    @FXML
    private TextField txtFieldUsuario;
    @FXML
    private Label lblCamposVacios;
    @FXML
    private PasswordField pssFieldContrasena;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicIngresar(ActionEvent event) {
        lblCamposVacios.setVisible(false);
        String usuario = txtFieldUsuario.getText();
        String password = pssFieldContrasena.getText();
        if(usuario.isEmpty() || password.isEmpty()){
            lblCamposVacios.setVisible(true);
        }else{
            validarCredenciales(usuario, password);
        }
    }
    
    private void validarCredenciales (String usuario, String contrasena){
        Usuario usuarioRespuesta = UsuarioDAO.verificarSesion(usuario, contrasena);
        switch (usuarioRespuesta.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión", 
                        "Por el momento no hay conexión, por favor inténtelo más tarde", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la solicitud", 
                        "Por el momento no se puede procesar la solicitud de verificación", Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                if(usuarioRespuesta.getIdUsuario() > 0){
                    Utilidades.mostrarDialogoSimple("Usuario verificado",
                            "Bienvenido(a) " +usuarioRespuesta.getNombre()+" al sistema ...", Alert.AlertType.INFORMATION);
                    irPantalla(usuarioRespuesta);
                }else{
                    Utilidades.mostrarDialogoSimple("Credenciales incorrectas",
                            "El usuario y/o constraseña no son correctos, por favor verifica la información", Alert.AlertType.WARNING);
                }
                break;
            default:
                Utilidades.mostrarDialogoSimple("Error",
                            "El sistema no está disponible por el momento", Alert.AlertType.ERROR);
                
        }
        
    }
    
    private void irPantalla (Usuario usuarioLogin){
        switch (usuarioLogin.getIdTipoUsuario()){
                case Constantes.ACADEMICO:
                    irPantallaAcademico(usuarioLogin);
                    break;
                case Constantes.ESTUDIANTE:
                    break;
                case Constantes.ADMINISTRADOR:
                    break;
                default:
                    System.out.print ("ERROR");
        }
    }
    
    private void irPantallaAcademico(Usuario usuarioLogin){
        Stage escenarioBase = (Stage) txtFieldUsuario.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLMenuPrincipalAcademico.fxml"));
            Parent vista = accesoControlador.load();
            FXMLMenuPrincipalAcademicoController menuPrincipalAcademico = accesoControlador.getController();
            menuPrincipalAcademico.inicializarInformacion(usuarioLogin);
            
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Menu Principal");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    
    
}
