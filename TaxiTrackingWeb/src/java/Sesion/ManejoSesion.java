package Sesion;

import Beans.Respuesta;
import ConexionSQL.UsuarioDAO;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ManejoSesion extends HttpServlet 
{
    //Variable para la conexion con la BD
    private UsuarioDAO usuarioDAO;
    
    //Variable para la sesion
    private HttpSession session = null;
    
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
            case 4: //Recuperar contraseña
                if(recuperarContrasena(request))
                    session.setAttribute("errorRecuperarContrasena", "Se ha enviado una nueva contraseña al correo");
                response.sendRedirect("recuperarContrasena.jsp");
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
        int rol;
        
        //Recuperamos los valores de los campos
        String user = request.getParameter("TBUsuario");
        String pwd = request.getParameter("TBContrasena");
        
        usuarioDAO = new UsuarioDAO();
        
        //Se hace la consulta a la BD
        rol = usuarioDAO.getLogin(user, pwd);
        
        //Si el usuario es valido
        if(rol != -1)
        {
            //Se crea la sesion y se suben los atributos
            session = request.getSession();
            session.setAttribute("nombre_usuario", user);
            session.setAttribute("rol", rol);
        }
        return rol;
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
        //Configuramos el tipo de respuesta
        response.setContentType("application/json");
        
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        List<Respuesta> respuesta = new ArrayList<Respuesta>();

        String user = request.getParameter("TBUsuario");
        String pwd = request.getParameter("TBContrasena");

        usuarioDAO = new UsuarioDAO();

        //Si es correcto el login
        if (usuarioDAO.getLogin(user, pwd) != -1)
            respuesta.add(new Respuesta("1"));
        else
            respuesta.add(new Respuesta("0"));

        out.println(gson.toJson(respuesta));
        out.close();
    }
    
    /*
     * Metodo para recuperar la contraseña de una sesion. 
     * Manda un correo con una nueva contraseña generada aleatoriamente.
     * Retorna true en caso de exito y false en caso de error.
     */
    private boolean recuperarContrasena(HttpServletRequest request)
    {
        boolean b;
        
        //Recuperamos los valores de los campos
        String email = request.getParameter("TBEmail");
        
        usuarioDAO = new UsuarioDAO();
        
        //Se hace la consulta a la BD
        b = usuarioDAO.existeEmail(email);
        
        //Si el mail es valido
        if(b)
        {
            //Se genera una nueva contraseña
            String nuevaContrasena = Password.getCadenaAlfanumAleatoria(8);
            
            b = usuarioDAO.setNuevaContrasena(email, nuevaContrasena);
            
            //Si se cambio correctamente la contraseña
            if(b)
            {
                Email serverEmail = new Email();
                b = serverEmail.enviar(email, "Su nueva contraseña es " + nuevaContrasena);
            }
        }
        else
            session.setAttribute("errorRecuperarContrasena", "El email no existe");
        return b;
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
