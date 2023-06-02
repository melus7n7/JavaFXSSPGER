/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/06/2023
*Fecha de modificación: 02/06/2023
*Descripción: Controlador de la vista de la lista de avances
*/
package javafxsspger.controladores;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafxsspger.modelo.dao.EstudianteDAO;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.modelo.pojo.EstudianteRespuesta;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;


public class FXMLAvancesController implements Initializable {

    private Academico academicoAvances;
    private ObservableList<Estudiante> estudiantes;
    
    @FXML
    private ComboBox<Estudiante> cmbBoxEstudiante;
    @FXML
    private VBox vBoxListaActividades;
    @FXML
    private Label lblTitulo;
    @FXML
    private Button bttCrearAvance;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void clicCrearAvance(ActionEvent event) {
    }

    @FXML
    private void clicMostrarAvances(ActionEvent event) {
    }

    @FXML
    private void clicRegresar(MouseEvent event) {
    }
    
    public void inicializarInformacionAcademico(Academico academico){
        this.academicoAvances = academico;
        bttCrearAvance.setVisible(false);
        cargarEstudiantesAcademico();
    }
    
    private void cargarEstudiantesAcademico(){
        estudiantes = FXCollections.observableArrayList();
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
                HashSet<Estudiante> conjunto = new HashSet(respuestaTotal.getEstudiantes());
                ArrayList<Estudiante> listaSinDuplicados = new ArrayList<>(conjunto);
                respuestaTotal.setEstudiantes(listaSinDuplicados);
            }else{
                respuestaTotal.setCodigoRespuesta(Constantes.ERROR_CONEXION);
            }
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
        }
    }
}
