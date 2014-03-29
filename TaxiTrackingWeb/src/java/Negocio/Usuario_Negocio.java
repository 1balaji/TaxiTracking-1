package Negocio;

import Beans.Usuario;
import ConexionSQL.Conexion;
import ConexionSQL.Metodos;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Usuario_Negocio extends HttpServlet 
{
    //Variables para la conexion con la base de datos
    private Statement sentencias = null;
    private Connection con;
    private ResultSet rs = null;
    private PreparedStatement pst = null;
    
    //Beans
    Usuario objUsuario;
    
    //Salida
    PrintWriter out;
    
    //Sesion
    HttpSession session = null;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        //Para que no se pueda llamar al servlet con la URL
        if(request.getSession(false) == null)
            response.sendRedirect("index.jsp");
        
        response.setContentType("text/html;charset=UTF-8");
        out = response.getWriter();
        
        //Variable para redireccionar a la pagina correspondiente
        int respuesta;
        
        //Nos dice que metodo hay que invocar
        int query = Integer.parseInt(request.getParameter("q"));
        
        switch(query)
        {
            case 1:
                respuesta = getLogin(request);
                switch(respuesta)
                {
                    case 0: 
                        response.sendRedirect("bienvenido.jsp");
                        break;
                    case 1:
                        response.sendRedirect("bienvenidoAdministrador.jsp");
                        break;
                    default:
                        response.sendRedirect("index.jsp?error=1");
                }
                break;
            case 2:
                objUsuario = new Usuario();
                objUsuario = buscar(request);
                session = request.getSession();
                session.setAttribute("objUsuario", objUsuario);
                response.sendRedirect("busqueda.jsp");
                break;
            case 3:
                //Botón de bloquear y desbloquear
                String Boton = request.getParameter("BT").toString();
                String Usuario = request.getParameter("usuario").toString();
                if (Boton.equals("Bloquear")) 
                {
                    boolean dd = bloqueaUsuario(Usuario);
                    
//                    out.println("<script>alert('El Usuario se ha bloqueado  correctamente')</script>");
//                    out.println("<meta http-equiv='refresh' content='0;url=bienvenidoAdministrador.jsp'");
                } 
                else 
                {
                    boolean ff = desbloqueaUsuario(Usuario);
//                   out.println("<script>alert('El Usuario se ha Desbloqueado  correctamente')</script>");
//                   out.println("<meta http-equiv='refresh' content='0;url=bienvenidoAdministrador.jsp'");
                }
                //response.sendRedirect("bienvenidoAdministrador.jsp");
                break;
            case 4:
                Usuario = request.getParameter("usuario").toString();
                Elimina_Usuario(Usuario);
                break;
            case 5:
                break;
            case 6:
                getLogin(request, response);
                break;
            default:
                response.sendRedirect("index.jsp");
        }
    }
    
    /*
     * Metodo para accesar al sistema. 
     * Crea la sesion correspondiente subiendo el rol y nombre del usuario
     * Retorna -1 en caso de error, 1 si es administrador y 0 si es usuario
     */
    private int getLogin(HttpServletRequest request)
    {
        int respuesta = -1;
        String user = request.getParameter("TBUsuario");
        String pwd = request.getParameter("TBContrasena");
        String consulta = "Select nombre_usuario, rol from usuario where nombre_usuario = ? && password = ?";
        
        try
        {
            con = Conexion.getConexion();
            pst = con.prepareStatement(consulta);
            pst.setString(1, user);
            pst.setString(2, pwd);
            rs = pst.executeQuery();
            if(rs.next())
            {
                String usuario = rs.getString("nombre_usuario");
                int rol = rs.getInt("rol");

                //Se crea la sesion y se suben los atributos
                session = request.getSession();
                session.setAttribute("nombre_usuario", usuario);
                session.setAttribute("rol", rol);

                //Se asigna el tipo de respuesta
                respuesta = (rol == 1)? 1: 0;
            }
            pst.close();
        }
        catch(SQLException e){ System.out.println("Error en el login D:\n" + e); }
        finally{ Conexion.closeConexion(); }
        return respuesta;
    }
    
    /*
     * Metodo para el inicio de sesion desde al app. Si es correcto se guarda en un List que contiene
     * un solo objeto respuesta, esto se hace para conseguir el formato para el JSON.
     * Se imprime la respuesta en el siguiente formato
     * [{"logstatus":"0"}] -> "logueo invalido"
     * [{"logstatus":"1"}] -> "logueo valido"
    */
    private void getLogin(HttpServletRequest request, HttpServletResponse response)
    {
        response.setContentType("application/json");
        Gson gson = new Gson();
        List<Respuesta> respuesta = new ArrayList<Respuesta>();

        String user = request.getParameter("TBUsuario");
        String pwd = request.getParameter("TBContrasena");

        Metodos m = new Metodos();

        //Si es correcto el login
        if (m.getLogin(user, pwd)) 
            respuesta.add(new Respuesta("1"));
        else
            respuesta.add(new Respuesta("0"));

        out.println(gson.toJson(respuesta));
        out.close();
    }
    
    private Usuario buscar(HttpServletRequest request)
    {
        Usuario buscado = new Usuario();
        String user = request.getParameter("TBBuscarUsuario");
        String consulta = "Select Nombre_usuario,Nombre,email,Apellido_Paterno,Apellido_Materno,status from usuario where Nombre_usuario = ? ";

        try
        {
            con = Conexion.getConexion();
            pst = con.prepareStatement(consulta);
            pst.setString(1, user);
            rs = pst.executeQuery();
            if (rs.next())
            {
                buscado.setNombreUsuario(rs.getString("nombre_usuario"));
                buscado.setNombre(rs.getString("Nombre"));
                buscado.setEmail(rs.getString("email"));
                buscado.setApellidoPaterno(rs.getString("Apellido_Paterno"));
                buscado.setApellidoMaterno(rs.getString("Apellido_Materno"));
                buscado.setStatus(rs.getInt("status"));
            }
            pst.close();            
        }
        catch (SQLException e) { System.out.println("Error en la busqueda D:\n" + e); }
        finally { Conexion.closeConexion(); }
        return buscado;
    }

    private boolean bloqueaUsuario(String usuario) 
    {
        try 
        {
            con = Conexion.getConexion();
            String consulta = "UPDATE  usuario SET status=0 where Nombre_usuario='" + usuario + "' ";
            sentencias = con.createStatement();
            int res = sentencias.executeUpdate(consulta);

            if (res == 1) 
            {   
                out.println("<script>alert('El Usuario se ha bloqueado  correctamente')</script>");
                out.println("<meta http-equiv='refresh' content='0;url=bienvenidoAdministrador.jsp'>");                
                return true;
            } 
            else
            {
                out.println("error");
            }
        } 
        catch (SQLException ex) { System.out.println("Error al bloquear usuario D:\n" +ex); }
        return false;
    }

    private boolean desbloqueaUsuario(String usuario) 
    {
        try 
        {
            String consulta = "UPDATE  usuario SET status=1 where Nombre_usuario= '" + usuario + "' ";
            con = Conexion.getConexion();
            sentencias = con.createStatement();
            int res = sentencias.executeUpdate(consulta);

            if (res == 1) 
            {
               out.println("<script>alert('El Usuario se ha desbloqueado  correctamente')</script>");
               out.println("<meta http-equiv='refresh' content='0;url=bienvenidoAdministrador.jsp'>");
                return true;
            } 
            else 
            {
                out.println("error");
            }
        } 
        catch (SQLException ex) { System.out.println("Error al desbloquear usuario D:\n" + ex); }
        return false;
    }

    private boolean Elimina_Usuario(String usuario) 
    {
        try 
        {
            String consulta = "DELETE FROM usuario WHERE Nombre_usuario= '" + usuario + "' ";
            con = Conexion.getConexion();
            sentencias = con.createStatement();
            int res = sentencias.executeUpdate(consulta);

            if (res == 1) 
            {
                out.println("<script>alert('El Usuario se ha eliminado correctamente')</script>");
                out.println("<meta http-equiv='refresh' content='0;url=index.jsp'>");
                return true;
            } 
            else
            {
                out.println("error");
            }
        } 
        catch (SQLException ex) { System.out.println("Error al eliminar usuario D:\n" + ex); }
        return false;
    }

    private boolean registra_Usuario() 
    {
        return false;
    }

    public boolean edita_Usuario() 
    {
        return false;
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Usuario_Negocio";
    }// </editor-fold>
}