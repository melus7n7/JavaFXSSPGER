/*
*Autor: Montiel Salas Jesús Jacob
*Fecha de creación: 20/05/2023
*Fecha de modificación: 20/05/2023
*Descripción: Controlador de la vista de Actividades para el Academico
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
import javafxsspger.modelo.pojo.TrabajoRecepcional;
import javafxsspger.modelo.pojo.TrabajoRecepcionalRespuesta;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author monti
 */
public class FXMLActividadesAcademicoController implements Initializable {
    
    private boolean esEstudiante;
    private boolean esProfesor;
    private boolean esDirector;
    
    private Academico usuarioAcademico;
    
    @FXML
    private VBox vBoxListaActividades;
    @FXML
    private Label lblTitulo;
    @FXML
    private ComboBox<TrabajoRecepcional> cmbBoxTrabajosRecepcionales;
    
    private ObservableList<TrabajoRecepcional> trabajosRecepcionales;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    private void cargarInformacionTrabajosRecepcionales(){
        trabajosRecepcionales=FXCollections.observableArrayList();
        TrabajoRecepcionalRespuesta trabajosRecepcionalesBD1, trabajosRecepcionalesBD2, trabajosRecepcionalesBD;
            //trabajosRecepcionalesBD1 = TrabajoRecepcionalDAO.obtenerNombresTrabajosRecepcionalesEstudiante(this.usuarioAcademico.getIdAcademico());         
            //trabajosRecepcionalesBD2 = TrabajoRecepcionalDAO.obtenerNombresTrabajosRecepcionalesAcademico(this.usuarioAcademico.getIdAcademico());         
            trabajosRecepcionalesBD = TrabajoRecepcionalDAO.obtenerNombresTrabajosRecepcionalesAcademico(this.usuarioAcademico.getIdAcademico());         
        /*    
        if(esEstudiante){
            //trabajosRecepcionalesBD = TrabajoRecepcionalDAO.obtenerNombresTrabajosRecepcionalesEstudiante(this.usuarioAcademico.getIdAcademico());         
        }else if (esProfesor){
            //trabajosRecepcionalesBD = TrabajoRecepcionalDAO.obtenerNombresTrabajosRecepcionalesProfesor(this.usuarioAcademico.getIdAcademico()); 
        }
        if(esDirector){
            //trabajosRecepcionalesDirectorBD TrabajoRecepcionalDAO.obtenerNombresTrabajosRecepcionalesDirector(this.usuarioAcademico.getIdAcademico());                         
        }
        
        if(esDirector && esProfesor){
            trabajosRecepcionalesBD.getTrabajosRecepcionales().addAll(trabajosRecepcionalesBD1.getTrabajosRecepcionales());
            Set <TrabajoRecepcional> hashSet = new HashSet<TrabajoRecepcional>(trabajosRecepcionalesBD.getTrabajosRecepcionales());
            
        }
        */
        switch(trabajosRecepcionalesBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion", "Por el momento no hay conexion", Alert.AlertType.ERROR);
            break;
        case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error cargar los datos", "Intentelo mas tarde", Alert.AlertType.WARNING);
             break;
        case Constantes.OPERACION_EXITOSA:
            trabajosRecepcionales.addAll(trabajosRecepcionalesBD.getTrabajosRecepcionales());
            cmbBoxTrabajosRecepcionales.setItems(trabajosRecepcionales);
            break;
        }
    }
    
    public void inicializarInformacion(Academico usuarioAcademico){
       this.usuarioAcademico = usuarioAcademico;
       cargarInformacionTrabajosRecepcionales();
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
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicMostrarActividades(ActionEvent event) {
        eliminarActividades();
        try{
            TrabajoRecepcional trabajoRecepcional = cmbBoxTrabajosRecepcionales.getSelectionModel().getSelectedItem();
            if (trabajoRecepcional == null) {
                Utilidades.mostrarDialogoSimple("Error cargar los datos", "No se ha seleccionado ninguna opción de la lista, seleccione uno para continuar", Alert.AlertType.WARNING);                                            
            } else {
                
               int idTrabajoRecepcional=trabajoRecepcional.getIdTrabajoRecepcional();
               System.out.println(idTrabajoRecepcional);
              cargarActividades(idTrabajoRecepcional);
            }
        }catch(NullPointerException e){
                Utilidades.mostrarDialogoSimple("Error cargar los datos", "No se ha seleccionado ninguna opción de la lista, seleccione uno para continuar", Alert.AlertType.WARNING);                            
        } 
    }
    
    private void eliminarActividades(){
        vBoxListaActividades.getChildren().clear();
    }
    
    private void cargarActividades(int idTrabajoRecepcional){
        trabajosRecepcionales.clear();
        cargarInformacionTrabajosRecepcionales();
        ActividadRespuesta respuestaBD = ActividadDAO.obtenerActividadesPorTrabajoRecepcionalAcademico(idTrabajoRecepcional);
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
        for (int i=0; i<actividades.size(); i++){
            FXMLLoader accesoControlador = new FXMLLoader();
            accesoControlador.setLocation(JavaFXSSPGER.class.getResource("vistas/FXMLActividadElemento.fxml"));
            try{
                Pane pane = accesoControlador.load();
                FXMLActividadElementoController elementoEnLista = accesoControlador.getController();
                elementoEnLista.inicializarActividadElemento(actividades.get(i), usuarioAcademico);
                vBoxListaActividades.getChildren().add(pane);
            }catch(IOException e){
                e.printStackTrace();
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
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Creacion de Actividad");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    
    
}
