/*
*Autor: Martínez Aguilar Sulem, Montiel Salas Jesús Jacob
*Fecha de creación: 05/06/2023
*Fecha de modificación: 05/06/2023
*Descripción: Controlador de la vista de los trabajos recepcionales
*/
package javafxsspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.dao.AvanceDAO;
import javafxsspger.modelo.dao.EstudianteDAO;
import javafxsspger.modelo.dao.TrabajoRecepcionalDAO;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Avance;
import javafxsspger.modelo.pojo.AvanceRespuesta;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.modelo.pojo.EstudianteRespuesta;
import javafxsspger.modelo.pojo.TrabajoRecepcional;
import javafxsspger.modelo.pojo.TrabajoRecepcionalRespuesta;
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
        cargarTrabajosRecepcionales();
    }
    
    private void cargarTrabajosRecepcionales(){
        trabajosRecepcionales = FXCollections.observableArrayList();
        TrabajoRecepcionalRespuesta respuesta = new TrabajoRecepcionalRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if(usuarioAcademico.isEsProfesor()){
            respuesta = TrabajoRecepcionalDAO.obtenerNombresTrabajosRecepcionalesProfesor(usuarioAcademico.getIdAcademico());
        }
        if(usuarioAcademico.isEsDirector()){
            respuesta = TrabajoRecepcionalDAO.obtenerNombresTrabajosRecepcionalesDirector(usuarioAcademico.getIdAcademico());
        }
        if(usuarioAcademico.isEsDirector() && usuarioAcademico.isEsProfesor()){
            TrabajoRecepcionalRespuesta respuesta1 = TrabajoRecepcionalDAO.obtenerNombresTrabajosRecepcionalesProfesor(usuarioAcademico.getIdAcademico());
            TrabajoRecepcionalRespuesta respuesta2 =  TrabajoRecepcionalDAO.obtenerNombresTrabajosRecepcionalesDirector(usuarioAcademico.getIdAcademico());
            TrabajoRecepcionalRespuesta respuestaTotal = new TrabajoRecepcionalRespuesta();
            if(respuesta1.getCodigoRespuesta()== Constantes.OPERACION_EXITOSA && respuesta2.getCodigoRespuesta()== Constantes.OPERACION_EXITOSA){
                respuestaTotal.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                respuestaTotal.setTrabajosRecepcionales(new ArrayList());
                respuestaTotal.getTrabajosRecepcionales().addAll(respuesta1.getTrabajosRecepcionales());
                respuestaTotal.getTrabajosRecepcionales().addAll(respuesta2.getTrabajosRecepcionales());
                ArrayList<TrabajoRecepcional> listaSinDuplicados = new ArrayList();
                for(TrabajoRecepcional trabajo: respuestaTotal.getTrabajosRecepcionales()){
                    if(!estaRepetido(trabajo, listaSinDuplicados)){
                        listaSinDuplicados.add(trabajo);
                    }
                }
                respuestaTotal.setTrabajosRecepcionales(listaSinDuplicados);
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
                cargarTrabajosRecepcionales(respuesta.getTrabajosRecepcionales());
                break;
        }
    }
    
    private void cargarTrabajosRecepcionales (ArrayList<TrabajoRecepcional> trabajos){
        int altoVBox = 0;
        for (int i=0; i<trabajos.size(); i++){
            try{
                FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLAnteproyectoElemento.fxml"));
                Pane pane = accesoControlador.load();
                FXMLAnteproyectoElementoController trabajoController = accesoControlador.getController();
                trabajoController.setTrabajoRecepcional(trabajos.get(i));
                altoVBox += pane.getPrefHeight();
                vBoxListaTrabajos.setPrefHeight(altoVBox);
                vBoxListaTrabajos.getChildren().add(pane);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        if(vBoxListaTrabajos.getPrefHeight() < scrPaneContenedorTrabajos.getPrefHeight()){
            vBoxListaTrabajos.setPrefHeight(scrPaneContenedorTrabajos.getPrefHeight());
        }
    }
    
    private boolean estaRepetido(TrabajoRecepcional trabajoComprobar, ArrayList<TrabajoRecepcional> trabajos){
        for(TrabajoRecepcional trabajo: trabajos){
            if(trabajo.getIdTrabajoRecepcional()== trabajoComprobar.getIdTrabajoRecepcional()){
                return true;
            }
        }
        return false;
    }
}
