package Negocio;

import Beans.Usuario;
import ConexionSQL.UsuarioDAO;
import Validacion.Validacion;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Usuario_Negocio extends HttpServlet 
{
    //Beans
    private Usuario objUsuario;
    private Usuario objUsuarios[];
    
    //Variable para la conexion con la BD
    private UsuarioDAO usuarioDAO;
    
    //Salida html
    private PrintWriter out;
    
    //Sesion
    private HttpSession session = null;
    
    //Respuesta
    private boolean respuesta;
    
    //Nos dice que metodo hay que invocar
    private int query;
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        session = request.getSession(false);
        
        //Para que no se pueda llamar al servlet con la URL
        if(session == null)
            response.sendRedirect("index.jsp");
        
        //Para que el usuario normal no pueda llamar el servlet
        else if((Integer)session.getAttribute("rol") != 1)
            response.sendRedirect("index.jsp");
        
        response.setContentType("text/html;charset=UTF-8");
        out = response.getWriter();
        
        try {query = Integer.parseInt(request.getParameter("q"));}
        catch (NumberFormatException e) {query = -1;}
        
        switch(query)
        {
            case 1: //Buscar usuario
                objUsuario = buscarUsuario(request);
                session.setAttribute("objUsuario", objUsuario);
                response.sendRedirect("busquedaUsuario.jsp");
                break;
            case 2: //Bloquear usuario
                respuesta = bloquearUsuario(request);
                if(respuesta)
                    out.println("1");
                else
                    out.println("0");
                break;
            case 3: //Desbloquear usuario
                respuesta = desbloquearUsuario(request);
                if(respuesta)
                    out.println("1");
                else
                    out.println("0");
                break;
            case 4: //Eliminar usuario
                respuesta = eliminarUsuario(request);
                if(respuesta)
                    out.println("1");
                else
                    out.println("0");
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
                                            "<button id='BTBloquearUsuario' name='BTBloquearUsuario' onClick='gestionar(\"" + objUsuarios[i].getNombreUsuario() + "\",2)'><i class=\"fa fa-lock fa-fw\"></i>Bloquear</button>\n" +
                                        "</td>");
                        else if(objUsuarios[i].getPeticion().getTipo() == 1)    //Desbloquear
                            out.println("<td>\n" +
                                            "<button id='BTDesbloquearUsuario' name='BTDesbloquearUsuario' onClick='gestionar(\"" + objUsuarios[i].getNombreUsuario() + "\",3)'><i class=\"fa fa-unlock fa-fw\"></i>Desbloquear</button>\n" +
                                        "</td>");
                        else    //Eliminar
                            out.println("<td>\n" +
                                            "<button id='BTEliminarUsuario' name='BTEliminarUsuario' onClick='gestionar(\"" + objUsuarios[i].getNombreUsuario() + "\",4)'><i class=\"fa fa-times fa-fw\"></i>Eliminar</button>\n" +
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
            case 6: //Buscar perfil
                objUsuario = getPerfil(session);
                out.println("<form action=\"" + request.getContextPath() + "/Usuario_Negocio?q=7\" method=\"POST\">\n");
                if(session.getAttribute("errorNombre") != null) { out.println("<div class = \"error\">" + session.getAttribute("errorNombre") + "</div>"); session.removeAttribute("errorNombre");}
                out.println(
"                        <div class=\"input-group\">\n" +
"                            <label class=\"input-group-label mediano centrado\" for=\"TBNombre\">Nombre</label>\n" +
"                            <input type=\"text\" id=\"TBNombre\" name=\"TBNombre\" class=\"form-control largo\" value=\"" + objUsuario.getNombre() + "\" required=\"required\" maxlength=\"30\" placeholder=\"Nombre\" />\n" +
"                        </div>\n");
                if(session.getAttribute("errorApellidoPaterno") != null) { out.println("<div class = \"error\">" + session.getAttribute("errorApellidoPaterno") + "</div>"); session.removeAttribute("errorApellidoPaterno");}
                out.println(
"                        <div class=\"input-group\">\n" +
"                            <label class=\"input-group-label mediano centrado\" for=\"TBApellidoPaterno\">Apellido Paterno</label>\n" +
"                            <input type=\"text\" id=\"TBApellidoPaterno\" name=\"TBApellidoPaterno\" class=\"form-control largo\" value=\"" + objUsuario.getApellidoPaterno() + "\" required=\"required\" maxlength=\"30\" placeholder=\"Apellido Paterno\" />\n" +
"                        </div>\n");
                if(session.getAttribute("errorApellidoMaterno") != null) { out.println("<div class = \"error\">" + session.getAttribute("errorApellidoMaterno") + "</div>"); session.removeAttribute("errorApellidoMaterno");}
                out.println(
"                        <div class=\"input-group\">\n" +
"                            <label class=\"input-group-label mediano centrado\" for=\"TBApellidoMaterno\">Apellido Materno</label>\n" +
"                            <input type=\"text\" id=\"TBApellidoMaterno\" name=\"TBApellidoMaterno\" class=\"form-control largo\" value=\"" + objUsuario.getApellidoMaterno() + "\" required=\"required\" maxlength=\"30\" placeholder=\"Apellido Materno\" />\n" +
"                        </div>\n" +
"                        <div class=\"centrado\">\n" +
"                            <button type=\"submit\" id=\"BTEditarPerfil\" name=\"BTEditarPerfil\">\n" +
"                                <i class=\"fa fa-pencil-square-o\"></i>Actualizar\n" +
"                            </button>\n" +
"                        </div>\n" +
"                    </form>");
                break;
            case 7: //Editar perfil
                respuesta = editarPerfil(request, session);
                if(respuesta)
                    session.setAttribute("errorNombre", "Los datos se actualizaron correctamente");
                response.sendRedirect("verPerfil.jsp");
                break;
            case 8: //Buscar email
                objUsuario = getEmail(session);
                out.println(objUsuario.getEmail());
                break;
            case 9: //Editar email
                break;
            case 10:    //Editar contraseña
                break;
            default:
                response.sendRedirect("bienvenidoAdministrador.jsp");
        }
        out.close();
    }
    
    private Usuario buscarUsuario(HttpServletRequest request)
    {
        String nombreUsuario = request.getParameter("TBBuscarUsuario");

        //Para que no se pueda buscar al administrador
        if(nombreUsuario.equals("admin"))
            nombreUsuario = null;

        objUsuario = new Usuario(nombreUsuario);
        usuarioDAO = new UsuarioDAO();
        
        //Hacemos la consulta a la BD
        objUsuario = usuarioDAO.buscarUsuario(objUsuario);
        
        return objUsuario;
    }

    private boolean bloquearUsuario(HttpServletRequest request)
    {
        boolean b;
        String nombreUsuario = request.getParameter("nombreUsuario");
        objUsuario = new Usuario(nombreUsuario);
        objUsuario.setStatus(0);
        usuarioDAO = new UsuarioDAO();
        
        //Hacemos la consulta a la BD
        b = usuarioDAO.cambiarStatus(objUsuario);
        
        //Si se bloqueo correctamente, eliminamos la peticion
        if(b)
            b = usuarioDAO.eliminarPeticion(objUsuario);
        
        return b;
    }

    private boolean desbloquearUsuario(HttpServletRequest request) 
    {
        boolean b;
        String nombreUsuario = request.getParameter("nombreUsuario");
        objUsuario = new Usuario(nombreUsuario);
        objUsuario.setStatus(1);
        usuarioDAO = new UsuarioDAO();
        
        //Hacemos la consulta a la BD
        b = usuarioDAO.cambiarStatus(objUsuario);
        
        //Si se desbloqueo correctamente, eliminamos la peticion
        if(b)
            b = usuarioDAO.eliminarPeticion(objUsuario);
        
        return b;
    }

    private boolean eliminarUsuario(HttpServletRequest request)
    {
        boolean b;
        String nombreUsuario = request.getParameter("nombreUsuario");
        objUsuario = new Usuario(nombreUsuario);
        usuarioDAO = new UsuarioDAO();
        
        //Hacemos la consulta a la BD
        b = usuarioDAO.eliminarUsuario(objUsuario);
        
        //Si se elimino correctamente, eliminamos la peticion
        if(b)
            b = usuarioDAO.eliminarPeticion(objUsuario);
        
        return b;
    }

    private Usuario [] getPeticiones()
    {
        Usuario usuarios[];
        usuarioDAO = new UsuarioDAO();
        
        //Hacemos la consulta a la BD
        usuarios = usuarioDAO.getPeticiones();
        
        return usuarios;
    }
    
    private Usuario getPerfil(HttpSession session) 
    {
        String nombreUsuario = (String) session.getAttribute("nombre_usuario");
        objUsuario = new Usuario(nombreUsuario);
        usuarioDAO = new UsuarioDAO();
        objUsuario = usuarioDAO.buscarUsuario(objUsuario);
        
        return objUsuario;
    }
    
    private boolean editarPerfil(HttpServletRequest request, HttpSession session) throws UnsupportedEncodingException
    {
        boolean b = true;
        String nombreUsuario = (String) session.getAttribute("nombre_usuario");
        
        String nombre = new String(request.getParameter("TBNombre").trim().getBytes("ISO-8859-1"),"UTF-8");
        if(!Validacion.esCadena(nombre))
        {
            session.setAttribute("errorNombre", "Error el nombre es incorrecto");
            b = false;
        }
        
        String apellido_paterno = new String(request.getParameter("TBApellidoPaterno").trim().getBytes("ISO-8859-1"),"UTF-8");
        if(!Validacion.esCadena(apellido_paterno))
        {
            session.setAttribute("errorApellidoPaterno", "Error el apellido paterno es incorrecto");
            b = false;
        }
        
        String apellido_materno = new String(request.getParameter("TBApellidoMaterno").trim().getBytes("ISO-8859-1"),"UTF-8");
        if(!Validacion.esCadena(apellido_materno))
        {
            session.setAttribute("errorApellidoMaterno", "Error el apellido materno es incorrecto");
            b = false;
        }
        
        //Si no hubo error y los datos son validos
        if(b)
        {
            objUsuario = new Usuario(nombreUsuario);
            objUsuario.setNombre(nombre);
            objUsuario.setApellidoPaterno(apellido_paterno);
            objUsuario.setApellidoMaterno(apellido_materno);
            
            usuarioDAO = new UsuarioDAO();
        
            //Hacemos la consulta a la BD
            b = usuarioDAO.editarPerfil(objUsuario);
        }
        
        return b;
    }
    
    private Usuario getEmail(HttpSession session) 
    {
        String nombreUsuario = (String) session.getAttribute("nombre_usuario");
        objUsuario = new Usuario(nombreUsuario);
        usuarioDAO = new UsuarioDAO();
        objUsuario = usuarioDAO.buscarUsuario(objUsuario);
        
        return objUsuario;
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