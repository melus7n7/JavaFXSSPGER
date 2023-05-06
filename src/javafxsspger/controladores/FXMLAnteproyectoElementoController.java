/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 05/05/2023
*Descripción: Controlador de la vista de un elemento en la lista de Anteproyectos Publicados
*/

package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsspger.modelo.pojo.Anteproyecto;
import javafxsspger.utils.Utilidades;


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
        lblNombreAnteproyecto.setText(anteproyectoElemento.getTitulo());
        lblNombreDirector.setText(anteproyectoElemento.getNombreDirector());
        lblFechaPublicacion.setText(anteproyectoElemento.getFechaPublicacion());
        idAnteproyecto = anteproyectoElemento.getIdAnteproyecto();
    }

    @FXML
    private void clicVerMasAnteproyecto(ActionEvent event) {
        //Solicitar Detalles Anteproyecto
        FXMLDetallesAnteproyectoController.setIdAnteproyectoDetalle(idAnteproyecto);
        //Display Detalles Anteproyecto
        Stage escenarioDetallesAnteproyecto = new Stage();
        escenarioDetallesAnteproyecto.setScene(Utilidades.inicializarEscena("vistas/FXMLDetallesAnteproyecto.fxml"));
        escenarioDetallesAnteproyecto.setTitle("Detalle Anteproyecto");
        escenarioDetallesAnteproyecto.initModality(Modality.APPLICATION_MODAL);//Aplication define un elemento como que va a "desabilitar" todas las demas pestañas
        escenarioDetallesAnteproyecto.showAndWait();
    }
    
}
