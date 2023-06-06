/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 23/05/2023
*Fecha de modificación: 23/05/2023
*Descripción: Controlador de la vista para la Creacion de LGACs para el Administrador
*/
package javafxsspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.dao.LGACDAO;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.LGAC;
import javafxsspger.modelo.pojo.Usuario;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author danie
 */
public class FXMLCreacionLGACController implements Initializable {

    private boolean esEdicion;
    private Usuario usuarioAdmin;
    @FXML
    private Label lblTituloLGAC;
    @FXML
    private TextArea txtAreaNombreLGAC;
    @FXML
    private TextArea txtAreaDescripcionLGAC;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void inicializarInformacion(Usuario usuarioAdmin){
        this.usuarioAdmin=usuarioAdmin;
    }
    

    @FXML
    private void clicRegresarLGACS(MouseEvent event) {
        cerrarVentana();
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) lblTituloLGAC.getScene().getWindow();
        escenarioBase.close();
    }
    
    @FXML
    private void clicCancelarLGAC(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void clicGuardarLGAC(ActionEvent event) {
        validarCamposRegistro();
    }
    
    private void validarCamposRegistro(){
        if(txtAreaNombreLGAC.getText().isEmpty() || txtAreaDescripcionLGAC.getText().isEmpty()){
            Utilidades.mostrarDialogoSimple("Campos invalidos","Error. Hay campos inválidos. Complételos o cámbielos para continuar", Alert.AlertType.ERROR);
        }else{
            String nombreLGAC = txtAreaNombreLGAC.getText();
            String descripcion = txtAreaDescripcionLGAC.getText();
            try{
                LGAC lgacValidada = new LGAC();
                lgacValidada.setNombreLGAC(nombreLGAC);
                lgacValidada.setDescripcion(descripcion);
                lgacValidada.setIdLGAC(1);
                registrarLGAC(lgacValidada);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    
    private void registrarLGAC(LGAC lgacNueva){
        int codigoRespuesta = LGACDAO.guardarLGAC(lgacNueva);           
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
            Utilidades.mostrarDialogoSimple("Sin conexion", "No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde.", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
            Utilidades.mostrarDialogoSimple("Error cargar los datos", "Intentelo mas tarde", Alert.AlertType.WARNING);
            break;
            case Constantes.OPERACION_EXITOSA:
            Utilidades.mostrarDialogoSimple("LGAC Registrada", "La LGAC fue añadida correctamente", Alert.AlertType.WARNING);        
            cerrarVentana();
            break;
        }
    }
}

