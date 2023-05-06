/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 05/05/2023
*Descripción: Controlador de la vista del detalle de un anteproyecto
*/
package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafxsspger.modelo.pojo.Anteproyecto;


public class FXMLDetallesAnteproyectoController implements Initializable {

    private static int idAnteproyectoDetalle;
    
    @FXML
    private Label lblNombreAnteproyecto;
    @FXML
    private Label lblDescripcionAnteproyecto;
    @FXML
    private Label lblNombreDirector;
    @FXML
    private Label lblCuerpoAcademico;
    @FXML
    private Label lblAreaDisciplinar;
    @FXML
    private Label lblIDANTEPROYECTO;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Recupera los datos del DAO y regresa un objeto Anteproyecto
        //Anteproyecto anteproyectoRespuesta = AnteproyectoDAO.obtenerDetallesAnteproyecto(idAnteproyectoDetalle);
        Anteproyecto anteproyectoRespuesta = new Anteproyecto();
        anteproyectoRespuesta.setTitulo("Hacia un Modelo de Campus Accesible: Facultad de Estadística e Informática");
        anteproyectoRespuesta.setDescripcion("AAAAAAAAAAA");
        anteproyectoRespuesta.setNombreDirector("MCC. Juan Carlos Pérez Arriaga");
        anteproyectoRespuesta.setCuerpoAcademico("BBBBBBBBBB");
        anteproyectoRespuesta.setAreaDisciplinar("CCCCCCC");
        
        mostrarDetalles(anteproyectoRespuesta);
    }
    
    public void mostrarDetalles (Anteproyecto anteproyectoRespuesta){
        lblNombreAnteproyecto.setText(anteproyectoRespuesta.getTitulo());
        lblDescripcionAnteproyecto.setText(anteproyectoRespuesta.getDescripcion());
        lblNombreDirector.setText(anteproyectoRespuesta.getNombreDirector());
        lblCuerpoAcademico.setText(anteproyectoRespuesta.getCuerpoAcademico());
        lblAreaDisciplinar.setText(anteproyectoRespuesta.getAreaDisciplinar());
        lblIDANTEPROYECTO.setText(""+idAnteproyectoDetalle);
    }

    public static void setIdAnteproyectoDetalle(int anteproyectoDetalle) {
        FXMLDetallesAnteproyectoController.idAnteproyectoDetalle = anteproyectoDetalle;
    }
    
}
