/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 14/05/2023
*Fecha de modificación: 23/05/2023
*Descripción: Clase encargada de la comunicación con la BD, especificamente para manipular la información de los anteproyectos
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
import javafxsspger.modelo.pojo.AnteproyectoRespuesta;
import javafxsspger.utils.Constantes;

public class AnteproyectoDAO {
    
    public static AnteproyectoRespuesta obtenerAnteproyectosPublicados(){
        AnteproyectoRespuesta respuesta = new AnteproyectoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT anteproyecto.titulo, usuario.nombreUsuario, usuario.apellidoPaterno, usuario.apellidoMaterno, anteproyecto.fechaAprobacion, " +
                        "anteproyecto.idAnteproyecto, encargadosanteproyecto.idAcademico " +
                        "FROM sspger.anteproyecto " +
                        "INNER JOIN encargadosanteproyecto ON anteproyecto.idAnteproyecto = encargadosanteproyecto.idAnteproyecto " +
                        "INNER JOIN academico ON encargadosanteproyecto.idAcademico = encargadosanteproyecto.idAcademico " +
                        "INNER JOIN usuario ON academico.idUsuario = usuario.idUsuario " +
                        "WHERE encargadosanteproyecto.esDirector = '1' AND encargadosanteproyecto.idAcademico = academico.idAcademico " +
                        "AND anteproyecto.noEstudiantesAsignados <= 0 " +
                        "AND anteproyecto.idEstado = 2 " +
                        "ORDER BY usuario.nombreUsuario ASC ";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList <Anteproyecto> anteproyectosConsulta = new ArrayList();
                while(resultado.next()){
                    Anteproyecto anteproyecto = new Anteproyecto();
                    anteproyecto.setTitulo(resultado.getString("titulo"));
                    String nombreCompleto = resultado.getString("nombreUsuario") + " " + resultado.getString("apellidoPaterno") + " " + resultado.getString("apellidoMaterno");
                    anteproyecto.setNombreDirector(nombreCompleto);
                    anteproyecto.setFechaAprobacion(resultado.getString("fechaAprobacion"));
                    anteproyecto.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                    anteproyectosConsulta.add(anteproyecto);
                }
                respuesta.setAnteproyectos(anteproyectosConsulta);
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
    
    public static AnteproyectoRespuesta obtenerAnteproyectosPorCorregir(){
        AnteproyectoRespuesta respuesta = new AnteproyectoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT anteproyecto.titulo, usuario.nombreUsuario, usuario.apellidoPaterno, usuario.apellidoMaterno, " +
                    "anteproyecto.fechaCreacion, anteproyecto.idAnteproyecto, " +
                    "encargadosanteproyecto.idAcademico " +
                    "FROM sspger.anteproyecto " +
                    "INNER JOIN encargadosanteproyecto ON anteproyecto.idAnteproyecto = encargadosanteproyecto.idAnteproyecto " +
                    "INNER JOIN academico ON encargadosanteproyecto.idAcademico = encargadosanteproyecto.idAcademico " +
                    "INNER JOIN usuario ON academico.idUsuario = usuario.idUsuario " +
                    "WHERE encargadosanteproyecto.esDirector = '1' AND encargadosanteproyecto.idAcademico = academico.idAcademico " +
                    "AND anteproyecto.noEstudiantesAsignados <= 0 AND anteproyecto.idEstado = 1 " +
                    "ORDER BY usuario.nombreUsuario ASC ";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList <Anteproyecto> anteproyectosConsulta = new ArrayList();
                while(resultado.next()){
                    Anteproyecto anteproyecto = new Anteproyecto();
                    anteproyecto.setTitulo(resultado.getString("titulo"));
                    String nombreCompleto = resultado.getString("nombreUsuario") + " " + resultado.getString("apellidoPaterno") + " " + resultado.getString("apellidoMaterno");
                    anteproyecto.setNombreDirector(nombreCompleto);
                    anteproyecto.setFechaCreacion(resultado.getString("fechaCreacion"));
                    anteproyecto.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                    anteproyectosConsulta.add(anteproyecto);
                }
                respuesta.setAnteproyectos(anteproyectosConsulta);
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
    
    public static AnteproyectoRespuesta obtenerAnteproyectosPropios(int idAcademico){
        AnteproyectoRespuesta respuesta = new AnteproyectoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT encargadosanteproyecto.idAnteproyecto, " +
                    "anteproyecto.titulo, anteproyecto.fechaCreacion, anteproyecto.fechaAprobacion " +
                    "FROM sspger.encargadosanteproyecto " +
                    "INNER JOIN anteproyecto ON anteproyecto.idAnteproyecto = encargadosanteproyecto.idAnteproyecto " +
                    "WHERE encargadosanteproyecto.idAcademico = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idAcademico);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList <Anteproyecto> anteproyectosConsulta = new ArrayList();
                while(resultado.next()){
                    Anteproyecto anteproyecto = new Anteproyecto();
                    anteproyecto.setTitulo(resultado.getString("titulo"));
                    anteproyecto.setFechaCreacion(resultado.getString("fechaCreacion"));
                    anteproyecto.setFechaAprobacion(resultado.getString("fechaAprobacion"));
                    anteproyecto.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                    anteproyectosConsulta.add(anteproyecto);
                }
                respuesta.setAnteproyectos(anteproyectosConsulta);
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
    
    public static Anteproyecto obtenerAnteproyecto (int idAnteproyecto){
        Anteproyecto anteproyectoRespuesta = new Anteproyecto();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT anteproyecto.idAnteproyecto, anteproyecto.titulo, anteproyecto.descripcion, anteproyecto.fechaCreacion, anteproyecto.fechaAprobacion, " +
                    "anteproyecto.noEstudiantesMaximos, anteproyecto.documento, anteproyecto.nombreDocumento, " +
                    "anteproyecto.idCuerpoAcademico, anteproyecto.idTipoAnteproyecto, anteproyecto.idLGAC, " +
                    "usuario.nombreUsuario, usuario.apellidoPaterno, usuario.apellidoMaterno, " +
                    "cuerpoacademico.nombre AS nombreCA, tipoanteproyecto.tipoanteproyecto, lgac.nombre AS nombreLGAC " +
                    "FROM sspger.anteproyecto " +
                    "INNER JOIN LGAC on LGAC.idLGAC = anteproyecto.idLGAC " +
                    "INNER JOIN cuerpoacademico ON cuerpoacademico.idCuerpoAcademico = anteproyecto.idCuerpoAcademico " +
                    "INNER JOIN tipoanteproyecto ON tipoAnteproyecto.idTipoAnteproyecto = anteproyecto.idTipoAnteproyecto " +
                    "INNER JOIN encargadosanteproyecto ON anteproyecto.idAnteproyecto = encargadosanteproyecto.idAnteproyecto " +
                    "INNER JOIN academico ON encargadosanteproyecto.idAcademico = encargadosanteproyecto.idAcademico " +
                    "INNER JOIN usuario ON academico.idUsuario = usuario.idUsuario " +
                    "WHERE encargadosanteproyecto.esDirector = '1' AND encargadosanteproyecto.idAcademico = academico.idAcademico " +
                    "AND anteproyecto.idAnteproyecto = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idAnteproyecto);
                ResultSet resultado = prepararSentencia.executeQuery();
                if(resultado.next()){
                    anteproyectoRespuesta.setTitulo(resultado.getString("titulo"));
                    anteproyectoRespuesta.setDescripcion(resultado.getString("descripcion"));
                    anteproyectoRespuesta.setFechaCreacion(resultado.getString("fechaCreacion"));
                    anteproyectoRespuesta.setFechaAprobacion(resultado.getString("fechaAprobacion"));
                    anteproyectoRespuesta.setNoEstudiantesMaximo(resultado.getInt("noEstudiantesMaximos"));
                    anteproyectoRespuesta.setDocumento(resultado.getBytes("documento"));
                    anteproyectoRespuesta.setNombreDocumento(resultado.getString("nombreDocumento"));
                    
                    anteproyectoRespuesta.setIdCuerpoAcademico(resultado.getInt("idCuerpoAcademico"));
                    anteproyectoRespuesta.setIdTipoAnteproyecto(resultado.getInt("idTipoAnteproyecto"));
                    anteproyectoRespuesta.setIdLGAC(resultado.getInt("idLGAC"));
                    
                    String nombreCompleto = resultado.getString("nombreUsuario") + " " + resultado.getString("apellidoPaterno") + " " + resultado.getString("apellidoMaterno");
                    anteproyectoRespuesta.setNombreDirector(nombreCompleto);
                    anteproyectoRespuesta.setNombreCuerpoAcademico(resultado.getString("nombreCA"));
                    anteproyectoRespuesta.setTipoAnteproyecto(resultado.getString("tipoAnteproyecto"));
                    anteproyectoRespuesta.setNombreLGAC(resultado.getString("nombreLGAC"));
                    
                    anteproyectoRespuesta.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                    
                }
                
                ArrayList <Academico> codirectores = new ArrayList();
                String consultaCodirectores = "SELECT academico.idAcademico, usuario.nombreUsuario, usuario.apellidoPaterno, usuario.apellidoMaterno " +
                    "FROM sspger.academico " +
                    "INNER JOIN encargadosanteproyecto ON encargadosanteproyecto.idAcademico = academico.idAcademico " +
                    "INNER JOIN usuario ON usuario.idUsuario = academico.idUsuario " +
                    "WHERE encargadosanteproyecto.esDirector = 0 AND encargadosanteproyecto.idAcademico = academico.idAcademico " +
                    " AND encargadosanteproyecto.idAnteproyecto = ?";
                PreparedStatement prepararSentenciaCodirectores = conexionBD.prepareStatement(consultaCodirectores);
                prepararSentenciaCodirectores.setInt(1, idAnteproyecto);
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
                anteproyectoRespuesta.setCodirectores(codirectores);
                
                String consultaDirector = "SELECT idAcademico FROM sspger.encargadosanteproyecto " +
                    "WHERE esDirector = 1 and idAnteproyecto = ?";
                PreparedStatement prepararSentenciaDirector = conexionBD.prepareStatement(consultaDirector);
                prepararSentenciaDirector.setInt(1, idAnteproyecto);
                ResultSet resultadoDirector = prepararSentenciaDirector.executeQuery();
                if(resultadoDirector.next()){
                    anteproyectoRespuesta.setIdDirector(resultadoDirector.getInt("idAcademico"));
                }
                
                anteproyectoRespuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                conexionBD.close();
            }catch(SQLException ex){
                anteproyectoRespuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                ex.printStackTrace();
            }
        }else{
            anteproyectoRespuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return anteproyectoRespuesta;
    }
    
    public static Anteproyecto guardarAnteproyecto (Anteproyecto anteproyectoNuevo){
        Anteproyecto anteproyectoRespuesta = new Anteproyecto();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD!=null){
            try{
                String sentencia = "insert into anteproyecto (titulo, descripcion, fechaCreacion, noEstudiantesMaximos, noEstudiantesAsignados, " +
                    "idEstado, idTipoAnteproyecto, idLGAC, documento, nombreDocumento, idCuerpoAcademico) " +
                    "values (?, ? , CURDATE(), ? , 0, 1 , ?, ?, ?, ?, ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString (1, anteproyectoNuevo.getTitulo());
                prepararSentencia.setString(2, anteproyectoNuevo.getDescripcion());
                prepararSentencia.setInt(3, anteproyectoNuevo.getNoEstudiantesMaximo());
                prepararSentencia.setInt(4, anteproyectoNuevo.getIdTipoAnteproyecto());
                prepararSentencia.setInt(5, anteproyectoNuevo.getIdLGAC());
                prepararSentencia.setBytes(6, anteproyectoNuevo.getDocumento());
                prepararSentencia.setString(7, anteproyectoNuevo.getNombreDocumento());
                prepararSentencia.setInt(8, anteproyectoNuevo.getIdCuerpoAcademico());
                int filasAfectadas = prepararSentencia.executeUpdate();
                anteproyectoRespuesta.setCodigoRespuesta ((filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA);
                
                String consulta = "SELECT idAnteproyecto FROM Anteproyecto " +
                    "WHERE titulo = ?";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(consulta);
                prepararConsulta.setString(1, anteproyectoNuevo.getTitulo());
                ResultSet resultado = prepararConsulta.executeQuery();
                if(resultado.next()){
                    anteproyectoRespuesta.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                }
                
                conexionBD.close();
            }catch (SQLException e){
                anteproyectoRespuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                e.printStackTrace();
            }
        }else{
            anteproyectoRespuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return anteproyectoRespuesta;
    }
    
    public static int publicarAnteproyecto(int idAnteproyectoModificado){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "UPDATE anteproyecto SET idEstado = 2, fechaAprobacion = curdate() WHERE idAnteproyecto = ?;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt (1, idAnteproyectoModificado);
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
                conexionBD.close();
            }catch (SQLException e){
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
    public static int devolderAnteproyecto(int idAnteproyectoModificado){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "UPDATE anteproyecto SET idEstado = 3 WHERE idAnteproyecto = ?;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt (1, idAnteproyectoModificado);
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
                conexionBD.close();
            }catch (SQLException e){
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
}
