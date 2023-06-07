/*
*Autor: Montiel Salas Jesús Jacob
*Fecha de creación: 20/05/2023
*Fecha de modificación: 06/06/2023
*Descripción: Controlador de la vista de actividades
*/
package javafxsspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
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
import javafxsspger.modelo.dao.ActividadDAO;
import javafxsspger.modelo.dao.TrabajoRecepcionalDAO;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Actividad;
import javafxsspger.modelo.pojo.ActividadRespuesta;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.modelo.pojo.TrabajoRecepcional;
import javafxsspger.modelo.pojo.TrabajoRecepcionalRespuesta;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;

public class FXMLActividadesController implements Initializable {
    
    
    private Academico usuarioAcademico;
    private Estudiante usuarioEstudiante;
    @FXML
    private VBox vBoxListaActividades;
    @FXML
    private Label lblTitulo;
    @FXML
    private ComboBox<TrabajoRecepcional> cmbBoxTrabajosRecepcionales;
    private ObservableList<TrabajoRecepcional> trabajosRecepcionales;
    @FXML
    private Button bttCrearActividad;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    private void cargarInformacionTrabajosRecepcionales(){
        trabajosRecepcionales=FXCollections.observableArrayList();
        TrabajoRecepcionalRespuesta trabajosRecepcionalesBD = new TrabajoRecepcionalRespuesta(); 
        TrabajoRecepcionalRespuesta respuestaTotal = new TrabajoRecepcionalRespuesta();
        respuestaTotal.setTrabajosRecepcionales(new ArrayList());
        if(usuarioAcademico != null){
            if(usuarioAcademico.isEsDirector() && usuarioAcademico.isEsProfesor()){
                TrabajoRecepcionalRespuesta trabajosRecepcionalesProfesor = TrabajoRecepcionalDAO.obtenerNombresTrabajosRecepcionalesProfesor(this.usuarioAcademico.getIdAcademico());                                                 
                TrabajoRecepcionalRespuesta trabajosRecepcionalesDirector = TrabajoRecepcionalDAO.obtenerNombresTrabajosRecepcionalesDirector(this.usuarioAcademico.getIdAcademico()); 
                respuestaTotal.getTrabajosRecepcionales().addAll(trabajosRecepcionalesProfesor.getTrabajosRecepcionales());
                respuestaTotal.getTrabajosRecepcionales().addAll(trabajosRecepcionalesDirector.getTrabajosRecepcionales());
                if(trabajosRecepcionalesProfesor.getCodigoRespuesta() == Constantes.OPERACION_EXITOSA && trabajosRecepcionalesDirector.getCodigoRespuesta() == Constantes.OPERACION_EXITOSA){
                    respuestaTotal.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                }else{
                    respuestaTotal.setCodigoRespuesta(Constantes.ERROR_CONSULTA);                
                }
            }else if(usuarioAcademico.isEsDirector()){
                trabajosRecepcionalesBD = TrabajoRecepcionalDAO.obtenerNombresTrabajosRecepcionalesDirector(this.usuarioAcademico.getIdAcademico());           
                respuestaTotal.getTrabajosRecepcionales().addAll(trabajosRecepcionalesBD.getTrabajosRecepcionales());    
                respuestaTotal.setCodigoRespuesta(trabajosRecepcionalesBD.getCodigoRespuesta());
            }else if(usuarioAcademico.isEsProfesor()){
                trabajosRecepcionalesBD = TrabajoRecepcionalDAO.obtenerNombresTrabajosRecepcionalesProfesor(this.usuarioAcademico.getIdAcademico()); 
                respuestaTotal.getTrabajosRecepcionales().addAll(trabajosRecepcionalesBD.getTrabajosRecepcionales());                
                respuestaTotal.setCodigoRespuesta(trabajosRecepcionalesBD.getCodigoRespuesta());
            }
        }else{    
            trabajosRecepcionalesBD = TrabajoRecepcionalDAO.obtenerNombresTrabajosRecepcionalesEstudiante(this.usuarioEstudiante.getIdEstudiante());                      
            respuestaTotal.getTrabajosRecepcionales().addAll(trabajosRecepcionalesBD.getTrabajosRecepcionales());
            respuestaTotal.setCodigoRespuesta(trabajosRecepcionalesBD.getCodigoRespuesta());
        }
        ArrayList<TrabajoRecepcional> listaSinDuplicados = new ArrayList();
            for(TrabajoRecepcional trabajorecepcional: respuestaTotal.getTrabajosRecepcionales()){
                if(!estaRepetido(trabajorecepcional, listaSinDuplicados)){
                    listaSinDuplicados.add(trabajorecepcional);
                }
            }
        respuestaTotal.setTrabajosRecepcionales(listaSinDuplicados);
        cargarComboBox(respuestaTotal);
    }
    
    private void cargarComboBox(TrabajoRecepcionalRespuesta respuestaTotal){
        switch(respuestaTotal.getCodigoRespuesta()){
               case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion", "Por el momento no hay conexion", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error cargar los datos", "Intentelo mas tarde", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                trabajosRecepcionales.addAll(respuestaTotal.getTrabajosRecepcionales());
                cmbBoxTrabajosRecepcionales.setItems(trabajosRecepcionales);
                break;
            }
    }
    
    private boolean estaRepetido(TrabajoRecepcional trabajo, ArrayList<TrabajoRecepcional> trabajosrecepcionales){
        for(TrabajoRecepcional trabajoRecepcionalLista: trabajosrecepcionales){
            if(trabajo.getIdTrabajoRecepcional() == trabajoRecepcionalLista.getIdTrabajoRecepcional()){
                return true;
            }
        }
        return false;
    }
    
    public void inicializarInformacionEstudiante(Estudiante usuarioEstudiante){
       this.usuarioEstudiante = usuarioEstudiante;
       cargarInformacionTrabajosRecepcionales();
    }
    
    public void inicializarInformacionAcademico(Academico usuarioAcademico){
       this.usuarioAcademico = usuarioAcademico;
       bttCrearActividad.setVisible(false);
       cargarInformacionTrabajosRecepcionales();
       
    }

    private void regresarMenuAcademico(){
        Stage escenarioBase = (Stage)lblTitulo.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLMenuPrincipalAcademico.fxml"));
            Parent vista = accesoControlador.load();
            FXMLMenuPrincipalAcademicoController menuPrincipalAcademico = accesoControlador.getController();
            menuPrincipalAcademico.inicializarInformacionConAcademico(usuarioAcademico);
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Menú Principal");
            escenarioBase.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    private void regresarMenuEstudiante(){
        Stage escenarioBase = (Stage)lblTitulo.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLMenuPrincipalEstudiante.fxml"));
            Parent vista = accesoControlador.load();
            FXMLMenuPrincipalEstudianteController menuPrincipalEstudiante = accesoControlador.getController();
            menuPrincipalEstudiante.inicializarInformacion(usuarioEstudiante);
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Menú Principal");
            escenarioBase.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicMostrarActividades(ActionEvent event) {
        eliminarActividades();
        try{
            TrabajoRecepcional trabajoRecepcional = cmbBoxTrabajosRecepcionales.getSelectionModel().getSelectedItem();
            if(trabajoRecepcional == null){
                Utilidades.mostrarDialogoSimple("Error cargar los datos", "No se ha seleccionado ninguna opción de la lista, seleccione uno para continuar", Alert.AlertType.WARNING);                                            
            }else{
               int idTrabajoRecepcional = trabajoRecepcional.getIdTrabajoRecepcional();
               if(usuarioAcademico != null){
                    cargarActividadesDirector(idTrabajoRecepcional);
               }else{
                   cargarActividadesEstudiante(idTrabajoRecepcional);
               }
            }
        }catch(NullPointerException e){
            Utilidades.mostrarDialogoSimple("Error al cargar los datos", "No se ha seleccionado ninguna opción de la lista, seleccione uno para continuar", Alert.AlertType.WARNING);                            
        } 
    }
    
    private void eliminarActividades(){
        vBoxListaActividades.getChildren().clear();
    }
    
    private void cargarActividadesDirector(int idTrabajoRecepcional){
        trabajosRecepcionales.clear();
        cargarInformacionTrabajosRecepcionales();
        ActividadRespuesta respuestaBD = ActividadDAO.obtenerActividadesPorTrabajoRecepcionalAcademico(idTrabajoRecepcional);
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error Conexión", "No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos", "Hubo un error al cargar la información por favor inténtelo más tarde", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                mostrarActividades(respuestaBD.getActividades());
                break;
        }
    }
    
    private void cargarActividadesEstudiante(int idTrabajoRecepcional){
        trabajosRecepcionales.clear();
        cargarInformacionTrabajosRecepcionales();
        ActividadRespuesta respuestaBD = ActividadDAO.obtenerActividadesPorTrabajoRecepcionalEstudiante(idTrabajoRecepcional, usuarioEstudiante.getIdEstudiante());
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error Conexión", "No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos", "Hubo un error al cargar la información por favor inténtelo más tarde", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                mostrarActividades(respuestaBD.getActividades());
                break;
        }
    }
    
    private void mostrarActividades(ArrayList <Actividad> actividades){
        if(usuarioAcademico != null){
            if(usuarioAcademico.isEsDirector() || usuarioAcademico.isEsProfesor()){
                for (int i=0; i<actividades.size(); i++){
                    FXMLLoader accesoControlador = new FXMLLoader();
                    accesoControlador.setLocation(JavaFXSSPGER.class.getResource("vistas/FXMLActividadElemento.fxml"));
                    try{
                        Pane pane = accesoControlador.load();
                        FXMLActividadElementoController elementoEnLista = accesoControlador.getController();
                        elementoEnLista.inicializarActividadElementoAcademico(actividades.get(i), usuarioAcademico);
                        vBoxListaActividades.getChildren().add(pane);
                    }catch(IOException e){
                        e.printStackTrace();
                    }   
                }
            }
        }else{
            for (int i=0; i<actividades.size(); i++){
                FXMLLoader accesoControlador = new FXMLLoader();
                accesoControlador.setLocation(JavaFXSSPGER.class.getResource("vistas/FXMLActividadElemento.fxml"));
                try{
                    Pane pane = accesoControlador.load();
                    FXMLActividadElementoController elementoEnLista = accesoControlador.getController();
                    elementoEnLista.inicializarActividadElementoEstudiante(actividades.get(i), usuarioEstudiante);
                    vBoxListaActividades.getChildren().add(pane);
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void clicCrearActividad(ActionEvent event) {
        Stage escenarioBase = (Stage)lblTitulo.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLCreacionActividad.fxml"));
            Parent vista = accesoControlador.load();
            FXMLCreacionActividadController creacionActividad = accesoControlador.getController();
            creacionActividad.inicializarInformacionEstudiante(usuarioEstudiante,false, null); 
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Creacion de Actividad");
            escenarioBase.show();
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicRegresar(MouseEvent event) {
        if(usuarioAcademico != null){
            regresarMenuAcademico();
        }else{
            regresarMenuEstudiante();
        }        
    }
    
}
