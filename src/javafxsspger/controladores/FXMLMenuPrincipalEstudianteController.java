/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 27/05/2023
*Fecha de modificación: 27/05/2023
*Descripción: Controlador de la vista del menú principal del estudiante
*/
package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafxsspger.modelo.dao.EstudianteDAO;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.modelo.pojo.Usuario;


public class FXMLMenuPrincipalEstudianteController implements Initializable {

    private Estudiante usuarioEstudiante;
    
    @FXML
    private Label lblSaludo;
    
    
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
    }
    
    public void inicializarInformacionConEstudiante(Estudiante usuarioEstudiante){
        this.usuarioEstudiante = usuarioEstudiante;
        lblSaludo.setText("Hola estudiante " + this.usuarioEstudiante.getNombre());
    }
    
}
