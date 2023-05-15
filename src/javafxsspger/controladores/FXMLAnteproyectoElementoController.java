/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 14/05/2023
*Descripción: Controlador de la vista de un elemento en la lista de Anteproyectos Publicados
*/

package javafxsspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsspger.modelo.pojo.Anteproyecto;
import javafxsspger.utils.Utilidades;
import javafxsspger.JavaFXSSPGER;


public class FXMLAnteproyectoElementoController implements Initializable {

    @FXML
    private Label lblNombreAnteproyecto;
    @FXML
    private Label lblNombreDirector;
    @FXML
    private Label lblFechaPublicacion;
    
    private int idAnteproyecto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setElementoAnteproyecto (Anteproyecto anteproyectoElemento){
        this.idAnteproyecto = anteproyectoElemento.getIdAnteproyecto();
        lblNombreAnteproyecto.setText(anteproyectoElemento.getTitulo());
        lblNombreDirector.setText(anteproyectoElemento.getNombreDirector());
        lblFechaPublicacion.setText(anteproyectoElemento.getFechaPublicacion());
        idAnteproyecto = anteproyectoElemento.getIdAnteproyecto();
    }

    @FXML
    private void clicVerMasAnteproyecto(ActionEvent event) {
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLDetallesAnteproyecto.fxml"));
            Parent vista = accesoControlador.load();
            FXMLDetallesAnteproyectoController detallesAnteproyecto = accesoControlador.getController(); 
            detallesAnteproyecto.inicializarInformacion(idAnteproyecto);
            
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene (vista));
            escenarioFormulario.setTitle("Detalles Anteproyecto");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    
}
