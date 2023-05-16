/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 14/05/2023
*Descripción: Controlador de la vista del menú principal del director
*/
package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Usuario;
import javafxsspger.utils.Utilidades;

public class FXMLMenuPrincipalAcademicoController implements Initializable {
    
    private Academico usuarioAcademico;
    
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblNombreDirector;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        escenarioBase.setScene(Utilidades.inicializarEscena("vistas/FXMLAnteproyectos.fxml"));
        escenarioBase.setTitle("Anteproyectos");
        escenarioBase.show();
    }

    @FXML
    private void clicAsignarEstudiante(ActionEvent event) {
    }

    @FXML
    private void clicAvances(ActionEvent event) {
    }
    
    @FXML
    private void clicGenerarReporte(ActionEvent event) {
    }
    
    public void inicializarInformacion(Usuario usuarioLogin){
        usuarioAcademico = new Academico(usuarioLogin.getIdUsuario(), usuarioLogin.getNombre(), usuarioLogin.getIdTipoUsuario());
        lblNombreDirector.setText("Hola académico " + usuarioAcademico.getNombre());
    }
}
