/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 06/06/2023
*Fecha de modificación: 06/06/2023
*Descripción: Controlador de la vista del detalle de un trabajo recepcional
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
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.dao.TrabajoRecepcionalDAO;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.modelo.pojo.TrabajoRecepcional;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;


public class FXMLDetalleTrabajoRecepcionalController implements Initializable {
    
    private TrabajoRecepcional trabajoRecepcional;
    private Academico usuarioAcademico;
    private Estudiante usuarioEstudiante;

    @FXML
    private Button bttModificar;
    @FXML
    private Label lblTitulo;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    @FXML
    private void clicModificar(ActionEvent event) {
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLModificacionTrabajoRecepcional.fxml"));
            Parent vista = accesoControlador.load();
            FXMLModificacionTrabajoRecepcionalController modificacionTrabajoRecepcional = accesoControlador.getController(); 
            modificacionTrabajoRecepcional.inicializarPantalla(trabajoRecepcional, usuarioAcademico);
            
            Stage escenarioDetalle = (Stage) lblTitulo.getScene().getWindow();
            escenarioDetalle.setScene(new Scene (vista));
            escenarioDetalle.setTitle("Modificacion Trabajo Recepcional");
            escenarioDetalle.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void clicRegresar(ActionEvent event) {
        
    }

    public void inicializarPantallaAcademico(int idTrabajoRecepcional, Academico usuarioAcademico){
        this.usuarioAcademico = usuarioAcademico;
        cargarElemento(idTrabajoRecepcional);
        
    }

    private void cargarElemento(int idTrabajoRecepcional){
        TrabajoRecepcional respuesta = TrabajoRecepcionalDAO.recuperarDetallesTrabajoRecepcional(idTrabajoRecepcional);
        switch(respuesta.getCodigoRespuesta()){
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
                    mostrarDetalles(respuesta);
                break;
        }
    }
    
    private void mostrarDetalles(TrabajoRecepcional trabajoRecepcional){
        this.trabajoRecepcional = trabajoRecepcional;
        lblTitulo.setText(trabajoRecepcional.getTitulo());
    }

    
    
}
