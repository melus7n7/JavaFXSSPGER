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
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.dao.AnteproyectoDAO;
import javafxsspger.modelo.pojo.Anteproyecto;
import javafxsspger.modelo.pojo.AnteproyectoRespuesta;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;


public class FXMLAnteproyectosController implements Initializable {

    @FXML
    private VBox vBoxListaAnteproyectosPublicados;
    
    private ObservableList<Pane> listaElementosAnteproyectos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarElementos();
    }
    
    private void cargarElementos(){
        listaElementosAnteproyectos = FXCollections.observableArrayList();
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
    
    private void mostrarElementos (ArrayList <Anteproyecto> anteproyectos){
        for (int i=0; i<anteproyectos.size(); i++){
            FXMLLoader fmxlLoaderAnteproyecto = new FXMLLoader();
            fmxlLoaderAnteproyecto.setLocation(JavaFXSSPGER.class.getResource("vistas/FXMLAnteproyectoElemento.fxml"));
            try{
                Pane pane = fmxlLoaderAnteproyecto.load();
                FXMLAnteproyectoElementoController elementoEnLista = fmxlLoaderAnteproyecto.getController();
                elementoEnLista.setElementoAnteproyecto(anteproyectos.get(i));
                listaElementosAnteproyectos.addAll(pane);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        vBoxListaAnteproyectosPublicados.getChildren().addAll(listaElementosAnteproyectos);
    }

    @FXML
    private void clicAnteproyectosPropiosPublicados(ActionEvent event) {
    }

    @FXML
    private void clicAnteproyectosPorCorregir(ActionEvent event) {
    }

    @FXML
    private void clicCrearAnteproyecto(ActionEvent event) {
    }

    @FXML
    private void clicRegresarMenuPrincipal(MouseEvent event) {
        Stage escenarioBase = (Stage)vBoxListaAnteproyectosPublicados.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializarEscena("vistas/FXMLMenuPrincipalAcademico.fxml"));
        escenarioBase.setTitle("Menú principal");
        escenarioBase.show();
    }
    
}
