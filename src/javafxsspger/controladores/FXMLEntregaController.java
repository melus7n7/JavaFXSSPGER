/*
*Autor: Montiel Salas Jesús Jacob
*Fecha de creación: 05/06/2023
*Fecha de modificación: 05/06/2023
*Descripción: Controlador de la vista de la entrega
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.dao.EntregaDAO;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Actividad;
import javafxsspger.modelo.pojo.Entrega;
import javafxsspger.modelo.pojo.EntregaRespuesta;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;

public class FXMLEntregaController implements Initializable {

    private Academico usuarioAcademico;
    private Estudiante usuarioEstudiante;
    private Actividad actividad;
    @FXML
    private Label lblNivelSatisfaccion;
    @FXML
    private Label lblDescripcion;
    @FXML
    private Label lblCalificacion;
    @FXML
    private Label lblFechaEtiqueta;
    @FXML
    private Button bttEntregar;
    @FXML
    private Button bttCalificarEntrega;
    @FXML
    private ScrollPane scrPaneContenedorDocumentos;
    @FXML
    private VBox vBoxListaDocumentos;
    @FXML
    private Label lblFechaEntrega;
    private Entrega entrega;
    @FXML
    private Label lblRetroalimentacion;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void inicializarInformacionAcademico(Academico usuarioAcademico, Actividad actividad){
       this.usuarioAcademico = usuarioAcademico;
       this.actividad=actividad;
       bttEntregar.setVisible(false);
       if(usuarioAcademico.isEsProfesor()){
           bttCalificarEntrega.setVisible(false);
       }
       cargarInformacionEntrega(); 
    }
    
    public void inicializarInformacionEstudiante(Estudiante usuarioEstudiante, Actividad actividad){
       this.usuarioEstudiante = usuarioEstudiante;
       this.actividad=actividad;
       bttCalificarEntrega.setVisible(false);
       cargarInformacionEntrega();
    }
    
    private void cargarInformacionEntrega(){
        EntregaRespuesta respuestaBD = EntregaDAO.recuperarEntrega(actividad.getIdActividad());
        
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error Conexión", "No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error al cargar los datos", "Hubo un error al cargar la información por favor inténtelo más tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                this.entrega=respuestaBD.getEntregas().get(0);
                if(entrega.getFechaEntrega()==null){
                    lblFechaEntrega.setText("Pendiente de Entrega");
                    lblDescripcion.setText("Pendiente de Entrega");
                }else{
                    lblFechaEntrega.setText(Utilidades.darFormatofechas(entrega.getFechaEntrega()));
                    lblDescripcion.setText(entrega.getDescripcion());
                }
                if(entrega.getRetroalimentacion()==null){
                    lblRetroalimentacion.setText("Pendiente de Calificación");
                    lblCalificacion.setText("Pendiente");
                    lblNivelSatisfaccion.setText("Pendiente de Calificación");
                }else{
                    recuperarRetroalimentacion();
                    
                }
                break;
        }
    }
    
    public void recuperarRetroalimentacion(){
        EntregaRespuesta respuestaBDRetroalimentacion = EntregaDAO.recuperarCalificacion(actividad.getIdActividad()); 
        switch(respuestaBDRetroalimentacion.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error Conexión", "No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error al cargar los datos", "Hubo un error al cargar la información por favor inténtelo más tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                this.entrega=respuestaBDRetroalimentacion.getEntregas().get(0);           
                lblRetroalimentacion.setText(entrega.getRetroalimentacion());
                lblCalificacion.setText(String.valueOf(entrega.getPuntajeSatisfaccion()));
                lblNivelSatisfaccion.setText(entrega.getNivelSatisfaccion());
                System.out.println(entrega.getNivelSatisfaccion());
                break;
            }
    }

    @FXML
    private void clicRegresar(MouseEvent event) {
        Stage escenarioBase = (Stage)lblCalificacion.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLDetallesActividad.fxml"));
            Parent vista = accesoControlador.load();
            FXMLDetallesActividadController actividadesAcademico = accesoControlador.getController();
            if(usuarioAcademico!=null){
                actividadesAcademico.inicializarInformacionAcademico(usuarioAcademico,actividad);
            }else{
                actividadesAcademico.inicializarInformacionEstudiante(usuarioEstudiante,actividad);                
            }
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Actividades");
            escenarioBase.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    
    
    @FXML
    private void clicEntregar(ActionEvent event) {
    
    }

    @FXML
    private void clicCalificarEntrega(ActionEvent event) {
        Stage escenarioBase = (Stage)lblFechaEntrega.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLRetroalimentacionEntrega.fxml"));
            Parent vista = accesoControlador.load();
            FXMLRetroalimentacionEntregaController retroalimentar = accesoControlador.getController();
            retroalimentar.inicializarInformacionAcademico(usuarioAcademico, entrega,actividad); 
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Retroalimentar Entrega");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
}
