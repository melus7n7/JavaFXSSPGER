/*
*Autor: Montiel Salas Jesús Jacob
*Fecha de creación: 05/06/2023
*Fecha de modificación: 05/06/2023
*Descripción: Controlador de la vista del Cronograma de Actividades
*/
package javafxsspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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

/**
 * FXML Controller class
 *
 * @author monti
 */
public class FXMLCronogramaActividadesController implements Initializable {

    private Academico usuarioAcademico;
    private Estudiante usuarioEstudiante;
    @FXML
    private ComboBox<TrabajoRecepcional> cmbBoxTrabajosRecepcionales;
    private ObservableList<TrabajoRecepcional> trabajosRecepcionales;
    @FXML
    private VBox vBoxListaActividades;
    @FXML
    private Label lblTitulo;
    @FXML
    private DatePicker dtPickerFechaFiltro;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void inicializarInformacionEstudiante(Estudiante usuarioEstudiante){
       this.usuarioEstudiante = usuarioEstudiante;
       cargarInformacionTrabajosRecepcionales();
    }
    
    public void inicializarInformacionAcademico(Academico usuarioAcademico){
       this.usuarioAcademico = usuarioAcademico;
       cargarInformacionTrabajosRecepcionales();
    }

    private void cargarInformacionTrabajosRecepcionales(){
        trabajosRecepcionales=FXCollections.observableArrayList();
        TrabajoRecepcionalRespuesta trabajosRecepcionalesBD = new TrabajoRecepcionalRespuesta(); 
        TrabajoRecepcionalRespuesta respuestaTotal = new TrabajoRecepcionalRespuesta();
        respuestaTotal.setTrabajosRecepcionales(new ArrayList());
        if(usuarioAcademico!=null){
            if(usuarioAcademico.isEsDirector() && usuarioAcademico.isEsProfesor()){
                TrabajoRecepcionalRespuesta trabajosRecepcionalesProfesor = TrabajoRecepcionalDAO.obtenerNombresTrabajosRecepcionalesProfesor(this.usuarioAcademico.getIdAcademico());                                                 
                TrabajoRecepcionalRespuesta trabajosRecepcionalesDirector = TrabajoRecepcionalDAO.obtenerNombresTrabajosRecepcionalesDirector(this.usuarioAcademico.getIdAcademico()); 
                respuestaTotal.getTrabajosRecepcionales().addAll(trabajosRecepcionalesProfesor.getTrabajosRecepcionales());
                respuestaTotal.getTrabajosRecepcionales().addAll(trabajosRecepcionalesDirector.getTrabajosRecepcionales());
                if(trabajosRecepcionalesProfesor.getCodigoRespuesta()==Constantes.OPERACION_EXITOSA && trabajosRecepcionalesDirector.getCodigoRespuesta()==Constantes.OPERACION_EXITOSA){
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
    
    private boolean estaRepetido(TrabajoRecepcional trabajo, ArrayList<TrabajoRecepcional> trabajosrecepcionales){
        for(TrabajoRecepcional trabajoRecepcionalLista: trabajosrecepcionales){
            if(trabajo.getIdTrabajoRecepcional()== trabajoRecepcionalLista.getIdTrabajoRecepcional()){
                return true;
            }
        }
        return false;
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
    
    @FXML
    private void clicMostrarActividades(ActionEvent event) {
        eliminarActividades();
        try{
            TrabajoRecepcional trabajoRecepcional = cmbBoxTrabajosRecepcionales.getSelectionModel().getSelectedItem();
            LocalDate dateFechaFiltro = dtPickerFechaFiltro.getValue();
            if(trabajoRecepcional == null || dateFechaFiltro == null){
                Utilidades.mostrarDialogoSimple("Error cargar los datos", "No se ha seleccionado ninguna opción de la lista, seleccione trabajo recepcional y fecha para continuar", Alert.AlertType.WARNING);                                            
            }else{
               int idTrabajoRecepcional=trabajoRecepcional.getIdTrabajoRecepcional();
               String fechaFiltro = dateFechaFiltro.toString();
               dtPickerFechaFiltro.setValue(null);
               if(usuarioAcademico!=null){
                    cargarActividadesDirector(idTrabajoRecepcional, fechaFiltro);
               }else{
                   cargarActividadesEstudiante(idTrabajoRecepcional, fechaFiltro);
               }
               
            }
        }catch(NullPointerException e){
                Utilidades.mostrarDialogoSimple("Error al cargar los datos", "No se ha seleccionado ninguna opción de la lista, seleccione uno para continuar", Alert.AlertType.WARNING);                            
        } 
    }
    
    private void eliminarActividades(){
        vBoxListaActividades.getChildren().clear();
    }
    
    private void cargarActividadesDirector(int idTrabajoRecepcional, String fechaFiltro){
        trabajosRecepcionales.clear();
        cargarInformacionTrabajosRecepcionales();
        ActividadRespuesta respuestaBD = ActividadDAO.obtenerActividadesPorFechaYTrabajoRecepcionalAcademico(idTrabajoRecepcional,fechaFiltro);
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error Conexión", "No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error al cargar los datos", "Hubo un error al cargar la información por favor inténtelo más tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    mostrarActividades(respuestaBD.getActividades());
                break;
        }
    }
    
    private void cargarActividadesEstudiante(int idTrabajoRecepcional,String fechaFiltro){
        trabajosRecepcionales.clear();
        cargarInformacionTrabajosRecepcionales();
        ActividadRespuesta respuestaBD = ActividadDAO.obtenerActividadesPorFechaYTrabajoRecepcionalEstudiante(idTrabajoRecepcional,fechaFiltro,usuarioEstudiante.getIdEstudiante());
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error Conexión", "No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error al cargar los datos", "Hubo un error al cargar la información por favor inténtelo más tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    mostrarActividades(respuestaBD.getActividades());
                break;
        }
    }
    
    private void mostrarActividades(ArrayList <Actividad> actividades){
        if(usuarioAcademico!=null){
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
    private void clicRegresar(MouseEvent event) {
        if(usuarioAcademico!=null){
            regresarMenuAcademico();
        }else{
            regresarMenuEstudiante();
        } 
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
    
}
