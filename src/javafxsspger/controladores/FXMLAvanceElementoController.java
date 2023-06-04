/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 03/06/2023
*Fecha de modificación: 03/06/2023
*Descripción: Controlador de la vista de un elemento de un avance
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
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Avance;
import javafxsspger.modelo.pojo.Estudiante;


public class FXMLAvanceElementoController implements Initializable {
    
    private Avance avance;
    private Academico usuarioAcademico;
    private Estudiante usuarioEstudiante;
    private boolean esAcademico;

    @FXML
    private Label lblNombreAvance;
    @FXML
    private Label lblFechaCreacion;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void clicVerDetalle(ActionEvent event) {
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLDetalleAvance.fxml"));
            Parent vista = accesoControlador.load();
            FXMLDetalleAvanceController detalleAvance = accesoControlador.getController();
            if(esAcademico){
                detalleAvance.inicializarDetalleAvanceAcademico(avance.getIdAvance(),usuarioAcademico);
            }else{
                detalleAvance.inicializarDetalleAvanceEstudiante(avance.getIdAvance(),usuarioEstudiante);
            }
            Stage escenarioBase = new Stage();
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Detalles avance");
            escenarioBase.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public void incializarElementoAcademico(Avance avance, Academico academico){
        this.avance = avance;
        this.usuarioAcademico = academico;
        this.esAcademico = true;
        lblNombreAvance.setText(avance.getTitulo());
        lblFechaCreacion.setText(avance.getFechaCreacion());
    }
    
    public void incializarElementoEstudiante(Avance avance, Estudiante estudiante){
        this.avance = avance;
        this.usuarioEstudiante = estudiante;
        this.esAcademico = false;
        lblNombreAvance.setText(avance.getTitulo());
        lblFechaCreacion.setText(avance.getFechaCreacion());
    }
}