package ConexionSQL;

import Beans.Evaluacion;
import Beans.Taxi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BitacoraDAO 
{
    //Variables para la conexion con la base de datos
    private Connection con;
    private ResultSet rs;
    private PreparedStatement pst;
    
    //Variable que contendra la consulta
    private String consulta;
    
    private Evaluacion objComentario;
    private Evaluacion[] objEvaluaciones;
    
    public BitacoraDAO()
    {
        con = null;
        rs = null;
        pst = null;
        consulta = "";
    }
    
    /*
    * Metodo para buscar las evaluaciones asociadas a un taxista. 
    * Recibe un objeto taxi con el id del taxi del que se buscaran los comentarios.
    * Retorna un arreglo de 5 evaluaciones.
    */
    public Evaluacion[] getEvaluacionesTaxi(Taxi objTaxi)
    {
        //Iniciamos el arreglo
        objEvaluaciones = new Evaluacion[5];
        for(int i = 0; i < 5; i++)
            objEvaluaciones[i] = new Evaluacion();
            
        consulta = "SELECT comentario, SUM(calificacion) AS totalEstrellas, COUNT(*) AS totalRegistros, idBitacora FROM bitacora WHERE idTaxista = ? ORDER BY idBitacora DESC LIMIT 5";

        try
        {
            con = Conexion.getConexion();
            pst = con.prepareStatement(consulta);
            pst.setString(1, objTaxi.getIdTaxista());
            rs = pst.executeQuery();
            
            if(rs.next())
            {
                objEvaluaciones[0].setComentario(rs.getString("comentario"));
                objEvaluaciones[0].setCalificacion(rs.getInt("totalEstrellas"));
                objEvaluaciones[1].setCalificacion(rs.getInt("totalRegistros"));
            }
            
            //Llenamos resto del arreglo
            for(int i = 1; rs.next(); i++)
                objEvaluaciones[i].setComentario(rs.getString("comentario"));
            
            pst.close();
        }
        catch (SQLException e) { System.out.println("Error al buscar los comentarios D:\n" + e); }
        finally { Conexion.closeConexion(); }
        
        return objEvaluaciones;
    }
}
