/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 31/05/2023
*Fecha de modificación: 31/05/2023
*Descripción: Controlador de la vista de la lista de los anteproyectos que se le pueden asignar estudiantes
*/
package javafxsspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.dao.AnteproyectoDAO;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Anteproyecto;
import javafxsspger.modelo.pojo.AnteproyectoRespuesta;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;


public class FXMLAnteproyectosParaAsignarController implements Initializable {
    
    private Academico usuarioAcademico;

    @FXML
    private VBox vBoxListaAnteproyectos;
    @FXML
    private Label lblTitulo;
    @FXML
    private ScrollPane scrPaneContenedorAnteproyectos;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void clicRegresarMenuPrincipal(MouseEvent event) {
        Stage escenarioBase = (Stage)lblTitulo.getScene().getWindow();
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
        cargarAnteproyectosEnDesarrolloPublicados();
    }
    
    private void cargarAnteproyectosEnDesarrolloPublicados(){
        AnteproyectoRespuesta respuestaBD = AnteproyectoDAO.recuperarAnteproyectosEnDesarrolloPublicados(usuarioAcademico.getIdAcademico());
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
                elementoEnLista.setElementoAnteproyecto(anteproyectos.get(i), Constantes.ES_ASIGNAR_ESTUDIANTES, null, usuarioAcademico.getIdAcademico());
                altoVBox += pane.getPrefHeight();
                vBoxListaAnteproyectos.setPrefHeight(altoVBox);
                vBoxListaAnteproyectos.getChildren().add(pane);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        if(vBoxListaAnteproyectos.getPrefHeight() < scrPaneContenedorAnteproyectos.getPrefHeight()){
            vBoxListaAnteproyectos.setPrefHeight(scrPaneContenedorAnteproyectos.getPrefHeight());
        }
    }
    
}
