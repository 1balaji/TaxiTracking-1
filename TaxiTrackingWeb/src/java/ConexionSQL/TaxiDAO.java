package ConexionSQL;

import Beans.Comentario;
import Beans.Taxi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaxiDAO 
{
    //Variables para la conexion con la base de datos
    private Connection con;
    private ResultSet rs;
    private PreparedStatement pst;
    
    //Variable que contendra la consulta
    private String consulta;
    
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
        consulta = "INSERT INTO taxista VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
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
            pst.setLong(7, objTaxi.getFolio());
            pst.setLong(8, objTaxi.getNumeroLicencia());            
            pst.setString(9, objTaxi.getVigencia());
            pst.setString(10, objTaxi.getFechaExpedicion());
            pst.setString(11, objTaxi.getHoraExpedicion());
            pst.setInt(12, objTaxi.getStatus());
            pst.setInt(13, objTaxi.getTipo());
            
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
        consulta = "SELECT * FROM taxista WHERE idTaxista = ? OR curp = ? OR folio = ? OR numeroLicencia = ?";

        try
        {
            con = Conexion.getConexion();
            pst = con.prepareStatement(consulta);
            pst.setString(1, objTaxi.getIdTaxista());
            pst.setString(2, objTaxi.getCURP());
            pst.setLong(3, objTaxi.getFolio());
            pst.setLong(4, objTaxi.getNumeroLicencia());
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
                objTaxi.setFolio(rs.getLong("folio"));
                objTaxi.setNumeroLicencia(rs.getLong("numeroLicencia"));
                objTaxi.setVigencia(rs.getString("vigencia"));
                objTaxi.setFechaExpedicion(rs.getString("fechaExpedicion"));
                objTaxi.setHoraExpedicion(rs.getString("horaExpedicion"));
                objTaxi.setStatus(rs.getInt("status"));
            }
            pst.close();            
        }
        catch (SQLException e) { System.out.println("Error al buscar un taxista D:\n" + e); }
        finally { Conexion.closeConexion(); }
        
        return objTaxi;
    }
    
    /*
    * Metodo para cambiar el status de un taxi. 
    * Recibe un objeto taxi con el nuevo status
    * Retorna true en caso de exito y false en caso de error
    */
    public boolean cambiarStatus(Taxi objUsuario) 
    {
        boolean b = false;
        consulta = "UPDATE taxista SET status = ? WHERE idTaxista = ?";
        
        try
        {
            con = Conexion.getConexion();
            pst = con.prepareStatement(consulta);
            pst.setInt(1, objUsuario.getStatus());
            pst.setString(2, objUsuario.getIdTaxista());
            pst.executeUpdate();
            pst.close();
            
            b = true;
        }
        catch(SQLException e){ System.out.println("Error al cambiar el status del taxista D:\n" + e); }
        finally{ Conexion.closeConexion(); }
        return b;
    }
    
    /*
    * Metodo para eliminar un taxista. 
    * Recibe un objeto taxi con el id del taxista que se va a eliminar
    * Retorna true en caso de exito y false en caso de error
    */
    public boolean eliminarTaxi(Taxi objTaxi) 
    {
        boolean b = false;
        consulta = "DELETE FROM taxista WHERE idTaxista = ?";
        
        try
        {
            con = Conexion.getConexion();
            pst = con.prepareStatement(consulta);
            pst.setString(1, objTaxi.getIdTaxista());
            pst.execute();
            pst.close();
            
            b = true;
        }
        catch(SQLException e){ System.out.println("Error al eliminar al taxista D:\n" + e); }
        finally{ Conexion.closeConexion(); }
        return b;
    }
}
