/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 05/05/2023
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.dao.AnteproyectoDAO;
import javafxsspger.modelo.pojo.Anteproyecto;
import javafxsspger.modelo.pojo.AnteproyectoRespuesta;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Usuario;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;


public class FXMLAnteproyectosController implements Initializable {

    @FXML
    private VBox vBoxListaAnteproyectosPublicados;
    
    private Academico usuarioAcademico;
    private boolean esPorCorregir;
    @FXML
    private Button btnAnteproyectoPorCorregir;
    @FXML
    private Button btnAnteproyectosPublicados;
    @FXML
    private Label lblTitulo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.esPorCorregir = false;
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
        AnteproyectoRespuesta respuestaBD = AnteproyectoDAO.obtenerAnteproyectosPorCorregir();
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
        for (int i=0; i<anteproyectos.size(); i++){
            FXMLLoader fmxlLoaderAnteproyecto = new FXMLLoader();
            fmxlLoaderAnteproyecto.setLocation(JavaFXSSPGER.class.getResource("vistas/FXMLAnteproyectoElemento.fxml"));
            try{
                Pane pane = fmxlLoaderAnteproyecto.load();
                FXMLAnteproyectoElementoController elementoEnLista = fmxlLoaderAnteproyecto.getController();
                elementoEnLista.setElementoAnteproyecto(anteproyectos.get(i), esPorCorregir);
                vBoxListaAnteproyectosPublicados.getChildren().add(pane);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void clicAnteproyectosPropiosPublicados(ActionEvent event) {
    }

    @FXML
    private void clicAnteproyectosPorCorregir(ActionEvent event) {
        this.esPorCorregir = true;
        lblTitulo.setText("Anteproyectos Por Corregir");
        btnAnteproyectosPublicados.setDisable(false);
        btnAnteproyectoPorCorregir.setDisable(true);
        cargarElementosPorCorregir();
    }

    @FXML
    private void clicCrearAnteproyecto(ActionEvent event) {
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLCreacionAnteproyecto.fxml"));
            Parent vista = accesoControlador.load();
            FXMLCreacionAnteproyectoController creacionAnteproyecto = accesoControlador.getController();
            creacionAnteproyecto.inicializarPantalla(usuarioAcademico);
            
            Stage escenarioBase = new Stage();
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Creación Anteproyecto");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicRegresarMenuPrincipal(MouseEvent event) {
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
    
    public void inicializarInformacion(Academico usuarioAcademico){
        this.usuarioAcademico = usuarioAcademico;
    }

    @FXML
    private void clicAnteproyectosPublicados(ActionEvent event) {
        this.esPorCorregir = false;
        lblTitulo.setText("Anteproyectos Publicados");
        btnAnteproyectosPublicados.setDisable(true);
        btnAnteproyectoPorCorregir.setDisable(false);
        cargarElementosPublicados();
    }
    
}
