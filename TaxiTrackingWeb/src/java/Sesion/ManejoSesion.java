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
                    case 0: //Usuario normal
                        response.sendRedirect("bienvenido.jsp");
                        break;
                    case 1: //Administrador
                        response.sendRedirect("bienvenidoAdministrador.jsp");
                        break;
                    case 2: //Cuenta bloqueada
                        session.setAttribute("error", "2");
                        response.sendRedirect("index.jsp");
                        break;
                    default:
                        session.setAttribute("error", "1");
                        response.sendRedirect("index.jsp");
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
            case 5: //Activar cuenta
                if(activarCuenta(request))
                {
                    PrintWriter out = response.getWriter();
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Cuenta activada</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<p align = 'center'><h1>La cuenta se ha activado correctamente</h1></p>");
                    out.println("<p align = 'center'><h1><a href = 'index.jsp'>Presione aqui para ir a la pagina de inicio</a></h1></p>");
                    out.println("</body>");
                    out.println("</html>");
                }
                else
                    response.sendRedirect("index.jsp");
                break;
            case 6: //Recuperar contraseña movil
                recuperarContrasena(request, response);
                break;
            default:
                response.sendRedirect("index.jsp");
        }
    }
    
    /*
     * Metodo para accesar al sistema desde web.
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
        
        //Se hace la consulta a la BD. Aqui no nos importa el status
        rol = usuarioDAO.getLogin(user, pwd, false);
        
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
     * [{"respuesta":0}] -> "logueo invalido"
     * [{"respuesta":1}] -> "logueo valido"
     * [{"respuesta":2}] -> "cuenta bloqueada"
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

        //Si es correcto el login. Aqui si nos importa verificar el status
        int respuestaLogin = usuarioDAO.getLogin(user, pwd, true);
        if (respuestaLogin == -1)
            respuesta.add(new Respuesta(0));
        else if (respuestaLogin == 0 || respuestaLogin == 1)
            respuesta.add(new Respuesta(1));
        else if (respuestaLogin == 2)
            respuesta.add(new Respuesta(2));

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
                b = serverEmail.enviar(email, "Reestablecimiento de contraseña", "Su nueva contraseña es " + nuevaContrasena);
            }
        }
        else
            session.setAttribute("errorRecuperarContrasena", "El email no existe");
        return b;
    }
    
    private boolean activarCuenta(HttpServletRequest request)
    {
        boolean b;
        String nombreUsuario = request.getParameter("nombreUsuario");
        usuarioDAO = new UsuarioDAO();
        b = usuarioDAO.activarCuenta(nombreUsuario);
        return b;
    }
    
    /*
     * Metodo para recuperar la contraseña de una sesion desde un movil. 
     * Manda un correo con una nueva contraseña generada aleatoriamente.
     * No retorna nada pero envia un correo e imprime una respuesta "0" en caso de error o "1" en caso de exito.
     */
    private void recuperarContrasena(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        //Configuramos el tipo de respuesta
        response.setContentType("application/json");
        
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        List<Respuesta> respuesta = new ArrayList<Respuesta>();

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
                b = serverEmail.enviar(email, "Reestablecimiento de contraseña", "Su nueva contraseña es " + nuevaContrasena);
                
                //Si se envio correctamente el email
                if(b)
                    respuesta.add(new Respuesta(1));
            }
        }
        else
            respuesta.add(new Respuesta(0));

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
