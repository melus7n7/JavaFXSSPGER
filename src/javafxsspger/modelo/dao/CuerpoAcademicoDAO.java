/*
*Autor: Martínez Aguilar Sulem
*Fecha de creación: 20/05/2023
*Fecha de modificación: 20/05/2023
*Descripción: Clase encargada de la comunicación con la BD, especificamente para manipular la información de los cuerpos académicos
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsspger.modelo.ConexionBD;
import static javafxsspger.modelo.dao.LGACDAO.actualizarLGAC;
import javafxsspger.modelo.pojo.CuerpoAcademico;
import javafxsspger.modelo.pojo.CuerpoAcademicoRespuesta;
import javafxsspger.modelo.pojo.LGAC;
import javafxsspger.utils.Constantes;

public class CuerpoAcademicoDAO {
    
    public static CuerpoAcademicoRespuesta recuperarCuerposAcademicos (){
        CuerpoAcademicoRespuesta cuerposAcademicosRespuesta = new CuerpoAcademicoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT CuerpoAcademico.idCuerpoAcademico, CuerpoAcademico.nombre, CuerpoAcademico.claveCuerpoAcademico, CuerpoAcademico.descripcion,  " +
                "CuerpoAcademico.areaConocimiento,  Academico.idAcademico, " +
                "CuerpoAcademico.idConsolidacion, Consolidacion.nivelConsolidacion    " +
                "from CuerpoAcademico   " +
                "INNER JOIN Consolidacion ON CuerpoAcademico.idConsolidacion = Consolidacion.idConsolidacion " +
                "LEFT JOIN Academico ON CuerpoAcademico.idCuerpoAcademico=Academico.idCuerpoAcademico ORDER BY Cuerpoacademico.nombre ASC";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList <CuerpoAcademico> cuerposAcademicos = new ArrayList();
                while(resultado.next()){
                    CuerpoAcademico cuerpoAcademico = new CuerpoAcademico();
                    cuerpoAcademico.setIdCuerpoAcademico(resultado.getInt("idCuerpoAcademico"));
                    cuerpoAcademico.setNombre(resultado.getString("nombre"));
                    cuerpoAcademico.setClaveCuerpoAcademico(resultado.getString("claveCuerpoAcademico"));
                    cuerpoAcademico.setDescripcion(resultado.getString("descripcion"));
                    cuerpoAcademico.setAreaConocimiento(resultado.getString("areaConocimiento"));
                    cuerpoAcademico.setIdConsolidacion(resultado.getInt("idConsolidacion"));
                    cuerpoAcademico.setNivelConsolidacion(resultado.getString("nivelConsolidacion"));
                    cuerpoAcademico.setIdAcademico(resultado.getInt("idAcademico"));
                    cuerposAcademicos.add(cuerpoAcademico);
                }
                cuerposAcademicosRespuesta.setCuerposAcademicos(cuerposAcademicos);
                cuerposAcademicosRespuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                conexionBD.close();
            }catch(SQLException e){
                cuerposAcademicosRespuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                e.printStackTrace();
            }
        }else{
            cuerposAcademicosRespuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return cuerposAcademicosRespuesta;
    }
    
    public static int guardarCuerpoAcademico(CuerpoAcademico cuerpoAcademicoNuevo,ArrayList <LGAC> lgacCuerpoAcademico){
            int respuesta;
            Connection conexionBD = ConexionBD.abrirConexionBD();
            if(conexionBD!=null){
                try{
                String sentencia = "INSERT INTO cuerpoacademico (claveCuerpoAcademico, nombre, descripcion, areaConocimiento, idConsolidacion) "
                        + "VALUES (?,?,?,?,?);";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia,PreparedStatement.RETURN_GENERATED_KEYS);
                prepararSentencia.setString(1, cuerpoAcademicoNuevo.getClaveCuerpoAcademico());
                prepararSentencia.setString(2, cuerpoAcademicoNuevo.getNombre());
                prepararSentencia.setString(3, cuerpoAcademicoNuevo.getDescripcion());
                prepararSentencia.setString(4, cuerpoAcademicoNuevo.getAreaConocimiento());
                prepararSentencia.setInt(5, cuerpoAcademicoNuevo.getIdConsolidacion());
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
                ResultSet generatedKeys = prepararSentencia.getGeneratedKeys();
                if (generatedKeys.next() && respuesta==Constantes.OPERACION_EXITOSA) {
                    int idCuerpoAcademico = generatedKeys.getInt(1);
                    for(int i=0; i<=lgacCuerpoAcademico.size()-1;i++)
                    respuesta = LGACDAO.actualizarLGAC(idCuerpoAcademico, lgacCuerpoAcademico.get(i).getIdLGAC());
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
    
    public static int actualizarCuerpoAcademico(CuerpoAcademico cuerpoAcademicoActualizacion, ArrayList <LGAC> lgacCuerpoAcademico){
            int respuesta;
            Connection conexionBD = ConexionBD.abrirConexionBD();
            if(conexionBD!=null){
                try{
                String sentencia = "UPDATE cuerpoacademico SET claveCuerpoAcademico = ?, "
                        + "nombre = ?, descripcion = ?, areaConocimiento = ?, "
                        + "idConsolidacion = ? WHERE idCuerpoAcademico = ?;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, cuerpoAcademicoActualizacion.getClaveCuerpoAcademico());
                prepararSentencia.setString(2, cuerpoAcademicoActualizacion.getNombre());
                prepararSentencia.setString(3, cuerpoAcademicoActualizacion.getDescripcion());
                prepararSentencia.setString(4, cuerpoAcademicoActualizacion.getAreaConocimiento());
                prepararSentencia.setInt(5, cuerpoAcademicoActualizacion.getIdConsolidacion());
                prepararSentencia.setInt(6, cuerpoAcademicoActualizacion.getIdCuerpoAcademico());
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
                for(int i=0; i<=lgacCuerpoAcademico.size()-1;i++){
                    respuesta = LGACDAO.actualizarLGAC(cuerpoAcademicoActualizacion.getIdCuerpoAcademico(), lgacCuerpoAcademico.get(i).getIdLGAC());
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
    
}
