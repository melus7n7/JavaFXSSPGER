/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 14/05/2023
*Descripción: Controlador de la vista del detalle de un anteproyecto
*/
package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsspger.modelo.dao.AnteproyectoDAO;
import javafxsspger.modelo.pojo.Anteproyecto;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;


public class FXMLDetallesAnteproyectoController implements Initializable {
    
    private int idAnteproyectoDetalle;
    
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
    @FXML
    private Label lblNoEstudiantesMaximo;
    @FXML
    private Label lblCodirectores;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void inicializarInformacion (int idAnteproyectoDetalles){
        this.idAnteproyectoDetalle = idAnteproyectoDetalles;
        lblIDANTEPROYECTO.setText(idAnteproyectoDetalles+"");
        cargarElemento();
    }
        
    private void cargarElemento(){
        Anteproyecto respuestaBD = AnteproyectoDAO.obtenerAnteproyecto(idAnteproyectoDetalle);
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Sin Conexion", 
                        "Lo sentimos por el momento no tiene conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                        "Hubo un error al cargar la información por favor inténtelo más tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    mostrarDetalles(respuestaBD);
                break;
        }
    }
    
    public void mostrarDetalles (Anteproyecto anteproyectoRespuesta){
        lblNombreAnteproyecto.setText(anteproyectoRespuesta.getTitulo());
        lblDescripcionAnteproyecto.setText(anteproyectoRespuesta.getDescripcion());
        lblNombreDirector.setText(anteproyectoRespuesta.getNombreDirector());
        lblCuerpoAcademico.setText(anteproyectoRespuesta.getCuerpoAcademico());
        lblAreaDisciplinar.setText(anteproyectoRespuesta.getAreaDisciplinar());
        lblNoEstudiantesMaximo.setText(""+anteproyectoRespuesta.getNoEstudiantesMaximo());
        lblIDANTEPROYECTO.setText(""+idAnteproyectoDetalle);
        lblCodirectores.setText(anteproyectoRespuesta.getNombreCodirectores());
    }

    @FXML
    private void clicRegresarAnteproyectos(MouseEvent event) {
        Stage escenarioBase = (Stage) lblNombreAnteproyecto.getScene().getWindow();
        escenarioBase.close();
    }
    
}
