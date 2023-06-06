/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 06/06/2023
*Fecha de modificación: 06/06/2023
*Descripción: Controlador de la vista de las LGACS
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.dao.LGACDAO;
import javafxsspger.modelo.pojo.LGAC;
import javafxsspger.modelo.pojo.LGACRespuesta;
import javafxsspger.modelo.pojo.Usuario;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author danie
 */
public class FXMLLGACSController implements Initializable {

    private Usuario usuarioAdmin;
    @FXML
    private VBox vBoxListaLGACS;
    @FXML
    private Label lblTitulo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private void cargarLGACS(){
        LGACRespuesta respuestaBD = LGACDAO.recuperarLGAC(0);

        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error Conexión", "No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error al cargar los datos", "Hubo un error al cargar la información por favor inténtelo más tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    mostrarLGACS(respuestaBD.getLGACs());
                break;
        }
    }
    
    private void mostrarLGACS(ArrayList <LGAC> lgacs){
        for (int i=0; i<lgacs.size(); i++){
                FXMLLoader accesoControlador = new FXMLLoader();
                accesoControlador.setLocation(JavaFXSSPGER.class.getResource("vistas/FXMLLGACElemento.fxml"));
                try{
                    Pane pane = accesoControlador.load();
                    FXMLLGACElementoController elementoEnLista = accesoControlador.getController();
                    elementoEnLista.inicializarElementoLGAC(lgacs.get(i), usuarioAdmin);
                    vBoxListaLGACS.getChildren().add(pane);
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
    }
    
    public void inicializarInformacionUsuario(Usuario usuarioAdmin){
        this.usuarioAdmin=usuarioAdmin;
    }

    @FXML
    private void clicCrearLGAC(ActionEvent event) {
        Stage escenarioBase = (Stage)lblTitulo.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLCreacionLGAC.fxml"));
            Parent vista = accesoControlador.load();
            FXMLCreacionLGACController lgac = accesoControlador.getController();
            lgac.inicializarInformacion(usuarioAdmin);
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("");
            escenarioBase.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicRegresar(MouseEvent event) {
        Stage escenarioBase = (Stage)lblTitulo.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLMenuPrincipalAdministrador.fxml"));
            Parent vista = accesoControlador.load();
            FXMLMenuPrincipalAdministradorController menuAdmin = accesoControlador.getController();
            menuAdmin.inicializarInformacion(usuarioAdmin);
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("");
            escenarioBase.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
