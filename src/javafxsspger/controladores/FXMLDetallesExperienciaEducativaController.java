 /*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 06/06/2023
*Fecha de modificación: 06/06/2023
*Descripción: Controlador de la vista del detalle de una Experiencia Educativa
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Actividad;
import javafxsspger.modelo.pojo.ExperienciaEducativa;
import javafxsspger.modelo.pojo.Usuario;
import javafxsspger.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author danie
 */
public class FXMLDetallesExperienciaEducativaController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private Button btnConfirmar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnModificarIntegrantes;
    @FXML
    private ComboBox<?> cmbBoxExperiencia;
    @FXML
    private ComboBox<?> cmbBoxAcademico;
    @FXML
    private ComboBox<?> cmbBoxCoordinadorAcademia;
    @FXML
    private DatePicker dpFechaComienzo;
    @FXML
    private DatePicker dpFechaCierre;
    @FXML
    private TextArea txtAreaNRCExperienciaEducativa;
    @FXML
    private TextArea txtAreaPeriodoEscolar;
    
    private ExperienciaEducativa experienciaEducativa;
    private Usuario usuarioAdministrador;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
     public void inicializarInformacionAdministrador(Usuario usuarioAdministrador, ExperienciaEducativa experienciaEducativa){
       this.usuarioAdministrador = usuarioAdministrador;
       cargarInformacionExperienciaEducativa();
       btnModificarIntegrantes.setVisible(false);
       btnEliminar.setVisible(false);
    }
     
    public void cargarInformacionExperienciaEducativa(){
        //lblTitulo.setText(experienciaEducativa.getNombreExperienciaEducativa);
        txtAreaNRCExperienciaEducativa.setText(experienciaEducativa.getNRC());
        //getFechaComienzo
        //getFechaCierre
        txtAreaPeriodoEscolar.setText(experienciaEducativa.getNombrePeriodoEscolar());
        //getAcademico
        //getCoordinadorAcademia
    }

    @FXML
    private void clicRegresar(MouseEvent event) {
        regresarExperienciasEducativasAdministrador;
    }
    
    private void regresarExperienciasEducativasAdministrador(){
        Stage escenarioBase = (Stage)lblTitulo.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLExperienciasEducativas.fxml"));
            Parent vista = accesoControlador.load();
            FXMLExperienciasEducativasController expEducativasAdministrador = accesoControlador.getController();
            expEducativasAdministrador.inicializarInformacionUsuario(usuarioAdmin);
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Experiencias Educativas");
            escenarioBase.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicConfirmar(ActionEvent event) {
        
    }

    @FXML
    private void clicEliminar(ActionEvent event) {
        
    }

    @FXML
    private void clicModificarIntegrantes(ActionEvent event) {
        
    }
    
}
