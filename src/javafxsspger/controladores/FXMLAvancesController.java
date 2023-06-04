/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/06/2023
*Fecha de modificación: 03/06/2023
*Descripción: Controlador de la vista de la lista de avances
*/
package javafxsspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.dao.AvanceDAO;
import javafxsspger.modelo.dao.EstudianteDAO;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Avance;
import javafxsspger.modelo.pojo.AvanceRespuesta;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.modelo.pojo.EstudianteRespuesta;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;


public class FXMLAvancesController implements Initializable {

    private Academico academicoAvances;
    private Estudiante estudianteAvances;
    private ObservableList<Estudiante> estudiantes;
    private boolean esAcademico;
    
    @FXML
    private ComboBox<Estudiante> cmbBoxEstudiante;
    @FXML
    private Label lblTitulo;
    @FXML
    private Button bttCrearAvance;
    @FXML
    private VBox vBoxListaAvances;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void clicCrearAvance(ActionEvent event) {
    }

    @FXML
    private void clicMostrarAvances(ActionEvent event) {
        Estudiante estudiante = cmbBoxEstudiante.getSelectionModel().getSelectedItem();
        if(estudiante != null){
            recuperarAvances(estudiante);
        }else{
            Utilidades.mostrarDialogoSimple("Estudiante no seleccionado", 
                "No se ha seleccionado ninguna opción de la lista, seleccione uno para continuar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicRegresar(MouseEvent event) {
        //Cambiar
        if(esAcademico){
            irMenuPrincipalAcademico();
        }else{
            regresarMenuEstudiante();
        }
            
    }
    
    private void irMenuPrincipalAcademico(){
        Stage escenarioBase = (Stage)lblTitulo.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLMenuPrincipalAcademico.fxml"));
            Parent vista = accesoControlador.load();
            FXMLMenuPrincipalAcademicoController menuPrincipalAcademico = accesoControlador.getController();
            menuPrincipalAcademico.inicializarInformacionConAcademico(academicoAvances);
            
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Menú Principal");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void regresarMenuEstudiante(){
        Stage escenarioBase = (Stage)lblTitulo.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLMenuPrincipalEstudiante.fxml"));
            Parent vista = accesoControlador.load();
            FXMLMenuPrincipalEstudianteController menuPrincipalEstudiante = accesoControlador.getController();
            menuPrincipalEstudiante.inicializarInformacionConEstudiante(estudianteAvances);
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Menú Principal");
            escenarioBase.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public void inicializarInformacionAcademico(Academico academico){
        this.academicoAvances = academico;
        this.esAcademico = true;
        bttCrearAvance.setVisible(false);
        cargarEstudiantesAcademico();
    }
    
    public void inicializarInformacionEstudiante(Estudiante estudiante){
        this.estudianteAvances = estudiante;
        this.esAcademico = false;
        bttCrearAvance.setVisible(true);
        estudiantes = FXCollections.observableArrayList();
        ArrayList<Estudiante> estudianteLista = new ArrayList();
        estudianteLista.add(estudiante);
        estudiantes.addAll(estudianteLista);
        cmbBoxEstudiante.setItems(estudiantes);
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
        }
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
                cargarAvances(avancesBD.getAvances());
                break;
        }
    }
    
    private void cargarAvances(ArrayList<Avance> avances){
        for (int i=0; i<avances.size(); i++){
            try{
                FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLAvanceElemento.fxml"));
                Pane pane = accesoControlador.load();
                FXMLAvanceElementoController avanceElementoController = accesoControlador.getController();
                if(esAcademico){
                    avanceElementoController.incializarElementoAcademico(avances.get(i),academicoAvances);
                }else{
                    avanceElementoController.incializarElementoEstudiante(avances.get(i),estudianteAvances);
                }
                vBoxListaAvances.getChildren().add(pane);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    
    private boolean estaRepetido(Estudiante estudiante, ArrayList<Estudiante> estudiantes){
        for(Estudiante estudianteLista: estudiantes){
            if(estudiante.getIdEstudiante() == estudianteLista.getIdEstudiante()){
                return true;
            }
        }
        return false;
    }
}
