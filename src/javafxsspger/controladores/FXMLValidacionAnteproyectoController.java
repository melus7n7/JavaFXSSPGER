/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 23/05/2023
*Fecha de modificación: 27/05/2023
*Descripción: Controlador de la vista de Validación 
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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.interfaces.INotificacionAnteproyectos;
import javafxsspger.modelo.dao.AnteproyectoDAO;
import javafxsspger.modelo.dao.ComentarioDAO;
import javafxsspger.modelo.pojo.Anteproyecto;
import javafxsspger.modelo.pojo.Comentario;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;


public class FXMLValidacionAnteproyectoController implements Initializable {

    private Anteproyecto anteproyectoValidacion;
    private int idAcademico;
    private INotificacionAnteproyectos interfazNotificacion;
    
    @FXML
    private TextArea txtAreaDescripcion;
    @FXML
    private Label lblLGAC;
    @FXML
    private Label lblTipoAnteproyecto;
    @FXML
    private Label lblNombreDocumento;
    @FXML
    private Label lblFechaCreacion;
    @FXML
    private Label lblCodirectores;
    @FXML
    private Label lblNoEstudiantesMaximo;
    @FXML
    private Label lblCuerpoAcademico;
    @FXML
    private Label lblNombreDirector;
    @FXML
    private Label lblNombreAnteproyecto;
    @FXML
    private TextArea txtAreaComentario;

        @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicDescargarDocumento(ActionEvent event) {
        String directorio = javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
        String directorioBase =  directorio + "/" + anteproyectoValidacion.getNombreDocumento();
        File archivo = new File(directorioBase);
        try {
            OutputStream output = new FileOutputStream(archivo);
            output.write(anteproyectoValidacion.getDocumento());
            output.close();
            Utilidades.mostrarDialogoSimple("Documento descargado", 
                "Se ha descargado el documento en la dirección: " + directorio, Alert.AlertType.INFORMATION);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clicDevolverParaCorreccion(ActionEvent event) {
        cambiarPorCorregir();
    }

    @FXML
    private void clicAprobarParaPublicar(ActionEvent event) {
        publicarAnteproyecto();
    }
    
    @FXML
    private void clicRegresar(MouseEvent event) {
        Stage escenarioBase = (Stage) lblNombreAnteproyecto.getScene().getWindow();
        escenarioBase.close();
    }
    
    public void inicializarInformacion (Anteproyecto anteproyectoValidacion, INotificacionAnteproyectos interfazNotificacion, int idAcademico){
        this.idAcademico = idAcademico;
        this.interfazNotificacion = interfazNotificacion;
        this.anteproyectoValidacion = anteproyectoValidacion;
        cargarDatos();
    }
    
    private void cargarDatos(){
        lblNombreAnteproyecto.setText(anteproyectoValidacion.getTitulo());
        txtAreaDescripcion.setText(anteproyectoValidacion.getDescripcion());
        lblNombreDirector.setText(anteproyectoValidacion.getNombreDirector());
        lblCodirectores.setText(anteproyectoValidacion.getNombreCodirectores());
        lblCuerpoAcademico.setText(anteproyectoValidacion.getNombreCuerpoAcademico());
        lblLGAC.setText(anteproyectoValidacion.getNombreLGAC());
        lblTipoAnteproyecto.setText(anteproyectoValidacion.getTipoAnteproyecto());
        lblNoEstudiantesMaximo.setText(""+anteproyectoValidacion.getNoEstudiantesMaximo());
        lblNombreDocumento.setText(anteproyectoValidacion.getNombreDocumento());
        lblFechaCreacion.setText(anteproyectoValidacion.getFechaCreacion());
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) lblNombreAnteproyecto.getScene().getWindow();
        escenarioBase.close();
    }
    
    private void publicarAnteproyecto(){
        int codigoRespuesta = AnteproyectoDAO.publicarAnteproyecto(anteproyectoValidacion.getIdAnteproyecto());
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error de conexión", 
                            "Error en la conexión con la base de datos", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error de consulta", 
                            "Por el momento no se puede actualizar la información en la base de datos", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    Utilidades.mostrarDialogoSimple("Anteproyecto Validado", 
                            "Se publicó el anteproyecto correctamente", Alert.AlertType.INFORMATION);
                    interfazNotificacion.notificarCargarAnteproyectosPorCorregir();
                    cerrarVentana();
                break;
        }
    }
    
    private void cambiarPorCorregir(){
        int codigoRespuesta = AnteproyectoDAO.devolderAnteproyecto(anteproyectoValidacion.getIdAnteproyecto());
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error de conexión", 
                            "Error en la conexión con la base de datos", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error de consulta", 
                            "Por el momento no se puede actualizar la información en la base de datos", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    guardarComentario();
                break;
        }
    }
    
    private void guardarComentario(){
        Comentario comentarioNuevo = new Comentario();
        comentarioNuevo.setTexto(txtAreaComentario.getText().replaceAll("\n", System.getProperty("line.separator")));
        comentarioNuevo.setIdAcademico(idAcademico);
        comentarioNuevo.setIdAnteproyecto(anteproyectoValidacion.getIdAnteproyecto());
        
        //Validación
        
        int codigoRespuesta = ComentarioDAO.guardarComentario(comentarioNuevo);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error de conexión", 
                            "Error en la conexión con la base de datos", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error de consulta", 
                            "Por el momento no se puede actualizar la información en la base de datos", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    Utilidades.mostrarDialogoSimple("Anteproyecto Corregido", 
                            "Se regresó el anteproyecto para su corrección correctamente", Alert.AlertType.INFORMATION);
                    interfazNotificacion.notificarCargarAnteproyectosPorCorregir();
                    cerrarVentana();
                break;
        } 
    }
}
