/*
*Autor: Mongeote Tlachy Daniel
*Fecha de creación: 06/06/2023
*Fecha de modificación: 06/06/2023
*Descripción: Clase encargada de la comunicación con la BD, especificamente para manipular la información de las experiencias educativas
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.Actividad;
import javafxsspger.modelo.pojo.ExperienciaEducativa;
import javafxsspger.modelo.pojo.ExperienciaEducativaRespuesta;
import javafxsspger.modelo.pojo.LGAC;
import javafxsspger.utils.Constantes;

/**
 *
 * @author danie
 */
public class ExperienciaEducativaDAO {
    
    public static ExperienciaEducativaRespuesta recuperarExperienciaEducativa(int idMateria){
        ExperienciaEducativaRespuesta expEduRespuesta = new ExperienciaEducativaRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = " ... ";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idMateria);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<ExperienciaEducativa> ExperienciasEducativasConsulta = new ArrayList();
                while(resultado.next()){
                    ExperienciaEducativa expEdu = new ExperienciaEducativa();
                    expEdu.setIdMateria(resultado.getInt("idExperienciaEducativa"));
                    //expEdu.setNOMBREEXPERIENCIAEDUCATIVA
                    //expEdu.setNombreAcademico
                    expEdu.setNombrePeriodoEscolar(resultado.getString("nombrePeriodoEscolar"));
                    ExperienciasEducativasConsulta.add(expEdu);
                }
                respuesta.setActividades(ExperienciasEducativasConsulta);
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
    
    public static int guardarExperienciaEducativa(ExperienciaEducativa expEduNueva){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD!=null){
            try{
            String sentencia = "Insert into ExperienciaEducativa(nrc, fechaComienzo, fechaFin, nombrePeriodoEscolar) " + 
                               "VALUES (?, ?, ?, ?);";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
            prepararSentencia.setString(1, expEduNueva.getNRC());
            prepararSentencia.setString(2, expEduNueva.getFechaComienzo());
            prepararSentencia.setString(3, expEduNueva.getFechaFin());
            prepararSentencia.setString(4, expEduNueva.getNombrePeriodoEscolar());
            int filasAfectadas = prepararSentencia.executeUpdate();
            respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
            conexionBD.close();
            }catch(SQLException e){
                e.printStackTrace();
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }

    public static int eliminarExperienciaEducativa(int idExperienciaEducativa){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD!=null){
            try{
            String sentencia = "DELETE FROM ExperienciaEducativa WHERE idExperienciaEducativa = ?";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
            prepararSentencia.setInt(1, idExperienciaEducativa);
            int filasAfectadas = prepararSentencia.executeUpdate();
            respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
            conexionBD.close();
            }catch(SQLException e){
                e.printStackTrace();
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;   
    }

    public static int modificarExperienciaEducativa(ExperienciaEducativa modificarExperienciaEducativa){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD!=null){
            try{
            String sentencia = " UPDATE ExperienciaEducativa SET nrc = ?, fechaComienzo = ?, fechaFin = ?, nombrePeriodoEscolar = ? "+ //debe poder modificar tmb tipoEE, Academico y Coordinador
                    "WHERE idExperienciaEducativa = ?;";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
            prepararSentencia.setString(1, modificarExperienciaEducativa.getNRC());
            prepararSentencia.setString(2, modificarExperienciaEducativa.getFechaComienzo());
            prepararSentencia.setString(3, modificarExperienciaEducativa.getFechaFin());
            prepararSentencia.setString(4, modificarExperienciaEducativa.getNombrePeriodoEscolar());
            int filasAfectadas = prepararSentencia.executeUpdate();
            respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
            conexionBD.close();
            }catch(SQLException e){
                e.printStackTrace();
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
}
