/*
*Autor: Montiel Salas Jesús Jacob
*Fecha de creación: 06/06/2023
*Fecha de modificación: 06/06/2023
*Descripción: Controlador de la vista de un elemento en la lista de Integrantes del Cuerpo Academico
*/
package javafxsspger.controladores;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.dao.AcademicoDAO;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.AcademicoRespuesta;
import javafxsspger.modelo.pojo.CuerpoAcademico;
import javafxsspger.modelo.pojo.Usuario;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author monti
 */
public class FXMLModificarIntegrantesCuerpoAcademicoController implements Initializable {

    @FXML
    private TableColumn tblColumnNombreAcademico;
    @FXML
    private TableColumn tblColumnPuestoAcademico;
    private ObservableList<Academico> academicos;


    
    private Usuario usuarioAdmin;
    private CuerpoAcademico cuerpoAcademico;
    @FXML
    private TableView<Academico> tblViewAcademicos;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }    

    private void cargarInformacionTabla(){
        academicos = FXCollections.observableArrayList();
        AcademicoRespuesta respuestaBD = AcademicoDAO.obtenerPosiblesAcademicos(cuerpoAcademico.getIdCuerpoAcademico());
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion", "Por el momento no hay conexion", Alert.AlertType.ERROR);
            break;
        case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error cargar los datos", "Intentelo mas tarde", Alert.AlertType.WARNING);
             break;
        case Constantes.OPERACION_EXITOSA:
            academicos.addAll(respuestaBD.getAcademicos());
            tblViewAcademicos.setItems(academicos);
            break;
        }
    
    }
    
    private void configurarTabla(){        
        try{
        tblColumnPuestoAcademico.setCellValueFactory(new PropertyValueFactory("puesto"));
        tblColumnNombreAcademico.setCellValueFactory(new PropertyValueFactory("nombreCompleto"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void inicializarInformacion(Usuario usuarioAdmin, CuerpoAcademico cuerpoAcademico){
        this.usuarioAdmin=usuarioAdmin;
        this.cuerpoAcademico=cuerpoAcademico;
        cargarInformacionTabla();
    }
    
    
    @FXML
    private void clicEscogerResponsable(ActionEvent event) {
        Academico AcademicoSeleccionado= tblViewAcademicos.getSelectionModel().getSelectedItem();
        if(AcademicoSeleccionado!=null){
            if(cuerpoAcademico.getIdAcademico()==0){
                hacerResponsable(AcademicoSeleccionado.getIdAcademico());
            }else{
                Utilidades.mostrarDialogoSimple("Error responsables", "No es posible registrar en un cuerpo académico dos responsables de cuerpo académico", Alert.AlertType.WARNING);                
            }
        }else{
            Utilidades.mostrarDialogoSimple("Seleccione a un academico", "Escoger académico para su modificación", Alert.AlertType.WARNING);
        }
    }
    
    private void hacerResponsable(int idAcademico){
        int respuestaBD = AcademicoDAO.hacerResponsable(idAcademico, cuerpoAcademico.getIdCuerpoAcademico());
        switch(respuestaBD){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion", "Por el momento no hay conexion", Alert.AlertType.ERROR);
            break;
        case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Este academico no pertenece al Cuerpo Academico", "No se puede modificar al académico seleccionado", Alert.AlertType.WARNING);
             break;
        case Constantes.OPERACION_EXITOSA:
            Utilidades.mostrarDialogoSimple("Responsable academico actualizado", "Responsable de cuerpo académico actualizado", Alert.AlertType.INFORMATION);
             cuerpoAcademico.setIdAcademico(idAcademico);
            cargarInformacionTabla();
            break;
        }
    }

    @FXML
    private void clicRegresar(MouseEvent event) {
        Stage escenarioBase = (Stage)tblViewAcademicos.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLCuerposAcademicos.fxml"));
            Parent vista = accesoControlador.load();
            FXMLCuerposAcademicosController cuerpoAcademico = accesoControlador.getController();
            cuerpoAcademico.inicializarInformacionUsuario(usuarioAdmin); 
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Cuerpos Academicos");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicEliminarCuerpoAcademico(ActionEvent event) {
        Academico AcademicoSeleccionado= tblViewAcademicos.getSelectionModel().getSelectedItem();
        if(AcademicoSeleccionado!=null && cuerpoAcademico.getIdCuerpoAcademico()==AcademicoSeleccionado.getIdAcademico()){
            eliminarResponsable(cuerpoAcademico.getIdCuerpoAcademico());
        
        }else{
            Utilidades.mostrarDialogoSimple("Seleccione a un academico", "Escoger académico para su modificación", Alert.AlertType.WARNING);
        }
    }
    
    private void eliminarResponsable(int idCuerpoAcademico){
        int respuestaBD = AcademicoDAO.eliminarResponsable(idCuerpoAcademico);
        switch(respuestaBD){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexion", "Por el momento no hay conexion", Alert.AlertType.ERROR);
            break;
        case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Este academico no pertenece al Cuerpo Academico", "No se puede modificar al académico seleccionado", Alert.AlertType.WARNING);
             break;
        case Constantes.OPERACION_EXITOSA:
            Utilidades.mostrarDialogoSimple("Responsable academico eliminado", "Se ha actualizado al Responsable de Cuerpo académico", Alert.AlertType.INFORMATION);
            cuerpoAcademico.setIdAcademico(0);
            cargarInformacionTabla();
            break;
        }
        
    }
    
    
}
