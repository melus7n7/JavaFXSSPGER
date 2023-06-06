/*
*Autor: Montiel Salas Jesús Jacob
*Fecha de creación: 06/06/2023
*Fecha de modificación: 06/06/2023
*Descripción: Controlador de la vista de un elemento en la lista de Cuerpos Academicos
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
import javafxsspger.modelo.pojo.CuerpoAcademico;
import javafxsspger.modelo.pojo.Usuario;

/**
 * FXML Controller class
 *
 * @author monti
 */
public class FXMLCuerpoAcademicoElementoController implements Initializable {

    @FXML
    private Label lblNombreCuerpoAcademico;
    @FXML
    private Label lblClaveCuerpoAcademico;
    @FXML
    private Label lblAreaConocimiento;
    @FXML
    private Label lblConsolidacion;

    private Usuario usuarioAdmin;
    private CuerpoAcademico cuerpoAcademicoElemento;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void inicializarElementoAcademico(CuerpoAcademico cuerpoAcademicoElemento, Usuario usuarioAdmin){
        this.cuerpoAcademicoElemento=cuerpoAcademicoElemento;
        this.usuarioAdmin=usuarioAdmin;
        lblNombreCuerpoAcademico.setText(cuerpoAcademicoElemento.getNombre());
        lblClaveCuerpoAcademico.setText(cuerpoAcademicoElemento.getClaveCuerpoAcademico());
        lblConsolidacion.setText(cuerpoAcademicoElemento.getNivelConsolidacion());
        lblAreaConocimiento.setText(cuerpoAcademicoElemento.getAreaConocimiento());
    }
    

    @FXML
    private void clicModificarIntegrantesCuerpoAcademico(ActionEvent event) {
    }

    @FXML
    private void clicModificar(ActionEvent event) {
        Stage escenarioBase = (Stage)lblNombreCuerpoAcademico.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLCreacionCuerpoAcademico.fxml"));
            Parent vista = accesoControlador.load();
            FXMLCreacionCuerpoAcademicoController cuerpoAcademico = accesoControlador.getController();
            cuerpoAcademico.inicializarInformacion(usuarioAdmin);
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("");
            escenarioBase.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
        
    }
    
    
}
