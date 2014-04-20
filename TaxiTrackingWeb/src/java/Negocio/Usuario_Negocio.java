package Negocio;

import Beans.Peticion;
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
        
        try {query = Integer.parseInt(request.getParameter("q"));}
        catch (NumberFormatException e) {query = -1;}
        
        //Para que el usuario normal no pueda llamar las opciones de administrador del servlet
        if((Integer)session.getAttribute("rol") != 1 && query <= 5)
            response.sendRedirect("bienvenido.jsp");
        
        //Para que el administrador no pueda llamar las opciones de usuario normal del servlet
        if((Integer)session.getAttribute("rol") != 0 && query >= 11)
            response.sendRedirect("bienvenidoAdministrador.jsp");
        
        response.setContentType("text/html;charset=UTF-8");
        out = response.getWriter();
        
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
                respuesta = editarEmail(request, session);
                if(respuesta)
                    session.setAttribute("errorEmail", "Los datos se actualizaron correctamente");
                response.sendRedirect("configurarPerfil.jsp");
                break;
            case 10:    //Editar contraseña
                respuesta = editarContrasena(request, session);
                if(respuesta)
                    session.setAttribute("errorContrasena", "Los datos se actualizaron correctamente");
                else
                    session.setAttribute("errorContrasena", "La contraseña es incorrecta");
                
                response.sendRedirect("configurarPerfil.jsp");
                break;
            case 11:    //get peticiones de un usuario normal
                objUsuario = getPeticion(session);
                if(objUsuario.getPeticion() != null)
                {
                    int tipo = objUsuario.getPeticion().getTipo();
                    String s;
                    if (tipo == 0)
                        s = "Bloqueo de la cuenta";
                    else if(tipo == 1)
                        s = "Desloqueo de la cuenta";
                    else
                        s = "Eliminaci&oacute;n de la cuenta";
                    out.println("<form action=\"" + request.getContextPath() + "/Usuario_Negocio?q=12\" id=\"formCancelarPeticion\">\n" +
"                            <p><strong>Petici&oacute;n pendiente</strong></p>\n" +
"                            <div class=\"input-group\">\n" +
"                                <label class=\"input-group-label mediano centrado\" for=\"TBTipo\">Petici&oacute;n</label>\n" +
"                                <input type=\"text\" id=\"TBTipo\" name=\"TBTipo\" class=\"form-control largo\" readonly=\"readonly\" value=\"" + s + "\" />\n" +
"                            </div>\n" +
"                            <div class=\"input-group\">\n" +
"                                <textarea id=\"TBDescripcion\" readonly=\"readonly\" rows=\"4\" cols=\"50\">" + objUsuario.getPeticion().getComentario() + "</textarea>\n" +
"                            </div>\n" +
"                            <div class=\"centrado\">\n" +
"                                <button type=\"submit\" id=\"BTCancelarPeticion\" name=\"BTCancelarPeticion\" onclick=\"gestionar(12)\">\n" +
"                                    <i class=\"fa fa-times fa-fw\"></i>Cancelar Peticion\n" +
"                                </button>\n" +
"                            </div>\n" +
"                        </form>");
                }
                else
                {
                    out.println("<form action=\"" + request.getContextPath() + "/Usuario_Negocio?q=13\" id=\"formEnviarPeticion\">\n" +
"                            <p><strong>Hacer una peticion</strong></p>\n");
                    if(session.getAttribute("errorPeticion") != null)
                    {
                        out.print("<div class='error centrado'>" + (String) session.getAttribute("errorPeticion") + "</div>");
                        session.removeAttribute("errorPeticion");
                    }
                    out.println("                            <div class=\"input-group\">\n" +
"                                <label class=\"input-group-label mediano centrado\" for=\"TBTipo\">Petici&oacute;n</label>\n" +
"                                <select id=\"TBTipo\" name=\"TBTipo\" class=\"form-control largo select\" required=\"required\">\n" +
"                                    <option value=\"\">Seleccionar</option>\n" +
"                                    <option value=\"0\">Bloqueo de la cuenta</option>\n" +
"                                    <option value=\"1\">Desbloqueo de la cuenta</option>\n" +
"                                    <option value=\"2\">Eliminaci&oacute;n de la cuenta</option>\n" +
"                                </select>\n" +
"                            </div>\n" +
"                            <div class=\"input-group\">\n" +
"                                <textarea id=\"TBDescripcion\" name=\"TBDescripcion\" placeholder=\"Descripci&oacute;n\" rows=\"4\" cols=\"50\" maxlength=\"150\" wrap=\"hard\" required=\"required\"></textarea>\n" +
"                            </div>\n" +
"                            <div class=\"centrado\">\n" +
"                                <button type=\"submit\" id=\"BTEnviarPeticion\" name=\"BTEnviarPeticion\" onclick=\"gestionar(13)\">\n" +
"                                    <i class=\"fa fa-plus-circle fa-fw\"></i>Enviar Peticion\n" +
"                                </button>\n" +
"                            </div>\n" +
"                        </form>");
                }
                break;
            case 12:    //Cancelar una peticion
                respuesta = cancelarPeticion(session);
                if(respuesta)
                    session.setAttribute("errorPeticion", "La petición se canceló correctamente");
                else
                    session.setAttribute("errorPeticion", "Error al cancelar la petición");
                response.sendRedirect("peticiones.jsp");
                break;
            case 13:    //Enviar una peticion
                respuesta = agregarPeticion(request, session);
                if(respuesta)
                    session.setAttribute("errorPeticion", "La petición se envió correctamente");
                response.sendRedirect("peticiones.jsp");
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
    
    private boolean editarEmail(HttpServletRequest request, HttpSession session)
    {
        boolean b = true;
        String nombreUsuario = (String) session.getAttribute("nombre_usuario");
        
        String email = request.getParameter("TBEmail").trim();
        if(!Validacion.esEmail(email))
        {
            session.setAttribute("errorEmail", "Error el email es incorrecto");
            b = false;
        }
        
        String emailConfirmacion = request.getParameter("TBConfirmarEmail").trim();
        if(email.compareTo(emailConfirmacion) != 0)
        {
            session.setAttribute("errorEmail", "Error los correos electrónicos no coinciden");
            b = false;
        }
        
        //Si no hubo error y los datos son validos
        if(b)
        {
            objUsuario = new Usuario(nombreUsuario);
            objUsuario.setEmail(email);
            
            usuarioDAO = new UsuarioDAO();
        
            //Hacemos la consulta a la BD
            b = usuarioDAO.editarEmail(objUsuario);
        }
        
        return b;
    }
    
    private boolean editarContrasena(HttpServletRequest request, HttpSession session)
    {
        boolean b = true;
        String nombreUsuario = (String) session.getAttribute("nombre_usuario");
        
        String contrasena = request.getParameter("TBContrasena").trim();
        if(!Validacion.esAlfanumerico(contrasena))
        {
            session.setAttribute("errorContrasena", "Error la contraseña es incorrecta");
            b = false;
        }
        
        String nuevaContrasena = request.getParameter("TBNuevaContrasena").trim();
        if(!Validacion.esAlfanumerico(nuevaContrasena))
        {
            session.setAttribute("errorContrasena", "Error la nueva contraseña es incorrecta");
            b = false;
        }
        
        String contrasenaConfirmacion = request.getParameter("TBConfirmarContrasena").trim();
        if(nuevaContrasena.compareTo(contrasenaConfirmacion) != 0)
        {
            session.setAttribute("errorEmail", "Error las contraseñas no coinciden");
            b = false;
        }
        
        //Si no hubo error y los datos son validos
        if(b)
        {
            usuarioDAO = new UsuarioDAO();
        
            //Hacemos la consulta a la BD
            b = usuarioDAO.editarContrasena(nombreUsuario, contrasena, nuevaContrasena);
        }
        
        return b;
    }
    
    private Usuario getPeticion(HttpSession session) 
    {
        String nombreUsuario = (String) session.getAttribute("nombre_usuario");
        objUsuario = new Usuario(nombreUsuario);
        usuarioDAO = new UsuarioDAO();
        objUsuario = usuarioDAO.getPeticionUsuario(objUsuario);
        
        return objUsuario;
    }
    
    private boolean cancelarPeticion(HttpSession session) 
    {
        boolean b;
        String nombreUsuario = (String) session.getAttribute("nombre_usuario");
        objUsuario = new Usuario(nombreUsuario);
        usuarioDAO = new UsuarioDAO();
        
        //Hacemos la consulta a la BD
        b = usuarioDAO.eliminarPeticion(objUsuario);
        
        return b;
    }
    
    private boolean agregarPeticion(HttpServletRequest request, HttpSession session) throws UnsupportedEncodingException
    {
        boolean b = true;
        String nombreUsuario = (String) session.getAttribute("nombre_usuario");
        
        int tipo = 0;
        try {tipo = Integer.parseInt(request.getParameter("TBTipo"));}
        catch(NumberFormatException e)
        {
            session.setAttribute("errorPeticion", "Error el tipo de petición es incorrecto");
            b = false;
        }
        
        //String descripcion = new String(request.getParameter("TBDescripcion").trim().getBytes("ISO-8859-1"),"UTF-8");
        String descripcion = request.getParameter("TBDescripcion").trim();
        System.out.println(descripcion);
        if(!Validacion.esCadena(descripcion))
        {
            session.setAttribute("errorPeticion", "Error la descripción es incorrecta");
            b = false;
        }
        
        //Si no hubo error y los datos son validos
        if(b)
        {
            objUsuario = new Usuario(nombreUsuario);
            objUsuario.setPeticion(new Peticion(tipo, descripcion));
            
            usuarioDAO = new UsuarioDAO();
        
            //Hacemos la consulta a la BD
            b = usuarioDAO.agregarPeticion(objUsuario);
        }
        
        return b;
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