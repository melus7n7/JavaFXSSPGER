/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 24/05/2023
*Fecha de modificación: 24/05/2023
*Descripción: Clase encargada de la comunicación con la bd, especificamente para manipular la información de los comentarios
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.Comentario;
import javafxsspger.modelo.pojo.ComentarioRespuesta;
import javafxsspger.utils.Constantes;


public class ComentarioDAO {
    
    
    public static int guardarComentario(Comentario comentarioNuevo){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "INSERT comentario(texto,fechaCreacion,idAcademico,idAnteproyecto) " +
                    "values(?, curdate(), ?, ?);";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, comentarioNuevo.getTexto());
                prepararSentencia.setInt(2, comentarioNuevo.getIdAcademico());
                prepararSentencia.setInt(3, comentarioNuevo.getIdAnteproyecto());
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
    
    public static ComentarioRespuesta recuperarComentarios(int idAnteproyecto){
        ComentarioRespuesta respuesta = new ComentarioRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT Comentario.idComentario, Comentario.texto, Comentario.fechaCreacion, Comentario.idAcademico,  " +
                    "Usuario.nombreUsuario, Usuario.apellidoPaterno, Usuario.apellidoMaterno " +
                    "FROM sspger.comentario " +
                    "INNER JOIN Academico ON academico.idAcademico = comentario.idAcademico " +
                    "INNER JOIN Usuario ON usuario.idUsuario = academico.idUsuario " +
                    "WHERE idAnteproyecto = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idAnteproyecto);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList <Comentario> comentarios = new ArrayList();
                while(resultado.next()){
                    Comentario comentario = new Comentario();
                    comentario.setIdComentario(resultado.getInt("idComentario"));
                    comentario.setTexto(resultado.getString("texto"));
                    comentario.setFechaCreacion(resultado.getDate("fechaCreacion"));
                    comentario.setIdAcademico(resultado.getInt("idAcademico"));
                    String nombre = resultado.getString("nombreUsuario") + " " + resultado.getString("apellidoPaterno") + " " + resultado.getString("apellidoMaterno");
                    comentario.setNombreAcademico(nombre);
                    comentarios.add(comentario);
                }
                respuesta.setComentarios(comentarios);
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
