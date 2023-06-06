/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 20/05/2023
*Fecha de modificación: 20/05/2023
*Descripción: Clase encargada de la comunicación con la BD, especificamente para manipular la información de las LGAC
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.LGAC;
import javafxsspger.modelo.pojo.LGACRespuesta;
import javafxsspger.utils.Constantes;

public class LGACDAO {
    
    public static LGACRespuesta recuperarLGAC(int idCuerpoAcademico){
        LGACRespuesta lgacRespuesta = new LGACRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT idLGAC, nombre FROM LGAC Where idCuerpoAcademico = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idCuerpoAcademico);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList <LGAC> lgacs = new ArrayList();
                while(resultado.next()){
                    LGAC lgac = new LGAC();
                    lgac.setIdLGAC(resultado.getInt("idLGAC"));
                    lgac.setNombreLGAC(resultado.getString("nombre"));
                    lgacs.add(lgac);
                }
                lgacRespuesta.setLGACs(lgacs);
                lgacRespuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                conexionBD.close();
            }catch(SQLException e){
                lgacRespuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                e.printStackTrace();
            }
        }else{
            lgacRespuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return lgacRespuesta;
    }
    
    public static LGACRespuesta recuperarPosiblesLGACCreacion(){
        LGACRespuesta lgacRespuesta = new LGACRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT LGAC.idLGAC, LGAC.nombre FROM LGAC WHERE LGAC.idCuerpoAcademico IS NULL;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList <LGAC> lgacs = new ArrayList();
                while(resultado.next()){
                    LGAC lgac = new LGAC();
                    lgac.setIdLGAC(resultado.getInt("idLGAC"));
                    lgac.setNombreLGAC(resultado.getString("nombre"));
                    lgacs.add(lgac);
                }
                lgacRespuesta.setLGACs(lgacs);
                lgacRespuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                conexionBD.close();
            }catch(SQLException e){
                lgacRespuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                e.printStackTrace();
            }
        }else{
            lgacRespuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return lgacRespuesta;
    }
    
    
}

