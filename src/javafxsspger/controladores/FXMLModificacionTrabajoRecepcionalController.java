/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 06/06/2023
*Fecha de modificación: 06/06/2023
*Descripción: Controlador de la vista de la modificacion de un trabajo recepcional
*/
package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.TrabajoRecepcional;


public class FXMLModificacionTrabajoRecepcionalController implements Initializable {

    @FXML
    private Label lblDirector;
    @FXML
    private ComboBox<?> cmbBoxTipoAnteproyecto;
    @FXML
    private Button bttDocumento;
    @FXML
    private Label lblNombreDocumento;
    @FXML
    private TextArea txtAreaNombreTrabajo;
    @FXML
    private TextArea txtAreaDescripcionTrabajo;
    @FXML
    private ScrollPane scrPaneCajaCodirectores;
    @FXML
    private VBox vBoxListaCodirectores;
    @FXML
    private Label lblLGAC;
    @FXML
    private Label lblCuerpoAcademico;
    @FXML
    private ScrollPane scrPaneCajaCodirectores1;
    @FXML
    private VBox vBoxListaEstudiantes;
    @FXML
    private Label lblEstudiantesMaximos;
    @FXML
    private Label lblEstado;
    @FXML
    private ImageView imgViewFlechaAnteriorEstado;
    @FXML
    private ImageView imgViewSiguienteEstado;
    @FXML
    private Label lblCreacion;
    @FXML
    private Label lblFechaAprobacion;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void clicGuardarCambios(ActionEvent event) {
    }

    @FXML
    private void clicRegresar(MouseEvent event) {
    }

    @FXML
    private void clicAdjuntarDocumento(ActionEvent event) {
    }

    @FXML
    private void clicAnteriorEstado(MouseEvent event) {
    }

    @FXML
    private void clicSiguienteEstado(MouseEvent event) {
    }
    
    
    public void inicializarPantalla(TrabajoRecepcional trabajo, Academico usuarioAcademico){
        
    }
}
