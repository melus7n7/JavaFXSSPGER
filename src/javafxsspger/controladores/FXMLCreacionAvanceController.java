/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 04/06/2023
*Fecha de modificación: 04/06/2023
*Descripción: Controlador de la vista de la creación y edición del avance
*/
package javafxsspger.controladores;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.interfaces.INotificacionActividad;
import javafxsspger.interfaces.INotificacionAvances;
import javafxsspger.modelo.dao.ActividadDAO;
import javafxsspger.modelo.dao.AvanceDAO;
import javafxsspger.modelo.pojo.Actividad;
import javafxsspger.modelo.pojo.ActividadRespuesta;
import javafxsspger.modelo.pojo.Avance;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.utils.Constantes;
import javafxsspger.utils.Utilidades;


public class FXMLCreacionAvanceController implements Initializable, INotificacionActividad {

    private Estudiante usuarioEstudiante;
    private ArrayList<Actividad> actividadesActuales;
    private Avance avanceEdicion;
    private boolean esEdicion;
    private INotificacionAvances notificacion;
    
    private String estiloError = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    private String estiloNormal = "-fx-border-width: 0;";
    private SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
    
    @FXML
    private TextArea txtAreaTitulo;
    @FXML
    private TextArea txtAreaDescripcion;
    @FXML
    private VBox vBoxListaActividades;
    @FXML
    private Button bttCrearAvance;
    @FXML
    private ScrollPane scrPaneContenedorActividades;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void clicRegresar(MouseEvent event) {
        cerrarVentana();
    }

    @FXML
    private void clicCrearAvance(ActionEvent event) {
        validarCamposCreacion();
    }
    
    @Override
    public void notificarAñadirActividad(Actividad actividad) {
        actividadesActuales.add(actividad);
    }

    @Override
    public void notificarEliminarActividad(Actividad actividad) {
        for(int i = 0; i<actividadesActuales.size(); i++){
            if(actividadesActuales.get(i).getIdActividad() == actividad.getIdActividad()){
                actividadesActuales.remove(i);
            }
        }
    }
    
    public void inicializarPantalla(Estudiante usuarioEstudiante, INotificacionAvances notificacion){
        this.notificacion = notificacion;
        this.esEdicion = false;
        this.actividadesActuales = new ArrayList();
        this.usuarioEstudiante = usuarioEstudiante;
        inicializarActividades();
    }
    
    public void incializarPantallaEdicion(Estudiante usuarioEstudiante, Avance avanceEdicion, INotificacionAvances notificacion){
        this.notificacion = notificacion;
        this.avanceEdicion = avanceEdicion;
        this.actividadesActuales = new ArrayList(avanceEdicion.getActividades());
        this.esEdicion = true;
        this.usuarioEstudiante = usuarioEstudiante;
        txtAreaTitulo.setText(avanceEdicion.getTitulo());
        txtAreaDescripcion.setText(avanceEdicion.getDescripcion());
        bttCrearAvance.setText("Guardar cambios");
        recuperarActividadesAvance(avanceEdicion);
    }
    
    private void recuperarActividadesAvance(Avance avanceEdicion){
        avanceEdicion.setIdEstudiante(usuarioEstudiante.getIdEstudiante());
        ActividadRespuesta respuesta = ActividadDAO.obtenerActividadesPosiblesYDelAvance(avanceEdicion);
        switch(respuesta.getCodigoRespuesta()){
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
                    cargarActividadesEdicion(respuesta.getActividades());
                break;
        }
    }
    
    private void cargarActividadesEdicion(ArrayList<Actividad> actividadesDelEstudiante){
        int altoVBox = 0;
        for (int i=0; i<actividadesDelEstudiante.size(); i++){
            boolean estaSeleccionado = encontrarActividad(actividadesDelEstudiante.get(i).getIdActividad());
            try{
                FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLActividadEnAvanceElemento.fxml"));
                Pane pane = accesoControlador.load();
                FXMLActividadEnAvanceElementoController actividadElementoController = accesoControlador.getController();
                actividadElementoController.cargarActividadCreacionAvance(actividadesDelEstudiante.get(i), this, estaSeleccionado);
                altoVBox += pane.getPrefHeight();
                vBoxListaActividades.setPrefHeight(altoVBox);
                vBoxListaActividades.getChildren().add(pane);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        if(vBoxListaActividades.getPrefHeight() < scrPaneContenedorActividades.getPrefHeight()){
            vBoxListaActividades.setPrefHeight(scrPaneContenedorActividades.getPrefHeight());
        }
    }
    
    
    private void inicializarActividades(){
        ActividadRespuesta respuesta = ActividadDAO.obtenerActividades(usuarioEstudiante.getIdEstudiante());
        switch(respuesta.getCodigoRespuesta()){
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
                    cargarActividades(respuesta.getActividades());
                break;
        }
    }
    
    private void cargarActividades(ArrayList<Actividad> actividades){
        for (int i=0; i<actividades.size(); i++){
            try{
                FXMLLoader accesoControlador = new FXMLLoader(JavaFXSSPGER.class.getResource("vistas/FXMLActividadEnAvanceElemento.fxml"));
                Pane pane = accesoControlador.load();
                FXMLActividadEnAvanceElementoController actividadElementoController = accesoControlador.getController();
                actividadElementoController.cargarActividadCreacionAvance(actividades.get(i), this, false);
                vBoxListaActividades.getChildren().add(pane);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) vBoxListaActividades.getScene().getWindow();
        escenarioBase.close();
    }
    
    private void validarCamposCreacion(){
        establecerEstiloNormal();
        boolean camposValidos = true;
        
        String titulo = txtAreaTitulo.getText().replaceAll("\n", System.getProperty("line.separator"));
        String descripcion = txtAreaDescripcion.getText().replaceAll("\n", System.getProperty("line.separator"));
        
        if(titulo.isEmpty() || titulo.length() > 300){
            txtAreaTitulo.setStyle(estiloError);
            camposValidos = false;
        }
        if(descripcion.isEmpty() || descripcion.length() > 2000){
            txtAreaDescripcion.setStyle(estiloError);
            camposValidos = false;
        }
        
        if(camposValidos){
            Avance avanceValidado = new Avance();
            avanceValidado.setTitulo(titulo);
            avanceValidado.setDescripcion(descripcion);
            avanceValidado.setIdEstudiante(usuarioEstudiante.getIdEstudiante());
            if(!actividadesActuales.isEmpty()){
                avanceValidado.setFechaInicio(obtenerFechaInicio());
                avanceValidado.setFechaFin(obtenerFechaFin());
            }
            if(!esEdicion){
                guardarAvance(avanceValidado);
            }else{
                avanceValidado.setIdAvance(avanceEdicion.getIdAvance());
                guardarCambios(avanceValidado);
            }
        }else{
            Utilidades.mostrarDialogoSimple("Error", "Hay campos inválidos. Complételos o cámbielos para continuar", Alert.AlertType.WARNING);
        }
        
    }
    
    private void guardarAvance(Avance avanceValidado){
        Avance avanceGuardado = AvanceDAO.guardarAvance(avanceValidado);
        switch(avanceGuardado.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error de conexión", 
                            "Error en la conexión con la base de datos", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error de registro del avance", 
                            "Por el momento no se puede guardar la información en la base de datos", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    registrarActividades(avanceGuardado);
                break;
        }
    }
    
    private void registrarActividades(Avance avanceNuevo){
        int respuesta = Constantes.OPERACION_EXITOSA;
        for(Actividad actividad: actividadesActuales){
            respuesta = ActividadDAO.agregarActividadAvance(actividad.getIdActividad(), avanceNuevo.getIdAvance());
            if(respuesta == Constantes.ERROR_CONEXION || respuesta == Constantes.ERROR_CONSULTA){
                break;
            }
        }
        switch(respuesta){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error de conexión", 
                            "Error en la conexión con la base de datos", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error de registro de actividades", 
                            "Por el momento no se puede guardar la información en la base de datos", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    if(!esEdicion){
                        Utilidades.mostrarDialogoSimple("Avance Creado", 
                            "Se creó correctamente el avance", Alert.AlertType.INFORMATION);
                    }else{
                        Utilidades.mostrarDialogoSimple("Avance Modificado", 
                            "Se ha modificado correctamente el avance", Alert.AlertType.INFORMATION);
                    }
                    notificacion.notificarCargarAvances();
                    cerrarVentana();
                break;
        }
        
    }
    
    private void guardarCambios(Avance avanceValidado){
        int respuesta = AvanceDAO.guardarModificaciones(avanceValidado);
        switch(respuesta){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error de conexión", 
                            "Error en la conexión con la base de datos", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error de modificación de cambios", 
                            "Por el momento no se puede guardar la información en la base de datos", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    borrarActividades();
                break;
        } 
    }
    
    private void borrarActividades(){
        if(!avanceEdicion.getActividades().isEmpty()){
            int respuesta = AvanceDAO.borrarAsociacionPreviaActividades(avanceEdicion);
            switch(respuesta){
                case Constantes.ERROR_CONEXION:
                        Utilidades.mostrarDialogoSimple("Error de conexión", 
                                "Error en la conexión con la base de datos", Alert.AlertType.ERROR);
                    break;
                case Constantes.ERROR_CONSULTA:
                        Utilidades.mostrarDialogoSimple("Error de registro borrar actividades", 
                                "Por el momento no se puede guardar la información en la base de datos", Alert.AlertType.WARNING);
                    break;
                case Constantes.OPERACION_EXITOSA:
                    break;
            } 
        }
        registrarActividades(avanceEdicion);
    }
    
    private String obtenerFechaInicio(){
        Date fechaInicio = convertirStringFecha(actividadesActuales.get(0).getFechaInicio());
        for(Actividad actividad: actividadesActuales){
            Date fecha = convertirStringFecha(actividad.getFechaInicio());
            if(fecha.before(fechaInicio)){
                fechaInicio = fecha;
            }
        }
        String fechaString = formato.format(fechaInicio);
        return fechaString;
    }
    
    private String obtenerFechaFin(){
        Date fechaFin = convertirStringFecha(actividadesActuales.get(0).getFechaFinal());
        for(Actividad actividad: actividadesActuales){
            Date fecha = convertirStringFecha(actividad.getFechaFinal());
            if(fecha.after(fechaFin)){
                fechaFin = fecha;
            }
        }
        String fechaString = formato.format(fechaFin);
        return fechaString;
    }
    
    private Date convertirStringFecha(String fechaString){
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(fechaString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fechaDate;
    }
    
    private void establecerEstiloNormal(){
        txtAreaDescripcion.setStyle(estiloNormal);
        txtAreaTitulo.setStyle(estiloNormal);
    }
    
    private boolean encontrarActividad(int idActividad){
        for(Actividad posibleActividad: actividadesActuales){
            if(posibleActividad.getIdActividad()== idActividad){
                return true;
            }
        }
        return false;
    }
}
