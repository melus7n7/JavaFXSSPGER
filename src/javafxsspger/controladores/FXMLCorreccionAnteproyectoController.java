/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 29/05/2023
*Fecha de modificación: 29/05/2023
*Descripción: Controlador de la vista de la corrección y modificación de anteproyectos
*/
package javafxsspger.controladores;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.interfaces.INotificacionAnteproyectos;
import javafxsspger.interfaces.INotificacionCodirector;
import javafxsspger.modelo.dao.AcademicoDAO;
import javafxsspger.modelo.dao.AnteproyectoDAO;
import javafxsspger.modelo.dao.ComentarioDAO;
import javafxsspger.modelo.dao.CuerpoAcademicoDAO;
import javafxsspger.modelo.dao.EncargadosAnteproyectoDAO;
import javafxsspger.modelo.dao.LGACDAO;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.AcademicoRespuesta;
import javafxsspger.modelo.pojo.Anteproyecto;
import javafxsspger.modelo.pojo.Comentario;
import javafxsspger.modelo.pojo.ComentarioRespuesta;
import javafxsspger.modelo.pojo.CuerpoAcademico;
import javafxsspger.modelo.pojo.CuerpoAcademicoRespuesta;
import javafxsspger.modelo.pojo.LGAC;
import javafxsspger.modelo.pojo.LGACRespuesta;
import javafxsspger.modelo.pojo.TipoAnteproyecto;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;


public class FXMLCorreccionAnteproyectoController implements Initializable, INotificacionCodirector {
    
    private ArrayList <Academico> codirectoresAnteproyecto;
    private ObservableList<CuerpoAcademico> cuerposAcademicos;
    private ObservableList<LGAC> lgacs;
    private ObservableList<TipoAnteproyecto> tiposAnteproyecto;
    private File archivoElegido;
    private int idAcademicoModificacion;
    private Anteproyecto anteproyectoModificacion;
    private INotificacionAnteproyectos notificacion;
    
    private String estiloError = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    private String estiloNormal = "-fx-border-width: 0;";

    @FXML
    private ComboBox<CuerpoAcademico> cmbBoxCuerpoAcademico;
    @FXML
    private ScrollPane scrPaneCajaCodirectores;
    @FXML
    private VBox vBoxListaCodirectores;
    @FXML
    private TextArea txtAreaDescripcionAnteproyecto;
    @FXML
    private TextArea txtAreaNombreAnteproyecto;
    @FXML
    private Label lblNombreDocumento;
    @FXML
    private Button bttAdjuntarDocumento;
    @FXML
    private ComboBox<LGAC> cmbBoxLGAC;
    @FXML
    private ComboBox<TipoAnteproyecto> cmbBoxTipoAnteproyecto;
    @FXML
    private TextField txtFieldEstudiantesMaximos;
    @FXML
    private VBox vBoxComentarios;
    @FXML
    private Label lblDirector;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        codirectoresAnteproyecto = new ArrayList();
        inicializarCuerposAcademico();
        inicializarDatos();
        cmbBoxCuerpoAcademico.valueProperty().addListener(new ChangeListener<CuerpoAcademico>() {
            @Override
            public void changed(ObservableValue<? extends CuerpoAcademico> observable, CuerpoAcademico oldValue, CuerpoAcademico newValue) {
                if (newValue != null)
                    inicializarLGAC(newValue.getIdCuerpoAcademico());
            }
        });
    }    

    @FXML
    private void clicAdjuntarDocumento(ActionEvent event) {
        if(anteproyectoModificacion.getDocumento()==null){
            FileChooser dialogoSeleccionImg = new FileChooser();
            dialogoSeleccionImg.setTitle("Selecciona un documento");
            Stage escenarioBase = (Stage)lblNombreDocumento.getScene().getWindow();
            archivoElegido = dialogoSeleccionImg.showOpenDialog(escenarioBase);

            if(archivoElegido != null){
                    lblNombreDocumento.setText(archivoElegido.getName());
                    bttAdjuntarDocumento.setText("Eliminar");
            }
        }else{
            archivoElegido = null;
            anteproyectoModificacion.setDocumento(null);
            lblNombreDocumento.setText("");
            bttAdjuntarDocumento.setText("Adjuntar Documento");
        }
    }

    @FXML
    private void clicGuardarComoBorrador(ActionEvent event) {
    }

    @FXML
    private void clicRegresar(MouseEvent event) {
        regresar();
    }

    @FXML
    private void clicEnviarParaAprobacion(ActionEvent event) {
    }
    
    @FXML
    private void clicEliminar(ActionEvent event) {
    }
    
    @Override
    public void notificarAñadirCodirector(Academico codirector) {
        codirectoresAnteproyecto.add(codirector);
    }

    @Override
    public void notificarEliminarCodirector(Academico codirector) {
        codirectoresAnteproyecto.remove(codirector);
    }
    
    public void iniciarPantalla(Anteproyecto anteproyectoModificacion,int idAcademicoModificacion, INotificacionAnteproyectos notificacion){
        this.notificacion = notificacion;
        this.anteproyectoModificacion = anteproyectoModificacion;
        this.idAcademicoModificacion = idAcademicoModificacion;
        inicializarAnteproyecto();
        inicializarComentarios();
    }
    
    private void inicializarAnteproyecto(){
        lblDirector.setText("Director: " + anteproyectoModificacion.getNombreDirector());
        txtAreaNombreAnteproyecto.setText(anteproyectoModificacion.getTitulo());
        txtAreaDescripcionAnteproyecto.setText(anteproyectoModificacion.getDescripcion());
        txtFieldEstudiantesMaximos.setText(anteproyectoModificacion.getNoEstudiantesMaximo()+"");
        int posicionTipo = obtenerPosicionComboTipoAnteproyecto(anteproyectoModificacion.getIdTipoAnteproyecto());
        cmbBoxTipoAnteproyecto.getSelectionModel().select(posicionTipo);
        int posicionCuerpoAcademico = obtenerPosicionComboCuerpoAcademico(anteproyectoModificacion.getIdCuerpoAcademico());
        cmbBoxCuerpoAcademico.getSelectionModel().select(posicionCuerpoAcademico);
        int posicionLgac = obtenerPosicionComboLGAC(anteproyectoModificacion.getIdLGAC());
        cmbBoxLGAC.getSelectionModel().select(posicionLgac);
        lblNombreDocumento.setText(anteproyectoModificacion.getNombreDocumento());
        codirectoresAnteproyecto.addAll(anteproyectoModificacion.getCodirectores());
        inicializarCodirectores();
    }
    
    private void inicializarCuerposAcademico(){
        cuerposAcademicos = FXCollections.observableArrayList();
        CuerpoAcademicoRespuesta respuestaBD = CuerpoAcademicoDAO.recuperarCuerposAcademicos();
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Sin Conexion", 
                        "Lo sentimos por el momento no tiene conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                        "Hubo un error al cargar la información por favor inténtelo más tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    cuerposAcademicos.addAll(respuestaBD.getCuerposAcademicos());
                    cmbBoxCuerpoAcademico.setItems(cuerposAcademicos);
                break;
        }
    }
    
    private void inicializarComentarios(){
        ComentarioRespuesta respuestaBD = ComentarioDAO.recuperarComentarios(anteproyectoModificacion.getIdAnteproyecto());
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Sin Conexion", 
                        "Lo sentimos por el momento no tiene conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                        "Hubo un error al cargar la información por favor inténtelo más tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    cargarComentarios(respuestaBD.getComentarios());
                break;
        }
    }
    
    private void cargarComentarios(ArrayList <Comentario> comentarios){
        for (int i=0; i<comentarios.size(); i++){
            FXMLLoader fmxlLoader = new FXMLLoader();
            fmxlLoader.setLocation(JavaFXSSPGER.class.getResource("vistas/FXMLComentarioElemento.fxml"));
            try{
                Pane pane = fmxlLoader.load();
                FXMLComentarioElementoController elementoEnLista = fmxlLoader.getController();
                elementoEnLista.setComentario(comentarios.get(i));
                vBoxComentarios.getChildren().add(pane);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    
    private void inicializarDatos(){
        tiposAnteproyecto = FXCollections.observableArrayList();
        tiposAnteproyecto.addAll(Utilidades.obtenerTiposAnteproyecto());
        cmbBoxTipoAnteproyecto.setItems(tiposAnteproyecto);
    }
    
    private void inicializarLGAC(int idCuerpoAcademico){
        lgacs = FXCollections.observableArrayList();
        LGACRespuesta lgacRespuesta = LGACDAO.recuperarLGAC(idCuerpoAcademico);
        switch(lgacRespuesta.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error de conexión", 
                            "Error en la conexión con la base de datos", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error de consulta", 
                            "Por el momento no se puede obtener información de la base de datos", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                lgacs.addAll(lgacRespuesta.getLGACs());
                cmbBoxLGAC.setItems(lgacs);
                break;
        }
    }
    
    private void inicializarCodirectores (){
        Academico academicoModificacion = new Academico ();
        academicoModificacion.setIdAcademico(anteproyectoModificacion.getIdDirector());
        AcademicoRespuesta respuestaBD = AcademicoDAO.obtenerPosiblesCodirectores(academicoModificacion);
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Sin Conexion", 
                        "Lo sentimos por el momento no tiene conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                        "Hubo un error al cargar la información por favor inténtelo más tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    cargarDirectores(respuestaBD.getAcademicos());
                break;
        }
    }
    
    private void cargarDirectores (ArrayList <Academico> academicos){
        for (int i=0; i<academicos.size(); i++){
            boolean estaSeleccionado = encontrarCodirector(academicos.get(i).getIdAcademico());
            FXMLLoader fmxlLoader = new FXMLLoader();
            fmxlLoader.setLocation(JavaFXSSPGER.class.getResource("vistas/FXMLCodirectorElemento.fxml"));
            try{
                Pane pane = fmxlLoader.load();
                FXMLCodirectorElementoController elementoEnLista = fmxlLoader.getController();
                elementoEnLista.setElementos(academicos.get(i), this, estaSeleccionado);
                vBoxListaCodirectores.getChildren().add(pane);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    
    private boolean encontrarCodirector(int idAcademico){
        for(Academico posibleCodirector: codirectoresAnteproyecto){
            if(posibleCodirector.getIdAcademico()== idAcademico){
                return true;
            }
        }
        
        return false;
    }
    
    private int obtenerPosicionComboTipoAnteproyecto(int idTipoAnteproyecto){
        for(int i = 0; i < tiposAnteproyecto.size() ; i++){
            if(tiposAnteproyecto.get(i).getIdTipoAnteproyecto()== idTipoAnteproyecto){
                return i;
            }
        }
        return 0;
    }
    
    private int obtenerPosicionComboCuerpoAcademico(int idCuerpoAcademico){
        for(int i = 0; i < cuerposAcademicos.size() ; i++){
            if(cuerposAcademicos.get(i).getIdCuerpoAcademico()== idCuerpoAcademico){
                return i;
            }
        }
        return 0;
    }
    
    private int obtenerPosicionComboLGAC(int idLGAC){
        for(int i = 0; i < lgacs.size() ; i++){
            if(lgacs.get(i).getIdLGAC()== idLGAC){
                return i;
            }
        }
        return 0;
    }

    private void regresar(){
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLDetallesAnteproyecto.fxml"));
            Parent vista = accesoControlador.load();
            FXMLDetallesAnteproyectoController detallesAnteproyecto = accesoControlador.getController(); 
            detallesAnteproyecto.inicializarInformacion(anteproyectoModificacion.getIdAnteproyecto(), Constantes.ES_PROPIO, notificacion, anteproyectoModificacion.getIdDirector());
            
            Stage escenarioFormulario = (Stage) lblDirector.getScene().getWindow();
            escenarioFormulario.setScene(new Scene (vista));
            escenarioFormulario.setTitle("Detalles Anteproyecto");
            escenarioFormulario.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void validarCamposRegistro(){
        establecerEstiloNormal();
        boolean camposValidos = true;
        
        String titulo = txtAreaNombreAnteproyecto.getText().replaceAll("\n", System.getProperty("line.separator"));
        String descripcion = txtAreaDescripcionAnteproyecto.getText().replaceAll("\n", System.getProperty("line.separator"));
        String noEstudiantesMaximo = txtFieldEstudiantesMaximos.getText();
        int posicionLGAC = cmbBoxLGAC.getSelectionModel().getSelectedIndex();
        int posicionTipoAnteproyecto = cmbBoxTipoAnteproyecto.getSelectionModel().getSelectedIndex();
        int posicionCuerpoAcademico = cmbBoxCuerpoAcademico.getSelectionModel().getSelectedIndex();
        
        //Proceso de validación
        if(titulo.isEmpty() || titulo.length()>300){
            txtAreaNombreAnteproyecto.setStyle(estiloError);
            camposValidos = false;
        }
        if(descripcion.isEmpty() || descripcion.length()>20000){
            txtAreaDescripcionAnteproyecto.setStyle(estiloError);
            camposValidos = false;
        }
        if(!noEstudiantesMaximo.equals("1") && !noEstudiantesMaximo.equals("2")){
            txtFieldEstudiantesMaximos.setStyle(estiloError);
            camposValidos = false;
        }
        if(posicionTipoAnteproyecto == -1){
            cmbBoxTipoAnteproyecto.setStyle(estiloError);
            camposValidos = false;
        }
        if(posicionCuerpoAcademico == -1){
            cmbBoxCuerpoAcademico.setStyle(estiloError);
            camposValidos = false;
        }
        if(posicionLGAC == -1){
            cmbBoxLGAC.setStyle(estiloError);
            camposValidos = false;
        }
        if(codirectoresAnteproyecto.size()>2){
            scrPaneCajaCodirectores.setStyle(estiloError);
            Utilidades.mostrarDialogoSimple("Error", "Hay más de 2 codirectores seleccionados", Alert.AlertType.WARNING);
            camposValidos = false;
        }
        if(archivoElegido == null){
            lblNombreDocumento.setStyle(estiloError+"-fx-background-color:WHITE;");
        }
        
        if(camposValidos){
            if(archivoElegido != null){
                Anteproyecto anteproyectoValidado = new Anteproyecto();
                anteproyectoValidado.setTitulo(titulo);
                anteproyectoValidado.setDescripcion(descripcion);
                anteproyectoValidado.setNoEstudiantesMaximo(Integer.parseInt(noEstudiantesMaximo));
                anteproyectoValidado.setIdLGAC(cmbBoxLGAC.getSelectionModel().getSelectedItem().getIdLGAC());
                anteproyectoValidado.setIdTipoAnteproyecto(cmbBoxTipoAnteproyecto.getSelectionModel().getSelectedItem().getIdTipoAnteproyecto());
                anteproyectoValidado.setIdCuerpoAcademico(cmbBoxCuerpoAcademico.getSelectionModel().getSelectedItem().getIdCuerpoAcademico());
                anteproyectoValidado.setIdEstado(Constantes.PENDIENTE);
                try {
                    anteproyectoValidado.setDocumento(Files.readAllBytes(archivoElegido.toPath()));
                    anteproyectoValidado.setNombreDocumento(archivoElegido.getName());
                }catch (IOException ex) {
                    ex.printStackTrace();
                }
                registrarAnteproyecto(anteproyectoValidado);
            }else{
                Utilidades.mostrarDialogoSimple("Error", "Debe seleccionar un archivo para adjuntar", Alert.AlertType.WARNING);
            }
        }else{
            Utilidades.mostrarDialogoSimple("Error", "Hay campos inválidos. Complételos o cámbielos para continuar", Alert.AlertType.WARNING);
        }
    }
    
    public void registrarAnteproyecto(Anteproyecto anteproyectoNuevo){
        Anteproyecto anteproyectoRespuesta = AnteproyectoDAO.guardarAnteproyecto(anteproyectoNuevo);
        switch(anteproyectoRespuesta.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error de conexión", 
                            "Error en la conexión con la base de datos", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error de consulta", 
                            "Por el momento no se puede guardar la información en la base de datos", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    registrarDirectores(anteproyectoRespuesta);
                break;
        }
    }
    
    private void registrarDirectores (Anteproyecto anteproyectoRespuesta){
        Academico academicoModificacion = new Academico();
        academicoModificacion.setIdAnteproyecto(anteproyectoRespuesta.getIdAnteproyecto());
        int codigoRespuesta = EncargadosAnteproyectoDAO.guardarEncargados(academicoModificacion, codirectoresAnteproyecto);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error de conexión", 
                            "Error en la conexión con la base de datos", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error de consulta", 
                            "Por el momento no se puede guardar la información en la base de datos", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    Utilidades.mostrarDialogoSimple("Anteproyecto Registrado", 
                            "Se envió correctamente el anteproyecto", Alert.AlertType.INFORMATION);
                    regresar();
                break;
        }
    }
    
    private void establecerEstiloNormal(){
        txtAreaNombreAnteproyecto.setStyle(estiloNormal);
        txtAreaDescripcionAnteproyecto.setStyle(estiloNormal);
        txtFieldEstudiantesMaximos.setStyle(estiloNormal);
        vBoxListaCodirectores.setStyle(estiloNormal);
        cmbBoxCuerpoAcademico.setStyle(estiloNormal);
        cmbBoxLGAC.setStyle(estiloNormal);
        cmbBoxTipoAnteproyecto.setStyle(estiloNormal);
        lblNombreDocumento.setStyle(estiloNormal+"-fx-background-color:WHITE;");
    }
    
    private void guardarCambios(){
        
    }

    
    
}
