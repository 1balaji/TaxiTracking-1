package Sesion;

import ConexionSQL.Conexion;
import ConexionSQL.Metodos;
import Beans.Respuesta;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ManejoSesion extends HttpServlet 
{
    //Variables para la conexion con la base de datos
    private Connection con;
    private ResultSet rs = null;
    private PreparedStatement pst = null;
    
    //Sesion
    HttpSession session = null;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException 
    {
        session = request.getSession();
        
        //Nos dice que metodo hay que invocar
        int query = Integer.parseInt(request.getParameter("q"));
        
        switch(query)
        {
            case 1: //Iniciar sesion web
                int respuesta = getLogin(request);
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
            case 2: //Iniciar sesion movil
                getLogin(request, response);
                break;
            case 3: //Cerrar sesion web
                session.invalidate();
                response.sendRedirect("index.jsp");
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
    private void getLogin(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
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
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
        return "Short description";
    }// </editor-fold>

}
