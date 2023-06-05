/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Estudiante;

/**
 * FXML Controller class
 *
 * @author monti
 */
public class FXMLCronogramaActividadesController implements Initializable {

    private Academico usuarioAcademico;
    private Estudiante usuarioEstudiante;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void inicializarInformacionEstudiante(Estudiante usuarioEstudiante){
       this.usuarioEstudiante = usuarioEstudiante;
       
    }
    
    public void inicializarInformacionAcademico(Academico usuarioAcademico){
       this.usuarioAcademico = usuarioAcademico;
       
    }
    
}
