/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 24/05/2023
*Fecha de modificación: 24/05/2023
*Descripción: Clase encargada de la comunicación con la BD, especificamente para manipular la información de los comentarios
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.Comentario;
import javafxsspger.utils.Constantes;


public class ComentarioDAO {
    
    public static int guardarComentario(Comentario comentarioNuevo){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD!=null){
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
}
