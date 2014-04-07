package Negocio;

import Beans.Usuario;
import ConexionSQL.Conexion;
import ConexionSQL.UsuarioDAO;
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
    Usuario objUsuarios[];
    
    //Variable para la conexion con la BD
    private UsuarioDAO usuarioDAO;
    
    //Salida
    PrintWriter out;
    
    //Sesion
    HttpSession session = null;
    
    //Respuesta
    boolean respuesta;
    
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
            case 1: //Buscar usuario
                objUsuario = buscarUsuario(request);
                session.setAttribute("objUsuario", objUsuario);
                response.sendRedirect("busqueda.jsp");
                break;
            case 2: //Bloquear usuario
                respuesta = bloquearUsuario(request);
                out.println("false");
                //out.println(respuesta);
                break;
            case 3: //Desbloquear usuario
//                Usuario = request.getParameter("usuario");
//                Elimina_Usuario(Usuario);
                break;
            case 4: //Eliminar usuario
                break;
            case 5: //GetPeticiones
                objUsuarios = getPeticiones();
                
                //Indice para movernos en el arreglo
                int i = objUsuarios.length - 1;
                
                //Creamos la tabla de salida
                out.println("<table id=\"tabla\">");
                out.println("<tr>\n" +
                                "<th class=\"ajustado\">Nombre</th>\n" +
                                "<th class=\"ajustado\">Correo Electr&oacute;nico</th>\n" +
                                "<th>Petici&oacute;n</th>\n" +
                                "<th class=\"control\">Control</th>\n" +
                            "</tr>");
                
                //Si hay resultados
                if(i != -1)
                {
                    for (;i>=0; i--)
                    {
                        //Checamos si es impar la fila a imprimir, para cambiar el color
                        if((i&1) == 1)
                            out.println("<tr>");
                        else
                            out.println("<tr class=\"alt\">");
                        
                        out.println("<td>" + objUsuarios[i].getNombreUsuario() + "</td>");
                        out.println("<td>" + objUsuarios[i].getEmail() + "</td>");
                        out.println("<td>" + objUsuarios[i].getPeticion().getComentario()+ "</td>");
                        
                        //Checamos que tipo de peticion es para poner el control correspondiente
                        if(objUsuarios[i].getPeticion().getTipo() == 0) //Bloquear
                            out.println("<td>\n" +
                                            "<button id='bloquear' onClick='gestionar(\"" + objUsuarios[i].getNombreUsuario() + "\",2)'><i class=\"fa fa-lock fa-fw\"></i>Bloquear</button>\n" +
                                        "</td>");
                        else if(objUsuarios[i].getPeticion().getTipo() == 1)    //Desbloquear
                            out.println("<td>\n" +
                                            "<button id='desbloquear' onClick='gestionar(\"" + objUsuarios[i].getNombreUsuario() + "\",3)'><i class=\"fa fa-unlock fa-fw\"></i>Desbloquear</button>\n" +
                                        "</td>");
                        else    //Eliminar
                            out.println("<td>\n" +
                                            "<button id='eliminar' onClick='gestionar(\"" + objUsuarios[i].getNombreUsuario() + "\",4)'><i class=\"fa fa-times fa-fw\"></i>Eliminar</button>\n" +
                                        "</td>");
                        out.println("</tr>");
                    }
                }
                else
                {
                    out.println("<tr><td colspan=4>No hay peticiones</td></tr>");
                }
                out.println("</table>");
                break;
            default:
                response.sendRedirect("index.jsp");
        }
    }
    
    private Usuario buscarUsuario(HttpServletRequest request)
    {
        Usuario buscado = new Usuario();
        String user = request.getParameter("TBBuscarUsuario");
        String consulta = "Select nombre_usuario,nombre,email,apellido_paterno,apellido_materno,status from usuario where nombre_usuario = ? ";

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

    private boolean bloquearUsuario(HttpServletRequest request)
    {
        boolean b;
        String nombreUsuario = request.getParameter("nombreUsuario");
        objUsuario = new Usuario(nombreUsuario);
        
        usuarioDAO = new UsuarioDAO();
        
        //Hacemos la consulta a la BD
        b = usuarioDAO.bloquearUsuario(objUsuario);
        
        return b;
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

    private Usuario [] getPeticiones()
    {
        Usuario usuarios[];
        usuarioDAO = new UsuarioDAO();
        
        //Hacemos la consulta a la BD
        usuarios = usuarioDAO.getPeticiones();
        
        return usuarios;
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