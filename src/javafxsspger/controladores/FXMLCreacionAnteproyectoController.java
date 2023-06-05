/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 20/05/2023
*Fecha de modificación: 30/05/2023
*Descripción: Controlador de la vista de la creación de los anteproyectos
*/
package javafxsspger.controladores;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.interfaces.INotificacionAnteproyectos;
import javafxsspger.interfaces.INotificacionCodirector;
import javafxsspger.modelo.dao.AcademicoDAO;
import javafxsspger.modelo.dao.AnteproyectoDAO;
import javafxsspger.modelo.dao.CuerpoAcademicoDAO;
import javafxsspger.modelo.dao.EncargadosAnteproyectoDAO;
import javafxsspger.modelo.dao.LGACDAO;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.AcademicoRespuesta;
import javafxsspger.modelo.pojo.Anteproyecto;
import javafxsspger.modelo.pojo.CuerpoAcademico;
import javafxsspger.modelo.pojo.CuerpoAcademicoRespuesta;
import javafxsspger.modelo.pojo.LGAC;
import javafxsspger.modelo.pojo.LGACRespuesta;
import javafxsspger.modelo.pojo.TipoAnteproyecto;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;


public class FXMLCreacionAnteproyectoController implements Initializable, INotificacionCodirector {

    private ArrayList <Academico> codirectoresAnteproyecto;
    private ObservableList<CuerpoAcademico> cuerposAcademicos;
    private ObservableList<LGAC> lgacs;
    private ObservableList<TipoAnteproyecto> tiposAnteproyecto;
    private File archivoElegido;
    private Academico academicoCreacion;
    private INotificacionAnteproyectos notificacion;
    
    private String estiloError = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    private String estiloNormal = "-fx-border-width: 0;";
    
    @FXML
    private TextField txtFieldEstudiantesMaximos;
    @FXML
    private ComboBox<TipoAnteproyecto> cmbBoxTipoAnteproyecto;
    @FXML
    private ComboBox<LGAC> cmbBoxLGAC;
    @FXML
    private ComboBox<CuerpoAcademico> cmbBoxCuerpoAcademico;
    @FXML
    private Button btnGuardarCambios;
    @FXML
    private Label lblNombreDocumento;
    @FXML
    private TextArea txtAreaNombreAnteproyecto;
    @FXML
    private TextArea txtAreaDescripcionAnteproyecto;
    @FXML
    private VBox vBoxListaCodirectores;
    @FXML
    private ScrollPane scrPaneCajaCodirectores;
    @FXML
    private Button bttAdjuntarDocumento;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        codirectoresAnteproyecto = new ArrayList();
        inicializarCuerposAcademico();
        inicializarDatos();
        cmbBoxCuerpoAcademico.valueProperty().addListener(new ChangeListener<CuerpoAcademico>() {
            @Override
            public void changed(ObservableValue<? extends CuerpoAcademico> observable, CuerpoAcademico oldValue, CuerpoAcademico newValue) {
                if (newValue != null)
                    inicializarLGAC(newValue.getIdCuerpoAcademico());
            }
        });
    }
    
    @FXML
    private void clicAdjuntarDocumento(ActionEvent event) {
        if(archivoElegido == null){
            FileChooser dialogoSeleccionImg = new FileChooser();
            dialogoSeleccionImg.setTitle("Selecciona un documento");
            Stage escenarioBase = (Stage)lblNombreDocumento.getScene().getWindow();
            archivoElegido = dialogoSeleccionImg.showOpenDialog(escenarioBase);

            if(archivoElegido != null){
                    lblNombreDocumento.setText(archivoElegido.getName());
                    bttAdjuntarDocumento.setText("Eliminar");
            }
        }else{
            archivoElegido = null;
            bttAdjuntarDocumento.setText("Adjuntar Documento");
            lblNombreDocumento.setText("");
        }
        
    }
    
    @FXML
    private void clicGuardarCambios(ActionEvent event) {
        validarCamposRegistro();
    }
    
    @FXML
    private void clicRegresar(MouseEvent event) {
        cerrarVentana();
    }
    
    @Override
    public void notificarAñadirCodirector(Academico codirector) {
        codirectoresAnteproyecto.add(codirector);
    }

    @Override
    public void notificarEliminarCodirector(Academico codirector) {
        codirectoresAnteproyecto.remove(codirector);
    }
    
    public void inicializarPantalla (Academico academicoCreacion, INotificacionAnteproyectos notificacion){
        this.notificacion = notificacion;
        this.academicoCreacion = academicoCreacion;
        inicializarCodirectores();
    }
    
    private void inicializarCuerposAcademico(){
        cuerposAcademicos = FXCollections.observableArrayList();
        CuerpoAcademicoRespuesta respuestaBD = CuerpoAcademicoDAO.recuperarCuerposAcademicos();
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
                    cuerposAcademicos.addAll(respuestaBD.getCuerposAcademicos());
                    cmbBoxCuerpoAcademico.setItems(cuerposAcademicos);
                break;
        }
    }
    
    private void inicializarCodirectores (){
        AcademicoRespuesta respuestaBD = AcademicoDAO.obtenerPosiblesCodirectores(academicoCreacion);
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
                    cargarDirectores(respuestaBD.getAcademicos());
                break;
        }
    }
    
    private void cargarDirectores (ArrayList <Academico> academicos){
        int altoVBox = 0;
        for (int i=0; i<academicos.size(); i++){
            FXMLLoader fmxlLoader = new FXMLLoader();
            fmxlLoader.setLocation(JavaFXSSPGER.class.getResource("vistas/FXMLCodirectorElemento.fxml"));
            try{
                Pane pane = fmxlLoader.load();
                FXMLCodirectorElementoController elementoEnLista = fmxlLoader.getController();
                elementoEnLista.setElementos(academicos.get(i), this, false);
                altoVBox += pane.getPrefHeight();
                vBoxListaCodirectores.setPrefHeight(altoVBox);
                vBoxListaCodirectores.getChildren().add(pane);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        if(vBoxListaCodirectores.getPrefHeight() < scrPaneCajaCodirectores.getPrefHeight()){
            vBoxListaCodirectores.setPrefHeight(scrPaneCajaCodirectores.getPrefHeight());
        }
    }
    
    private void inicializarDatos(){
        tiposAnteproyecto = FXCollections.observableArrayList();
        tiposAnteproyecto.addAll(Utilidades.obtenerTiposAnteproyecto());
        cmbBoxTipoAnteproyecto.setItems(tiposAnteproyecto);
    }
    
    private void inicializarLGAC(int idCuerpoAcademico){
        lgacs = FXCollections.observableArrayList();
        LGACRespuesta lgacRespuesta = LGACDAO.recuperarLGAC(idCuerpoAcademico);
        switch(lgacRespuesta.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error de conexión", 
                            "Error en la conexión con la base de datos", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error de consulta", 
                            "Por el momento no se puede obtener información de la base de datos", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                lgacs.addAll(lgacRespuesta.getLGACs());
                cmbBoxLGAC.setItems(lgacs);
                break;
        }
    }

    private void validarCamposRegistro(){
        establecerEstiloNormal();
        boolean camposValidos = true;
        
        String titulo = txtAreaNombreAnteproyecto.getText().replaceAll("\n", System.getProperty("line.separator"));
        String descripcion = txtAreaDescripcionAnteproyecto.getText().replaceAll("\n", System.getProperty("line.separator"));
        String noEstudiantesMaximo = txtFieldEstudiantesMaximos.getText();
        int posicionLGAC = cmbBoxLGAC.getSelectionModel().getSelectedIndex();
        int posicionTipoAnteproyecto = cmbBoxTipoAnteproyecto.getSelectionModel().getSelectedIndex();
        int posicionCuerpoAcademico = cmbBoxCuerpoAcademico.getSelectionModel().getSelectedIndex();
        
        //Proceso de validación
        if(titulo.isEmpty() || titulo.length()>300){
            txtAreaNombreAnteproyecto.setStyle(estiloError);
            camposValidos = false;
        }
        if(descripcion.isEmpty() || descripcion.length()>20000){
            txtAreaDescripcionAnteproyecto.setStyle(estiloError);
            camposValidos = false;
        }
        if(!noEstudiantesMaximo.equals("1") && !noEstudiantesMaximo.equals("2")){
            txtFieldEstudiantesMaximos.setStyle(estiloError);
            camposValidos = false;
        }
        if(posicionTipoAnteproyecto == -1){
            cmbBoxTipoAnteproyecto.setStyle(estiloError);
            camposValidos = false;
        }
        if(posicionCuerpoAcademico == -1){
            cmbBoxCuerpoAcademico.setStyle(estiloError);
            camposValidos = false;
        }
        if(posicionLGAC == -1){
            cmbBoxLGAC.setStyle(estiloError);
            camposValidos = false;
        }
        if(codirectoresAnteproyecto.size()>2){
            scrPaneCajaCodirectores.setStyle(estiloError);
            Utilidades.mostrarDialogoSimple("Error", "Hay más de 2 codirectores seleccionados", Alert.AlertType.WARNING);
            camposValidos = false;
        }
        if(archivoElegido == null){
            lblNombreDocumento.setStyle(estiloError+"-fx-background-color:WHITE;");
        }
        
        if(camposValidos){
            if(archivoElegido != null){
                Anteproyecto anteproyectoValidado = new Anteproyecto();
                anteproyectoValidado.setTitulo(titulo);
                anteproyectoValidado.setDescripcion(descripcion);
                anteproyectoValidado.setNoEstudiantesMaximo(Integer.parseInt(noEstudiantesMaximo));
                anteproyectoValidado.setIdLGAC(cmbBoxLGAC.getSelectionModel().getSelectedItem().getIdLGAC());
                anteproyectoValidado.setIdTipoAnteproyecto(cmbBoxTipoAnteproyecto.getSelectionModel().getSelectedItem().getIdTipoAnteproyecto());
                anteproyectoValidado.setIdCuerpoAcademico(cmbBoxCuerpoAcademico.getSelectionModel().getSelectedItem().getIdCuerpoAcademico());
                anteproyectoValidado.setIdEstado(Constantes.PENDIENTE);
                try {
                    anteproyectoValidado.setDocumento(Files.readAllBytes(archivoElegido.toPath()));
                    anteproyectoValidado.setNombreDocumento(archivoElegido.getName());
                }catch (IOException ex) {
                    ex.printStackTrace();
                }
                registrarAnteproyecto(anteproyectoValidado);
            }else{
                Utilidades.mostrarDialogoSimple("Error", "Debe seleccionar un archivo para adjuntar", Alert.AlertType.WARNING);
            }
        }else{
            Utilidades.mostrarDialogoSimple("Error", "Hay campos inválidos. Complételos o cámbielos para continuar", Alert.AlertType.WARNING);
        }
    }
    
    private void registrarAnteproyecto(Anteproyecto anteproyectoNuevo){
        Anteproyecto anteproyectoRespuesta = AnteproyectoDAO.guardarAnteproyecto(anteproyectoNuevo);
        switch(anteproyectoRespuesta.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error de conexión", 
                            "Error en la conexión con la base de datos", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error de consulta", 
                            "Por el momento no se puede guardar la información en la base de datos", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    registrarDirectores(anteproyectoRespuesta);
                break;
        }
    }
    
    private void registrarDirectores (Anteproyecto anteproyectoRespuesta){
        this.academicoCreacion.setIdAnteproyecto(anteproyectoRespuesta.getIdAnteproyecto());
        int codigoRespuesta = EncargadosAnteproyectoDAO.guardarEncargados(academicoCreacion, codirectoresAnteproyecto);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error de conexión", 
                            "Error en la conexión con la base de datos", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error de consulta", 
                            "Por el momento no se puede guardar la información en la base de datos", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    Utilidades.mostrarDialogoSimple("Anteproyecto Registrado", 
                            "Se envió correctamente el anteproyecto", Alert.AlertType.INFORMATION);
                    notificacion.notificarCargarAnteproyectos();
                    cerrarVentana();
                break;
        }
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) lblNombreDocumento.getScene().getWindow();
        escenarioBase.close();
    }
    
    private void establecerEstiloNormal(){
        txtAreaNombreAnteproyecto.setStyle(estiloNormal);
        txtAreaDescripcionAnteproyecto.setStyle(estiloNormal);
        txtFieldEstudiantesMaximos.setStyle(estiloNormal);
        vBoxListaCodirectores.setStyle(estiloNormal);
        cmbBoxCuerpoAcademico.setStyle(estiloNormal);
        cmbBoxLGAC.setStyle(estiloNormal);
        cmbBoxTipoAnteproyecto.setStyle(estiloNormal);
        lblNombreDocumento.setStyle(estiloNormal+"-fx-background-color:WHITE;");
    }
    
}
