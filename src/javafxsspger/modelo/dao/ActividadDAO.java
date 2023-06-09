/*
*Autor: Montiel Salas Jesús Jacob, Martinez Aguilar Sulem
*Fecha de creación: 20/05/2023
*Fecha de modificación: 05/06/2023
*Descripción: Clase encargada de la comunicación con la bd, especificamente para manipular la información de las Actividades
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsspger.modelo.ConexionBD;
import static javafxsspger.modelo.dao.EntregaDAO.guardarEntrega;
import javafxsspger.modelo.pojo.Actividad;
import javafxsspger.modelo.pojo.ActividadRespuesta;
import javafxsspger.modelo.pojo.Avance;
import javafxsspger.utils.Constantes;

public class ActividadDAO {
    
    
    public static ActividadRespuesta obtenerActividadesPorTrabajoRecepcionalAcademico(int idTrabajoRecepcional){
        ActividadRespuesta respuesta = new ActividadRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT Usuario.nombreUsuario, Usuario.apellidoPaterno, Usuario.apellidoMaterno, Actividad.idActividad, Actividad.titulo,Actividad.descripcion, " +
                "Actividad.fechaCreacion,Actividad.fechaInicio,Actividad.fechaFinal " +
                "from Usuario " +
                "INNER JOIN Estudiante ON Usuario.idUsuario=Estudiante.idUsuario " +
                "INNER JOIN Actividad ON Estudiante.idEstudiante=Actividad.idEstudiante " +
                "INNER JOIN TrabajoRecepcional ON Actividad.idTrabajoRecepcional=TrabajoRecepcional.idTrabajoRecepcional " +
                "where TrabajoRecepcional.idTrabajoRecepcional=?;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idTrabajoRecepcional);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Actividad> ActividadesConsulta = new ArrayList();
                while(resultado.next()){
                    Actividad actividad = new Actividad();
                    actividad.setNombreEstudiante(resultado.getString("nombreUsuario"));
                    actividad.setApellidoPaternoEstudiante(resultado.getString("apellidoPaterno"));
                    actividad.setApellidoMaternoEstudiante(resultado.getString("apellidoMaterno"));
                    actividad.setIdActividad(resultado.getInt("idActividad"));
                    actividad.setTitulo(resultado.getString("titulo"));
                    actividad.setDescripcion(resultado.getString("descripcion"));
                    actividad.setFechaCreacion(resultado.getString("fechaCreacion"));
                    actividad.setFechaInicio(resultado.getString("fechaInicio"));
                    actividad.setFechaFinal(resultado.getString("fechaFinal"));
                    ActividadesConsulta.add(actividad);
                }
                respuesta.setActividades(ActividadesConsulta);
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
    
    public static ActividadRespuesta obtenerActividadesPorTrabajoRecepcionalEstudiante(int idTrabajoRecepcional, int idEstudiante){
        ActividadRespuesta respuesta = new ActividadRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT Usuario.nombreUsuario, Usuario.apellidoPaterno, Usuario.apellidoMaterno, Actividad.idActividad, Actividad.titulo,Actividad.descripcion, " +
                "Actividad.fechaCreacion,Actividad.fechaInicio,Actividad.fechaFinal " +
                "from Usuario " +
                "INNER JOIN Estudiante ON Usuario.idUsuario=Estudiante.idUsuario " +
                "INNER JOIN Actividad ON Estudiante.idEstudiante=Actividad.idEstudiante " +
                "INNER JOIN TrabajoRecepcional ON Actividad.idTrabajoRecepcional=TrabajoRecepcional.idTrabajoRecepcional " +
                "where TrabajoRecepcional.idTrabajoRecepcional=? AND Estudiante.idEstudiante=? ;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idTrabajoRecepcional);
                prepararSentencia.setInt(2, idEstudiante); 
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Actividad> ActividadesConsulta = new ArrayList();
                while(resultado.next()){
                    Actividad actividad = new Actividad();
                    actividad.setNombreEstudiante(resultado.getString("nombreUsuario"));
                    actividad.setApellidoPaternoEstudiante(resultado.getString("apellidoPaterno"));
                    actividad.setApellidoMaternoEstudiante(resultado.getString("apellidoMaterno"));
                    actividad.setIdActividad(resultado.getInt("idActividad"));
                    actividad.setTitulo(resultado.getString("titulo"));
                    actividad.setDescripcion(resultado.getString("descripcion"));
                    actividad.setFechaCreacion(resultado.getString("fechaCreacion"));
                    actividad.setFechaInicio(resultado.getString("fechaInicio"));
                    actividad.setFechaFinal(resultado.getString("fechaFinal"));
                    ActividadesConsulta.add(actividad);
                }
                respuesta.setActividades(ActividadesConsulta);
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
    
    public static int guardarActividad(Actividad actividadNueva){
            int respuesta;
            Connection conexionBD = ConexionBD.abrirConexionBD();
            if(conexionBD!=null){
                try{
                    String sentencia = "Insert into Actividad(titulo, descripcion, fechaCreacion, fechaInicio, fechaFinal, idEstudiante, idTrabajoRecepcional) "+
                                   "VALUES (?,?,CURDATE(),?,?,?,?);";
                    PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia,PreparedStatement.RETURN_GENERATED_KEYS);
                    prepararSentencia.setString(1, actividadNueva.getTitulo());
                    prepararSentencia.setString(2, actividadNueva.getDescripcion());
                    prepararSentencia.setString(3, actividadNueva.getFechaInicio());
                    prepararSentencia.setString(4, actividadNueva.getFechaFinal());
                    prepararSentencia.setInt(5, actividadNueva.getIdEstudiante());
                    prepararSentencia.setInt(6, actividadNueva.getIdTrabajoRecepcional());
                    int filasAfectadas = prepararSentencia.executeUpdate();
                    respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
                    ResultSet generatedKeys = prepararSentencia.getGeneratedKeys();
                    if (generatedKeys.next()  &&  respuesta == Constantes.OPERACION_EXITOSA) {
                        int idActividad = generatedKeys.getInt(1);
                        String consultaEntrega = "INSERT INTO entrega (idActividad) VALUES (?)";
                        PreparedStatement prepararSentenciaEntrega = conexionBD.prepareStatement(consultaEntrega);
                        prepararSentenciaEntrega.setInt(1, idActividad);
                        prepararSentenciaEntrega.executeUpdate();
                        respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
                    }
                    conexionBD.close();
                }catch(SQLException e){
                    respuesta = Constantes.ERROR_CONSULTA;
                }
            }else{
                respuesta = Constantes.ERROR_CONEXION;
            }
            return respuesta;
    }
    
    public static int eliminarActividad(int idActividad){
            int respuesta;
            Connection conexionBD = ConexionBD.abrirConexionBD();
            if(conexionBD != null){
                try{
                    String sentencia = "DELETE FROM actividad WHERE idActividad=?;";
                    PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                    prepararSentencia.setInt(1, idActividad);
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
    
        public static int modificarActividad(Actividad modificacionActividad){
            int respuesta;
            Connection conexionBD = ConexionBD.abrirConexionBD();
            if(conexionBD != null){
                try{
                    String sentencia = "UPDATE Actividad SET titulo = ?, descripcion = ?, fechaInicio = ?, fechaFinal = ?  "+
                                   "WHERE idActividad = ?;";
                    PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                    prepararSentencia.setString(1, modificacionActividad.getTitulo());
                    prepararSentencia.setString(2, modificacionActividad.getDescripcion());
                    prepararSentencia.setString(3, modificacionActividad.getFechaInicio());
                    prepararSentencia.setString(4, modificacionActividad.getFechaFinal());
                    prepararSentencia.setInt(5, modificacionActividad.getIdActividad());
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
        
    public static ActividadRespuesta obtenerActividades(int idEstudiante){
        ActividadRespuesta respuesta = new ActividadRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT idActividad, titulo, fechaCreacion, fechaInicio, fechaFinal FROM sspger.actividad WHERE idEstudiante = ? and idAvance is null";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idEstudiante);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Actividad> actividades = new ArrayList();
                while(resultado.next()){
                    Actividad actividad = new Actividad();
                    actividad.setIdActividad(resultado.getInt("idActividad"));
                    actividad.setTitulo(resultado.getString("titulo"));
                    actividad.setFechaCreacion(resultado.getString("fechaCreacion"));
                    actividad.setFechaInicio(resultado.getString("fechaInicio"));
                    actividad.setFechaFinal(resultado.getString("fechaFinal"));
                    actividades.add(actividad);
                }
                respuesta.setActividades(actividades);
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
    
    public static ActividadRespuesta obtenerActividadesPosiblesYDelAvance(Avance avance){
        ActividadRespuesta respuesta = new ActividadRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT idActividad, titulo, fechaCreacion, fechaInicio, fechaFinal, idAvance " +
                    "FROM sspger.actividad WHERE idEstudiante = ? and idAvance is null or idAvance = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, avance.getIdEstudiante());
                prepararSentencia.setInt(2, avance.getIdAvance());
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Actividad> actividades = new ArrayList();
                while(resultado.next()){
                    Actividad actividad = new Actividad();
                    actividad.setIdActividad(resultado.getInt("idActividad"));
                    actividad.setTitulo(resultado.getString("titulo"));
                    actividad.setFechaCreacion(resultado.getString("fechaCreacion"));
                    actividad.setFechaInicio(resultado.getString("fechaInicio"));
                    actividad.setFechaFinal(resultado.getString("fechaFinal"));
                    actividad.setIdAvance(resultado.getInt("idAvance"));
                    actividades.add(actividad);
                }
                respuesta.setActividades(actividades);
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
    
    public static int agregarActividadAvance(int idActividad, int idAvance){
        int respuesta;
            Connection conexionBD = ConexionBD.abrirConexionBD();
            if(conexionBD != null){
                try{
                String sentencia = "UPDATE Actividad SET idAvance = ? WHERE idActividad = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idAvance);
                prepararSentencia.setInt(2, idActividad);
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
    
    public static ActividadRespuesta obtenerActividadesPorFechaYTrabajoRecepcionalAcademico(int idTrabajoRecepcional, String fechaFiltro){
        ActividadRespuesta respuesta = new ActividadRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT Usuario.nombreUsuario, Usuario.apellidoPaterno, Usuario.apellidoMaterno, Actividad.idActividad, Actividad.titulo,Actividad.descripcion, " +
                "Actividad.fechaCreacion,Actividad.fechaInicio,Actividad.fechaFinal " +
                "from Usuario  " +
                "INNER JOIN Estudiante ON Usuario.idUsuario=Estudiante.idUsuario " +
                "INNER JOIN Actividad ON Estudiante.idEstudiante=Actividad.idEstudiante " +
                "INNER JOIN TrabajoRecepcional ON Actividad.idTrabajoRecepcional=TrabajoRecepcional.idTrabajoRecepcional " +
                "where TrabajoRecepcional.idTrabajoRecepcional=? AND Actividad.fechaInicio <= ? AND Actividad.fechaFinal >= ?; ";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idTrabajoRecepcional);
                prepararSentencia.setString(2, fechaFiltro);
                prepararSentencia.setString(3, fechaFiltro);                
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Actividad> ActividadesConsulta = new ArrayList();
                while(resultado.next()){
                    Actividad actividad = new Actividad();
                    actividad.setNombreEstudiante(resultado.getString("nombreUsuario"));
                    actividad.setApellidoPaternoEstudiante(resultado.getString("apellidoPaterno"));
                    actividad.setApellidoMaternoEstudiante(resultado.getString("apellidoMaterno"));
                    actividad.setIdActividad(resultado.getInt("idActividad"));
                    actividad.setTitulo(resultado.getString("titulo"));
                    actividad.setDescripcion(resultado.getString("descripcion"));
                    actividad.setFechaCreacion(resultado.getString("fechaCreacion"));
                    actividad.setFechaInicio(resultado.getString("fechaInicio"));
                    actividad.setFechaFinal(resultado.getString("fechaFinal"));
                    ActividadesConsulta.add(actividad);
                }
                respuesta.setActividades(ActividadesConsulta);
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
    
    public static ActividadRespuesta obtenerActividadesPorFechaYTrabajoRecepcionalEstudiante(int idTrabajoRecepcional, String fechaFiltro, int idEstudiante){
        ActividadRespuesta respuesta = new ActividadRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT Usuario.nombreUsuario, Usuario.apellidoPaterno, Usuario.apellidoMaterno, Actividad.idActividad, Actividad.titulo,Actividad.descripcion, " +
                "Actividad.fechaCreacion,Actividad.fechaInicio,Actividad.fechaFinal " +
                "from Usuario  " +
                "INNER JOIN Estudiante ON Usuario.idUsuario=Estudiante.idUsuario " +
                "INNER JOIN Actividad ON Estudiante.idEstudiante=Actividad.idEstudiante " +
                "INNER JOIN TrabajoRecepcional ON Actividad.idTrabajoRecepcional=TrabajoRecepcional.idTrabajoRecepcional " +
                "where TrabajoRecepcional.idTrabajoRecepcional=? AND Actividad.fechaInicio <= ? AND Actividad.fechaFinal >= ? AND Estudiante.idEstudiante=?; ";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idTrabajoRecepcional);
                prepararSentencia.setString(2, fechaFiltro);
                prepararSentencia.setString(3, fechaFiltro);                
                prepararSentencia.setInt(4, idEstudiante);                
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Actividad> ActividadesConsulta = new ArrayList();
                while(resultado.next()){
                    Actividad actividad = new Actividad();
                    actividad.setNombreEstudiante(resultado.getString("nombreUsuario"));
                    actividad.setApellidoPaternoEstudiante(resultado.getString("apellidoPaterno"));
                    actividad.setApellidoMaternoEstudiante(resultado.getString("apellidoMaterno"));
                    actividad.setIdActividad(resultado.getInt("idActividad"));
                    actividad.setTitulo(resultado.getString("titulo"));
                    actividad.setDescripcion(resultado.getString("descripcion"));
                    actividad.setFechaCreacion(resultado.getString("fechaCreacion"));
                    actividad.setFechaInicio(resultado.getString("fechaInicio"));
                    actividad.setFechaFinal(resultado.getString("fechaFinal"));
                    ActividadesConsulta.add(actividad);
                }
                respuesta.setActividades(ActividadesConsulta);
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
