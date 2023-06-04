/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 03/06/2023
*Fecha de modificación: 03/06/2023
*Descripción: Controlador de la vista del elemento de una actividad en la vista de avance
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Actividad;
import javafxsspger.modelo.pojo.Estudiante;


public class FXMLActividadEnAvanceElementoController implements Initializable {

    private Actividad actividadActual;
    private Estudiante usuarioEstudiante;
    private Academico usuarioAcademico;
    private boolean esAcademico;
    
    @FXML
    private Label lblNombreActividad;
    @FXML
    private Button bttVerMasActividad;
    @FXML
    private Label lblFechaCreacion;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicVerMasActividad(ActionEvent event) {
        Stage escenarioBase = (Stage)lblFechaCreacion.getScene().getWindow();
            try {
                FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLDetallesActividad.fxml"));
                Parent vista = accesoControlador.load();
                FXMLDetallesActividadController actividad = accesoControlador.getController();
                if(esAcademico){
                    actividad.inicializarInformacionAcademico(usuarioAcademico, actividadActual);
                }else{
                    actividad.inicializarInformacionEstudiante(usuarioEstudiante, actividadActual);
                }
                escenarioBase.setScene(new Scene (vista));
                escenarioBase.setTitle("Actividades");
                escenarioBase.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }
    
    public void cargarActividadAcademico(Actividad actividad, Academico usuarioAcademico){
        this.usuarioAcademico = usuarioAcademico;
        this.actividadActual = actividad;
        this.esAcademico = true;
        lblNombreActividad.setText(actividad.getTitulo());
        lblFechaCreacion.setText("Fecha Creación: " + actividad.getFechaCreacion());
    }
    
    public void cargarActividadEstudiante(Actividad actividad, Estudiante usuarioEstudiante){
        this.actividadActual = actividad;
        this.usuarioEstudiante = usuarioEstudiante;
        this.esAcademico = false;
        lblNombreActividad.setText(actividad.getTitulo());
        lblFechaCreacion.setText("Fecha Creación: " + actividad.getFechaCreacion());
    }
    
}
