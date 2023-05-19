/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 14/05/2023
*Descripción: Controlador de la vista del menú principal del director
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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.dao.AcademicoDAO;
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
        Stage escenarioBase = (Stage) lblTitulo.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLAnteproyectos.fxml"));
            Parent vista = accesoControlador.load();
            FXMLAnteproyectosController anteproyectos = accesoControlador.getController();
            anteproyectos.inicializarInformacion(usuarioAcademico);
            
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Anteproyectos");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
        Academico respuesta = AcademicoDAO.obtenerDetallesAcademico(usuarioLogin.getIdUsuario());
        respuesta.setIdUsuario(usuarioLogin.getIdUsuario());
        respuesta.setNombre(usuarioLogin.getNombre());
        respuesta.setIdTipoUsuario(usuarioLogin.getIdTipoUsuario());
        this.usuarioAcademico = respuesta;
        lblNombreDirector.setText("Hola académico " + usuarioAcademico.getNombre());
    }
    
    public void inicializarInformacionConAcademico(Academico usuarioAcademico){
        this.usuarioAcademico = usuarioAcademico;
        lblNombreDirector.setText("Hola académico " + this.usuarioAcademico.getNombre());
    }
}
