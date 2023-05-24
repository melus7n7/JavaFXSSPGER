/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 23/05/2023
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
import javafxsspger.interfaces.INotificacionAnteproyectos;


public class FXMLAnteproyectoElementoController implements Initializable {

    private int idAnteproyecto;
    private boolean esPorCorregir;
    private INotificacionAnteproyectos interfazNotificacion;
    
    @FXML
    private Label lblNombreAnteproyecto;
    @FXML
    private Label lblNombreDirector;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblFechaEtiqueta;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setElementoAnteproyecto (Anteproyecto anteproyectoElemento, boolean esCorreccion, INotificacionAnteproyectos interfazNotificacion){
        this.interfazNotificacion = interfazNotificacion;
        this.idAnteproyecto = anteproyectoElemento.getIdAnteproyecto();
        this.esPorCorregir = esCorreccion;
        lblNombreAnteproyecto.setText(anteproyectoElemento.getTitulo());
        lblNombreDirector.setText(anteproyectoElemento.getNombreDirector());
        lblFecha.setText(anteproyectoElemento.getFechaAprobacion());
        idAnteproyecto = anteproyectoElemento.getIdAnteproyecto();
        if(esCorreccion){
            lblFechaEtiqueta.setText("Fecha creación:");
            lblFecha.setText(anteproyectoElemento.getFechaCreacion());
        }
    }

    @FXML
    private void clicVerMasAnteproyecto(ActionEvent event) {
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLDetallesAnteproyecto.fxml"));
            Parent vista = accesoControlador.load();
            FXMLDetallesAnteproyectoController detallesAnteproyecto = accesoControlador.getController(); 
            detallesAnteproyecto.inicializarInformacion(idAnteproyecto, esPorCorregir, interfazNotificacion);
            
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
