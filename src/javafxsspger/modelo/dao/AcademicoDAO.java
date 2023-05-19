/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 14/05/2023
*Fecha de modificación: 16/05/2023
*Descripción: Clase encargada de la comunicación con la BD, especificamente para manipular la información de los académicos
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.utils.Constantes;

public class AcademicoDAO {
    
    public static Academico obtenerDetallesAcademico (int idUsuario){
        Academico academicoRespuesta = new Academico();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT * FROM sspger.academico " +
                    "WHERE idUsuario = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idUsuario);
                ResultSet resultado = prepararSentencia.executeQuery();
                if(resultado.next()){
                    academicoRespuesta.setIdAcademico(resultado.getInt("idAcademico"));
                    academicoRespuesta.setNoPersonal(resultado.getString("noPersonal"));
                    academicoRespuesta.setIdCuerpoAcademico(resultado.getInt("idCuerpoAcademico"));
                }
                academicoRespuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                conexionBD.close();
            }catch(SQLException e){
                academicoRespuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                e.printStackTrace();
            }
        }else{
            academicoRespuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return academicoRespuesta;
    }
}
