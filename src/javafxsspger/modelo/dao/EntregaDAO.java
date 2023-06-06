/*
*Autor: Montiel Salas Jesús Jacob
*Fecha de creación: 05/06/2023
*Fecha de modificación: 05/06/2023
*Descripción: Clase encargada de la comunicación con la BD, especificamente para manipular la información de las entregas
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.Actividad;
import javafxsspger.modelo.pojo.Entrega;
import javafxsspger.modelo.pojo.EntregaRespuesta;
import javafxsspger.utils.Constantes;

public class EntregaDAO {
    
    public static int guardarEntrega(Actividad actividad){
            int respuesta;
            Connection conexionBD = ConexionBD.abrirConexionBD();
            if(conexionBD!=null){
                try{
                String sentencia = "INSERT INTO entrega (idActividad) VALUES ('?');";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, actividad.getIdActividad());
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
    
    public static int modificarEntrega(Entrega entrega){
            int respuesta;
            Connection conexionBD = ConexionBD.abrirConexionBD();
            if(conexionBD!=null){
                try{
                String sentencia = "UPDATE entrega SET retroalimentacion = ?, idRubricaCalificacion=? WHERE idEntrega = ?;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, entrega.getRetroalimentacion());
                prepararSentencia.setInt(2, entrega.getIdRubrica());
                prepararSentencia.setInt(3, entrega.getIdEntrega());
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
                conexionBD.close();
                }catch(SQLException e){
                    respuesta = Constantes.ERROR_CONSULTA;
                    e.printStackTrace();
                }
            }else{
                respuesta = Constantes.ERROR_CONEXION;
            }
            return respuesta;
                    
        }
    
    public static EntregaRespuesta recuperarEntrega(int idActividad){
        EntregaRespuesta respuesta = new EntregaRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD!=null){
            try{
                String consulta = "SELECT entrega.idEntrega, actividad.titulo, entrega.fechaEntrega, entrega.descripcion, "
                    + "entrega.retroalimentacion, entrega.idRubricaCalificacion, Usuario.nombreUsuario, Usuario.apellidoPaterno, Usuario.apellidoMaterno " +
                        "FROM sspger.usuario " +
                        "INNER JOIN estudiante ON estudiante.idUsuario=usuario.idUsuario " +
                        "INNER JOIN actividad ON actividad.idEstudiante=estudiante.idEstudiante " +
                        "INNER JOIN entrega ON actividad.idActividad = entrega.idActividad      " +
                        "where Entrega.idActividad = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idActividad);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Entrega> entregasConsulta = new ArrayList();
                while(resultado.next()){
                    Entrega entrega = new Entrega();
                    entrega.setIdEntrega(resultado.getInt("idEntrega"));
                    entrega.setTituloActividad(resultado.getString("titulo"));
                    entrega.setFechaEntrega(resultado.getString("fechaEntrega"));
                    entrega.setDescripcion(resultado.getString("descripcion"));
                    entrega.setRetroalimentacion(resultado.getString("retroalimentacion"));
                    entrega.setIdRubrica(resultado.getInt("idRubricaCalificacion"));
                    entrega.setNombreEstudiante(resultado.getString("nombreUsuario"));
                    entrega.setApellidoMaternoEstudiante(resultado.getString("apellidoPaterno"));
                    entrega.setApellidoPaternoEstudiante(resultado.getString("apellidoMaterno"));
                    
                    entregasConsulta.add(entrega);
                }
                respuesta.setEntregas(entregasConsulta);
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
    
    public static EntregaRespuesta recuperarCalificacion(int idActividad){
        EntregaRespuesta respuesta = new EntregaRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD!=null){
            try{
                String consulta = "SELECT entrega.idEntrega, actividad.titulo, entrega.fechaEntrega, entrega.descripcion,  " +
                "entrega.retroalimentacion, entrega.idRubricaCalificacion, Usuario.nombreUsuario, Usuario.apellidoPaterno, Usuario.apellidoMaterno, " +
                "rubricaCalificacion.nivelSatisfaccion, rubricaCalificacion.puntajeSatisfaccion   " +
                "FROM sspger.usuario    " +
                "INNER JOIN estudiante ON estudiante.idUsuario=usuario.idUsuario    " +
                "INNER JOIN actividad ON actividad.idEstudiante=estudiante.idEstudiante    " +
                "INNER JOIN entrega ON actividad.idActividad = entrega.idActividad     " +
                "INNER JOIN rubricacalificacion ON entrega.idRubricaCalificacion= rubricaCalificacion.idRubricaCalificacion " +
                "where Entrega.idActividad =  ?;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idActividad);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Entrega> entregasConsulta = new ArrayList();
                while(resultado.next()){
                    Entrega entrega = new Entrega();
                    entrega.setIdEntrega(resultado.getInt("idEntrega"));
                    entrega.setTituloActividad(resultado.getString("titulo"));
                    entrega.setFechaEntrega(resultado.getString("fechaEntrega"));
                    entrega.setDescripcion(resultado.getString("descripcion"));
                    entrega.setRetroalimentacion(resultado.getString("retroalimentacion"));
                    entrega.setIdRubrica(resultado.getInt("idRubricaCalificacion"));
                    entrega.setNombreEstudiante(resultado.getString("nombreUsuario"));
                    entrega.setApellidoMaternoEstudiante(resultado.getString("apellidoPaterno"));
                    entrega.setApellidoPaternoEstudiante(resultado.getString("apellidoMaterno"));
                    entrega.setNivelSatisfaccion(resultado.getString("nivelSatisfaccion"));
                    entrega.setPuntajeSatisfaccion(resultado.getInt("puntajeSatisfaccion"));
                    entregasConsulta.add(entrega);
                }
                respuesta.setEntregas(entregasConsulta);
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
