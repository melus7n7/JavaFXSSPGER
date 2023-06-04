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
                    "avance.fechaFinal, avance.idRubricaCalificacion, rubricacalificacion.nivelSatisfaccion, " +
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
                        
                        String consultaEntrega = "SELECT idEntrega FROM sspger.entrega WHERE idActividad = ?";
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
}
