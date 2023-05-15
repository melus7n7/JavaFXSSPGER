/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 14/05/2023
*Fecha de modificación: 14/05/2023
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
                //TO-DO Ordenar por Director y solo traer los publicados
                String consulta = "SELECT anteproyecto.titulo, usuario.nombre, anteproyecto.fechaAprobacion, " +
                        "anteproyecto.idAnteproyecto, encargadosanteproyecto.idAcademico " +
                        "FROM sspger.anteproyecto " +
                        "INNER JOIN encargadosanteproyecto ON anteproyecto.idAnteproyecto = encargadosanteproyecto.idAnteproyecto " +
                        "INNER JOIN academico ON encargadosanteproyecto.idAcademico = encargadosanteproyecto.idAcademico " +
                        "INNER JOIN usuario ON academico.idUsuario = usuario.idUsuario " +
                        "WHERE encargadosanteproyecto.esDirector = '1' AND encargadosanteproyecto.idAcademico = academico.idAcademico";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList <Anteproyecto> anteproyectosConsulta = new ArrayList();
                while(resultado.next()){
                    Anteproyecto anteproyecto = new Anteproyecto();
                    anteproyecto.setTitulo(resultado.getString("titulo"));
                    anteproyecto.setNombreDirector(resultado.getString("nombre"));
                    anteproyecto.setFechaPublicacion(resultado.getString("fechaAprobacion"));
                    anteproyecto.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                    anteproyectosConsulta.add(anteproyecto);
                }
                respuesta.setAnteproyectos(anteproyectosConsulta);
                respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                conexionBD.close();
            }catch(SQLException e){
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
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
                String consulta = "SELECT anteproyecto.titulo, usuario.nombre, anteproyecto.fechaAprobacion, " +
                        "anteproyecto.idAnteproyecto, encargadosanteproyecto.idAcademico, anteproyecto.noEstudiantesMaximos, anteproyecto.descripcion " +
                        "FROM sspger.anteproyecto " +
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
                    anteproyectoRespuesta.setNombreDirector(resultado.getString("nombre"));
                    anteproyectoRespuesta.setFechaPublicacion(resultado.getString("fechaAprobacion"));
                    anteproyectoRespuesta.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                    anteproyectoRespuesta.setNoEstudiantesMaximo(resultado.getInt("noEstudiantesMaximos"));
                }
                
                ArrayList <Academico> codirectores = new ArrayList();
                String consultaCodirectores = "SELECT academico.idAcademico, academico.noPersonal, usuario.nombre, encargadosanteproyecto.esDirector " +
                    "FROM sspger.academico " +
                    "INNER JOIN encargadosanteproyecto ON encargadosanteproyecto.idAcademico = academico.idAcademico " +
                    "INNER JOIN usuario ON usuario.idUsuario = academico.idUsuario " +
                    "WHERE encargadosanteproyecto.esDirector = 0 AND encargadosanteproyecto.idAcademico = academico.idAcademico " +
                    " AND encargadosanteproyecto.idAnteproyecto = ?";
                PreparedStatement prepararSentenciaCodirectores = conexionBD.prepareStatement(consultaCodirectores);
                prepararSentenciaCodirectores.setInt(1, idAnteproyecto);
                ResultSet resultadoCodirectores = prepararSentenciaCodirectores.executeQuery();
                anteproyectoRespuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                while(resultadoCodirectores.next()){
                    Academico codirector = new Academico();
                    codirector.setIdAcademico(resultadoCodirectores.getInt("idAcademico"));
                    codirector.setNombre(resultadoCodirectores.getString("nombre"));
                    codirectores.add(codirector);
                }
                anteproyectoRespuesta.setCodirectores(codirectores);
                conexionBD.close();
            }catch(SQLException ex){
                anteproyectoRespuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            anteproyectoRespuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return anteproyectoRespuesta;
    }
}
