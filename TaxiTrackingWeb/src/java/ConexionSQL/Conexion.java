package ConexionSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion
{
    private static Connection con = null;
    private static String BD = "taxitracking";
    private static String URL = "jdbc:mysql://localhost:3306/" + BD;
    private static String usuario = "root";
    private static String contrasena = "pass";

    public static Connection getConexion()
    {
        try
        {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            con = DriverManager.getConnection(URL, usuario, contrasena);
        }
        catch(SQLException e){ System.out.println("Error al abrir la conexion D:\n" + e); }
        return con;
    }
    
    public static void closeConexion()
    {
        try{ con.close(); }
        catch(SQLException e){ System.out.println("Error al abrir la conexion D:\n" + e); }
    }
}