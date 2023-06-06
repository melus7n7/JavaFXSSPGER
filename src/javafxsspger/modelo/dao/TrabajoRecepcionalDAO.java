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
    
    
    public static TrabajoRecepcionalRespuesta obtenerNombresTrabajosRecepcionalesDirector(int idAcademico){
        TrabajoRecepcionalRespuesta respuesta = new TrabajoRecepcionalRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT trabajorecepcional.idtrabajorecepcional, trabajorecepcional.titulo, encargadostrabajorecepcional.idAcademico, TrabajoRecepcional.fechaAprobacion " +
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
                    trabajoRecepcional.setFechaAprobacion(resultado.getString("fechaAprobacion"));
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
                String consulta = "SELECT TrabajoRecepcional.idTrabajoRecepcional, TrabajoRecepcional.titulo, TrabajoRecepcional.fechaAprobacion " +
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
                    trabajoRecepcional.setFechaAprobacion(resultado.getString("fechaAprobacion"));
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
    
    public static TrabajoRecepcionalRespuesta obtenerNombresTrabajosRecepcionalesProfesor(int idAcademico){
        TrabajoRecepcionalRespuesta respuesta = new TrabajoRecepcionalRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT trabajorecepcional.idtrabajorecepcional, trabajorecepcional.titulo, TrabajoRecepcional.fechaAprobacion " +
                       "FROM sspger.ExperienciaEducativa      " +
                       "INNER JOIN perteneceexperienciaeducativa ON perteneceexperienciaeducativa.idExperienciaEducativa = ExperienciaEducativa.idExperienciaEducativa     " +
                       "INNER JOIN estudiante ON estudiante.idEstudiante = perteneceexperienciaeducativa.idEstudiante      " +
                       "INNER JOIN usuario ON usuario.idUsuario = estudiante.idUsuario   "+
                       "INNER JOIN trabajorecepcional ON trabajorecepcional.idTrabajoRecepcional=estudiante.idTrabajoRecepcional " +
                       "where ExperienciaEducativa.idAcademico = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idAcademico);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList <TrabajoRecepcional> trabajosRecepcionalesConsulta = new ArrayList();
                while(resultado.next()){
                    TrabajoRecepcional trabajoRecepcional = new TrabajoRecepcional();
                    trabajoRecepcional.setIdTrabajoRecepcional(resultado.getInt("idtrabajorecepcional"));
                    trabajoRecepcional.setTitulo(resultado.getString("titulo"));  
                    trabajoRecepcional.setFechaAprobacion(resultado.getString("fechaAprobacion"));
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
    
    public static TrabajoRecepcional crearTrabajoRecepcional(int idAnteproyecto){
        TrabajoRecepcional respuesta = new TrabajoRecepcional();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "INSERT INTO trabajorecepcional (titulo, descripcion, fechaCreacion, fechaAprobacion, noEstudiantesMaximos, noEstudiantesAsignados, " +
                    " documento, nombreDocumento, idAnteproyecto, idTipoAnteproyecto, idCuerpoAcademico, idLGAC, idEstado) " +
                    "SELECT titulo, descripcion, fechaCreacion, fechaAprobacion, noEstudiantesMaximos, noEstudiantesAsignados, " +
                    "documento, nombreDocumento, idAnteproyecto, idTipoAnteproyecto, idCuerpoAcademico, idLGAC, ? FROM anteproyecto " +
                    "WHERE idAnteproyecto = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt (1, Constantes.EN_DESARROLLO);
                prepararSentencia.setInt (2, idAnteproyecto);
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta.setCodigoRespuesta((filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA);
                
                String consulta = "SELECT MAX(idTrabajoRecepcional) AS idTrabajoRecepcional FROM trabajoRecepcional;";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararConsulta.executeQuery();
                if(resultado.next()){
                    respuesta.setIdTrabajoRecepcional(resultado.getInt("idTrabajoRecepcional"));
                }
                conexionBD.close();
            }catch (SQLException e){
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                e.printStackTrace();
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static int eliminarTrabajoRecepcional(int idAnteproyecto){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "DELETE FROM trabajorecepcional WHERE idAnteproyecto = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt (1, idAnteproyecto);
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
