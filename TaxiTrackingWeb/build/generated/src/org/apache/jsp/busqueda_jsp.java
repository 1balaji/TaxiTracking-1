package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import Beans.Usuario;;

public final class busqueda_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\r\n");
      out.write("\r\n");

    String usuario = null;
    int rol = -1;
    
    //Checamos que exista la sesion
    if(session != null && session.getAttribute("rol") != null)
    {
        rol = (Integer)session.getAttribute("rol");

        //Si no es administrador se redirecciona al inicio de un usuario normal
        if(rol!=1){response.sendRedirect("bienvenido.jsp");}

        usuario = (String)session.getAttribute("nombre_usuario");
    }
    else
        response.sendRedirect("index.jsp");

      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html lang=\"es\">\r\n");
      out.write("    <head>\r\n");
      out.write("        <meta charset=\"UTF-8\">\r\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("        <title>Taxi Tracking - Inicio</title>\r\n");
      out.write("        <link rel=\"icon\" href=\"images/favicon.gif\" type=\"image/x-icon\"/>\r\n");
      out.write("\r\n");
      out.write("        <!--[if lt IE 9]>\r\n");
      out.write("        <script src=\"js/html5shiv/dist/html5shiv.js\"></script>\r\n");
      out.write("        <![endif]-->\r\n");
      out.write("\r\n");
      out.write("        <link rel=\"shortcut icon\" href=\"images/favicon.gif\" type=\"image/x-icon\"/>\r\n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"css/styles.css\"/>\r\n");
      out.write("    </head>\r\n");
      out.write("    <body>\r\n");
      out.write("        ");

            /*Usuario objUsuario = null;
            if(session.getAttribute("objUsuario") != null)
            {
                objUsuario = new Usuario();
                objUsuario = (Usuario)session.getAttribute("objUsuario");
                //Quitamos el usuario de la sesion
                session.removeAttribute("objUsuario");
            }
            else
                response.sendRedirect("bienvenidoAdministrador.jsp");*/
        
      out.write("\r\n");
      out.write("        <!--start container-->\r\n");
      out.write("        <div id=\"container\">\r\n");
      out.write("\r\n");
      out.write("            <!--start header-->\r\n");
      out.write("            <header>\r\n");
      out.write("\r\n");
      out.write("                <!--start logo-->\r\n");
      out.write("                <a href=\"#\" id=\"logo\"><img src=\"images/logo.png\" width=\"221\" height=\"84\" alt=\"logo\"/></a>\r\n");
      out.write("                <!--end logo-->\r\n");
      out.write("\r\n");
      out.write("                <!--start menu-->\r\n");
      out.write("                <nav>\r\n");
      out.write("                    <ul>\r\n");
      out.write("                        <li><a href=\"#\" class=\"current\">Inicio</a></li>\r\n");
      out.write("                        <li><a href=\"#\">Acerca de</a></li>\r\n");
      out.write("                        <li><a href=\"#\">Contacto</a></li>\r\n");
      out.write("                    </ul>\r\n");
      out.write("                </nav>\r\n");
      out.write("                <!--end menu-->\r\n");
      out.write("            </header>\r\n");
      out.write("            <!--end header-->\r\n");
      out.write("\r\n");
      out.write("            <!--start intro-->\r\n");
      out.write("            <div id=\"intro\">\r\n");
      out.write("            </div>\r\n");
      out.write("            <section class=\"group_bannner_left\">\r\n");
      out.write("                <hgroup>\r\n");
      out.write("                    <!--<h1>Nombre </h1>-->\r\n");
      out.write("                    <TABLE BORDER=1 WIDTH=300>\r\n");
      out.write("                        <TR>\r\n");
      out.write("                            <TD WIDTH=100>Nombre(completo)</TD>\r\n");
      out.write("                            <TD WIDTH=100>Correo Electronico</TD>\r\n");
      out.write("                            <TD WIDTH=100>Status</TD>\r\n");
      out.write("                            <TD WIDTH=100>Bloquear/Desbloquear</TD>\r\n");
      out.write("                            <TD WIDTH=100>Eliminar</TD>\r\n");
      out.write("                        </TR>\r\n");
      out.write("                        <TR>\r\n");
      out.write("                            \r\n");
      out.write("                            </TD>\r\n");
      out.write("                            <TD WIDTH=100>\r\n");
      out.write("                                <form action=\"");
      out.print(request.getContextPath());
      out.write("/Usuario_Negocio?q=4\" method=\"post\">\r\n");
      out.write("                                    <input type=\"submit\" name=\"BTEliminar\" id=\"BTEliminar\" value=\"Eliminar\" />\r\n");
      out.write("                                </form>\r\n");
      out.write("                            </TD>\r\n");
      out.write("                        </TR>\r\n");
      out.write("                    </TABLE>\r\n");
      out.write("                    <h2>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec molestie. Sed aliquam sem ut arcu. Phasellus sollicitudin.</h2>\r\n");
      out.write("                </hgroup>\r\n");
      out.write("                <div class=\"button black\"><a href=\"#\">Read more about our fresh ideas</a></div>\r\n");
      out.write("            </section>\r\n");
      out.write("            <!--end intro-->\r\n");
      out.write("\r\n");
      out.write("            <!--start holder-->\r\n");
      out.write("            <div class=\"holder_content\">\r\n");
      out.write("                <section class=\"group1\">\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("                    <h3>Caracter&iacute;sticas</h3>\r\n");
      out.write("                    <p>Taxi Tracking es una aplicaci&oacute;n cuyo objetivo es el de proveer una herramienta, a trav&eacute;s de dispositivos móviles, a los \r\n");
      out.write("                        usuarios que realizan viajes en el sistema de transporte tipo taxi, para monitorear su viaje y proporcionar una opción de ayuda en caso\r\n");
      out.write("                        de ser necesario.</p>\r\n");
      out.write("                    <p>A continuación se enlistan las caracter&iacute;sticas que permiten a Taxi Tracking llevar a cabo su prop&oacute;sito.</p>\r\n");
      out.write("\r\n");
      out.write("                    <article class=\"holder_gallery\">\r\n");
      out.write("                        <a class=\"photo_hover\" href=\"#\"><img src=\"images/picture2.jpg\" width=\"150\" height=\"115\" alt=\"picture1\"/></a>\r\n");
      out.write("                        <h2>Seguimiento</h2>\r\n");
      out.write("                        <p>Taxi Tracking da seguimiento al viaje del usuario mediante la tecnología GPS y API's de Google.</p>\r\n");
      out.write("                        <span class=\"readmore\"><a href=\"#\">Ver im&aacute;genes...</a></span>\r\n");
      out.write("                    </article>\r\n");
      out.write("\r\n");
      out.write("                    <article class=\"holder_gallery\">\r\n");
      out.write("                        <a class=\"photo_hover\" href=\"#\"><img src=\"images/picture4.jpg\" width=\"150\" height=\"115\" alt=\"picture1\"/></a>\r\n");
      out.write("                        <h2>Env&iacute;o de alertas</h2>\r\n");
      out.write("                        <p>Taxi Tracking realiza denuncias en l&iacute;nea en la red social <a href=\"https://twitter.com/CASPOLICIA_CDMX\">\r\n");
      out.write("                                <i>Twitter</i></a> de el <a href=\"http://www.ssp.df.gob.mx/Emergencias/Pages/CAS.aspx\">Centro de Atenci&oacute;n del Secretario de \r\n");
      out.write("                                Seguridad P&uacute;blica del D.F.</a> El cual promete una respuesta de patrullas\to elementos en un m&aacute;ximo de 10 minutos.</p>\r\n");
      out.write("                        <span class=\"readmore\"><a href=\"#\">Ver im&aacute;genes...</a></span>\r\n");
      out.write("                    </article>\r\n");
      out.write("\r\n");
      out.write("                    <article class=\"holder_gallery\">\r\n");
      out.write("                        <a class=\"photo_hover\" href=\"#\"><img src=\"images/picture5.jpg\" width=\"150\" height=\"115\" alt=\"picture1\"/></a>\r\n");
      out.write("                        <h2>Sugerencia de rutas</h2>\r\n");
      out.write("                        <p>Para un servicio m&aacute;s completo, Taxi Tracking puede sugerir una ruta para el viaje del usuario.</p>\r\n");
      out.write("                        <span class=\"readmore\"><a href=\"#\">Ver im&aacute;genes...</a></span>\r\n");
      out.write("                    </article>\r\n");
      out.write("\r\n");
      out.write("                    <article class=\"holder_gallery\">\r\n");
      out.write("                        <a class=\"photo_hover\" href=\"#\"><img src=\"images/picture5.jpg\" width=\"150\" height=\"115\" alt=\"picture1\"/></a>\r\n");
      out.write("                        <h2>Estimaci&oacute;n de costo y distancia</h2>\r\n");
      out.write("                        <p>Con la finalidad de evitar los abusos en el cobro del viaje, Taxi Tracking cuenta con un tax&iacute;metro virtual que te da un\r\n");
      out.write("                            estimado del costo de tu viaje.</p>\r\n");
      out.write("                        <span class=\"readmore\"><a href=\"#\">Ver im&aacute;genes...</a></span>\r\n");
      out.write("                    </article>\r\n");
      out.write("\r\n");
      out.write("                    <article class=\"holder_gallery\">\r\n");
      out.write("                        <a class=\"photo_hover\" href=\"#\"><img src=\"images/picture5.jpg\" width=\"150\" height=\"115\" alt=\"picture1\"/></a>\r\n");
      out.write("                        <h2>Identificaci&oacute;n de taxis por QR</h2>\r\n");
      out.write("                        <p>Con la intenci&oacute;n de mantener un control de los taxis leg&iacute;timos y evitar los taxis piratas,\r\n");
      out.write("                            Taxi Tracking genera un c&oacute;digo QR &uacute;nico para cada unidad.</p>\r\n");
      out.write("                        <span class=\"readmore\"><a href=\"#\">Ver im&aacute;genes...</a></span>\r\n");
      out.write("                    </article>\r\n");
      out.write("\r\n");
      out.write("                    <article class=\"holder_gallery\">\r\n");
      out.write("                        <a class=\"photo_hover\" href=\"#\"><img src=\"images/picture5.jpg\" width=\"150\" height=\"115\" alt=\"picture1\"/></a>\r\n");
      out.write("                        <h2>Ranking de taxis</h2>\r\n");
      out.write("                        <p>Para una informaci&oacute;n m&aacute;s espec&iacute;fica acerca de la unidad, Taxi Tracking permite a los usuarios evaluar\r\n");
      out.write("                            al taxi y con base en esto se realiza un promedio del servicio brindado.</p>\r\n");
      out.write("                        <span class=\"readmore\"><a href=\"#\">Ver im&aacute;genes...</a></span>\r\n");
      out.write("                    </article>\r\n");
      out.write("                </section>\r\n");
      out.write("\r\n");
      out.write("                <aside class=\"group2\">\r\n");
      out.write("                    \r\n");
      out.write("\r\n");
      out.write("                    <section>\r\n");
      out.write("                        <h3>Noticias relacionadas</h3>\r\n");
      out.write("                        <article class=\"holder_news\">\r\n");
      out.write("                            <span class=\"news_span\">02.03.2011</span>\r\n");
      out.write("                            <h4>Crecen 139% asaltos en taxis en cinco a&ntilde;os</h4>\r\n");
      out.write("                            <p>Durante 20 horas Karla experiment&oacute; el terror a bordo de un taxi en el centro de la ciudad de M&eacute;xico.\r\n");
      out.write("                                <span class=\"readmore\"><a href=\"http://www.eluniversal.com.mx/ciudad/105375.html\">Leer m&aacute;s...</a></span></p>\r\n");
      out.write("                        </article>\r\n");
      out.write("\r\n");
      out.write("                        <article class=\"holder_news\">\r\n");
      out.write("                            <span class=\"news_span\">19.02.2011</span>\r\n");
      out.write("                            <h4>Taxistas prevendr&aacute;n delitos</h4>\r\n");
      out.write("                            <p>La Procuradur&iacute;a General de Justicia del Distrito Federal (PGJDF), la Secretar&iacute;a de Transportes y Vialidad\r\n");
      out.write("                                (SETRAVI), as&iacute; como 17 organizaciones de taxistas firmaron un convenio para una red de protecci&oacute;n para prevenir\r\n");
      out.write("                                los delitos en taxis.\r\n");
      out.write("                                <span class=\"readmore\"><a href=\"http://www.eluniversal.com.mx/ciudad/105264.html\">Leer m&aacute;s...</a></span></p>\r\n");
      out.write("                        </article>\r\n");
      out.write("                        <a class=\"photo_hover\" href=\"#\"><img src=\"images/picture3.jpg\" width=\"257\" height=\"295\" alt=\"picture\"/></a>\r\n");
      out.write("                    </section>\r\n");
      out.write("                </aside>\r\n");
      out.write("            </div>\r\n");
      out.write("            <!--end holder-->\r\n");
      out.write("        </div>\r\n");
      out.write("        <!--end container-->\r\n");
      out.write("\r\n");
      out.write("        <!--start footer-->\r\n");
      out.write("        <footer>\r\n");
      out.write("            <div class=\"container\">\r\n");
      out.write("                <div id=\"FooterLeft\">Desarrollado por Carlos Jim&eacute;nez y Francisco Negrete / 2014</div>\r\n");
      out.write("                <div id=\"FooterRight\">© TT2013-A022</div>\r\n");
      out.write("            </div>\r\n");
      out.write("        </footer>\r\n");
      out.write("        <!--end footer-->\r\n");
      out.write("        <!-- Free template distributed by http://freehtml5templates.com -->\r\n");
      out.write("    </body>\r\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
