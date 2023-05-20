/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 05/05/2023
*Descripción: Controlador de la vista de la creación y edición de los anteproyectos
*/
package javafxsspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.dao.AcademicoDAO;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.AcademicoRespuesta;
import javafxsspger.modelo.pojo.LGAC;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;


public class FXMLCreacionAnteproyectoController implements Initializable {

    @FXML
    private TextField txtFieldEstudiantesMaximos;
    @FXML
    private ComboBox<String> cmbBoxTipoAnteproyecto;
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
                elementoEnLista.setElementos(academicos.get(i));
                vBoxListaCodirectores.getChildren().add(pane);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void clicGuardarCambios(ActionEvent event) {
    }

    @FXML
    private void clicAdjuntarCambios(ActionEvent event) {
    }
    
}
