/*
*Autor: Montiel Salas Jesús Jacob
*Fecha de creación: 20/05/2023
*Fecha de modificación: 21/05/2023
*Descripción: Controlador de la vista de Actividades para el Estudiante
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Estudiante;

/**
 * FXML Controller class
 *
 * @author monti
 */
public class FXMLActividadesEstudianteController implements Initializable {
    
    
    private Estudiante usuarioEstudiante;
    @FXML
    private VBox vBoxListaActividades;
    @FXML
    private Label lblTitulo;
    @FXML
    private ComboBox<?> cmbBoxTrabajosRecepcionales;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void inicializarInformacion(Academico usuarioAcademico){
        //usuarioEstudiante.setIdEstudiante(1); //MODIFICAR
    }
    
    
    @FXML
    private void clicRegresarMenuPrincipal(MouseEvent event) {
        Stage escenarioBase = (Stage)lblTitulo.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLMenuPrincipalEstudiante.fxml"));
            Parent vista = accesoControlador.load();
            FXMLMenuPrincipalEstudianteController menuPrincipalEstudiante = accesoControlador.getController();
            //menuPrincipalEstudiante.inicializarInformacionConEstudiante(usuarioEstudiante); //MODIFICAR
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Menú Principal");
            escenarioBase.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }


    @FXML
    private void clicCrearActividad(ActionEvent event) {
        Stage escenarioBase = (Stage)lblTitulo.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLCreacionActividad.fxml"));
            Parent vista = accesoControlador.load();
            FXMLCreacionActividadController creacionActividad = accesoControlador.getController();
            //creacionActividad.inicializarInformacion(usuarioEstudiante);//MODIFICAR
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Creacion de Actividad");
            escenarioBase.show();;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicMostrarActividades(ActionEvent event) {
    }
    
}
