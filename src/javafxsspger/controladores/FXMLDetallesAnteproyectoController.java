/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 14/05/2023
*Descripción: Controlador de la vista del detalle de un anteproyecto
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.dao.AnteproyectoDAO;
import javafxsspger.modelo.pojo.Anteproyecto;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;


public class FXMLDetallesAnteproyectoController implements Initializable {
    
    private int idAnteproyectoDetalle;
    private Anteproyecto anteproyectoDetalle;
    private boolean esPorCorregir;
    
    @FXML
    private Label lblNombreAnteproyecto;
    @FXML
    private Label lblNombreDirector;
    @FXML
    private Label lblCuerpoAcademico;
    @FXML
    private Label lblNoEstudiantesMaximo;
    @FXML
    private Label lblCodirectores;
    @FXML
    private Label lblNombreDocumento;
    @FXML
    private Label lblTipoAnteproyecto;
    @FXML
    private Label lblLGAC;
    @FXML
    private TextArea txtAreaDescripcion;
    @FXML
    private Label lblFecha;
    @FXML
    private Button btnIniciarProcesoValidacion;
    @FXML
    private Label lblFechaEtiqueta;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void inicializarInformacion (int idAnteproyectoDetalles, boolean esPorCorregir){
        this.idAnteproyectoDetalle = idAnteproyectoDetalles;
        this.esPorCorregir = esPorCorregir;
        if(!esPorCorregir){
            btnIniciarProcesoValidacion.setVisible(false);
        }
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
        this.anteproyectoDetalle = anteproyectoRespuesta;
        lblNombreAnteproyecto.setText(anteproyectoRespuesta.getTitulo());
        txtAreaDescripcion.setText(anteproyectoRespuesta.getDescripcion());
        lblNombreDirector.setText(anteproyectoRespuesta.getNombreDirector());
        lblCodirectores.setText(anteproyectoRespuesta.getNombreCodirectores());
        lblCuerpoAcademico.setText(anteproyectoRespuesta.getNombreCuerpoAcademico());
        lblLGAC.setText(anteproyectoRespuesta.getNombreLGAC());
        lblTipoAnteproyecto.setText(anteproyectoRespuesta.getTipoAnteproyecto());
        lblNoEstudiantesMaximo.setText(""+anteproyectoRespuesta.getNoEstudiantesMaximo());
        lblNombreDocumento.setText("Documento jiji");
        lblFecha.setText(anteproyectoRespuesta.getFechaAprobacion());
        if(esPorCorregir){
            lblFechaEtiqueta.setText("Fecha creación:");
            lblFecha.setText(anteproyectoRespuesta.getFechaCreacion());
        }
    }

    @FXML
    private void clicRegresarAnteproyectos(MouseEvent event) {
        Stage escenarioBase = (Stage) lblNombreAnteproyecto.getScene().getWindow();
        escenarioBase.close();
    }

    @FXML
    private void clicDescargarDocumento(ActionEvent event) {
    }

    @FXML
    private void clicIniciarProcesoValidacion(ActionEvent event) {
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLValidacionAnteproyecto.fxml"));
            Parent vista = accesoControlador.load();
            FXMLValidacionAnteproyectoController validacionAnteproyecto = accesoControlador.getController(); 
            validacionAnteproyecto.inicializarInformacion(anteproyectoDetalle);
            
            Stage escenarioDetalle = (Stage) lblCodirectores.getScene().getWindow();
            escenarioDetalle.setScene(new Scene (vista));
            escenarioDetalle.setTitle("Validación Anteproyecto");
            escenarioDetalle.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
