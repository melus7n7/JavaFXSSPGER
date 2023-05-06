/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 05/05/2023
*Descripción: Controlador de la vista del menú principal del director
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
import javafxsspger.modelo.pojo.Director;
import javafxsspger.modelo.pojo.Usuario;
import javafxsspger.utils.Utilidades;

public class FXMLMenuPrincipalDirectorController implements Initializable {

    @FXML
    private Label lblTitulo;
    private static Usuario usuarioDirector;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUsuarioDirector();
    }    

    @FXML
    private void clicAnteproyectosDesarrollo(ActionEvent event) {
    }

    @FXML
    private void clicActividades(ActionEvent event) {
    }

    @FXML
    private void clicCronogramaActividades(ActionEvent event) {
    }

    @FXML
    private void clicAnteproyectos(ActionEvent event) {
        //Display Anteproyectos Publicados
        Stage escenarioBase = (Stage) lblTitulo.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializarEscena("vistas/FXMLAnteproyectosPublicados.fxml"));
        escenarioBase.setTitle("Anteproyectos Publicados");
        escenarioBase.show();
    }

    @FXML
    private void clicAsignarEstudiante(ActionEvent event) {
    }

    @FXML
    private void clicAvances(ActionEvent event) {
    }
    
    private static Usuario getUsuarioDirector (){
        return usuarioDirector;
    }
    
    private static void setUsuarioDirector (){
        //Causa si se ocupa director
        //Haga una consulta a la base de datos para recuperar los datos del director específicamente
        usuarioDirector =  FXMLInicioSesionController.getUsuarioLogin();
    }
    
}
