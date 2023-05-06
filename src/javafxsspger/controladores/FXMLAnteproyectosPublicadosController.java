/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 05/05/2023
*Descripción: Controlador de la vista de los Anteproyectos Publicados
*/

package javafxsspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.pojo.Anteproyecto;
import javafxsspger.modelo.pojo.Director;
import javafxsspger.utils.Utilidades;


public class FXMLAnteproyectosPublicadosController implements Initializable {

    @FXML
    private VBox vBoxListaAnteproyectosPublicados;
    
    private static Director usuarioDirector;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //PENDIENTE: Obtención de los Anteproyectos por medio del DAO
        List <Anteproyecto> listaAnteproyectos = new ArrayList<>(anteproyectos());
        for (int i=0; i<listaAnteproyectos.size(); i++){
            FXMLLoader fmxlLoaderAnteproyecto = new FXMLLoader ();
            fmxlLoaderAnteproyecto.setLocation(JavaFXSSPGER.class.getResource("vistas/FXMLAnteproyectoElemento.fxml"));
            try{
                Pane pane = fmxlLoaderAnteproyecto.load();
                FXMLAnteproyectoElementoController elementoEnLista = fmxlLoaderAnteproyecto.getController();
                elementoEnLista.setElementoAnteproyecto(listaAnteproyectos.get(i));
                vBoxListaAnteproyectosPublicados.getChildren().add(pane);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    private List <Anteproyecto> anteproyectos (){
        List <Anteproyecto> ls = new ArrayList<>();
        Anteproyecto anteproyecto = new Anteproyecto();
        anteproyecto.setTitulo("Hacia un Modelo de Campus Accesible: Facultad de Estadística e Informática");
        anteproyecto.setNombreDirector("MCC. Juan Carlos Pérez Arriaga");
        anteproyecto.setFechaPublicacion("2 de diciembre de 2022");
        anteproyecto.setIdAnteproyecto(18);
        ls.add(anteproyecto);
        
        anteproyecto = new Anteproyecto();
        anteproyecto.setTitulo("Ingeniería de Software e Inteligencia Artificial");
        anteproyecto.setNombreDirector("Dr. Ángel Juan Sánchez García");
        anteproyecto.setFechaPublicacion("25 de noviembre de 2022");
        anteproyecto.setIdAnteproyecto(12);
        ls.add(anteproyecto);
        
        anteproyecto = new Anteproyecto();
        anteproyecto.setTitulo("Herramientas desarrollo de Bots");
        anteproyecto.setNombreDirector("Dr. Jorge Octavio Ocharán Hernández");
        anteproyecto.setFechaPublicacion("25 de noviembre de 2022");
        anteproyecto.setIdAnteproyecto(13);
        ls.add(anteproyecto);
        
        anteproyecto = new Anteproyecto();
        anteproyecto.setTitulo("Hacia un Modelo de Campus Accesible: Facultad de Estadística e Informática");
        anteproyecto.setNombreDirector("MCC. Juan Carlos Pérez Arriaga");
        anteproyecto.setFechaPublicacion("2 de diciembre de 2022");
        anteproyecto.setIdAnteproyecto(14);
        ls.add(anteproyecto);
        
        anteproyecto = new Anteproyecto();
        anteproyecto.setTitulo("Ingeniería de Software e Inteligencia Artificial");
        anteproyecto.setNombreDirector("Dr. Ángel Juan Sánchez García");
        anteproyecto.setFechaPublicacion("25 de noviembre de 2022");
        anteproyecto.setIdAnteproyecto(15);
        ls.add(anteproyecto);
        
        anteproyecto = new Anteproyecto();
        anteproyecto.setTitulo("Herramientas desarrollo de Bots");
        anteproyecto.setNombreDirector("Dr. Jorge Octavio Ocharán Hernández");
        anteproyecto.setFechaPublicacion("25 de noviembre de 2022");
        anteproyecto.setIdAnteproyecto(16);
        ls.add(anteproyecto);
        
        anteproyecto = new Anteproyecto();
        anteproyecto.setTitulo("Hacia un Modelo de Campus Accesible: Facultad de Estadística e Informática");
        anteproyecto.setNombreDirector("MCC. Juan Carlos Pérez Arriaga");
        anteproyecto.setFechaPublicacion("2 de diciembre de 2022");
        anteproyecto.setIdAnteproyecto(17);
        ls.add(anteproyecto);
        
        return ls;
    }

    @FXML
    private void clicAnteproyectosPropiosPublicados(ActionEvent event) {
    }

    @FXML
    private void clicAnteproyectosPorCorregir(ActionEvent event) {
    }

    @FXML
    private void clicCrearAnteproyecto(ActionEvent event) {
    }

    @FXML
    private void clicRegresarMenuPrincipal(MouseEvent event) {
        Stage escenarioBase = (Stage)vBoxListaAnteproyectosPublicados.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializarEscena("vistas/FXMLMenuPrincipalDirector.fxml"));
        escenarioBase.setTitle("Menú principal");
        escenarioBase.show();
    }
    
}
