/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 03/06/2023
*Fecha de modificación: 03/06/2023
*Descripción: Clase encargada de la comunicación con la BD, especificamente para manipular la información de los Avances
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Actividad;
import javafxsspger.modelo.pojo.Avance;
import javafxsspger.modelo.pojo.AvanceRespuesta;
import javafxsspger.utils.Constantes;

public class AvanceDAO {
    
    public static AvanceRespuesta obtenerAvances (int idEstudiante){
        AvanceRespuesta respuesta = new AvanceRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD!=null){
            try{
                String consulta = "SELECT idAvance, titulo, fechaCreacion FROM sspger.avance where idEstudiante = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idEstudiante);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Avance> avances = new ArrayList();
                while(resultado.next()){
                    Avance avance = new Avance();
                    avance.setIdAvance(resultado.getInt("idAvance"));
                    avance.setTitulo(resultado.getString("titulo"));
                    avance.setFechaCreacion(resultado.getString("fechaCreacion"));
                    avances.add(avance);
                }
                respuesta.setAvances(avances);
                conexionBD.close();
                respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
            }catch(SQLException e){
                e.printStackTrace();
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            } 
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static Avance obtenerAvance (int idAvance){
        Avance respuesta = new Avance();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD!=null){
            try{
                String consulta = "SELECT avance.idAvance, avance.titulo, avance.descripcion, avance.fechaCreacion, avance.fechaInicio, " +
                    "avance.fechaFinal, avance.idRubricaCalificacion, rubricacalificacion.nivelSatisfaccion, avance.retroalimentacion, " +
                    "rubricacalificacion.puntajeSatisfaccion FROM avance " +
                    "INNER JOIN rubricacalificacion ON rubricacalificacion.idRubricaCalificacion = avance.idRubricaCalificacion " +
                    "Where idAvance = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idAvance);
                ResultSet resultado = prepararSentencia.executeQuery();
                if(resultado.next()){
                    respuesta.setIdAvance(resultado.getInt("idAvance"));
                    respuesta.setTitulo(resultado.getString("titulo"));
                    respuesta.setDescripcion(resultado.getString("descripcion"));
                    respuesta.setFechaCreacion(resultado.getString("fechaCreacion"));
                    respuesta.setFechaInicio(resultado.getString("fechaInicio"));
                    respuesta.setFechaFin(resultado.getString("fechaFinal"));
                    respuesta.setIdRubrica(resultado.getInt("idRubricaCalificacion"));
                    respuesta.setNivelSatisfaccion(resultado.getString("nivelSatisfaccion"));
                    respuesta.setPuntajeSatisfaccion(resultado.getInt("puntajeSatisfaccion"));
                    respuesta.setRetroalimentacion(resultado.getString("retroalimentacion"));
                    
                    String consultaDirectores = "SELECT encargadostrabajorecepcional.idAcademico FROM avance " +
                        "INNER JOIN estudiante ON estudiante.idEstudiante = avance.idEstudiante " +
                        "INNER JOIN trabajorecepcional on trabajorecepcional.idTrabajoRecepcional = estudiante.idTrabajoRecepcional " +
                        "INNER JOIN encargadostrabajorecepcional on encargadostrabajorecepcional.idTrabajoRecepcional = trabajorecepcional.idTrabajoRecepcional " +
                        "Where idAvance = ?";
                    PreparedStatement prepararSentenciaActividadesDirectores = conexionBD.prepareStatement(consultaDirectores);
                    prepararSentenciaActividadesDirectores.setInt(1, idAvance);
                    ResultSet resultadoDirectores = prepararSentenciaActividadesDirectores.executeQuery();
                    ArrayList<Academico> academicos = new ArrayList();
                    while(resultadoDirectores.next()){
                        Academico academico = new Academico();
                        academico.setIdAcademico(resultadoDirectores.getInt("idAcademico"));
                        academicos.add(academico);
                    }
                    respuesta.setDirectores(academicos);
                    
                    String consultaActividades = "SELECT actividad.idActividad, actividad.descripcion, actividad.titulo, actividad.fechaCreacion, actividad.idEstudiante, " +
                        "usuario.nombreUsuario, usuario.apellidoPaterno, usuario.apellidoMaterno, actividad.fechaCreacion, actividad.fechaFinal, " +
                        "actividad.fechaInicio FROM sspger.actividad " +
                        "INNER JOIN estudiante ON actividad.idEstudiante = estudiante.idEstudiante " +
                        "INNER JOIN usuario ON usuario.idUsuario = estudiante.idUsuario " +
                        "WHERE actividad.idAvance = ?";
                    PreparedStatement prepararSentenciaActividades = conexionBD.prepareStatement(consultaActividades);
                    prepararSentenciaActividades.setInt(1, idAvance);
                    ResultSet resultadoActividades = prepararSentenciaActividades.executeQuery();
                    
                    ArrayList<Actividad> actividades = new ArrayList();
                    while(resultadoActividades.next()){
                        Actividad actividad = new Actividad();
                        actividad.setNombreEstudiante(resultadoActividades.getString("nombreUsuario"));
                        actividad.setApellidoPaternoEstudiante(resultadoActividades.getString("apellidoPaterno"));
                        actividad.setApellidoMaternoEstudiante(resultadoActividades.getString("apellidoMaterno"));
                        actividad.setIdActividad(resultadoActividades.getInt("idActividad"));
                        actividad.setTitulo(resultadoActividades.getString("titulo"));
                        actividad.setDescripcion(resultadoActividades.getString("descripcion"));
                        actividad.setFechaCreacion(resultadoActividades.getString("fechaCreacion"));
                        actividad.setFechaInicio(resultadoActividades.getString("fechaInicio"));
                        actividad.setFechaFinal(resultadoActividades.getString("fechaFinal"));
                        actividades.add(actividad);
                        
                        String consultaEntrega = "SELECT idEntrega FROM sspger.entrega WHERE idActividad = ? AND fechaEntrega is not null";
                        PreparedStatement prepararSentenciaEntrega = conexionBD.prepareStatement(consultaEntrega);
                        prepararSentenciaEntrega.setInt(1, actividad.getIdActividad());
                        ResultSet resultadoEntrega = prepararSentenciaEntrega.executeQuery();
                        actividad.setTieneEntrega(resultadoEntrega.next());
                    }
                    respuesta.setActividades(actividades);
                }
                conexionBD.close();
                respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
            }catch(SQLException e){
                e.printStackTrace();
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            } 
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static Avance guardarAvance(Avance avance){
        Avance respuesta = new Avance();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD!=null){
            try{
                String sentencia = "INSERT INTO Avance(titulo,descripcion,fechaCreacion, fechaInicio, fechaFinal, idEstudiante, idRubricaCalificacion) " +
                    "VALUES (?, ?, curdate(), ?, ?, ?, ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, avance.getTitulo());
                prepararSentencia.setString(2, avance.getDescripcion());
                prepararSentencia.setString(3, avance.getFechaInicio());
                prepararSentencia.setString(4, avance.getFechaFin());
                prepararSentencia.setInt(5, avance.getIdEstudiante());
                prepararSentencia.setInt(6, Constantes.ID_CALIFICACION_PENDIENTE);
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta.setCodigoRespuesta(((filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA));
                
                if(respuesta.getCodigoRespuesta() == Constantes.OPERACION_EXITOSA){
                    String consulta = "SELECT MAX(idAvance) AS idAvance FROM avance";
                    PreparedStatement prepararConsulta = conexionBD.prepareStatement(consulta);
                    ResultSet resultado = prepararConsulta.executeQuery();
                    if(resultado.next()){
                        respuesta.setIdAvance(resultado.getInt("idAvance"));
                    }
                }
                respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
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
    
    public static int guardarModificaciones(Avance avance){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD!=null){
            try{
                String sentencia = "UPDATE Avance SET titulo = ?, descripcion = ?, fechaInicio = ?, fechaFinal = ?, idEstudiante = ? WHERE idAvance = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, avance.getTitulo());
                prepararSentencia.setString(2, avance.getDescripcion());
                prepararSentencia.setString(3, avance.getFechaInicio());
                prepararSentencia.setString(4, avance.getFechaFin());
                prepararSentencia.setInt(5, avance.getIdEstudiante());
                prepararSentencia.setInt(6, avance.getIdAvance());
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = ((filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA);
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
    
    public static int borrarAsociacionPreviaActividades(Avance avance){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD!=null){
            try{
                String sentencia = "UPDATE actividad SET idAvance = null WHERE idAvance = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, avance.getIdAvance());
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas >= 0) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
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
    
    public static int eliminarAvance(int idAvance){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD!=null){
            try{
            String sentencia = "DELETE FROM avance WHERE idAvance =?";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
            prepararSentencia.setInt(1, idAvance);
            int filasAfectadas = prepararSentencia.executeUpdate();
            respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
            conexionBD.close();
            }catch(SQLException e){
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;   
    }
    
    public static int guardarCalificacion(Avance avance){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD!=null){
            try{
                String sentencia = "UPDATE Avance SET idRubricaCalificacion = ?, retroalimentacion = ? WHERE idAvance = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, avance.getIdRubrica());
                prepararSentencia.setString(2, avance.getRetroalimentacion());
                prepararSentencia.setInt(3, avance.getIdAvance());
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = ((filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA);
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
