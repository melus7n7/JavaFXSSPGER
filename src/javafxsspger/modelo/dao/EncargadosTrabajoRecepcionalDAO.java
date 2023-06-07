/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 01/06/2023
*Fecha de modificación: 01/06/2023
*Descripción: Clase encargada de la comunicación con la bd, especificamente para manipular la información de los encargados de los trabajos recepcionales
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.utils.Constantes;


public class EncargadosTrabajoRecepcionalDAO {
    
    
    public static int guardarEncargadosTrabajoRecepcional(int idAnteproyecto, int idTrabajoRecepcional){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "INSERT INTO encargadostrabajorecepcional (idAcademico, idTrabajoRecepcional, esDirector) " +
                    "SELECT idAcademico, ?, esDirector FROM encargadosanteproyecto " +
                    "WHERE idAnteproyecto = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt (1, idTrabajoRecepcional);
                prepararSentencia.setInt(2, idAnteproyecto);
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
