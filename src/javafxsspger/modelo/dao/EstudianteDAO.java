/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 27/05/2023
*Fecha de modificación: 27/05/2023
*Descripción: Clase encargada de la comunicación con la BD, especificamente para manipular la información de los estudiantes
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.utils.Constantes;


public class EstudianteDAO {
    
    public static Estudiante obtenerDetallesEstudiante (int idUsuario){
        Estudiante estudianteRespuesta = new Estudiante ();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT idEstudiante, matricula,idAnteproyecto, idTrabajoRecepcional " +
                    "FROM sspger.estudiante WHERE idUsuario = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idUsuario);
                ResultSet resultado = prepararSentencia.executeQuery();
                if(resultado.next()){
                    estudianteRespuesta.setIdEstudiante(resultado.getInt("idEstudiante"));
                    estudianteRespuesta.setMatricula(resultado.getString("matricula"));
                    estudianteRespuesta.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                    estudianteRespuesta.setIdTrabajoRecepcional(resultado.getInt("idTrabajoRecepcional"));
                }
                estudianteRespuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                conexionBD.close();
            }catch(SQLException e){
                estudianteRespuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                e.printStackTrace();
            }
        }else{
            estudianteRespuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return estudianteRespuesta;
    }
}
