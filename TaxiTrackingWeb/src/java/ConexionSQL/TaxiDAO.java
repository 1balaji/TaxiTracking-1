package ConexionSQL;

import Beans.Taxi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaxiDAO 
{
    //Variables para la conexion con la base de datos
    Connection con;
    ResultSet rs;
    PreparedStatement pst;
    
    //Variable que contendra la consulta
    String consulta;
    
    public TaxiDAO()
    {
        con = null;
        rs = null;
        pst = null;
        consulta = "";
    }
    
   /*
    * Metodo para agregar un nuevo taxi. 
    * Recibe un objeto taxi con los datos a registrar
    * Retorna true en caso de exito y false en caso de error
    */
    public boolean agregarTaxi(Taxi objTaxi) 
    {
        boolean b = false;
        consulta = "INSERT INTO taxista VALUES (null, ?, ?, ?, ?, ?, ?, ?)";
        
        try
        {
            con = Conexion.getConexion();
            pst = con.prepareStatement(consulta);
            pst.setString(1, objTaxi.getNombre());
            pst.setString(2, objTaxi.getApellidoPaterno());
            pst.setString(3, objTaxi.getApellidoMaterno());
            pst.setString(4, objTaxi.getRFC());
            pst.setInt(5, objTaxi.getMatricula());
            pst.setDate(6, objTaxi.getVigencia());
            pst.setInt(7, objTaxi.getFolio());
            pst.execute();
            pst.close();
            
            b = true;
        }
        catch(SQLException e){ System.out.println("Error al agregar al taxista D:\n" + e); }
        finally{ Conexion.closeConexion(); }
        return b;
    }

}
