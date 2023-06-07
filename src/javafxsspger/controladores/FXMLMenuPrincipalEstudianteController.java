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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.dao.EstudianteDAO;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.modelo.pojo.Usuario;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;


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
    @FXML
    private Label lblTitulo;
    @FXML
    private ImageView imgViewTrabajoRecepcional;
    @FXML
    private ImageView imgViewAnteproyectos;
    @FXML
    private ImageView imgViewActividades;
    @FXML
    private ImageView imgViewAvances;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    @FXML
    private void clicActividades(ActionEvent event) {
        Stage escenarioBase = (Stage)lblSaludo.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLActividades.fxml"));
            Parent vista = accesoControlador.load();
            FXMLActividadesController actividades = accesoControlador.getController();
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
        Stage escenarioBase = (Stage)lblSaludo.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLCronogramaActividades.fxml"));
            Parent vista = accesoControlador.load();
            FXMLCronogramaActividadesController cronogramaActividades = accesoControlador.getController();
            cronogramaActividades.inicializarInformacionEstudiante(usuarioEstudiante); 
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Cronograma de Actividades");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicAnteproyectos(ActionEvent event) {
        Stage escenarioBase = (Stage)lblTitulo.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLAnteproyectos.fxml"));
            Parent vista = accesoControlador.load();
            FXMLAnteproyectosController anteproyectos = accesoControlador.getController();
            anteproyectos.mostrarPantallaPublicados(Constantes.ESTUDIANTE, usuarioEstudiante);
            
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Anteproyectos");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    

    @FXML
    private void clicCerrarSesion(MouseEvent event) {
        if(Utilidades.mostrarDialogoConfirmacion("Cerrar Sesión", "¿Desea cerrar la sesión actual?")){
            Stage escenarioBase = (Stage)lblSaludo.getScene().getWindow();
            try {
                FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLInicioSesion.fxml"));
                Parent vista = accesoControlador.load();
                escenarioBase.setScene(new Scene (vista));
                escenarioBase.setTitle("Inicio Sesión");
                escenarioBase.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    @FXML
    private void clicTrabajoRecepcionalImagen(MouseEvent event) {
        clicTrabajoRecepcional(new ActionEvent());
    }

    @FXML
    private void clicAnteproyectosImagen(MouseEvent event) {
        clicAnteproyectos(new ActionEvent());
    }

    @FXML
    private void clicActividadesImagen(MouseEvent event) {
        clicActividades(new ActionEvent());
    }

    @FXML
    private void clicAvancesImagen(MouseEvent event) {
        clicAvances(new ActionEvent());
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

    private void mostrarPermisos(){
        if(usuarioEstudiante.getIdAnteproyecto() != 0){
            bttActividades.setDisable(false);
            imgViewActividades.setDisable(false);
            bttAvances.setDisable(false);
            imgViewAvances.setDisable(false);
            bttCronogramaActividades.setDisable(false);
            imgViewTrabajoRecepcional.setDisable(false);
        }
    }
    
}
