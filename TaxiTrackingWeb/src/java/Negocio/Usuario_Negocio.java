package Negocio;

import Beans.Usuario;
import ConexionSQL.Conexion;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        session = request.getSession(false);
        
        //Para que no se pueda llamar al servlet con la URL
        if(session == null)
            response.sendRedirect("index.jsp");
        
        response.setContentType("text/html;charset=UTF-8");
        out = response.getWriter();
        
        //Nos dice que metodo hay que invocar
        int query = Integer.parseInt(request.getParameter("q"));
        
        switch(query)
        {
            case 1:
                objUsuario = new Usuario();
                objUsuario = buscar(request);
                session = request.getSession();
                session.setAttribute("objUsuario", objUsuario);
                response.sendRedirect("busqueda.jsp");
                break;
            case 2:
                //Botón de bloquear y desbloquear
                String Boton = request.getParameter("BT");
                String Usuario = request.getParameter("usuario");
                if (Boton.equals("Bloquear"))
                    bloqueaUsuario(Usuario);
                else
                    desbloqueaUsuario(Usuario);
                break;
            case 3:
                Usuario = request.getParameter("usuario");
                Elimina_Usuario(Usuario);
                break;
            default:
                response.sendRedirect("index.jsp");
        }
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