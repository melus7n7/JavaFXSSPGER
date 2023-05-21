/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 20/05/2023
*Fecha de modificación: 20/05/2023
*Descripción: Controlador de la vista de la creación y edición de los anteproyectos
*/
package javafxsspger.controladores;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.interfaces.INotificacionCodirector;
import javafxsspger.modelo.dao.AcademicoDAO;
import javafxsspger.modelo.dao.LGACDAO;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.AcademicoRespuesta;
import javafxsspger.modelo.pojo.LGAC;
import javafxsspger.modelo.pojo.LGACRespuesta;
import javafxsspger.modelo.pojo.TipoAnteproyecto;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;
import javax.imageio.ImageIO;


public class FXMLCreacionAnteproyectoController implements Initializable, INotificacionCodirector {

    private ArrayList <Academico> codirectoresAnteproyecto;
    private ObservableList<LGAC> lgacs;
    private ObservableList<TipoAnteproyecto> tiposAnteproyecto;
    private File archivoElegido;
    
    @FXML
    private TextField txtFieldEstudiantesMaximos;
    @FXML
    private ComboBox<TipoAnteproyecto> cmbBoxTipoAnteproyecto;
    @FXML
    private ComboBox<LGAC> cmbBoxLGAC;
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

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        codirectoresAnteproyecto = new ArrayList();
        inicializarLGAC();
        inicializarDatos();
    }
    
    @FXML
    private void clicAdjuntarDocumento(ActionEvent event) {
        FileChooser dialogoSeleccionImg = new FileChooser();
        dialogoSeleccionImg.setTitle("Selecciona un documento");
        //FileChooser.ExtensionFilter filtroDialogo = new FileChooser.ExtensionFilter("Archivos PNG(*.png)","*.PNG");
        //dialogoSeleccionImg.getExtensionFilters().add(filtroDialogo);
        Stage escenarioBase = (Stage)lblNombreDocumento.getScene().getWindow();
        archivoElegido = dialogoSeleccionImg.showOpenDialog(escenarioBase);
        
        if(archivoElegido != null){
                lblNombreDocumento.setText(archivoElegido.getName());
        }
    }
    
    @FXML
    private void clicGuardarCambios(ActionEvent event) {
        validarCamposRegistro();
    }
    
    @Override
    public void notificarAñadirCodirector(Academico codirector) {
        codirectoresAnteproyecto.add(codirector);
    }

    @Override
    public void notificarEliminarCodirector(Academico codirector) {
        codirectoresAnteproyecto.remove(codirector);
    }
    
    public void inicializarCodirectores (Academico academicoCreacion){
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
    
    public void cargarDirectores (ArrayList <Academico> academicos){
        for (int i=0; i<academicos.size(); i++){
            FXMLLoader fmxlLoader = new FXMLLoader();
            fmxlLoader.setLocation(JavaFXSSPGER.class.getResource("vistas/FXMLCodirectorElemento.fxml"));
            try{
                Pane pane = fmxlLoader.load();
                FXMLCodirectorElementoController elementoEnLista = fmxlLoader.getController();
                elementoEnLista.setElementos(academicos.get(i), this);
                vBoxListaCodirectores.getChildren().add(pane);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    
    public void inicializarDatos(){
        tiposAnteproyecto = FXCollections.observableArrayList();
        tiposAnteproyecto.addAll(Utilidades.obtenerTiposAnteproyecto());
        cmbBoxTipoAnteproyecto.setItems(tiposAnteproyecto);
    }
    
    public void inicializarLGAC(){
        lgacs = FXCollections.observableArrayList();
        LGACRespuesta lgacRespuesta = LGACDAO.recuperarLGAC();
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

    public void validarCamposRegistro(){
        String titulo = txtAreaNombreAnteproyecto.getText();
        String descripcion = txtAreaDescripcionAnteproyecto.getText();
        String noEstudiantesMaximo = txtFieldEstudiantesMaximos.getText();
        
    }

    
    
}