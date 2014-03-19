package ConexionSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Metodos 
{
    //Variables para la conexion con la base de datos
    Connection con;
    ResultSet rs = null;
    PreparedStatement pst = null;
    
    /*
     * Metodo para acceder al sistema desde la aplicacion movil.
     * Retorna true si el login es correcto y false si es incorrecto.
     */
    public boolean getLogin(String user, String pwd)
    {
        boolean b = false;
        
        //Sacamos un count porque solo un usuario cumpliria con los datos si es correcto
        String consulta = "SELECT COUNT(*) FROM usuario WHERE nombre_usuario = ? && password = ?";
        try
        {
            con = Conexion.getConexion();
            pst = con.prepareStatement(consulta);
            pst.setString(1, user);
            pst.setString(2, pwd);
            System.out.print(consulta);
            rs = pst.executeQuery();
            rs.next();
            
            //Si hubo una coincidencia
            if (rs.getInt(1) == 1)
                b = true;
            else
                b = false;
            pst.close();
        }
        catch(SQLException e){ System.out.println("Error en el login D:\n" + e); }
        finally
        {
            Conexion.closeConexion(); 
        }
        return b;
    }
}
