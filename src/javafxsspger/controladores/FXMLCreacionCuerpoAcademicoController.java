/*
*Autor: Montiel Salas Jesús Jacob
*Fecha de creación: 06/06/2023
*Fecha de modificación: 06/06/2023
*Descripción: Controlador de la vista de la creacion del cuerpo academico
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.interfaces.INotificacionLGAC;
import javafxsspger.modelo.dao.ConsolidacionDAO;
import javafxsspger.modelo.dao.CuerpoAcademicoDAO;
import javafxsspger.modelo.dao.LGACDAO;
import javafxsspger.modelo.pojo.Consolidacion;
import javafxsspger.modelo.pojo.ConsolidacionRespuesta;
import javafxsspger.modelo.pojo.CuerpoAcademico;
import javafxsspger.modelo.pojo.LGAC;
import javafxsspger.modelo.pojo.LGACRespuesta;
import javafxsspger.modelo.pojo.Usuario;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author monti
 */
public class FXMLCreacionCuerpoAcademicoController implements Initializable, INotificacionLGAC {

    boolean esEdicion=false;
    CuerpoAcademico cuerpoAcademico;
    private ArrayList <LGAC> lgacCuerpoAcademico;
    @FXML
    private TextArea txtAreaClaveCuerpoAcademico;
    @FXML
    private TextArea txtAreaDescripcionCuerpoAcademico;
    @FXML
    private Label lblTitulo;
    @FXML
    private TextArea txtAreaNombreCuerpoAcademico;
    @FXML
    private ComboBox<Consolidacion> cmbBoxConsolidacion;
    private ObservableList<Consolidacion> consolidaciones;

    @FXML
    private ScrollPane scrPaneCajaLGAC;
    @FXML
    private VBox vBoxListaLGAC;
    private Usuario usuarioAdmin;
    @FXML
    private TextArea txtAreaAreaConocimiento;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lgacCuerpoAcademico = new ArrayList();
    }    

    public void inicializarInformacionEdicion(Usuario usuarioAdmin, boolean esEdicion,CuerpoAcademico cuerpoAcademicoAnterior){
        this.usuarioAdmin=usuarioAdmin;
        this.esEdicion=esEdicion;
        this.cuerpoAcademico=cuerpoAcademicoAnterior;
        cargarInformacionConsolidacion();
        lblTitulo.setText("Modificación de Cuerpo Académico");
        txtAreaClaveCuerpoAcademico.setText(cuerpoAcademicoAnterior.getClaveCuerpoAcademico());
        txtAreaNombreCuerpoAcademico.setText(cuerpoAcademicoAnterior.getNombre());
        txtAreaDescripcionCuerpoAcademico.setText(cuerpoAcademicoAnterior.getDescripcion());
        txtAreaAreaConocimiento.setText(cuerpoAcademicoAnterior.getAreaConocimiento());
        int posicionConsolidacion=obtenerPosicionComboConsolidacion(cuerpoAcademicoAnterior.getIdConsolidacion());
        cmbBoxConsolidacion.getSelectionModel().select(posicionConsolidacion);
        inicializarLGAC();
    }
    
    private int obtenerPosicionComboConsolidacion(int idConsolidacion){
        for (int i = 0; i < consolidaciones.size(); i++) {
            if(consolidaciones.get(i).getIdConsolidacion()==idConsolidacion)
                return i;
        }
        return 0;
    }
    
    
    public void inicializarInformacion(Usuario usuarioAdmin){
        this.usuarioAdmin=usuarioAdmin;
        this.cuerpoAcademico= new CuerpoAcademico();        
        inicializarLGAC();
        cargarInformacionConsolidacion();
    }
    
    private void cargarInformacionConsolidacion(){
        consolidaciones=FXCollections.observableArrayList();
        ConsolidacionRespuesta respuestaBD = ConsolidacionDAO.recuperarConsolidacion();
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
                    consolidaciones.addAll(respuestaBD.getConsolidaciones());
                    cmbBoxConsolidacion.setItems(consolidaciones);
                break;
        }
    }
    
   
    
    private void inicializarLGAC(){
        LGACRespuesta respuestaBD;
        if(esEdicion){
            respuestaBD = LGACDAO.recuperarPosiblesLGACEdicion(cuerpoAcademico.getIdCuerpoAcademico()); 
        }else{
            respuestaBD = LGACDAO.recuperarPosiblesLGACCreacion();           
        }
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
                    cargarLGAC(respuestaBD.getLGACs());
                break;
        }
    }
    
    private void cargarLGAC(ArrayList<LGAC> lgac){
        int altoVBox = 0;
        for (int i=0; i<lgac.size(); i++){
            FXMLLoader fmxlLoader = new FXMLLoader();
            fmxlLoader.setLocation(JavaFXSSPGER.class.getResource("vistas/FXMLLGACElemento.fxml"));
            try{
                Pane pane = fmxlLoader.load();
                FXMLLGACElementoController elementoEnLista = fmxlLoader.getController();
                elementoEnLista.setElementos(lgac.get(i), this, (lgac.get(i).getIdCuerpoAcademico() > 0) ? true : false);
                if(lgac.get(i).getIdCuerpoAcademico() > 0){  
                    notificarAñadirLGAC(lgac.get(i));
                }
                altoVBox += pane.getPrefHeight();
                vBoxListaLGAC.setPrefHeight(altoVBox);
                vBoxListaLGAC.getChildren().add(pane);
                System.out.println(lgac.get(i).getIdCuerpoAcademico());
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        if(vBoxListaLGAC.getPrefHeight() < scrPaneCajaLGAC.getPrefHeight()){
            vBoxListaLGAC.setPrefHeight(scrPaneCajaLGAC.getPrefHeight());
        }
    }

    @FXML
    private void clicRegresarCuerposAcademicos(MouseEvent event) {
        cerrarVentana();
    }

    @FXML
    private void clicGuardarCuerpoAcademico(ActionEvent event) {
        
        validarCampos();
    }

    @Override
    public void notificarAñadirLGAC(LGAC lgac) {
        lgacCuerpoAcademico.add(lgac);

    }

    @Override
    public void notificarEliminarLGAC(LGAC lgac) {
        lgacCuerpoAcademico.remove(lgac);
    }

    private void validarCampos(){
        if(lgacCuerpoAcademico.isEmpty() || txtAreaClaveCuerpoAcademico.getText().isEmpty() || txtAreaNombreCuerpoAcademico.getText().isEmpty() || txtAreaDescripcionCuerpoAcademico.getText().isEmpty()
                || txtAreaAreaConocimiento.getText().isEmpty() || cmbBoxConsolidacion.getSelectionModel().getSelectedIndex()==-1){
            Utilidades.mostrarDialogoSimple("Seleccionar al menos una LGAC y llenar los demas datos", "Error. Hay campos inválidos. Complételos o cámbielos para continuar", Alert.AlertType.WARNING);
        }else{
            cuerpoAcademico.setClaveCuerpoAcademico(txtAreaClaveCuerpoAcademico.getText());
            cuerpoAcademico.setNombre(txtAreaNombreCuerpoAcademico.getText());
            cuerpoAcademico.setDescripcion(txtAreaDescripcionCuerpoAcademico.getText());
            cuerpoAcademico.setAreaConocimiento(txtAreaAreaConocimiento.getText());
            cuerpoAcademico.setIdConsolidacion(cmbBoxConsolidacion.getSelectionModel().getSelectedItem().getIdConsolidacion());
             registrarCuerpoAcademico(cuerpoAcademico);
        }
        
    }
    
    private void eliminarLGACCuerpoAcademico(CuerpoAcademico cuerpoAcademico){
        int codigoRespuesta = LGACDAO.eliminarLGACDeCuerpoAcademico(cuerpoAcademico.getIdCuerpoAcademico());
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión", "Error en la conexión con la base de datos", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                break;
            case Constantes.OPERACION_EXITOSA:
                break;
        }
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage)lblTitulo.getScene().getWindow();
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
    
    private void registrarCuerpoAcademico(CuerpoAcademico cuerpoAcademico){
        int codigoRespuesta;
        if(esEdicion){
            eliminarLGACCuerpoAcademico(cuerpoAcademico);
            codigoRespuesta = CuerpoAcademicoDAO.actualizarCuerpoAcademico(cuerpoAcademico, lgacCuerpoAcademico);
        }else{
            codigoRespuesta = CuerpoAcademicoDAO.guardarCuerpoAcademico(cuerpoAcademico, lgacCuerpoAcademico);
        }
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión", "Error en la conexión con la base de datos", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta", "Por el momento no se puede guardar la información en la base de datos", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                if(esEdicion){
                    Utilidades.mostrarDialogoSimple("Cuerpo academico modificado con exito", "El cuerpo académico ha sido modificado con éxito", Alert.AlertType.INFORMATION);                    
                }else{
                    Utilidades.mostrarDialogoSimple("Cuerpo academico añadido con exito", "El cuerpo académico ha sido creado con éxito", Alert.AlertType.INFORMATION);
                }
                cerrarVentana();
                break;
        }
    }
   
    
    
    
    
}
