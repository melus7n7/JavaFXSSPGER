/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 31/05/2023
*Fecha de modificación: 31/05/2023
*Descripción: Controlador de la vista de la asignación de estudiantes
*/
package javafxsspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.dao.AnteproyectoDAO;
import javafxsspger.modelo.dao.EncargadosTrabajoRecepcionalDAO;
import javafxsspger.modelo.dao.EstudianteDAO;
import javafxsspger.modelo.dao.TrabajoRecepcionalDAO;
import javafxsspger.modelo.pojo.Anteproyecto;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.modelo.pojo.EstudianteRespuesta;
import javafxsspger.modelo.pojo.TrabajoRecepcional;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;

public class FXMLAsignacionEstudiantesController implements Initializable {

    private Anteproyecto anteproyectoAsignar;
    private ArrayList<Estudiante> estudiantesAsignados;
    private ObservableList<Estudiante> estudiantesPosibles;
    
    @FXML
    private Label lblEstudiante1;
    @FXML
    private Label lblEstudiante2;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblNombreAnteproyecto;
    @FXML
    private TextField txtFieldBuscador;
    @FXML
    private Button bttEliminarEstudiante1;
    @FXML
    private Button bttEliminarEstudiante2;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colMatricula;
    @FXML
    private TableView<Estudiante> tblViewPosiblesEstudiantes;
    @FXML
    private Pane pnEstudiante2;
    @FXML
    private Label lblEstudiantesMaximos;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }

    @FXML
    private void clicEliminarEstudiante1(ActionEvent event) {
        eliminarEstudiante(estudiantesAsignados.get(0));
    }

    @FXML
    private void clicEliminarEstudiante2(ActionEvent event) {
        eliminarEstudiante(estudiantesAsignados.get(1));
    }

    @FXML
    private void clicRegresarAnteproyectos(MouseEvent event) {
        Stage escenarioBase = (Stage) lblNombreAnteproyecto.getScene().getWindow();
        escenarioBase.close();
        
    }

    @FXML
    private void clicAgregarEstudiante(ActionEvent event) {
        Estudiante estudiante = tblViewPosiblesEstudiantes.getSelectionModel().getSelectedItem();
        if (estudiante != null){
            if(estudiantesAsignados.size()>=anteproyectoAsignar.getNoEstudiantesMaximo()){
                Utilidades.mostrarDialogoSimple("Error máximo de estudiantes alcanzado", 
                        "No se pueden añadir más estudiantes debido a que el máximo de estudiantes ha sido alcanzado", 
                        Alert.AlertType.WARNING);
            }else{
                validarExistenciaTrabajoRecepcional(estudiante);
            }
        }else{
            Utilidades.mostrarDialogoSimple("Selecciona un estudiante", 
                    "No hay un estudiante seleccionado para añadir", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicBuscarEstudiante(ActionEvent event) {
        filtrarBusqueda();
    }
    
    public void inicializarDetalles(Anteproyecto anteproyecto){
        this.anteproyectoAsignar = anteproyecto;
        recuperarEstudiantes();
        recuperarEstudiantesPosibles();
        cargarDetallesAnteproyecto();
    }
   
    private void recuperarEstudiantes(){
        EstudianteRespuesta respuestaBD = EstudianteDAO.recuperarEstudiantesAnteproyecto(anteproyectoAsignar.getIdAnteproyecto());
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
                    this.estudiantesAsignados = respuestaBD.getEstudiantes();
                    cargarDetallesEstudiantesAsignados();
                break;
        }
    }
    
    private void recuperarEstudiantesPosibles(){
        estudiantesPosibles = FXCollections.observableArrayList();
        EstudianteRespuesta respuestaBD = EstudianteDAO.recuperarEstudiantesPosibles(estudiantesAsignados);
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
                    estudiantesPosibles.addAll(respuestaBD.getEstudiantes());
                    tblViewPosiblesEstudiantes.setItems(estudiantesPosibles);
                break;
        }
    }
    
    private void cargarDetallesAnteproyecto(){
        lblEstudiantesMaximos.setText(anteproyectoAsignar.getNoEstudiantesMaximo()+"");
        lblNombreAnteproyecto.setText(anteproyectoAsignar.getTitulo());
        lblFecha.setText(Utilidades.darFormatofechas(anteproyectoAsignar.getFechaAprobacion()));
    }
    
    private void cargarDetallesEstudiantesAsignados(){
        lblEstudiante1.setText("");
        bttEliminarEstudiante1.setVisible(false);
        lblEstudiante2.setText("");
        bttEliminarEstudiante2.setVisible(false);
        
        if(anteproyectoAsignar.getNoEstudiantesMaximo()==1){
            pnEstudiante2.setVisible(false);
        }
        if(!estudiantesAsignados.isEmpty()){
            if(estudiantesAsignados.size()==2){
                lblEstudiante2.setText(estudiantesAsignados.get(1).toString());
                bttEliminarEstudiante2.setVisible(true);
            }
            lblEstudiante1.setText(estudiantesAsignados.get(0).toString());
            bttEliminarEstudiante1.setVisible(true);
        }
    }
    
    private void filtrarBusqueda(){
        if(!estudiantesPosibles.isEmpty()){
            FilteredList<Estudiante> filtradoEstudiantes = new FilteredList<>(estudiantesPosibles, p -> true);
            filtradoEstudiantes.setPredicate(estudianteFiltro -> {
            if(txtFieldBuscador.getText() == null || txtFieldBuscador.getText().isEmpty()){
                return true;
            }
            String lowerNewValue = txtFieldBuscador.getText().toLowerCase();
            return estudianteFiltro.getMatricula().toLowerCase().contains(lowerNewValue);
            });
            SortedList<Estudiante> sortedListaAlumnos = new SortedList<>(filtradoEstudiantes);
            sortedListaAlumnos.comparatorProperty().bind(tblViewPosiblesEstudiantes.comparatorProperty());
            tblViewPosiblesEstudiantes.setItems(sortedListaAlumnos);
        }
    }
    
    private void configurarTabla(){
        colMatricula.setCellValueFactory(new PropertyValueFactory("matricula"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombreCompleto"));
    }
    
    private void validarExistenciaTrabajoRecepcional(Estudiante estudiante){
        if(anteproyectoAsignar.getIdTrabajoRecepcional() == 0){
            anteproyectoAsignar.setIdTrabajoRecepcional(crearTrabajoRecepcional());
        }
        estudiante.setIdAnteproyecto(anteproyectoAsignar.getIdAnteproyecto());
        estudiante.setIdTrabajoRecepcional(anteproyectoAsignar.getIdTrabajoRecepcional());
        agregarEstudiante(estudiante);
    }
    
    private void agregarEstudiante(Estudiante estudiante){
        estudiante.setIdAnteproyecto(anteproyectoAsignar.getIdAnteproyecto());
        int respuestaBD = EstudianteDAO.asignarAnteproyectoEstudiante(estudiante);
        switch(respuestaBD){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Sin Conexion", 
                        "Lo sentimos por el momento no tiene conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error al asignar estudiante", 
                        "Hubo un error al asignar el estudiante, por favor inténtelo más tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    agregarEstudianteAnteproyecto(anteproyectoAsignar.getNoEstudiantesAsignados()+1, estudiante);
                break;
        }
    }
    
    
    private void eliminarEstudiante(Estudiante estudiante){
        int respuestaBD = EstudianteDAO.eliminarAnteproyectoEstudiante(estudiante);
        switch(respuestaBD){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Sin Conexion", 
                        "Lo sentimos por el momento no tiene conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error al eliminar estudiante", 
                        "Hubo un error al eliminar el estudiante, por favor inténtelo más tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    anteproyectoAsignar.setNoEstudiantesAsignados(anteproyectoAsignar.getNoEstudiantesAsignados()-1);
                    disminuirEstudianteAnteproyecto(anteproyectoAsignar.getNoEstudiantesAsignados(),estudiante);
                    if(anteproyectoAsignar.getNoEstudiantesAsignados()==0){
                        eliminarTrabajoRecepcional();
                        anteproyectoAsignar.setIdTrabajoRecepcional(0);
                    }
                    
                break;
        }
    }
    
    private int crearTrabajoRecepcional(){
        int idTrabajoRecepcional = 0;
        TrabajoRecepcional respuesta = TrabajoRecepcionalDAO.crearTrabajoRecepcional(anteproyectoAsignar.getIdAnteproyecto());
        switch(respuesta.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Sin Conexion", 
                        "Lo sentimos por el momento no tiene conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error al crear el trabajo recepcional", 
                        "Hubo un error al crear el trabajo recepcional, por favor inténtelo más tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                idTrabajoRecepcional = respuesta.getIdTrabajoRecepcional();
                asignarEncargados(idTrabajoRecepcional);
                break;
        }
        return idTrabajoRecepcional;
    }
    
    private void asignarEncargados(int idTrabajoRecepcional){
        int respuesta = EncargadosTrabajoRecepcionalDAO.guardarEncargadosTrabajoRecepcional(anteproyectoAsignar.getIdAnteproyecto(), idTrabajoRecepcional);
        switch(respuesta){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Sin Conexion", 
                        "Lo sentimos por el momento no tiene conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error al asignar directores trabajo recepcional", 
                        "Hubo un error al asignar directores al trabajo recepcional, por favor inténtelo más tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                break;
        }
    }
    
    private void eliminarTrabajoRecepcional(){
        int respuesta = TrabajoRecepcionalDAO.eliminarTrabajoRecepcional(anteproyectoAsignar.getIdAnteproyecto());
        switch(respuesta){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Sin Conexion", 
                        "Lo sentimos por el momento no tiene conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error al eliminar trabajo recepcional", 
                        "Hubo un error al eliminar el trabajo recepcional, por favor inténtelo más tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                break;
        }
    }
    
    private void agregarEstudianteAnteproyecto(int noEstudiantesAsignados, Estudiante estudiante){
        anteproyectoAsignar.setNoEstudiantesAsignados(noEstudiantesAsignados);
        int respuestaBD = AnteproyectoDAO.cambiarEstudianteAnteproyecto(anteproyectoAsignar);
        switch(respuestaBD){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Sin Conexion", 
                        "Lo sentimos por el momento no tiene conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error al eliminar estudiante", 
                        "Hubo un error al eliminar el estudiante, por favor inténtelo más tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    Utilidades.mostrarDialogoSimple("Estudiante asignado", 
                        "Se asignó correctamente el estudiante " + estudiante.getNombreCompleto(), 
                        Alert.AlertType.INFORMATION);
                    txtFieldBuscador.setText("");
                    recuperarEstudiantes();
                    recuperarEstudiantesPosibles();
                /*else{
                    Utilidades.mostrarDialogoSimple("Estudiante eliminado", 
                        "Se eliminó correctamente el estudiante " + estudiante.getNombreCompleto(), 
                        Alert.AlertType.INFORMATION);
                }*/
                break;
        }
    }
    
    private void disminuirEstudianteAnteproyecto(int noEstudiantesAsignados, Estudiante estudiante){
        anteproyectoAsignar.setNoEstudiantesAsignados(noEstudiantesAsignados);
        int respuestaBD = AnteproyectoDAO.cambiarEstudianteAnteproyecto(anteproyectoAsignar);
        switch(respuestaBD){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Sin Conexion", 
                        "Lo sentimos por el momento no tiene conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error al eliminar estudiante", 
                        "Hubo un error al eliminar el estudiante, por favor inténtelo más tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    Utilidades.mostrarDialogoSimple("Estudiante eliminado", 
                        "Se eliminó correctamente el estudiante " + estudiante.getNombreCompleto(), 
                        Alert.AlertType.INFORMATION);
                    txtFieldBuscador.setText("");
                    recuperarEstudiantes();
                    recuperarEstudiantesPosibles();
                break;
        }
    }
}
