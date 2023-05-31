/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 20/05/2023
*Fecha de modificación: 30/05/2023
*Descripción: Clase encargada de la comunicación con la BD, especificamente para manipular la información de los encargados de los anteproyectos
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.utils.Constantes;


public class EncargadosAnteproyectoDAO {
    
    public static int guardarEncargados(Academico director, ArrayList<Academico> codirectores){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD!=null){
            try{
                String sentencia = "insert into encargadosanteproyecto (idAcademico, idAnteproyecto, esDirector) " +
                    "values (?, ?, 1)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt (1, director.getIdAcademico());
                prepararSentencia.setInt(2, director.getIdAnteproyecto());
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
                
                for(int i = 0; i < codirectores.size(); i++){
                    String sentenciaCodirector = "insert into encargadosanteproyecto (idAcademico, idAnteproyecto, esDirector) " +
                        "values (?, ?, 0)";
                    PreparedStatement prepararSentenciaCodirector = conexionBD.prepareStatement(sentenciaCodirector);
                    prepararSentenciaCodirector.setInt (1, codirectores.get(i).getIdAcademico());
                    prepararSentenciaCodirector.setInt(2, director.getIdAnteproyecto());
                    int filasAfectadasCodirector = prepararSentenciaCodirector.executeUpdate();
                    respuesta = (filasAfectadasCodirector == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
                }
                
                conexionBD.close();
            }catch (SQLException e){
                respuesta = Constantes.ERROR_CONSULTA;
                e.printStackTrace();
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
    public static int borrarEncargados(int idAnteproyecto){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD!=null){
            try{
                String sentencia = "DELETE FROM encargadosanteproyecto WHERE idAnteproyecto = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idAnteproyecto);
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas >= 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
                conexionBD.close();
            }catch (SQLException e){
                respuesta = Constantes.ERROR_CONSULTA;
                e.printStackTrace();
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
}
