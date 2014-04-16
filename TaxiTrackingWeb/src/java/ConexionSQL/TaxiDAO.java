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
        consulta = "INSERT INTO taxista VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try
        {
            con = Conexion.getConexion();
            pst = con.prepareStatement(consulta);
            pst.setString(1, objTaxi.getIdTaxista());
            pst.setString(2, objTaxi.getNombre());
            pst.setString(3, objTaxi.getApellidoPaterno());
            pst.setString(4, objTaxi.getApellidoMaterno());
            pst.setString(5, objTaxi.getCURP());
            pst.setString(6, objTaxi.getMatricula());
            pst.setInt(7, objTaxi.getFolio());
            pst.setInt(8, objTaxi.getNumeroLicencia());            
            pst.setString(9, objTaxi.getVigencia());
            pst.setString(10, objTaxi.getFechaHoraExpedicion());
            pst.setInt(11, objTaxi.getStatus());
            
            pst.execute();
            pst.close();
            
            b = true;
        }
        catch(SQLException e){ System.out.println("Error al agregar al taxista D:\n" + e); }
        finally{ Conexion.closeConexion(); }
        return b;
    }
    
    /*
    * Metodo para buscar un taxista. 
    * Recibe un objeto taxi con el id del taxi, curp, folio o numero de licencia que se va a buscar
    * Retorna un objeto taxi lleno con los datos correspondientes o null en caso de no haber coincidencias
    */
    public Taxi buscarTaxi(Taxi objTaxi)
    {
        consulta = "SELECT * FROM taxista where idTaxista = ? OR curp = ? OR folio = ? OR numeroLicencia = ?";

        try
        {
            con = Conexion.getConexion();
            pst = con.prepareStatement(consulta);
            pst.setString(1, objTaxi.getIdTaxista());
            pst.setString(2, objTaxi.getCURP());
            pst.setInt(3, objTaxi.getFolio());
            pst.setInt(4, objTaxi.getNumeroLicencia());
            rs = pst.executeQuery();
            
            //Reiniciamos el objeto
            objTaxi = null;
                    
            if (rs.next())
            {
                objTaxi = new Taxi();
                objTaxi.setIdTaxista(rs.getString("idTaxista"));
                objTaxi.setNombre(rs.getString("nombre"));
                objTaxi.setApellidoPaterno(rs.getString("apellido_paterno"));
                objTaxi.setApellidoMaterno(rs.getString("apellido_materno"));
                objTaxi.setCURP(rs.getString("curp"));
                objTaxi.setMatricula(rs.getString("matricula"));
                objTaxi.setFolio(rs.getInt("folio"));
                objTaxi.setNumeroLicencia(rs.getInt("numeroLicencia"));
                objTaxi.setVigencia(rs.getString("vigencia"));
                objTaxi.setFechaHoraExpedicion(rs.getString("fechaHoraExpedicion"));
                objTaxi.setStatus(rs.getInt("status"));
            }
            pst.close();            
        }
        catch (SQLException e) { System.out.println("Error al buscar un taxista D:\n" + e); }
        finally { Conexion.closeConexion(); }
        
        return objTaxi;
    }
}
