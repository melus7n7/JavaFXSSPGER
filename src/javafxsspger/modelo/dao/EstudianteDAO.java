/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 27/05/2023
*Fecha de modificación: 27/05/2023
*Descripción: Clase encargada de la comunicación con la bd, especificamente para manipular la información de los estudiantes
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.modelo.pojo.EstudianteRespuesta;
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
    
    public static EstudianteRespuesta recuperarEstudiantesAnteproyecto(int idAnteproyecto){
        EstudianteRespuesta respuesta = new EstudianteRespuesta ();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT estudiante.idEstudiante, estudiante.matricula, Usuario.nombreUsuario, Usuario.apellidoPaterno, Usuario.apellidoMaterno " +
                "FROM sspger.estudiante INNER JOIN usuario ON usuario.idUsuario = estudiante.idUsuario Where estudiante.idAnteproyecto = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idAnteproyecto);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList <Estudiante> estudiantes = new ArrayList();
                while(resultado.next()){
                    Estudiante estudiante = new Estudiante();
                    estudiante.setIdEstudiante(resultado.getInt("idEstudiante"));
                    estudiante.setMatricula(resultado.getString("matricula"));
                    estudiante.setNombre(resultado.getString("nombreUsuario"));
                    estudiante.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    estudiante.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    estudiantes.add(estudiante);
                }
                respuesta.setEstudiantes(estudiantes);
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
    
    public static EstudianteRespuesta recuperarEstudiantesPosibles(ArrayList<Estudiante> estudiantesAnteproyecto){
        int idEstudiante1 = 0;
        int idEstudiante2 = 0;
        if(!estudiantesAnteproyecto.isEmpty()){
            if(estudiantesAnteproyecto.size() == 2){
                idEstudiante2 = estudiantesAnteproyecto.get(1).getIdEstudiante();
            }
            idEstudiante1 = estudiantesAnteproyecto.get(0).getIdEstudiante();
        } 
        EstudianteRespuesta respuesta = new EstudianteRespuesta ();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT estudiante.idEstudiante, estudiante.matricula, Usuario.nombreUsuario, Usuario.apellidoPaterno, Usuario.apellidoMaterno " +
                    "FROM sspger.estudiante INNER JOIN usuario ON usuario.idUsuario = estudiante.idUsuario " +
                    "Where estudiante.idEstudiante != ? AND estudiante.idEstudiante != ? AND estudiante.idAnteproyecto is null";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idEstudiante1);
                prepararSentencia.setInt(2, idEstudiante2);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList <Estudiante> estudiantes = new ArrayList();
                while(resultado.next()){
                    Estudiante estudiante = new Estudiante();
                    estudiante.setIdEstudiante(resultado.getInt("idEstudiante"));
                    estudiante.setMatricula(resultado.getString("matricula"));
                    estudiante.setNombre(resultado.getString("nombreUsuario"));
                    estudiante.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    estudiante.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    estudiante.setNombreCompleto(estudiante.getNombre() + " " + estudiante.getApellidoPaterno() + " " + estudiante.getApellidoMaterno());
                    estudiantes.add(estudiante);
                }
                respuesta.setEstudiantes(estudiantes);
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
    
    public static int asignarAnteproyectoEstudiante(Estudiante estudiante){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "UPDATE estudiante SET idAnteproyecto = ?, idTrabajoRecepcional = ? WHERE idEstudiante = ?;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt (1, estudiante.getIdAnteproyecto());
                prepararSentencia.setInt (2, estudiante.getIdTrabajoRecepcional());
                prepararSentencia.setInt(3, estudiante.getIdEstudiante());
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
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
    
    public static int eliminarAnteproyectoEstudiante(Estudiante estudiante){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "UPDATE estudiante SET idAnteproyecto = null, idTrabajoRecepcional = null WHERE idEstudiante = ?;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, estudiante.getIdEstudiante());
                int filasAfectadas = prepararSentencia.executeUpdate();
                String sentenciaAvances = "DELETE FROM avance WHERE idEstudiante = ?";
                PreparedStatement prepararSentenciaAvances = conexionBD.prepareStatement(sentenciaAvances);
                prepararSentenciaAvances.setInt (1, estudiante.getIdEstudiante());
                int filasAfectadasAvances = prepararSentenciaAvances.executeUpdate();
                String sentenciaActividad = "DELETE FROM actividad WHERE idEstudiante = ?";
                PreparedStatement prepararSentenciaActividades = conexionBD.prepareStatement(sentenciaActividad);
                prepararSentenciaActividades.setInt (1, estudiante.getIdEstudiante());
                int filasAfectadasActividades = prepararSentenciaActividades.executeUpdate();
                respuesta = (filasAfectadas == 1 && filasAfectadasAvances >= 0 && filasAfectadasActividades >=0) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
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
    
    public static EstudianteRespuesta recuperarEstudiantesProfesor (int idAcademico){
        EstudianteRespuesta respuesta = new EstudianteRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT ExperienciaEducativa.idExperienciaEducativa, perteneceexperienciaeducativa.idestudiante, " +
                "usuario.nombreUsuario, usuario.apellidoPaterno, usuario.apellidoMaterno " +
                "FROM sspger.ExperienciaEducativa " +
                "INNER JOIN perteneceexperienciaeducativa ON perteneceexperienciaeducativa.idExperienciaEducativa = ExperienciaEducativa.idExperienciaEducativa " +
                "INNER JOIN estudiante ON estudiante.idEstudiante = perteneceexperienciaeducativa.idEstudiante " +
                "INNER JOIN usuario ON usuario.idUsuario = estudiante.idUsuario " +
                "where ExperienciaEducativa.idAcademico = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idAcademico);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList <Estudiante> estudiantesConsulta = new ArrayList();
                while(resultado.next()){
                    Estudiante estudiante = new Estudiante();
                    estudiante.setIdEstudiante(resultado.getInt("idestudiante"));
                    String nombreCompleto = resultado.getString("nombreUsuario") + " " + resultado.getString("apellidoPaterno") + " " + resultado.getString("apellidoMaterno");
                    estudiante.setNombreCompleto(nombreCompleto);
                    estudiante.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    estudiante.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    estudiante.setNombre(resultado.getString("nombreUsuario"));
                    estudiantesConsulta.add(estudiante);
                }
                respuesta.setEstudiantes(estudiantesConsulta);
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
    
    public static EstudianteRespuesta recuperarEstudiantesDirector (int idAcademico){
        EstudianteRespuesta respuesta = new EstudianteRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT estudiante.idestudiante, usuario.nombreUsuario, usuario.apellidoPaterno, usuario.apellidoMaterno " +
                    "FROM sspger.encargadostrabajorecepcional " +
                    "INNER JOIN estudiante ON estudiante.idTrabajoRecepcional = encargadostrabajorecepcional.idTrabajoRecepcional " +
                    "INNER JOIN usuario ON usuario.idUsuario = estudiante.idUsuario " +
                    "where encargadostrabajorecepcional.idAcademico = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idAcademico);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList <Estudiante> estudiantesConsulta = new ArrayList();
                while(resultado.next()){
                    Estudiante estudiante = new Estudiante();
                    estudiante.setIdEstudiante(resultado.getInt("idestudiante"));
                    String nombreCompleto = resultado.getString("nombreUsuario") + " " + resultado.getString("apellidoPaterno") + " " + resultado.getString("apellidoMaterno");
                    estudiante.setNombreCompleto(nombreCompleto);
                    estudiante.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    estudiante.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    estudiante.setNombre(resultado.getString("nombreUsuario"));
                    estudiantesConsulta.add(estudiante);
                }
                respuesta.setEstudiantes(estudiantesConsulta);
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
