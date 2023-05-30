/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 14/05/2023
*Fecha de modificación: 16/05/2023
*Descripción: Clase encargada de la comunicación con la BD, especificamente para manipular la información de los académicos
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.AcademicoRespuesta;
import javafxsspger.utils.Constantes;

public class AcademicoDAO {
    
    public static Academico obtenerDetallesAcademico (int idUsuario){
        Academico academicoRespuesta = new Academico();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT idAcademico, noPersonal, idCuerpoAcademico FROM sspger.academico " +
                    "WHERE idUsuario = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idUsuario);
                ResultSet resultado = prepararSentencia.executeQuery();
                
                if(resultado.next()){
                    academicoRespuesta.setIdAcademico(resultado.getInt("idAcademico"));
                    academicoRespuesta.setNoPersonal(resultado.getString("noPersonal"));
                    academicoRespuesta.setIdCuerpoAcademico(resultado.getInt("idCuerpoAcademico"));
                    if(academicoRespuesta.getIdCuerpoAcademico() != 0){
                        academicoRespuesta.setEsResponsableCA(true);
                    }else{
                        academicoRespuesta.setEsResponsableCA(false);
                    }
                    
                
                    String consultaEsDirector = "SELECT Anteproyecto.idAnteproyecto, encargadosanteproyecto.idAcademico, anteproyecto.idEstado " +
                        "FROM encargadosanteproyecto INNER JOIN anteproyecto ON anteproyecto.idAnteproyecto = encargadosanteproyecto.idAnteproyecto " +
                        "Where anteproyecto.idEstado = ? and encargadosanteproyecto.idAcademico = ?";
                    PreparedStatement prepararSentenciaEsDirector = conexionBD.prepareStatement(consultaEsDirector);
                    prepararSentenciaEsDirector.setInt(1, Constantes.APROBADO);
                    prepararSentenciaEsDirector.setInt(2, academicoRespuesta.getIdAcademico());
                    ResultSet resultadoEsDirector = prepararSentenciaEsDirector.executeQuery();
                    academicoRespuesta.setEsDirector(resultadoEsDirector.next());
                    
                    String consultaEsProfesor = "SELECT idAcademico, idExperienciaEducativa from experienciaeducativa Where idAcademico = ?";
                    PreparedStatement prepararSentenciaEsProfesor = conexionBD.prepareStatement(consultaEsProfesor);
                    prepararSentenciaEsProfesor.setInt(1, academicoRespuesta.getIdAcademico());
                    ResultSet resultadoEsProfesor = prepararSentenciaEsProfesor.executeQuery();
                    academicoRespuesta.setEsProfesor(resultadoEsProfesor.next());
                    
                    String consultaEsCoordinador = "SELECT idCoordinadorAcademia, idExperienciaEducativa from experienciaeducativa Where idCoordinadorAcademia = ?";
                    PreparedStatement prepararSentenciaEsCoordinador = conexionBD.prepareStatement(consultaEsCoordinador);
                    prepararSentenciaEsCoordinador.setInt(1, academicoRespuesta.getIdAcademico());
                    ResultSet resultadoEsCoordinador = prepararSentenciaEsCoordinador.executeQuery();
                    academicoRespuesta.setEsCoordinador(resultadoEsCoordinador.next());
                    
                }
                
                academicoRespuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                conexionBD.close();
                
            }catch(SQLException e){
                academicoRespuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                e.printStackTrace();
            }
        }else{
            academicoRespuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return academicoRespuesta;
    }
    
    public static AcademicoRespuesta obtenerPosiblesCodirectores(Academico academicoCreador){
        AcademicoRespuesta academicosRespuesta = new AcademicoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT Usuario.idUsuario, Academico.idAcademico, Usuario.nombreUsuario, " +
                    "Usuario.apellidoPaterno, Usuario.apellidoMaterno " +
                    "FROM Academico " +
                    "INNER JOIN Usuario ON Usuario.idUsuario = Academico.idUsuario " +
                    "WHERE Academico.idAcademico != ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, academicoCreador.getIdAcademico());
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList <Academico> academicos = new ArrayList();
                while(resultado.next()){
                    Academico academico = new Academico();
                    academico.setIdAcademico(resultado.getInt("idAcademico"));
                    academico.setIdUsuario(resultado.getInt("idUsuario"));
                    academico.setNombre(resultado.getString("nombreUsuario"));
                    academico.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    academico.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    academicos.add(academico);
                }
                academicosRespuesta.setAcademicos(academicos);
                academicosRespuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                conexionBD.close();
            }catch(SQLException e){
                academicosRespuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                e.printStackTrace();
            }
        }else{
            academicosRespuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return academicosRespuesta;
    }
}
