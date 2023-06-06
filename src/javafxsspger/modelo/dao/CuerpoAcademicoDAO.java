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
import javafxsspger.modelo.pojo.CuerpoAcademico;
import javafxsspger.modelo.pojo.CuerpoAcademicoRespuesta;
import javafxsspger.utils.Constantes;

public class CuerpoAcademicoDAO {
    
    public static CuerpoAcademicoRespuesta recuperarCuerposAcademicos (){
        CuerpoAcademicoRespuesta cuerposAcademicosRespuesta = new CuerpoAcademicoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT CuerpoAcademico.idCuerpoAcademico, CuerpoAcademico.nombre, CuerpoAcademico.claveCuerpoAcademico, CuerpoAcademico.descripcion, "
                        + "CuerpoAcademico.areaConocimiento, " +
                           "CuerpoAcademico.idConsolidacion, Consolidacion.nivelConsolidacion " +
                           "from CuerpoAcademico "
                        + "INNER JOIN Consolidacion ON CuerpoAcademico.idConsolidacion = Consolidacion.idConsolidacion ";
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
}
