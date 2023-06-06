/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 06/06/2023
*Fecha de modificación: 06/06/2023
*Descripción: Controlador de la vista de un elemento en la lista de Experiencias Educativas
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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.interfaces.INotificacionLGAC;
import javafxsspger.modelo.pojo.ExperienciaEducativa;
import javafxsspger.modelo.pojo.LGAC;
import javafxsspger.modelo.pojo.Usuario;

/**
 * FXML Controller class
 *
 * @author danie
 */
public class FXMLExperienciaEducativaElementoController implements Initializable {

    @FXML
    private Label lblNombreExperienciaEducativa;
    @FXML
    private Label lblNombreAcademico;
    @FXML
    private Label lblPeriodoEscolar;
    
    private Usuario usuarioAdmin;
    private ExperienciaEducativa expEduElemento;
    private INotificacionLGAC interfazNotificacion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void inicializarElementoExperienciaEducativa(ExperienciaEducativa expEduElemento, Usuario usuarioAdmin){
        this.expEduElemento=expEduElemento;
        this.usuarioAdmin=usuarioAdmin;
        //lblNombreExperienciaEducativa.setText(expEduElemento.getNombreExperienciaEducativas());
    }
    
    public void setElementos (ExperienciaEducativa expEdu, INotificacionLGAC interfazNotificacion){
        this.interfazNotificacion = interfazNotificacion;
        this.expEduElemento = expEdu;
        //lblNombreExperienciaEducativa.setText(expEdu.getNombreExperienciaEducativa());
    }
    
    @FXML
    private void clicVerDetallesExperienciaEducativa(ActionEvent event) {
        if(usuarioAdmin!=null){
            Stage escenarioBase = (Stage)lblNombreExperienciaEducativa.getScene().getWindow();
            try {
                FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLDetallesExperienciaEducativa.fxml"));
                Parent vista = accesoControlador.load();
                FXMLDetallesActividadController actividad = accesoControlador.getController();

                actividad.inicializarInformacionAcademico(usuarioAcademico, actividadActual);
                escenarioBase.setScene(new Scene (vista));
                escenarioBase.setTitle("Detalles de Actividad");
                escenarioBase.show();
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        }else{
            Stage escenarioBase = (Stage)lblTituloActividad.getScene().getWindow();
            try {
                FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLDetallesActividad.fxml"));
                Parent vista = accesoControlador.load();
                FXMLDetallesActividadController actividad = accesoControlador.getController();
            
                actividad.inicializarInformacionEstudiante(usuarioEstudiante, actividadActual);
                escenarioBase.setScene(new Scene (vista));
                escenarioBase.setTitle("Detalles de Actividad");
                escenarioBase.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
