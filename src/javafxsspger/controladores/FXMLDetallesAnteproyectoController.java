/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 28/05/2023
*Descripción: Controlador de la vista del detalle de un anteproyecto
*/
package javafxsspger.controladores;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import javafxsspger.interfaces.INotificacionAnteproyectos;
import javafxsspger.modelo.dao.AnteproyectoDAO;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Anteproyecto;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;


public class FXMLDetallesAnteproyectoController implements Initializable {
    
    private int idAnteproyectoDetalle;
    //private int idAcademico;
    private Academico usuarioAcademico;
    private Anteproyecto anteproyectoDetalle;
    private int numeroPantalla;
    private INotificacionAnteproyectos interfazNotificacion;
    private boolean esInvitado;
    private Estudiante estudiante;
    
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
    private Button bttIniciarProcesoValidacion;
    @FXML
    private Label lblFechaEtiqueta;
    @FXML
    private Button bttModificar;
    @FXML
    private Button bttEliminarPublicados;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    @FXML
    private void clicEliminarPublicados(ActionEvent event) {
        eliminarAnteproyectoPublicados();
    }
    @FXML
    private void clicRegresarAnteproyectos(MouseEvent event) {
        cerrarVentana();
    }

    @FXML
    private void clicDescargarDocumento(ActionEvent event) {
        String directorio = javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
        String directorioBase =  directorio + "/" + anteproyectoDetalle.getNombreDocumento();
        File archivo = new File(directorioBase);
        try {
            OutputStream output = new FileOutputStream(archivo);
            output.write(anteproyectoDetalle.getDocumento());
            output.close();
            Utilidades.mostrarDialogoSimple("Documento descargado", 
                "Se ha descargado el documento en la dirección: " + directorio, Alert.AlertType.INFORMATION);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clicIniciarProcesoValidacion(ActionEvent event) {
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLValidacionAnteproyecto.fxml"));
            Parent vista = accesoControlador.load();
            FXMLValidacionAnteproyectoController validacionAnteproyecto = accesoControlador.getController(); 
            validacionAnteproyecto.inicializarInformacion(anteproyectoDetalle, interfazNotificacion, usuarioAcademico);
            
            Stage escenarioDetalle = (Stage) lblCodirectores.getScene().getWindow();
            escenarioDetalle.setScene(new Scene (vista));
            escenarioDetalle.setTitle("Validación Anteproyecto");
            escenarioDetalle.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicModificarAnteproyecto(ActionEvent event) {
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLCorreccionAnteproyecto.fxml"));
            Parent vista = accesoControlador.load();
            FXMLCorreccionAnteproyectoController correcionAnteproyecto = accesoControlador.getController(); 
            correcionAnteproyecto.iniciarPantalla(anteproyectoDetalle, interfazNotificacion, usuarioAcademico);
            
            Stage escenarioDetalle = (Stage) lblCodirectores.getScene().getWindow();
            escenarioDetalle.setScene(new Scene (vista));
            escenarioDetalle.setTitle("Validación Anteproyecto");
            escenarioDetalle.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void inicializarInformacion (int idAnteproyectoDetalles, int numeroPantalla, INotificacionAnteproyectos interfazNotificacion, Academico academico){
        this.usuarioAcademico = academico;
        this.esInvitado = false;
        this.interfazNotificacion = interfazNotificacion;
        this.idAnteproyectoDetalle = idAnteproyectoDetalles;
        this.numeroPantalla = numeroPantalla;
        switch(this.numeroPantalla){
            case Constantes.ES_POR_CORREGIR:
                bttIniciarProcesoValidacion.setVisible(true);
                break;
            case Constantes.ES_PROPIO:
                bttModificar.setVisible(true);
                break;
        }
        cargarElemento();
    }
    
    public void inicializarInformacionInvitado(int idAnteproyectoDetalle, Estudiante estudiante){
        this.estudiante = estudiante;
        this.esInvitado = true;
        this.idAnteproyectoDetalle = idAnteproyectoDetalle;
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
        lblNombreDocumento.setText(anteproyectoRespuesta.getNombreDocumento());
        switch(this.numeroPantalla){
            case Constantes.ES_POR_CORREGIR:
            case Constantes.ES_PROPIO:
                lblFechaEtiqueta.setText("Fecha creación:");
                lblFecha.setText(Utilidades.darFormatofechas(anteproyectoRespuesta.getFechaCreacion()));
                break;
            default:
            case Constantes.ES_PUBLICADO:
                if(usuarioAcademico.getIdCuerpoAcademico() == anteproyectoRespuesta.getIdCuerpoAcademico()){
                    bttEliminarPublicados.setVisible(true);
                }
                lblFecha.setText(Utilidades.darFormatofechas(anteproyectoRespuesta.getFechaAprobacion()));
                break;
        }
        if(anteproyectoRespuesta.getIdEstado() == Constantes.APROBADO){
            bttModificar.setVisible(false);
        }
        if(esInvitado){
            bttIniciarProcesoValidacion.setVisible(false);
            bttModificar.setVisible(false);
        }
    }

    private void eliminarAnteproyectoPublicados(){
        int respuesta = AnteproyectoDAO.eliminarAnteproyectoDePublicados(anteproyectoDetalle.getIdAnteproyecto());
        switch(respuesta){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Sin Conexion", 
                        "Lo sentimos por el momento no tiene conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error de consulta", 
                        "Hubo un error al eliminar el anteproyecto de la lista de publicados por favor inténtelo más tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Anteproyecto eliminado de la lista", 
                        "Se eliminó el anteproyecto de la lista de publicados", 
                        Alert.AlertType.INFORMATION);
                interfazNotificacion.notificarCargarAnteproyectos();
                cerrarVentana();
                break;
        }
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) lblNombreAnteproyecto.getScene().getWindow();
        escenarioBase.close();
    }
}
