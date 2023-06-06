/*
*Autor: Montiel Salas Jesús Jacob
*Fecha de creación: 06/06/2023
*Fecha de modificación: 06/06/2023
*Descripción: Controlador de la vista de la creacion del cuerpo academico
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.pojo.Usuario;

/**
 * FXML Controller class
 *
 * @author monti
 */
public class FXMLCreacionCuerpoAcademicoController implements Initializable {

    @FXML
    private TextArea txtAreaClaveCuerpoAcademico;
    @FXML
    private TextArea txtAreaDescripcionCuerpoAcademico;
    @FXML
    private Label lblTitulo;
    @FXML
    private TextArea txtAreaNombreCuerpoAcademico;
    @FXML
    private TextArea txtAreaClaveCuerpoAcademico1;
    @FXML
    private ComboBox<?> cmbBoxConsolidacion;
    @FXML
    private ScrollPane scrPaneCajaLGAC;
    @FXML
    private VBox vBoxListaLGAC;
    private Usuario usuarioAdmin;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void inicializarInformacion(Usuario usuarioAdmin){
        this.usuarioAdmin=usuarioAdmin;
        inicializarLGAC();
    }
    
    private void inicializarLGAC(){
        
        
    }

    @FXML
    private void clicRegresarCuerposAcademicos(MouseEvent event) {
        Stage escenarioBase = (Stage)lblTitulo.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLCuerposAcademicos.fxml"));
            Parent vista = accesoControlador.load();
            FXMLCuerposAcademicosController cuerposAcademicos = accesoControlador.getController();
            cuerposAcademicos.inicializarInformacionUsuario(usuarioAdmin);
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("");
            escenarioBase.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicGuardarCuerpoAcademico(ActionEvent event) {
        //validarCampos();
    }
    
    
    
    
}
