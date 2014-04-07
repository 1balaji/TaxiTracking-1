package ConexionSQL;

import Beans.Peticion;
import Beans.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO 
{
    //Variables para la conexion con la base de datos
    Connection con;
    ResultSet rs;
    PreparedStatement pst;
    
    //Variables para retornar
    Usuario objUsuario;
    Usuario objUsuarios[];
    
    //Variable que contendra la consulta
    String consulta;
    
    public UsuarioDAO()
    {
        con = null;
        rs = null;
        pst = null;
        objUsuario = null;
        objUsuarios = null;
        consulta = "";
    }
    
    /*
    * Metodo para accesar al sistema. 
    * Recibe el usuario y contraseña
    * Retorna -1 en caso de error, 1 si es administrador y 0 si es usuario
    */
    public int getLogin(String user, String pwd)
    {
        int rol = -1;
        consulta = "SELECT rol FROM usuario WHERE nombre_usuario = ? AND password = ?";
        
        try
        {
            con = Conexion.getConexion();
            pst = con.prepareStatement(consulta);
            pst.setString(1, user);
            pst.setString(2, pwd);
            rs = pst.executeQuery();
            if(rs.next())
            {
                rol = rs.getInt("rol");
            }
            pst.close();
        }
        catch(SQLException e){ System.out.println("Error en el login D:\n" + e); }
        finally{ Conexion.closeConexion(); }
        return rol;
    }

    public Usuario[] getPeticiones() 
    {
        consulta = "SELECT COUNT(*) tipo FROM peticion";
        int numeroDePeticiones = 0;
        try
        {
            //Primero recuperamos el numero de peticiones que hay
            con = Conexion.getConexion();
            pst = con.prepareStatement(consulta);
            rs = pst.executeQuery();
            if(rs.next())
            {
                numeroDePeticiones = rs.getInt(1);
            }
            pst.close();
            
            //Creamos tantos usuarios como peticiones haya
            objUsuarios = new Usuario[numeroDePeticiones];
            
            //Instanciamos las clases
            for(int i = 0; i < numeroDePeticiones; i++)
                objUsuarios[i] = new Usuario();
            
            //Hacemos una segunda consulta para recuperar las peticiones
            consulta = "SELECT usuario.nombre_usuario, email, tipo, descripcion FROM peticion JOIN usuario ON usuario.nombre_usuario = peticion.nombre_usuario";
            pst = con.prepareStatement(consulta);
            rs = pst.executeQuery();
            
            //Llenamos los objetos con los resultados de la consulta
            for(int i = 0; rs.next(); i++)
            {
                objUsuarios[i].setNombreUsuario(rs.getString("nombre_usuario"));
                objUsuarios[i].setEmail(rs.getString("email"));
                objUsuarios[i].setPeticion(new Peticion(rs.getInt("tipo"), rs.getString("descripcion")));
            }
            pst.close();
        }
        catch(SQLException e){ System.out.println("Error al obtener las peticiones D:\n" + e); }
        finally{ Conexion.closeConexion(); }
        return objUsuarios;
    }
    
    public boolean bloquearUsuario(Usuario objUsuario) 
    {
        boolean b = false;
        consulta = "UPDATE usuario SET status = 0 WHERE nombre_usuario = ?";
        
        try
        {
            //Primero cambiaremos el status del usuario
            con = Conexion.getConexion();
            pst = con.prepareStatement(consulta);
            pst.setString(1, objUsuario.getNombreUsuario());
            pst.executeUpdate();
            pst.close();
            
            //Hacemos una segunda consulta para borrar la peticion del usuario
            consulta = "DELETE FROM peticion WHERE nombre_usuario = ?";
            pst = con.prepareStatement(consulta);
            pst.setString(1, objUsuario.getNombreUsuario());
            pst.execute();
            pst.close();
            
            b = true;
        }
        catch(SQLException e){ System.out.println("Error al bloquear al usuario D:\n" + e); }
        finally{ Conexion.closeConexion(); }
        return b;
    }
}
