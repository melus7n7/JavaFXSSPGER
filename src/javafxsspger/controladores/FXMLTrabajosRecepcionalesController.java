/*
*Autor: Martínez Aguilar Sulem, Montiel Salas Jesús Jacob
*Fecha de creación: 05/06/2023
*Fecha de modificación: 05/06/2023
*Descripción: Controlador de la vista de los trabajos recepcionales
*/
package javafxsspger.controladores;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafxsspger.modelo.dao.AvanceDAO;
import javafxsspger.modelo.dao.EstudianteDAO;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.AvanceRespuesta;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.modelo.pojo.EstudianteRespuesta;
import javafxsspger.modelo.pojo.TrabajoRecepcional;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;


public class FXMLTrabajosRecepcionalesController implements Initializable {
    
    private Academico usuarioAcademico;
    private ObservableList<TrabajoRecepcional> trabajosRecepcionales;

    @FXML
    private Label lblTitulo;
    @FXML
    private ScrollPane scrPaneContenedorTrabajos;
    @FXML
    private VBox vBoxListaTrabajos;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicRegresar(MouseEvent event) {
    }
    
    public void inicializarPantallaAcademico(Academico usuarioAcademico){
        this.usuarioAcademico = usuarioAcademico;
    }
    
    private void cargarTrabajosRecepcionales(){
        /*trabajosRecepcionales = FXCollections.observableArrayList();
        EstudianteRespuesta respuesta = new EstudianteRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if(academicoAvances.isEsProfesor()){
            respuesta = EstudianteDAO.recuperarEstudiantesProfesor(academicoAvances.getIdAcademico());
        }
        if(academicoAvances.isEsDirector()){
            respuesta = EstudianteDAO.recuperarEstudiantesDirector(academicoAvances.getIdAcademico());
        }
        if(academicoAvances.isEsDirector() && academicoAvances.isEsProfesor()){
            EstudianteRespuesta respuesta1 = EstudianteDAO.recuperarEstudiantesProfesor(academicoAvances.getIdAcademico());
            EstudianteRespuesta respuesta2 =  EstudianteDAO.recuperarEstudiantesDirector(academicoAvances.getIdAcademico());
            EstudianteRespuesta respuestaTotal = new EstudianteRespuesta();
            if(respuesta1.getCodigoRespuesta()== Constantes.OPERACION_EXITOSA && respuesta2.getCodigoRespuesta()== Constantes.OPERACION_EXITOSA){
                respuestaTotal.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                respuestaTotal.setEstudiantes(new ArrayList());
                respuestaTotal.getEstudiantes().addAll(respuesta1.getEstudiantes());
                respuestaTotal.getEstudiantes().addAll(respuesta2.getEstudiantes());
                ArrayList<Estudiante> listaSinDuplicados = new ArrayList();
                for(Estudiante estudiante: respuestaTotal.getEstudiantes()){
                    if(!estaRepetido(estudiante, listaSinDuplicados)){
                        listaSinDuplicados.add(estudiante);
                    }
                }
                respuestaTotal.setEstudiantes(listaSinDuplicados);
            }else{
                respuestaTotal.setCodigoRespuesta(Constantes.ERROR_CONEXION);
            }
            respuesta = respuestaTotal;
        }
        switch(respuesta.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error de conexión", 
                            "Error en la conexión con la base de datos", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error de consulta", 
                            "Por el momento no se puede obtener información de la base de datos", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                estudiantes.addAll(respuesta.getEstudiantes());
                cmbBoxEstudiante.setItems(estudiantes);
                break;
        }*/
    }
    
    private void recuperarAvances(Estudiante estudiante){
        AvanceRespuesta avancesBD = AvanceDAO.obtenerAvances(estudiante.getIdEstudiante());
        switch(avancesBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error de conexión", 
                            "Error en la conexión con la base de datos", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error de consulta", 
                            "Por el momento no se puede obtener información de la base de datos", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                //cargarAvances(avancesBD.getAvances());
                break;
        }
    }
}
