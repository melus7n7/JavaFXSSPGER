/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafxsspger.modelo.pojo.Usuario;

/**
 * FXML Controller class
 *
 * @author monti
 */
public class FXMLCuerposAcademicosController implements Initializable {

    private Usuario usuarioAdmin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void inicializarInformacionUsuario(Usuario usuarioAdmin){
        this.usuarioAdmin=usuarioAdmin;
    }
    
}
