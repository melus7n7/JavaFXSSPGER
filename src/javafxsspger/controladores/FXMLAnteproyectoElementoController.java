/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 05/06/2023
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsspger.modelo.pojo.Anteproyecto;
import javafxsspger.utils.Utilidades;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.interfaces.INotificacionAnteproyectos;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.modelo.pojo.TrabajoRecepcional;
import javafxsspger.utils.Constantes;


public class FXMLAnteproyectoElementoController implements Initializable {

    private int idAnteproyecto;
    private int idAcademico;
    private int numeroPantalla;
    private INotificacionAnteproyectos interfazNotificacion;
    private Anteproyecto anteproyectoElemento;
    private int tipoUsuario;
    private Estudiante usuarioEstudiante;
    
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
    @FXML
    private Button bttVerMas;
    @FXML
    private Button bttAsignar;
    @FXML
    private Label lblEtiquetaNombre;
    @FXML
    private Button bttMostrarTrabajo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    private void clicVerMasAnteproyecto(ActionEvent event) {
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLDetallesAnteproyecto.fxml"));
            Parent vista = accesoControlador.load();
            FXMLDetallesAnteproyectoController detallesAnteproyecto = accesoControlador.getController();
            switch(tipoUsuario){
                case Constantes.ACADEMICO:
                    detallesAnteproyecto.inicializarInformacion(idAnteproyecto, numeroPantalla, interfazNotificacion, idAcademico);
                    break;
                case Constantes.INVITADO:
                    detallesAnteproyecto.inicializarInformacionInvitado(idAnteproyecto, usuarioEstudiante);
                    break;
                case Constantes.ESTUDIANTE:
                    
            }
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene (vista));
            escenarioFormulario.setTitle("Detalles Anteproyecto");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }

    @FXML
    private void clicAsignarEstudiante(ActionEvent event) {
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLAsignacionEstudiantes.fxml"));
            Parent vista = accesoControlador.load();
            FXMLAsignacionEstudiantesController anteproyectos = accesoControlador.getController();
            anteproyectos.inicializarDetalles(anteproyectoElemento);
            
            Stage escenarioBase = new Stage();
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Asignación Anteproyectos");
            escenarioBase.initModality(Modality.APPLICATION_MODAL);
            escenarioBase.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void clicVerMasTrabajoRecepcional(ActionEvent event) {
    }
    
    public void setElementoAnteproyecto (Anteproyecto anteproyectoElemento, int numeroPantalla, INotificacionAnteproyectos interfazNotificacion, int idAcademico){
        this.tipoUsuario = Constantes.ACADEMICO;
        this.anteproyectoElemento = anteproyectoElemento;
        this.idAcademico = idAcademico;
        this.interfazNotificacion = interfazNotificacion;
        this.idAnteproyecto = anteproyectoElemento.getIdAnteproyecto();
        this.numeroPantalla = numeroPantalla;
        lblNombreAnteproyecto.setText(anteproyectoElemento.getTitulo());
        lblNombreDirector.setText(anteproyectoElemento.getNombreDirector());
        
        switch(this.numeroPantalla){
            case Constantes.ES_PUBLICADO:
                lblFecha.setText(anteproyectoElemento.getFechaAprobacion() + " ");
                break;
            case Constantes.ES_POR_CORREGIR:
                lblFechaEtiqueta.setText("Fecha creación:");
                lblFecha.setText(anteproyectoElemento.getFechaCreacion() + " ");
                break;
            case Constantes.ES_PROPIO:
                lblFecha.setText(anteproyectoElemento.getFechaAprobacion() + " ");
                lblNombreDirectorEtiqueta.setText("Fecha creación:");
                lblNombreDirector.setText(anteproyectoElemento.getFechaCreacion() + " ");
                break;
            case Constantes.ES_ASIGNAR_ESTUDIANTES:
                lblFecha.setText(anteproyectoElemento.getFechaAprobacion() + " ");
                lblNombreDirector.setVisible(false);
                lblNombreDirectorEtiqueta.setVisible(false);
                bttVerMas.setVisible(false);
                bttAsignar.setVisible(true);
        }
        if(lblFecha.getText().equals("null ")){
            lblFecha.setText("Pendiente por aprobación");
        }
    }
    
    public void setElementoAnteproyectoPublicados(Anteproyecto anteproyectoElemento, int tipoUsuario, Estudiante estudiante){
        this.usuarioEstudiante = estudiante;
        this.tipoUsuario = tipoUsuario;
        this.anteproyectoElemento = anteproyectoElemento;
        this.idAnteproyecto = anteproyectoElemento.getIdAnteproyecto();
        lblNombreAnteproyecto.setText(anteproyectoElemento.getTitulo());
        lblNombreDirector.setText(anteproyectoElemento.getNombreDirector());
        lblFecha.setText(anteproyectoElemento.getFechaAprobacion());
    }
    
    public void setTrabajoRecepcional(TrabajoRecepcional trabajo){
        lblEtiquetaNombre.setText("Nombre del trabajo recepcional:");
        lblNombreAnteproyecto.setText(trabajo.getTitulo());
        lblNombreDirectorEtiqueta.setVisible(false);
        lblNombreDirector.setVisible(false);
        lblFecha.setText(trabajo.getFechaAprobacion());
        bttMostrarTrabajo.setVisible(true);
    }

}
