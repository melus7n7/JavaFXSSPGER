/*
*Autor: Montiel Salas Jesús Jacob
*Fecha de creación: 06/06/2023
*Fecha de modificación: 06/06/2023
*Descripción: Controlador de la vista de los cuerpos academicos
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
import javafxsspger.modelo.dao.CuerpoAcademicoDAO;
import javafxsspger.modelo.pojo.CuerpoAcademico;
import javafxsspger.modelo.pojo.CuerpoAcademicoRespuesta;
import javafxsspger.modelo.pojo.Usuario;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;


public class FXMLCuerposAcademicosController implements Initializable {

    
    private Usuario usuarioAdmin;
    @FXML
    private VBox vBoxListaCuerposAcademicos;
    @FXML
    private Label lblTitulo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarCuerposAcademicos();
    }    
    
    private void cargarCuerposAcademicos(){
        CuerpoAcademicoRespuesta respuestaBD = CuerpoAcademicoDAO.recuperarCuerposAcademicos();
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error Conexión", "No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos", "Hubo un error al cargar la información por favor inténtelo más tarde", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                mostrarCuerposAcademicos(respuestaBD.getCuerposAcademicos());
                break;
        }
    }
    
    private void mostrarCuerposAcademicos(ArrayList <CuerpoAcademico> cuerposAcademicos){
        for (int i = 0; i < cuerposAcademicos.size(); i++){
                FXMLLoader accesoControlador = new FXMLLoader();
                accesoControlador.setLocation(JavaFXSSPGER.class.getResource("vistas/FXMLCuerpoAcademicoElemento.fxml"));
                try{
                    Pane pane = accesoControlador.load();
                    FXMLCuerpoAcademicoElementoController elementoEnLista = accesoControlador.getController();
                    elementoEnLista.inicializarElementoAcademico(cuerposAcademicos.get(i), usuarioAdmin);
                    vBoxListaCuerposAcademicos.getChildren().add(pane);
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
    }
    
    public void inicializarInformacionUsuario(Usuario usuarioAdmin){
        this.usuarioAdmin = usuarioAdmin;
    }

    @FXML
    private void clicCrearCuerpoAcademico(ActionEvent event) {
        Stage escenarioBase = (Stage)lblTitulo.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLCreacionCuerpoAcademico.fxml"));
            Parent vista = accesoControlador.load();
            FXMLCreacionCuerpoAcademicoController cuerpoAcademico = accesoControlador.getController();
            cuerpoAcademico.inicializarInformacion(usuarioAdmin);
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Crear Cuerpo Academico");
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
            escenarioBase.setTitle("Menú Principal");
            escenarioBase.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
}
