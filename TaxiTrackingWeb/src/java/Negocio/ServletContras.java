package Negocio;

import ConexionSQL.Conexion;
import com.mysql.jdbc.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ServletContras", urlPatterns = {"/ServletContras"})
public class ServletContras extends HttpServlet 
{
    String pas = "";
    String tipo = "contra";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String email = request.getParameter("email");
        Connection con = null;

        EmailServlet es = new EmailServlet();
        Pass p = new Pass();

        pas = p.getCadenaAlfanumAleatoria(4);

        // Buscamos en la base de datos
        try 
        {
            boolean existeUsuario = false;
            //Guardamos los datos enviados desde index

            //Establecemos la conexion
            con = Conexion.getConexion();
            String consulta = "Select * from usuario where email=?";
            ResultSet rs = null;
            PreparedStatement pst = null;
            pst = con.prepareStatement(consulta);
            pst.setString(1, email);

            rs = pst.executeQuery();
            String usuario = "";
            String nombre = "";
            String apellidopaterno = "";
            String tipou = "";

            while (rs.next()) 
            {
                //En caso de existir una coincidencia
                existeUsuario = true;
                //Y reemplazamos los atributos de dicho Usuario
                usuario = rs.getString("Nombre_usuario");
                nombre = rs.getString("nombre");
                apellidopaterno = rs.getString("Apellido_Paterno");

            }
            if (existeUsuario) 
            {
                //Para el usuario existente:
                Statement Sentencias = null;
                Sentencias = (Statement) con.createStatement();

                String consulta2 = "UPDATE usuario SET pass='" + pas + "' WHERE email='" + email + "'";

                int res = Sentencias.executeUpdate(consulta2);

                if (res == 1) 
                {
                    es.enviar(usuario, nombre, apellidopaterno, pas, email, tipo);
                    out.println("<script>alert('se ah enviado un correo con su nueva contraseña')</script>");
                    request.getRequestDispatcher("/index.jsp").forward(request, response);

                } 
                else 
                {
                    es.enviar(usuario, nombre, apellidopaterno, pas, email, tipo);
                    out.println("chale");
                }

            }
            else 
            {
                // request.setAttribute("usuario", user1);
                //De lo contrario vamos a la página errorLogin.jsp
                out.println("<script>alert('no se encontro ningun correo')</script>");
                request.getRequestDispatcher("/index.jsp").forward(request, response);

            }
            out.close();
        } catch (MessagingException ex) {
            out.println("Error al recuperar contraseña D:\n" + ex);
        } catch (SQLException ex) {
            out.println(ex.toString());
        }
        // hacer una lectura comoda (disponer del metodo readLine()).

        //sr.registro(user, email, lastname, name, pas);
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
