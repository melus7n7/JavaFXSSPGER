/*
*Autor: Martínez Aguilar Sulem, Montiel Salas Jesús Jacob
*Fecha de creación: 27/05/2023
*Fecha de modificación: 05/06/2023
*Descripción: Controlador de la vista del menú principal del administrador
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.pojo.Usuario;
import javafxsspger.utils.Utilidades;


public class FXMLMenuPrincipalAdministradorController implements Initializable {

    
    private Usuario usuarioAdmin;
    @FXML
    private Label lblSaludo;
    @FXML
    private Label lblTitulo;
    @FXML
    private Button bttLGAC;
    @FXML
    private Button bttExperienciasEducativas;
    @FXML
    private Button bttCuentasInstitucionales;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void inicializarInformacion(Usuario usuarioAdmin){
        this.usuarioAdmin = usuarioAdmin;
        lblSaludo.setText("Hola administrador");
    }

    @FXML
    private void clicCerrarSesion(MouseEvent event) {
        if(Utilidades.mostrarDialogoConfirmacion("Cerrar Sesión", "¿Desea cerrar la sesión actual?")){
            Stage escenarioBase = (Stage)lblSaludo.getScene().getWindow();
            try {
                FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLInicioSesion.fxml"));
                Parent vista = accesoControlador.load();
                escenarioBase.setScene(new Scene (vista));
                escenarioBase.setTitle("Inicio Sesión");
                escenarioBase.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    private void clicLGAC(ActionEvent event) {
        Stage escenarioBase = (Stage)lblSaludo.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLLGACS.fxml"));
            Parent vista = accesoControlador.load();
            FXMLLGACSController lgac = accesoControlador.getController();
            lgac.inicializarInformacionUsuario(usuarioAdmin); 
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("LGACS");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicExperienciaEducativa(ActionEvent event) {
    }

    @FXML
    private void clicCuerposAcademicos(ActionEvent event) {
        Stage escenarioBase = (Stage)lblSaludo.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLCuerposAcademicos.fxml"));
            Parent vista = accesoControlador.load();
            FXMLCuerposAcademicosController cuerpoAcademico = accesoControlador.getController();
            cuerpoAcademico.inicializarInformacionUsuario(usuarioAdmin); 
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Cuerpos Academicos");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicCuentaInstitucional(ActionEvent event) {
    }
    
}
