/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 20/05/2023
*Fecha de modificación: 06/06/2023
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
    
    public static int guardarLGAC(LGAC lgacNueva){
            int respuesta;
            Connection conexionBD = ConexionBD.abrirConexionBD();
            if(conexionBD!=null){
                try{
                String sentencia = "Insert into LGAC(nombre, descripcion) " + 
                                   "VALUES (?, ?);";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, lgacNueva.getNombreLGAC());
                prepararSentencia.setString(2, lgacNueva.getDescripcion());
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
                conexionBD.close();
                }catch(SQLException e){
                    e.printStackTrace();
                    respuesta = Constantes.ERROR_CONSULTA;
                }
            }else{
                respuesta = Constantes.ERROR_CONEXION;
            }
            return respuesta;
    }
    
    public static int eliminarLGAC(int idLGAC){
            int respuesta;
            Connection conexionBD = ConexionBD.abrirConexionBD();
            if(conexionBD!=null){
                try{
                String sentencia = "DELETE FROM LGAC WHERE idLGAC = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idLGAC);
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
                conexionBD.close();
                }catch(SQLException e){
                    e.printStackTrace();
                    respuesta = Constantes.ERROR_CONSULTA;
                }
            }else{
                respuesta = Constantes.ERROR_CONEXION;
            }
            return respuesta;   
        }
    
    public static int modificarLGAC(LGAC modificarLGAC){
            int respuesta;
            Connection conexionBD = ConexionBD.abrirConexionBD();
            if(conexionBD!=null){
                try{
                String sentencia = " UPDATE LGAC SET nombre = ?, descripción = ? "+
                        "WHERE idLGAC = ?;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, modificarLGAC.getNombreLGAC());
                prepararSentencia.setString(2, modificarLGAC.getDescripcion());
                prepararSentencia.setInt(3, modificarLGAC.getIdLGAC());
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
                conexionBD.close();
                }catch(SQLException e){
                    e.printStackTrace();
                    respuesta = Constantes.ERROR_CONSULTA;
                }
            }else{
                respuesta = Constantes.ERROR_CONEXION;
            }
            return respuesta;
        }
}

