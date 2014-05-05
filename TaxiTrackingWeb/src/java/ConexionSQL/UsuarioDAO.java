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
    private Connection con;
    private ResultSet rs;
    private PreparedStatement pst;
    
    //Variable para retornar
    private Usuario objUsuarios[];
    
    //Variable que contendra la consulta
    private String consulta;
    
    public UsuarioDAO()
    {
        con = null;
        rs = null;
        pst = null;
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

    /*
    * Metodo para recuperar las peticiones de los usuarios. 
    * No recibe nada
    * Retorna un arreglo de usuarios
    */
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
    
    /*
    * Metodo para cambiar el status de un usuario. 
    * Recibe un objeto usuario con el nuevo status
    * Retorna true en caso de exito y false en caso de error
    */
    public boolean cambiarStatus(Usuario objUsuario) 
    {
        boolean b = false;
        consulta = "UPDATE usuario SET status = ? WHERE nombre_usuario = ? AND rol != 1";
        
        try
        {
            con = Conexion.getConexion();
            pst = con.prepareStatement(consulta);
            pst.setInt(1, objUsuario.getStatus());
            pst.setString(2, objUsuario.getNombreUsuario());
            pst.executeUpdate();
            pst.close();
            
            b = true;
        }
        catch(SQLException e){ System.out.println("Error al cambiar el status del usuario D:\n" + e); }
        finally{ Conexion.closeConexion(); }
        return b;
    }
    
    /*
    * Metodo para eliminar la peticion de un usuario. 
    * Recibe un objeto usuario con el nombre del usuario al que se le eliminara la peticion
    * Retorna true en caso de exito y false en caso de error
    */
    public boolean eliminarPeticion(Usuario objUsuario) 
    {
        boolean b = false;
        consulta = "DELETE FROM peticion WHERE nombre_usuario = ?";
        
        try
        {
            con = Conexion.getConexion();
            pst = con.prepareStatement(consulta);
            pst.setString(1, objUsuario.getNombreUsuario());
            pst.execute();
            pst.close();
            
            b = true;
        }
        catch(SQLException e){ System.out.println("Error al eliminar la peticion D:\n" + e); }
        finally{ Conexion.closeConexion(); }
        return b;
    }
    
    /*
    * Metodo para eliminar un usuario. 
    * Recibe un objeto usuario con el nombre del usuario que se va a eliminar
    * Retorna true en caso de exito y false en caso de error
    */
    public boolean eliminarUsuario(Usuario objUsuario) 
    {
        boolean b = false;
        consulta = "DELETE FROM usuario WHERE nombre_usuario = ? AND rol != 1";
        
        try
        {
            con = Conexion.getConexion();
            pst = con.prepareStatement(consulta);
            pst.setString(1, objUsuario.getNombreUsuario());
            pst.execute();
            pst.close();
            
            b = true;
        }
        catch(SQLException e){ System.out.println("Error al eliminar al usuario D:\n" + e); }
        finally{ Conexion.closeConexion(); }
        return b;
    }
    
    /*
    * Metodo para buscar un usuario. 
    * Recibe un objeto usuario con el nombre del usuario que se va a buscar
    * Retorna un objeto usuario lleno con los datos correspondientes o vacio en caso de no haber coincidencias
    */
    public Usuario buscarUsuario(Usuario objUsuario) 
    {        
        consulta = "SELECT nombre_usuario,nombre,email,apellido_paterno,apellido_materno,status FROM usuario WHERE nombre_usuario = ?";

        try
        {
            con = Conexion.getConexion();
            pst = con.prepareStatement(consulta);
            pst.setString(1, objUsuario.getNombreUsuario());
            rs = pst.executeQuery();
            
            //Reiniciamos el objeto
            objUsuario = new Usuario();
                    
            if (rs.next())
            {
                objUsuario.setNombreUsuario(rs.getString("nombre_usuario"));
                objUsuario.setNombre(rs.getString("nombre"));
                objUsuario.setEmail(rs.getString("email"));
                objUsuario.setApellidoPaterno(rs.getString("apellido_paterno"));
                objUsuario.setApellidoMaterno(rs.getString("apellido_materno"));
                objUsuario.setStatus(rs.getInt("status"));
            }
            pst.close();            
        }
        catch (SQLException e) { System.out.println("Error al buscar un usuario D:\n" + e); }
        finally { Conexion.closeConexion(); }
        
        return objUsuario;
    }
    
    /*
    * Metodo para editar el perfil un usuario. 
    * Recibe un objeto usuario con los datos a actualizar
    * Retorna true en caso de exito y false en caso de error
    */
    public boolean editarPerfil(Usuario objUsuario) 
    {
        boolean b = false;
        consulta = "UPDATE usuario SET nombre = ?, apellido_paterno = ?, apellido_materno = ? WHERE nombre_usuario = ?";

        try
        {
            con = Conexion.getConexion();
            pst = con.prepareStatement(consulta);
            pst.setString(1, objUsuario.getNombre());
            pst.setString(2, objUsuario.getApellidoPaterno());
            pst.setString(3, objUsuario.getApellidoMaterno());
            pst.setString(4, objUsuario.getNombreUsuario());
            pst.executeUpdate();            
            pst.close();
            
            b = true;
        }
        catch (SQLException e) { System.out.println("Error al actualizar el perfil del usuario D:\n" + e); }
        finally { Conexion.closeConexion(); }
        
        return b;
    }
    
    /*
    * Metodo para editar el email un usuario. 
    * Recibe un objeto usuario con el nuevo email y el nombre del usuario
    * Retorna true en caso de exito y false en caso de error
    */
    public boolean editarEmail(Usuario objUsuario) 
    {
        boolean b = false;
        consulta = "UPDATE usuario SET email = ? WHERE nombre_usuario = ?";

        try
        {
            con = Conexion.getConexion();
            pst = con.prepareStatement(consulta);
            pst.setString(1, objUsuario.getEmail());
            pst.setString(2, objUsuario.getNombreUsuario());
            pst.executeUpdate();            
            pst.close();
            
            b = true;
        }
        catch (SQLException e) { System.out.println("Error al actualizar el email del usuario D:\n" + e); }
        finally { Conexion.closeConexion(); }
        
        return b;
    }
    
    /*
    * Metodo para editar la contraseña de un usuario. 
    * Recibe un objeto usuario con el nombre del usuario, la antigua contraseña y un string con la nueva contraseña
    * Retorna true en caso de exito y false en caso de error
    */
    public boolean editarContrasena(Usuario objUsuario, String nuevaContrasena) 
    {
        boolean b = false;
        
        //Para verificar que la contraseña es correcta "hacemos" un login
        if(getLogin(objUsuario.getNombreUsuario(), objUsuario.getContrasena()) != -1)
        {
            consulta = "UPDATE usuario SET password = ? WHERE nombre_usuario = ?";

            try
            {
                con = Conexion.getConexion();
                pst = con.prepareStatement(consulta);
                pst.setString(1, nuevaContrasena);
                pst.setString(2, objUsuario.getNombreUsuario());
                pst.executeUpdate();            
                pst.close();

                b = true;
            }
            catch (SQLException e) { System.out.println("Error al actualizar la contraseña del usuario D:\n" + e); }
            finally { Conexion.closeConexion(); }
        }
        
        return b;
    }
    
    /*
    * Metodo para recuperar la peticion de un usuario. 
    * Recibe un objeto usuario que contiene el nombre del usuario
    * Retorna un objeto de usuario con la peticion correspondiente o la peticion null
    */
    public Usuario getPeticionUsuario(Usuario objUsuario) 
    {
        consulta = "SELECT tipo, descripcion FROM peticion WHERE nombre_usuario = ?";
        
        try
        {
            con = Conexion.getConexion();
            pst = con.prepareStatement(consulta);
            pst.setString(1, objUsuario.getNombreUsuario());
            rs = pst.executeQuery();
            if(rs.next())
                objUsuario.setPeticion(new Peticion(rs.getInt("tipo"), rs.getString("descripcion")));
            
            pst.close();            
        }
        catch(SQLException e){ System.out.println("Error al obtener la peticion del usuario D:\n" + e); }
        finally{ Conexion.closeConexion(); }
        return objUsuario;
    }
    
    /*
    * Metodo para agregar una nueva peticion de un usuario. 
    * Recibe un objeto usuario con el nombre del usuario, el tipo y descripcion de la peticion
    * Retorna true en caso de exito y false en caso de error
    */
    public boolean agregarPeticion(Usuario objUsuario) 
    {
        boolean b = false;
        consulta = "INSERT INTO peticion VALUES (null, ?, ?, ?)";
        
        try
        {
            con = Conexion.getConexion();
            pst = con.prepareStatement(consulta);
            pst.setInt(1, objUsuario.getPeticion().getTipo());
            pst.setString(2, objUsuario.getPeticion().getComentario());
            pst.setString(3, objUsuario.getNombreUsuario());
            pst.execute();
            pst.close();
            
            b = true;
        }
        catch(SQLException e){ System.out.println("Error al agregar la peticion D:\n" + e); }
        finally{ Conexion.closeConexion(); }
        return b;
    }
    
    /*
    * Metodo para validar que exista un mail. 
    * Recibe un objeto usuario con el email a buscar
    * Retorna true en caso de que exista y false en caso de que no
    */
    public boolean existeEmail(String email) 
    {
        boolean b = false;
        consulta = "SELECT email FROM usuario WHERE email = ?";

        try
        {
            con = Conexion.getConexion();
            pst = con.prepareStatement(consulta);
            pst.setString(1, email);
            rs = pst.executeQuery();
            
            if(rs.next())
                b = true;
            
            pst.close();
        }
        catch (SQLException e) { System.out.println("Error al validar email D:\n" + e); }
        finally { Conexion.closeConexion(); }
        
        return b;
    }
    
    /*
    * Metodo para asignar la contraseña de un usuario. 
    * Recibe la contraseña y el email del usuario al que se le va a asignar
    * Retorna true en caso de exito y false en caso de error
    */
    public boolean setNuevaContrasena(String email, String nuevaContrasena) 
    {
        boolean b = false;
        
        consulta = "UPDATE usuario SET password = ? WHERE email = ?";

        try 
        {
            con = Conexion.getConexion();
            pst = con.prepareStatement(consulta);
            pst.setString(1, nuevaContrasena);
            pst.setString(2, email);
            pst.executeUpdate();
            pst.close();

            b = true;
        } 
        catch (SQLException e) { System.out.println("Error al asignar la nueva contraseña del usuario D:\n" + e); }
        finally { Conexion.closeConexion(); }
        
        return b;
    }
    
    /*
    * Metodo para agregar un usuario normal.
    * Recibe un objeto usuario con los datos que se van a registrar.
    * Retorna true en caso de exito y false en caso de error
    */
    public boolean agregarUsuario(Usuario objUsuario) 
    {
        boolean b = false;
        consulta = "INSERT INTO usuario VALUES (?, ?, ?, ?, ?, ?, 0, ?)";   //0 porque el tipo de usuario es normal
        
        try
        {
            con = Conexion.getConexion();
            pst = con.prepareStatement(consulta);
            pst.setString(1, objUsuario.getNombreUsuario());
            pst.setString(2, objUsuario.getContrasena());
            pst.setString(3, objUsuario.getNombre());
            pst.setString(4, objUsuario.getApellidoPaterno());
            pst.setString(5, objUsuario.getApellidoMaterno());
            pst.setString(6, objUsuario.getEmail());
            pst.setInt(7, objUsuario.getStatus());
            pst.execute();
            pst.close();
            
            b = true;
        }
        catch(SQLException e){ System.out.println("Error al agregar al usuario D:\n" + e); }
        finally{ Conexion.closeConexion(); }
        return b;
    }
}
