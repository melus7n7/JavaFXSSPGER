/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 27/05/2023
*Fecha de modificación: 03/06/2023
*Descripción: Controlador de la vista del menú principal del estudiante
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.dao.EstudianteDAO;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.modelo.pojo.Usuario;


public class FXMLMenuPrincipalEstudianteController implements Initializable {

    private Estudiante usuarioEstudiante;
    
    @FXML
    private Label lblSaludo;
    @FXML
    private Button bttActividades;
    @FXML
    private Button bttAvances;
    @FXML
    private Button bttTrabajoRecepcional;
    @FXML
    private Button bttCronogramaActividades;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void inicializarInformacion(Usuario usuarioLogin){
        Estudiante respuesta = EstudianteDAO.obtenerDetallesEstudiante(usuarioLogin.getIdUsuario());
        respuesta.setIdUsuario(usuarioLogin.getIdUsuario());
        respuesta.setNombre(usuarioLogin.getNombre());
        respuesta.setApellidoPaterno(usuarioLogin.getApellidoPaterno());
        respuesta.setApellidoMaterno(usuarioLogin.getApellidoMaterno());
        respuesta.setIdTipoUsuario(usuarioLogin.getIdTipoUsuario());
        this.usuarioEstudiante = respuesta;
        lblSaludo.setText("Hola estudiante " + usuarioEstudiante.getNombre());
        mostrarPermisos();
    }
    
    public void inicializarInformacionConEstudiante(Estudiante usuarioEstudiante){
        this.usuarioEstudiante = usuarioEstudiante;
        lblSaludo.setText("Hola estudiante " + this.usuarioEstudiante.getNombre());
        mostrarPermisos();
    }

    @FXML
    private void clicActividades(ActionEvent event) {
        Stage escenarioBase = (Stage)lblSaludo.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLActividades.fxml"));
            Parent vista = accesoControlador.load();
            FXMLActividadesController actividades = accesoControlador.getController();
            System.out.println(usuarioEstudiante.getIdEstudiante());
            actividades.inicializarInformacionEstudiante(usuarioEstudiante); 
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Actividades");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicAvances(ActionEvent event) {
        Stage escenarioBase = (Stage)lblSaludo.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLAvances.fxml"));
            Parent vista = accesoControlador.load();
            FXMLAvancesController actividades = accesoControlador.getController();
            actividades.inicializarInformacionEstudiante(usuarioEstudiante); 
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Avances");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void clicTrabajoRecepcional(ActionEvent event) {
    }

    @FXML
    private void clicCronogramaActividades(ActionEvent event) {
    }

    @FXML
    private void clicAnteproyectos(ActionEvent event) {
    }
    
    private void mostrarPermisos(){
        if(usuarioEstudiante.getIdAnteproyecto() == 0){
            bttActividades.setDisable(true);
            bttAvances.setDisable(true);
            bttCronogramaActividades.setDisable(true);
            bttTrabajoRecepcional.setDisable(true);
        }
    }

    
    
}
