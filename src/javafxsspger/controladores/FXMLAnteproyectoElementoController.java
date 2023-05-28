/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 28/05/2023
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
import javafxsspger.utils.Constantes;


public class FXMLAnteproyectoElementoController implements Initializable {

    private int idAnteproyecto;
    private int idAcademico;
    private int numeroPantalla;
    private INotificacionAnteproyectos interfazNotificacion;
    
    @FXML
    private Label lblNombreAnteproyecto;
    @FXML
    private Label lblNombreDirector;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblFechaEtiqueta;
    @FXML
    private Label lblNombreDirectorEtiqueta;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setElementoAnteproyecto (Anteproyecto anteproyectoElemento, int numeroPantalla, INotificacionAnteproyectos interfazNotificacion, int idAcademico){
        this.idAcademico = idAcademico;
        this.interfazNotificacion = interfazNotificacion;
        this.idAnteproyecto = anteproyectoElemento.getIdAnteproyecto();
        this.numeroPantalla = numeroPantalla;
        lblNombreAnteproyecto.setText(anteproyectoElemento.getTitulo());
        lblNombreDirector.setText(anteproyectoElemento.getNombreDirector());
        idAnteproyecto = anteproyectoElemento.getIdAnteproyecto();
        
        switch(this.numeroPantalla){
            case Constantes.ES_PUBLICADO:
                lblFecha.setText(anteproyectoElemento.getFechaAprobacion());
                break;
            case Constantes.ES_POR_CORREGIR:
                lblFechaEtiqueta.setText("Fecha creación:");
                lblFecha.setText(anteproyectoElemento.getFechaCreacion());
                break;
            case Constantes.ES_PROPIO:
                lblFecha.setText(anteproyectoElemento.getFechaAprobacion());
                lblNombreDirectorEtiqueta.setText("Fecha creación:");
                lblNombreDirector.setText(anteproyectoElemento.getFechaCreacion());
                break;
                
        }
    }

    @FXML
    private void clicVerMasAnteproyecto(ActionEvent event) {
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLDetallesAnteproyecto.fxml"));
            Parent vista = accesoControlador.load();
            FXMLDetallesAnteproyectoController detallesAnteproyecto = accesoControlador.getController(); 
            detallesAnteproyecto.inicializarInformacion(idAnteproyecto, numeroPantalla, interfazNotificacion, idAcademico);
            
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
