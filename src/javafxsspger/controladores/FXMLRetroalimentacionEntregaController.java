/*
*Autor: Montiel Salas Jesús Jacob
*Fecha de creación: 05/06/2023
*Fecha de modificación: 05/06/2023
*Descripción: Controlador de la vista de la calfiicación de la entrega
*/
package javafxsspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.dao.EntregaDAO;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Actividad;
import javafxsspger.modelo.pojo.Calificacion;
import javafxsspger.modelo.pojo.Entrega;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;
public class FXMLRetroalimentacionEntregaController implements Initializable {

    @FXML
    private TextArea txtAreaRetroalimentacion;
    
    @FXML
    private ComboBox<Calificacion> cmbBoxCalificacion;
    @FXML
    private Button bttCalificarEntrega;
    @FXML
    private Label lblFechaEntrega;
    @FXML
    private Label lblNombreEstudiante;

    private Academico usuarioAcademico;
    private Entrega entrega;
    private ObservableList<Calificacion> calificaciones;
    @FXML
    private Label lblTituloActividad;
    @FXML
    private Label lblTitulo;
    
    private Actividad actividad;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void inicializarInformacionAcademico(Academico usuarioAcademico, Entrega entrega, Actividad actividad){
       this.usuarioAcademico = usuarioAcademico;
       this.entrega=entrega;
       this.actividad=actividad;
       cargarRubricaCalificaciones();
       cargarInformacionEntrega(); 
    }
    
    private void cargarInformacionEntrega(){
        lblTituloActividad.setText(entrega.getTituloActividad());    
        lblNombreEstudiante.setText("Estudiante: "+entrega.getNombreEstudiante()+" "+entrega.getApellidoPaternoEstudiante()+" "+entrega.getApellidoMaternoEstudiante());
        if(entrega.getFechaEntrega()==null){
            lblFechaEntrega.setText("Fecha Entrega: Pendiente de Entrega");
        }else{
            lblFechaEntrega.setText(Utilidades.darFormatofechas(entrega.getFechaEntrega()));
        }
    }
    
    private void cargarRubricaCalificaciones(){
        calificaciones = FXCollections.observableArrayList();
        calificaciones.addAll(Utilidades.obtenerRubricaCalificacion());
        cmbBoxCalificacion.setItems(calificaciones);
    }
    

    @FXML
    private void clicCalificarEntrega(ActionEvent event) {
        validarCampos();
    }
    
    private void validarCampos(){
        if(cmbBoxCalificacion.getSelectionModel().getSelectedItem()==null || txtAreaRetroalimentacion.getText().isEmpty()){
            Utilidades.mostrarDialogoSimple("Campos invalidos","Error. Hay campos inválidos. Complételos o cámbielos para continuar", Alert.AlertType.ERROR);        
            
        }else{
            Calificacion calificacion = cmbBoxCalificacion.getSelectionModel().getSelectedItem();
            entrega.setIdRubrica(calificacion.getIdRubricaCalificacion());
            entrega.setRetroalimentacion(txtAreaRetroalimentacion.getText());
            actualizarEntrega(entrega);
        }
    }
    
    private void actualizarEntrega(Entrega entrega){
        int respuestaBD = EntregaDAO.modificarEntrega(entrega);
        System.out.println(entrega.getIdRubrica()+" "+entrega.getRetroalimentacion()+" "+entrega.getIdEntrega());
        switch(respuestaBD){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error Conexión", "No se pudo conectar con la base de datos. Inténtelo de nuevo o hágalo más tarde", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error al cargar los datos", "Hubo un error al cargar la información por favor inténtelo más tarde", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Retroalimentacion guardada", "La entrega ha sido retroalimentada", Alert.AlertType.INFORMATION);                
                cerrarVentana();
                break;
        }
    }

    @FXML
    private void clicRegresarEntrega(MouseEvent event) {
        cerrarVentana();
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage)lblTitulo.getScene().getWindow();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLEntrega.fxml"));
            Parent vista = accesoControlador.load();
            FXMLEntregaController entregaAcademico = accesoControlador.getController();
            entregaAcademico.inicializarInformacionAcademico(usuarioAcademico,actividad);
            escenarioBase.setScene(new Scene (vista));
            escenarioBase.setTitle("Entrega de la Actividad");
            escenarioBase.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    
}
