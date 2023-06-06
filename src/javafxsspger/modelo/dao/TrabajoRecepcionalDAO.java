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
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Anteproyecto;
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
    
    public static TrabajoRecepcional recuperarDetallesTrabajoRecepcional(int idTrabajoRecepcional){
        TrabajoRecepcional respuesta = new TrabajoRecepcional();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT trabajorecepcional.idTrabajoRecepcional, trabajorecepcional.idAnteproyecto, trabajorecepcional.titulo, trabajorecepcional.descripcion, trabajorecepcional.fechaCreacion, trabajorecepcional.fechaAprobacion, " +
                    "trabajorecepcional.noEstudiantesMaximos, trabajorecepcional.documento, trabajorecepcional.nombreDocumento, " +
                    "trabajorecepcional.idCuerpoAcademico, trabajorecepcional.idTipoAnteproyecto, trabajorecepcional.idLGAC, trabajorecepcional.idEstado, " +
                    "usuario.nombreUsuario, usuario.apellidoPaterno, usuario.apellidoMaterno, cuerpoacademico.nombre AS nombreCA, tipoanteproyecto.tipoanteproyecto, lgac.nombre AS nombreLGAC " +
                    "FROM sspger.trabajorecepcional " +
                    "INNER JOIN LGAC on LGAC.idLGAC = trabajorecepcional.idLGAC " +
                    "INNER JOIN cuerpoacademico ON cuerpoacademico.idCuerpoAcademico = trabajorecepcional.idCuerpoAcademico " +
                    "INNER JOIN tipoanteproyecto ON tipoAnteproyecto.idTipoAnteproyecto = trabajorecepcional.idTipoAnteproyecto " +
                    "INNER JOIN encargadostrabajorecepcional ON encargadostrabajorecepcional.idTrabajoRecepcional = trabajorecepcional.idTrabajoRecepcional " +
                    "INNER JOIN academico ON academico.idAcademico = encargadostrabajorecepcional.idAcademico " +
                    "INNER JOIN usuario ON academico.idUsuario = usuario.idUsuario " +
                    "WHERE encargadostrabajorecepcional.esDirector = 1 AND encargadostrabajorecepcional.idAcademico = academico.idAcademico " +
                    "AND trabajorecepcional.idTrabajoRecepcional = ? ";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idTrabajoRecepcional);
                ResultSet resultado = prepararSentencia.executeQuery();
                if(resultado.next()){
                    respuesta.setIdTrabajoRecepcional(idTrabajoRecepcional);
                    respuesta.setTitulo(resultado.getString("titulo"));
                    respuesta.setDescripcion(resultado.getString("descripcion"));
                    respuesta.setFechaCreacion(resultado.getString("fechaCreacion"));
                    respuesta.setFechaAprobacion(resultado.getString("fechaAprobacion"));
                    respuesta.setNoEstudiantesMaximos(resultado.getInt("noEstudiantesMaximos"));
                    respuesta.setDocumento(resultado.getBytes("documento"));
                    respuesta.setNombreDocumento(resultado.getString("nombreDocumento"));
                    
                    respuesta.setIdCuerpoAcademico(resultado.getInt("idCuerpoAcademico"));
                    respuesta.setIdTipoAnteproyecto(resultado.getInt("idTipoAnteproyecto"));
                    respuesta.setIdLGAC(resultado.getInt("idLGAC"));
                    
                    String nombreCompleto = resultado.getString("nombreUsuario") + " " + resultado.getString("apellidoPaterno") + " " + resultado.getString("apellidoMaterno");
                    respuesta.setNombreDirector(nombreCompleto);
                    
                    respuesta.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                    respuesta.setIdEstado(resultado.getInt("idEstado"));
                    
                }
                
                ArrayList <Academico> codirectores = new ArrayList();
                String consultaCodirectores = "SELECT academico.idAcademico, usuario.nombreUsuario, usuario.apellidoPaterno, usuario.apellidoMaterno " +
                    "FROM sspger.academico " +
                    "INNER JOIN encargadostrabajorecepcional ON encargadostrabajorecepcional.idAcademico = academico.idAcademico " +
                    "INNER JOIN usuario ON usuario.idUsuario = academico.idUsuario " +
                    "WHERE encargadostrabajorecepcional.esDirector = 0 AND encargadostrabajorecepcional.idAcademico = academico.idAcademico " +
                    " AND encargadostrabajorecepcional.idTrabajoRecepcional = ?";
                PreparedStatement prepararSentenciaCodirectores = conexionBD.prepareStatement(consultaCodirectores);
                prepararSentenciaCodirectores.setInt(1, idTrabajoRecepcional);
                ResultSet resultadoCodirectores = prepararSentenciaCodirectores.executeQuery();
                while(resultadoCodirectores.next()){
                    Academico codirector = new Academico();
                    codirector.setIdAcademico(resultadoCodirectores.getInt("idAcademico"));
                    String nombreCompleto = resultadoCodirectores.getString("nombreUsuario") + " " + resultadoCodirectores.getString("apellidoPaterno") + " " + resultadoCodirectores.getString("apellidoMaterno");
                    codirector.setNombre(resultadoCodirectores.getString("nombreUsuario"));
                    codirector.setApellidoPaterno(resultadoCodirectores.getString("apellidoPaterno"));
                    codirector.setApellidoMaterno(resultadoCodirectores.getString("apellidoMaterno"));
                    codirector.setNombreCompleto(nombreCompleto);
                    codirectores.add(codirector);
                }
                respuesta.setCodirectores(codirectores);
                
                String consultaDirector = "SELECT idAcademico FROM sspger.encargadostrabajorecepcional " +
                    "WHERE esDirector = 1 and idTrabajoRecepcional = ?";
                PreparedStatement prepararSentenciaDirector = conexionBD.prepareStatement(consultaDirector);
                prepararSentenciaDirector.setInt(1, idTrabajoRecepcional);
                ResultSet resultadoDirector = prepararSentenciaDirector.executeQuery();
                if(resultadoDirector.next()){
                    respuesta.setIdAcademico(resultadoDirector.getInt("idAcademico"));
                }
                
                respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                conexionBD.close();
            }catch(SQLException ex){
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                ex.printStackTrace();
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
}
