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
    
    private static Academico usuarioDirector;
    
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblNombreDirector;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUsuarioDirector();
        lblNombreDirector.setText("Hola director " + usuarioDirector.getNombre());
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
    
    private static Academico getUsuarioDirector (){
        return usuarioDirector;
    }
    
    private static void setUsuarioDirector (){
        //Haga una consulta a la base de datos para recuperar los datos del director específicamente
        //Con el Usuario se obtienen sus demás datos de la base de datos
        //Director director = DirectorDAO.obtenerDirector(FXMLInicioSesionController.getUsuarioLogin(););
        Usuario usuarioLogin =  FXMLInicioSesionController.getUsuarioLogin();
        usuarioDirector = new Academico (1, "Nombre", usuarioLogin.getTipoUsuario());
    }

    @FXML
    private void clicGenerarReporte(ActionEvent event) {
    }
    
}
