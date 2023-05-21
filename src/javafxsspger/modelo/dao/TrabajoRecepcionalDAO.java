/*
*Autor: Montiel Salas Jesús Jacob
*Fecha de creación: 21/05/2023
*Fecha de modificación: 21/05/2023
*Descripción: Clase encargada de la comunicación con la BD, especificamente para manipular la información de los Trabajos Recepcionales
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.TrabajoRecepcional;
import javafxsspger.modelo.pojo.TrabajoRecepcionalRespuesta;
import javafxsspger.utils.Constantes;

/**
 *
 * @author monti
 */
public class TrabajoRecepcionalDAO {
    
    
    public static TrabajoRecepcionalRespuesta obtenerNombresTrabajosRecepcionalesAcademico(int idAcademico){
        TrabajoRecepcionalRespuesta respuesta = new TrabajoRecepcionalRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT trabajorecepcional.idtrabajorecepcional, trabajorecepcional.titulo, encargadostrabajorecepcional.idAcademico " +
                "FROM sspger.trabajorecepcional " +
                "INNER JOIN encargadostrabajorecepcional ON trabajorecepcional.idtrabajorecepcional = encargadostrabajorecepcional.idtrabajorecepcional " +
                "INNER JOIN academico ON encargadostrabajorecepcional.idtrabajorecepcional = encargadostrabajorecepcional.idtrabajorecepcional " +
                "INNER JOIN usuario ON academico.idUsuario = usuario.idUsuario " +
                "WHERE Academico.idAcademico=? AND encargadostrabajorecepcional.idAcademico=? ";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idAcademico);
                prepararSentencia.setInt(2, idAcademico);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList <TrabajoRecepcional> trabajosRecepcionalesConsulta = new ArrayList();
                while(resultado.next()){
                    TrabajoRecepcional trabajoRecepcional = new TrabajoRecepcional();
                    trabajoRecepcional.setIdTrabajoRecepcional(resultado.getInt("idtrabajorecepcional"));
                    trabajoRecepcional.setTitulo(resultado.getString("titulo"));       
                    trabajosRecepcionalesConsulta.add(trabajoRecepcional);
                }
                respuesta.setTrabajosRecepcionales(trabajosRecepcionalesConsulta);
                respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                conexionBD.close();
            }catch(SQLException e){
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                e.printStackTrace();
            } 
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static TrabajoRecepcionalRespuesta obtenerNombresTrabajosRecepcionalesEstudiante(int idEstudiante){
        TrabajoRecepcionalRespuesta respuesta = new TrabajoRecepcionalRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT TrabajoRecepcional.idTrabajoRecepcional, TrabajoRecepcional.titulo " +
                "from Usuario " +
                "INNER JOIN Estudiante ON Usuario.idUsuario=Estudiante.idUsuario " +
                "INNER JOIN TrabajoRecepcional ON Estudiante.idTrabajoRecepcional=TrabajoRecepcional.idTrabajoRecepcional where Estudiante.idEstudiante=?; ";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idEstudiante);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList <TrabajoRecepcional> trabajosRecepcionalesConsulta = new ArrayList();
                while(resultado.next()){
                    TrabajoRecepcional trabajoRecepcional = new TrabajoRecepcional();
                    trabajoRecepcional.setIdTrabajoRecepcional(resultado.getInt("idtrabajorecepcional"));
                    trabajoRecepcional.setTitulo(resultado.getString("titulo"));       
                    trabajosRecepcionalesConsulta.add(trabajoRecepcional);
                }
                respuesta.setTrabajosRecepcionales(trabajosRecepcionalesConsulta);
                respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                conexionBD.close();
            }catch(SQLException e){
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                e.printStackTrace();
            } 
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
}
