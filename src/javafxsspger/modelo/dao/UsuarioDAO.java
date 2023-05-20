/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 14/05/2023
*Fecha de modificación: 19/05/2023
*Descripción: Clase encargada de la comunicación con la BD, especificamente para manipular la información de los usuarios
*/

package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.Usuario;
import javafxsspger.utils.Constantes;

public class UsuarioDAO {
    public static Usuario verificarSesion (String usuario, String contrasena){
        Usuario usuarioVerificado = new Usuario();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "Select usuario.idUsuario, usuario.nombreUsuario, usuario.idTipoUsuario, " +
                    "TipoUsuario.nombreTipoUsuario, " +
                    "usuario.usuario, usuario.contrasena " +
                    "From usuario " +
                    "INNER JOIN TipoUsuario ON TipoUsuario.idTipoUsuario = usuario.idTipoUsuario " +
                    "WHERE usuario.usuario = ? AND usuario.contrasena = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, usuario);
                prepararSentencia.setString(2, contrasena);
                ResultSet resultado = prepararSentencia.executeQuery();
                usuarioVerificado.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                if(resultado.next()){
                    usuarioVerificado.setIdUsuario(resultado.getInt("idUsuario"));
                    usuarioVerificado.setNombre(resultado.getString("nombreUsuario"));
                    usuarioVerificado.setIdTipoUsuario(resultado.getInt("idTipoUsuario"));
                    usuarioVerificado.setNombreTipoUsuario(resultado.getString("nombreTipoUsuario"));
                    usuarioVerificado.setUsuario(resultado.getString("usuario"));
                    usuarioVerificado.setContrasena(resultado.getString("contrasena"));
                }
                conexionBD.close();
            }catch(SQLException ex){
                usuarioVerificado.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                ex.printStackTrace();
            }
        }else{
            usuarioVerificado.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return usuarioVerificado;
    }
}
