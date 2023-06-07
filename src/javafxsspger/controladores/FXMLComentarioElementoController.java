/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 29/05/2023
*Fecha de modificación: 29/05/2023
*Descripción: Controlador de la vista de un elemento comentario
*/
package javafxsspger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafxsspger.modelo.pojo.Comentario;
import javafxsspger.utils.Utilidades;


public class FXMLComentarioElementoController implements Initializable {

    @FXML
    private Label lblAutor;
    @FXML
    private Label lblFecha;
    @FXML
    private TextArea txtAreaComentario;

    @Override
    public void initialize(URL url, ResourceBundle rb) { 
    }

    public void setComentario(Comentario comentario){
        lblAutor.setText("Por: " + comentario.getNombreAcademico());
        lblFecha.setText("Fecha: " + Utilidades.darFormatofechas(comentario.getFechaCreacion().toString()));
        txtAreaComentario.setText(comentario.getTexto());
    }
    
}
