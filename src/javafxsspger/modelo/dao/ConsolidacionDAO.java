/*
*Autor: Montiel Salas Jesús Jacob
*Fecha de creación: 06/06/2023
*Fecha de modificación: 06/06/2023
*Descripción: Clase encargada de la comunicación con la bd, especificamente para manipular la información de las consolidaciones
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.Consolidacion;
import javafxsspger.modelo.pojo.ConsolidacionRespuesta;
import javafxsspger.utils.Constantes;


public class ConsolidacionDAO {
    
    
    public static ConsolidacionRespuesta recuperarConsolidacion(){
        ConsolidacionRespuesta consolidacionRespuesta = new ConsolidacionRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT idConsolidacion, nivelConsolidacion FROM consolidacion;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList <Consolidacion> consolidaciones = new ArrayList();
                while(resultado.next()){
                    Consolidacion consolidacion = new Consolidacion();
                    consolidacion.setIdConsolidacion(resultado.getInt("idConsolidacion"));
                    consolidacion.setNivelConsolidacion(resultado.getString("nivelConsolidacion"));
                    consolidaciones.add(consolidacion);
                }
                consolidacionRespuesta.setConsolidaciones(consolidaciones);
                consolidacionRespuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                conexionBD.close();
            }catch(SQLException e){
                consolidacionRespuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                e.printStackTrace();
            }
        }else{
            consolidacionRespuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return consolidacionRespuesta;
    }
    
}
