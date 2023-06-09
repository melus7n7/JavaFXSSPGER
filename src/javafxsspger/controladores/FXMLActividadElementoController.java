/*
*Autor: Montiel Salas Jesús Jacob
*Fecha de creación: 21/05/2023
*Fecha de modificación: 06/06/2023
*Descripción: Controlador de la vista de un elemento en la lista de actividades
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
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Actividad;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.utils.Utilidades;

public class FXMLActividadElementoController implements Initializable {

    
    @FXML
    private Label lblTituloActividad;
    @FXML
    private Label lblNombreEstudiante;
    @FXML
    private Label lblFechaInicio;
    @FXML
    private Label lblFechaFinal;
    private Estudiante usuarioEstudiante;
    private Academico usuarioAcademico;
    private int idActividad;
    private Actividad actividadActual;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void inicializarActividadElementoAcademico(Actividad actividadElemento, Academico usuarioAcademico){
        this.actividadActual = actividadElemento;
        this.usuarioAcademico = usuarioAcademico;
        this.idActividad = actividadElemento.getIdActividad();
        lblTituloActividad.setText(actividadElemento.getTitulo());
        lblNombreEstudiante.setText(actividadElemento.getNombreEstudiante() + " " + actividadElemento.getApellidoPaternoEstudiante() + " " + actividadElemento.getApellidoMaternoEstudiante());
        lblFechaInicio.setText(Utilidades.darFormatofechas(actividadElemento.getFechaInicio()));
        lblFechaFinal.setText(Utilidades.darFormatofechas(actividadElemento.getFechaFinal()));
    }
    
    public void inicializarActividadElementoEstudiante(Actividad actividadElemento, Estudiante usuarioEstudiante){
        this.actividadActual = actividadElemento;
        this.usuarioEstudiante = usuarioEstudiante;
        this.idActividad = actividadElemento.getIdActividad();
        lblTituloActividad.setText(actividadElemento.getTitulo());
        lblNombreEstudiante.setText(actividadElemento.getNombreEstudiante()+" "+actividadElemento.getApellidoPaternoEstudiante()+" "+actividadElemento.getApellidoMaternoEstudiante());
        lblFechaInicio.setText(Utilidades.darFormatofechas(actividadElemento.getFechaInicio()));
        lblFechaFinal.setText(Utilidades.darFormatofechas(actividadElemento.getFechaFinal()));
    }
    
    @FXML
    private void clicVerDetallesActividad(ActionEvent event) {
        if(usuarioAcademico != null){
            Stage escenarioBase = (Stage)lblTituloActividad.getScene().getWindow();
            try {
                FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLDetallesActividad.fxml"));
                Parent vista = accesoControlador.load();
                FXMLDetallesActividadController actividad = accesoControlador.getController();
                actividad.inicializarInformacionAcademico(usuarioAcademico, actividadActual);
                escenarioBase.setScene(new Scene (vista));
                escenarioBase.setTitle("Detalles de Actividad");
                escenarioBase.show();
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        }else{
            Stage escenarioBase = (Stage)lblTituloActividad.getScene().getWindow();
            try {
                FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLDetallesActividad.fxml"));
                Parent vista = accesoControlador.load();
                FXMLDetallesActividadController actividad = accesoControlador.getController();
                actividad.inicializarInformacionEstudiante(usuarioEstudiante, actividadActual);
                escenarioBase.setScene(new Scene (vista));
                escenarioBase.setTitle("Detalles de Actividad");
                escenarioBase.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
