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
        
        consulta = "SELECT SUM(calificacion) AS totalEstrellas, COUNT(*) AS totalRegistros FROM bitacora WHERE idTaxista = ?";

        try
        {
            con = Conexion.getConexion();
            pst = con.prepareStatement(consulta);
            pst.setString(1, objTaxi.getIdTaxista());
            rs = pst.executeQuery();
            
            //Primero recuperamos la calificacion
            if(rs.next())
            {
                objEvaluaciones[0].setCalificacion(rs.getInt("totalEstrellas"));
                objEvaluaciones[1].setCalificacion(rs.getInt("totalRegistros"));
            }
            
            //Despues recuperamos los comentarios
            consulta = "SELECT comentario, idBitacora FROM bitacora WHERE idTaxista = ? ORDER BY idBitacora DESC LIMIT 5";
            pst = con.prepareStatement(consulta);
            pst.setString(1, objTaxi.getIdTaxista());
            rs = pst.executeQuery();
            
            //Llenamos resto del arreglo
            for(int i = 0; rs.next(); i++)
                objEvaluaciones[i].setComentario(rs.getString("comentario"));
            
            pst.close();
        }
        catch (SQLException e) { System.out.println("Error al buscar los comentarios D:\n" + e); }
        finally { Conexion.closeConexion(); }
        
        return objEvaluaciones;
    }
    
    /*
    * Metodo para registrar una evaluacion a un taxista. 
    * Recibe la posicion inicial y final del viaje, un objeto evaluacion, el nombre del usuario y el id del taxista
    * Retorna true en caso de exito y false en caso de error.
    */
    public boolean enviarEvaluacion(String posicionInicial, String posicionFinal, Evaluacion objEvaluacion, String nombreUsuario, String idTaxista)
    {
        boolean b = false;
        
        consulta = "INSERT INTO bitacora VALUES(null, ?, ?, ?, ?, ?, ?)";

        try
        {
            con = Conexion.getConexion();
            pst = con.prepareStatement(consulta);
            pst.setString(1, posicionInicial);
            pst.setString(2, posicionFinal);
            pst.setString(3, objEvaluacion.getComentario());
            pst.setFloat(4, objEvaluacion.getCalificacion());
            pst.setString(5, nombreUsuario);
            pst.setString(6, idTaxista);
            pst.execute();
            
            b = true;
            
            pst.close();
        }
        catch (SQLException e) { System.out.println("Error al agregar la evaluacion D:\n" + e); }
        finally { Conexion.closeConexion(); }
        
        return b;
    }
}
