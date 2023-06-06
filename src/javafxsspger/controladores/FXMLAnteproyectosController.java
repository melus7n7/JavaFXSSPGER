/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 23/05/2023
*Descripción: Controlador de la vista de los Anteproyectos Publicados
*/

package javafxsspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.interfaces.INotificacionAnteproyectos;
import javafxsspger.modelo.dao.AnteproyectoDAO;
import javafxsspger.modelo.pojo.Anteproyecto;
import javafxsspger.modelo.pojo.AnteproyectoRespuesta;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.modelo.pojo.Usuario;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;


public class FXMLAnteproyectosController implements Initializable, INotificacionAnteproyectos {

    private Academico usuarioAcademico;
    private Estudiante usuarioEstudiante;
    private int numeroPantalla;
    private int tipoUsuario;
    
    @FXML
    private VBox vBoxListaAnteproyectosPublicados;
    @FXML
    private Button btnAnteproyectoPorCorregir;
    @FXML
    private Button btnAnteproyectosPublicados;
    @FXML
    private Label lblTitulo;
    @FXML
    private Button btnAnteproyectosPropios;
    @FXML
    private ScrollPane scrPaneContenedorAnteproyectos;
    @FXML
    private Button bttCrearAnteproyecto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.numeroPantalla = Constantes.ES_PUBLICADO;
    }

    @FXML
    private void clicAnteproyectosPropiosPublicados(ActionEvent event) {
        this.numeroPantalla = Constantes.ES_PROPIO;
        lblTitulo.setText("Anteproyectos Propios");
        btnAnteproyectosPublicados.setDisable(false);
        btnAnteproyectoPorCorregir.setDisable(false);
        btnAnteproyectosPropios.setDisable(true);
        cargarElementosPropios();
    }

    @FXML
    private void clicAnteproyectosPorCorregir(ActionEvent event) {
        this.numeroPantalla = Constantes.ES_POR_CORREGIR;
        lblTitulo.setText("Anteproyectos Por Corregir");
        btnAnteproyectosPublicados.setDisable(false);
        btnAnteproyectosPropios.setDisable(false);
        btnAnteproyectoPorCorregir.setDisable(true);
        cargarElementosPorCorregir();
    }
    
    @FXML
    private void clicAnteproyectosPublicados(ActionEvent event) {
        this.numeroPantalla = Constantes.ES_PUBLICADO;
        lblTitulo.setText("Anteproyectos Publicados");
        btnAnteproyectosPublicados.setDisable(true);
        btnAnteproyectoPorCorregir.setDisable(false);
        btnAnteproyectosPropios.setDisable(false);
        cargarElementosPublicados();
    }

    @FXML
    private void clicCrearAnteproyecto(ActionEvent event) {
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLCreacionAnteproyecto.fxml"));
            Parent vista = accesoControlador.load();
            FXMLCreacionAnteproyectoController creacionAnteproyecto = accesoControlador.getController();
            creacionAnteproyecto.inicializarPantalla(usuarioAcademico,this);
            
            Stage escenarioBase = new Stage();
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Creación Anteproyecto");
            escenarioBase.initModality(Modality.APPLICATION_MODAL);
            escenarioBase.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicRegresarMenuPrincipal(MouseEvent event) {
        switch(tipoUsuario){
            case Constantes.ACADEMICO:
                regresarMenuPrincipalAcademico();
                break;
            case Constantes.ESTUDIANTE:
                regresarMenuPrincipalEstudiante();
                break;
            case Constantes.INVITADO:
                regresarInicioSesion();
                break;
        }
    }

    @Override
    public void notificarCargarAnteproyectos() {
        switch(numeroPantalla){
            case Constantes.ES_POR_CORREGIR:
                cargarElementosPorCorregir();
                break;
            case Constantes.ES_PROPIO:
                cargarElementosPropios();
                break;
            case Constantes.ES_PUBLICADO:
                cargarElementosPublicados();
                break;
        }
    }
    
    public void inicializarInformacion(Academico usuarioAcademico){
        this.tipoUsuario = Constantes.ACADEMICO;
        this.usuarioAcademico = usuarioAcademico;
        if(!usuarioAcademico.isEsResponsableCA()){
            btnAnteproyectoPorCorregir.setVisible(false);
        }
        cargarElementosPublicados();
    }
    
    
    public void mostrarPantallaPublicados(int tipoUsuario, Estudiante estudiante){
        this.usuarioEstudiante = estudiante;
        this.tipoUsuario = tipoUsuario;
        btnAnteproyectoPorCorregir.setVisible(false);
        btnAnteproyectosPropios.setVisible(false);
        bttCrearAnteproyecto.setVisible(false);
        cargarElementosPublicados();
    }
    
    private void cargarElementosPublicados(){
        vBoxListaAnteproyectosPublicados.getChildren().clear();
        AnteproyectoRespuesta respuestaBD = AnteproyectoDAO.obtenerAnteproyectosPublicados();
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
                    mostrarElementos(respuestaBD.getAnteproyectos());
                break;
        }
    }
    
    private void cargarElementosPorCorregir(){
        vBoxListaAnteproyectosPublicados.getChildren().clear();
        AnteproyectoRespuesta respuestaBD = AnteproyectoDAO.obtenerAnteproyectosPorCorregir(usuarioAcademico);
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
                    mostrarElementos(respuestaBD.getAnteproyectos());
                break;
        }
    }
    
    private void cargarElementosPropios(){
        vBoxListaAnteproyectosPublicados.getChildren().clear();
        AnteproyectoRespuesta respuestaBD = AnteproyectoDAO.obtenerAnteproyectosPropios(usuarioAcademico.getIdAcademico());
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
                    mostrarElementos(respuestaBD.getAnteproyectos());
                break;
        }
    }
    
    private void mostrarElementos (ArrayList <Anteproyecto> anteproyectos){
        int altoVBox = 0;
        for (int i=0; i<anteproyectos.size(); i++){
            FXMLLoader fmxlLoaderAnteproyecto = new FXMLLoader();
            fmxlLoaderAnteproyecto.setLocation(JavaFXSSPGER.class.getResource("vistas/FXMLAnteproyectoElemento.fxml"));
            try{
                Pane pane = fmxlLoaderAnteproyecto.load();
                FXMLAnteproyectoElementoController elementoEnLista = fmxlLoaderAnteproyecto.getController();
                switch(tipoUsuario){
                    case Constantes.ACADEMICO:
                        elementoEnLista.setElementoAnteproyecto(anteproyectos.get(i), numeroPantalla, this, usuarioAcademico.getIdAcademico());
                        break;
                    case Constantes.INVITADO:
                        elementoEnLista.setElementoAnteproyectoPublicados(anteproyectos.get(i), tipoUsuario, null);
                        break;
                    case Constantes.ESTUDIANTE:
                        elementoEnLista.setElementoAnteproyectoPublicados(anteproyectos.get(i), tipoUsuario, usuarioEstudiante);
                        break;
                }
                altoVBox += pane.getPrefHeight();
                vBoxListaAnteproyectosPublicados.setPrefHeight(altoVBox);
                vBoxListaAnteproyectosPublicados.getChildren().add(pane);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        if(vBoxListaAnteproyectosPublicados.getPrefHeight() < scrPaneContenedorAnteproyectos.getPrefHeight()){
            vBoxListaAnteproyectosPublicados.setPrefHeight(scrPaneContenedorAnteproyectos.getPrefHeight());
        }
    }
    
    private void regresarMenuPrincipalAcademico(){
        Stage escenarioBase = (Stage)vBoxListaAnteproyectosPublicados.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLMenuPrincipalAcademico.fxml"));
            Parent vista = accesoControlador.load();
            FXMLMenuPrincipalAcademicoController menuPrincipalAcademico = accesoControlador.getController();
            menuPrincipalAcademico.inicializarInformacionConAcademico(usuarioAcademico);
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Menú Principal");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void regresarInicioSesion(){
        Stage escenarioBase = (Stage)lblTitulo.getScene().getWindow();
            try {
                FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLInicioSesion.fxml"));
                Parent vista = accesoControlador.load();
                escenarioBase.setScene(new Scene (vista));
                escenarioBase.setTitle("Inicio Sesión");
                escenarioBase.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }
    
    private void regresarMenuPrincipalEstudiante(){
        Stage escenarioBase = (Stage)lblTitulo.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLMenuPrincipalEstudiante.fxml"));
            Parent vista = accesoControlador.load();
            FXMLMenuPrincipalEstudianteController menuPrincipalEstudiante = accesoControlador.getController();
            menuPrincipalEstudiante.inicializarInformacionConEstudiante(usuarioEstudiante);
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Menú Principal");
            escenarioBase.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
