/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/05/2023
*Fecha de modificación: 05/05/2023
*Descripción: Clase que contiene métodos simples y reutilizables
*/
package javafxsspger.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafxsspger.JavaFXSSPGER;
import javafxsspger.modelo.pojo.TipoAnteproyecto;


public class Utilidades {
    
    public static void mostrarDialogoSimple (String titulo, String mensaje, Alert.AlertType tipo){
        Alert alertaSimple = new Alert (tipo);
        alertaSimple.setTitle(titulo);
        alertaSimple.setContentText(mensaje);
        alertaSimple.setHeaderText(null);
        alertaSimple.setAlertType(tipo);
        alertaSimple.showAndWait();
    }
    
    public static Scene inicializarEscena(String ruta){
       Scene escena = null;
        try {
            Parent vista = FXMLLoader.load(JavaFXSSPGER.class.getResource(ruta));
            escena = new Scene (vista);
        } catch (IOException ex) {
            System.out.println("ERROR: "+ex.getMessage());
        }
        return escena;
       
   }
    
   public static ArrayList <TipoAnteproyecto> obtenerTiposAnteproyecto (){
       ArrayList <TipoAnteproyecto> tiposAnteproyecto = new ArrayList();
       TipoAnteproyecto tipo1 = new TipoAnteproyecto(1, "Tesis");
       TipoAnteproyecto tipo2 = new TipoAnteproyecto(2, "Tesina");
       TipoAnteproyecto tipo3 = new TipoAnteproyecto(3, "Monografía");
       TipoAnteproyecto tipo4 = new TipoAnteproyecto(4, "Reporte técnico");
       TipoAnteproyecto tipo5 = new TipoAnteproyecto(5, "Memoria");
       TipoAnteproyecto tipo6 = new TipoAnteproyecto(6, "Trabajos prácticos");
       
       tiposAnteproyecto.add(tipo1);
       tiposAnteproyecto.add(tipo2);
       tiposAnteproyecto.add(tipo3);
       tiposAnteproyecto.add(tipo4);
       tiposAnteproyecto.add(tipo5);
       tiposAnteproyecto.add(tipo6);
       
       return tiposAnteproyecto;
   }
   
}
