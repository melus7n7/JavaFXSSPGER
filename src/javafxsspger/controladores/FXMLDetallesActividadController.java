/*
*Autor: Montiel Salas Jesús Jacob
*Fecha de creación: 03/06/2023
*Fecha de modificación: 03/06/2023
*Descripción: Controlador de la vista del detalle de una actividad
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
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Actividad;
import javafxsspger.modelo.pojo.Estudiante;

/**
 * FXML Controller class
 *
 * @author monti
 */
public class FXMLDetallesActividadController implements Initializable {

    private Academico usuarioAcademico;
    private Actividad actividad;
    @FXML
    private TextArea txtAreaActividad;
    @FXML
    private Label lblTituloActividad;
    @FXML
    private Label lblNombreEstudiante;
    @FXML
    private Label lblFechaCreacion;
    @FXML
    private Label lblFechaInicio;
    @FXML
    private Label lblFechaFinal;
    @FXML
    private Button bttEditar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 
    }    

    
    public void inicializarInformacionAcademico(Academico usuarioAcademico, Actividad actividad){
       this.usuarioAcademico = usuarioAcademico;
       this.actividad=actividad;
       cargarInformacionActividad();
       bttEditar.setVisible(false);
    }
    
    public void inicializarInformacionEstudiante(Estudiante usuarioEstudiante, Actividad actividad){
       this.usuarioEstudiante = usuarioEstudiante;
       System.out.println("IDESTUDIANTE1: "+usuarioEstudiante.getIdEstudiante());
       this.actividad=actividad;
       cargarInformacionActividad();
    }
    
    public void cargarInformacionActividad(){
        lblTituloActividad.setText(actividad.getTitulo());
        lblNombreEstudiante.setText(actividad.getNombreEstudiante()+" "+actividad.getApellidoPaternoEstudiante()+" "+actividad.getApellidoMaternoEstudiante());
        txtAreaActividad.setText(actividad.getDescripcion());
        txtAreaActividad.setEditable(false);
        lblFechaInicio.setText(actividad.getFechaInicio());
        lblFechaFinal.setText(actividad.getFechaFinal());
        lblFechaCreacion.setText(actividad.getFechaCreacion());
        
    }

    @FXML
    private void clicVerEntrega(ActionEvent event) {
        
    }
    
    private Estudiante usuarioEstudiante;//MODIFICAR
    @FXML
    private void clicEditar(ActionEvent event) {
        Stage escenarioBase = (Stage)lblTituloActividad.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLCreacionActividad.fxml"));
            Parent vista = accesoControlador.load();
            FXMLCreacionActividadController creacionActividad = accesoControlador.getController();
            boolean esEdicion=true;    
            creacionActividad.inicializarInformacionEstudiante(usuarioEstudiante, esEdicion, actividad);
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Creacion de Actividad");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @FXML
    private void clicRegresar(MouseEvent event) {
        if(usuarioAcademico!=null){
            regresarActividadesAcademico();
        }else{
            regresarActividadesEstudiante();
        }
    }
    
    private void regresarActividadesAcademico(){
        Stage escenarioBase = (Stage)lblTituloActividad.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLActividades.fxml"));
            Parent vista = accesoControlador.load();
            FXMLActividadesController actividadesAcademico = accesoControlador.getController();
            actividadesAcademico.inicializarInformacionAcademico(usuarioAcademico);
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Actividades");
            escenarioBase.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    private void regresarActividadesEstudiante(){
        Stage escenarioBase = (Stage)lblTituloActividad.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLActividades.fxml"));
            Parent vista = accesoControlador.load();
            FXMLActividadesController actividadesEstudiante = accesoControlador.getController();
            actividadesEstudiante.inicializarInformacionEstudiante(usuarioEstudiante);
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Actividades");
            escenarioBase.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
}
