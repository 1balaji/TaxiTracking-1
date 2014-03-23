package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html lang=\"es\">\n");
      out.write("    <head>\n");
      out.write("        <meta charset=\"UTF-8\">\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>Taxi Tracking - Inicio</title>\n");
      out.write("        <link rel=\"icon\" href=\"images/favicon.gif\" type=\"image/x-icon\"/>\n");
      out.write("\n");
      out.write("        <!--[if lt IE 9]>\n");
      out.write("        <script src=\"js/html5shiv/dist/html5shiv.js\"></script>\n");
      out.write("        <![endif]-->\n");
      out.write("\n");
      out.write("        <link rel=\"shortcut icon\" href=\"images/favicon.gif\" type=\"image/x-icon\"/>\n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"css/styles.css\"/>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <!--start container-->\n");
      out.write("        <div id=\"container\">\n");
      out.write("\n");
      out.write("            <!--start header-->\n");
      out.write("            <header>\n");
      out.write("\n");
      out.write("                <!--start logo-->\n");
      out.write("                <a href=\"#\" id=\"logo\"><img src=\"images/logo.png\" width=\"221\" height=\"84\" alt=\"logo\"/></a>\n");
      out.write("                <!--end logo-->\n");
      out.write("\n");
      out.write("                <!--start menu-->\n");
      out.write("                <nav>\n");
      out.write("                    <ul>\n");
      out.write("                        <li><a href=\"#\" class=\"current\">Inicio</a></li>\n");
      out.write("                        <li><a href=\"#\">Acerca de</a></li>\n");
      out.write("                        <li><a href=\"#\">Contacto</a></li>\n");
      out.write("                    </ul>\n");
      out.write("                </nav>\n");
      out.write("                <!--end menu-->\n");
      out.write("            </header>\n");
      out.write("            <!--end header-->\n");
      out.write("\n");
      out.write("            <!--start intro-->\n");
      out.write("            <div id=\"intro\">\n");
      out.write("                <img src=\"images/banner.png\" alt=\"baner\"/>\n");
      out.write("            </div>\n");
      out.write("            <section class=\"group_bannner_left\">\n");
      out.write("                <hgroup>\n");
      out.write("                    <h1>We serve fresh ideas</h1>\n");
      out.write("                    <h2>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec molestie. Sed aliquam sem ut arcu. Phasellus sollicitudin.</h2>\n");
      out.write("                </hgroup>\n");
      out.write("                <div class=\"button black\"><a href=\"#\">Read more about our fresh ideas</a></div>\n");
      out.write("            </section>\n");
      out.write("            <!--end intro-->\n");
      out.write("\n");
      out.write("            <!--start holder-->\n");
      out.write("            <div class=\"holder_content\">\n");
      out.write("                <section class=\"group1\">\n");
      out.write("                    <h3>Caracter&iacute;sticas</h3>\n");
      out.write("                    <p>Taxi Tracking es una aplicaci&oacute;n cuyo objetivo es el de proveer una herramienta, a trav&eacute;s de dispositivos móviles, a los \n");
      out.write("                        usuarios que realizan viajes en el sistema de transporte tipo taxi, para monitorear su viaje y proporcionar una opción de ayuda en caso\n");
      out.write("                        de ser necesario.</p>\n");
      out.write("                    <p>A continuación se enlistan las caracter&iacute;sticas que permiten a Taxi Tracking llevar a cabo su prop&oacute;sito.</p>\n");
      out.write("\n");
      out.write("                    <article class=\"holder_gallery\">\n");
      out.write("                        <a class=\"photo_hover\" href=\"#\"><img src=\"images/picture2.jpg\" width=\"150\" height=\"115\" alt=\"picture1\"/></a>\n");
      out.write("                        <h2>Seguimiento</h2>\n");
      out.write("                        <p>Taxi Tracking da seguimiento al viaje del usuario mediante la tecnología GPS y API's de Google.</p>\n");
      out.write("                        <span class=\"readmore\"><a href=\"#\">Ver im&aacute;genes...</a></span>\n");
      out.write("                    </article>\n");
      out.write("\n");
      out.write("                    <article class=\"holder_gallery\">\n");
      out.write("                        <a class=\"photo_hover\" href=\"#\"><img src=\"images/picture4.jpg\" width=\"150\" height=\"115\" alt=\"picture1\"/></a>\n");
      out.write("                        <h2>Env&iacute;o de alertas</h2>\n");
      out.write("                        <p>Taxi Tracking realiza denuncias en l&iacute;nea en la red social <a href=\"https://twitter.com/CASPOLICIA_CDMX\">\n");
      out.write("                                <i>Twitter</i></a> de el <a href=\"http://www.ssp.df.gob.mx/Emergencias/Pages/CAS.aspx\">Centro de Atenci&oacute;n del Secretario de \n");
      out.write("                                Seguridad P&uacute;blica del D.F.</a> El cual promete una respuesta de patrullas\to elementos en un m&aacute;ximo de 10 minutos.</p>\n");
      out.write("                        <span class=\"readmore\"><a href=\"#\">Ver im&aacute;genes...</a></span>\n");
      out.write("                    </article>\n");
      out.write("\n");
      out.write("                    <article class=\"holder_gallery\">\n");
      out.write("                        <a class=\"photo_hover\" href=\"#\"><img src=\"images/picture5.jpg\" width=\"150\" height=\"115\" alt=\"picture1\"/></a>\n");
      out.write("                        <h2>Sugerencia de rutas</h2>\n");
      out.write("                        <p>Para un servicio m&aacute;s completo, Taxi Tracking puede sugerir una ruta para el viaje del usuario.</p>\n");
      out.write("                        <span class=\"readmore\"><a href=\"#\">Ver im&aacute;genes...</a></span>\n");
      out.write("                    </article>\n");
      out.write("\n");
      out.write("                    <article class=\"holder_gallery\">\n");
      out.write("                        <a class=\"photo_hover\" href=\"#\"><img src=\"images/picture5.jpg\" width=\"150\" height=\"115\" alt=\"picture1\"/></a>\n");
      out.write("                        <h2>Estimaci&oacute;n de costo y distancia</h2>\n");
      out.write("                        <p>Con la finalidad de evitar los abusos en el cobro del viaje, Taxi Tracking cuenta con un tax&iacute;metro virtual que te da un\n");
      out.write("                            estimado del costo de tu viaje.</p>\n");
      out.write("                        <span class=\"readmore\"><a href=\"#\">Ver im&aacute;genes...</a></span>\n");
      out.write("                    </article>\n");
      out.write("\n");
      out.write("                    <article class=\"holder_gallery\">\n");
      out.write("                        <a class=\"photo_hover\" href=\"#\"><img src=\"images/picture5.jpg\" width=\"150\" height=\"115\" alt=\"picture1\"/></a>\n");
      out.write("                        <h2>Identificaci&oacute;n de taxis por QR</h2>\n");
      out.write("                        <p>Con la intenci&oacute;n de mantener un control de los taxis leg&iacute;timos y evitar los taxis piratas,\n");
      out.write("                            Taxi Tracking genera un c&oacute;digo QR &uacute;nico para cada unidad.</p>\n");
      out.write("                        <span class=\"readmore\"><a href=\"#\">Ver im&aacute;genes...</a></span>\n");
      out.write("                    </article>\n");
      out.write("\n");
      out.write("                    <article class=\"holder_gallery\">\n");
      out.write("                        <a class=\"photo_hover\" href=\"#\"><img src=\"images/picture5.jpg\" width=\"150\" height=\"115\" alt=\"picture1\"/></a>\n");
      out.write("                        <h2>Ranking de taxis</h2>\n");
      out.write("                        <p>Para una informaci&oacute;n m&aacute;s espec&iacute;fica acerca de la unidad, Taxi Tracking permite a los usuarios evaluar\n");
      out.write("                            al taxi y con base en esto se realiza un promedio del servicio brindado.</p>\n");
      out.write("                        <span class=\"readmore\"><a href=\"#\">Ver im&aacute;genes...</a></span>\n");
      out.write("                    </article>\n");
      out.write("                </section>\n");
      out.write("\n");
      out.write("                <aside class=\"group2\">\n");
      out.write("                    <p id=\"login\">Login</p>\n");
      out.write("                    <form action=\"");
      out.print(request.getContextPath());
      out.write("/Usuario_Negocio?q=1\" method=\"post\">\n");
      out.write("                        <div id=\"upError\" class=\"error\" ");
if(request.getParameter("error")!=null){out.print("style='display:block;'");}
      out.write("></div>\n");
      out.write("                        <div class=\"form-field\">\n");
      out.write("                            <label for=\"TBUsuario\">Usuario</label><input type=\"text\" name=\"TBUsuario\" id=\"TBUsuario\" placeholder=\"Usuario\" />\n");
      out.write("                        </div>\n");
      out.write("                        <div id=\"downError\" class=\"error\"></div>\n");
      out.write("                        <div class=\"form-field\">\n");
      out.write("                            <label for=\"TBContrasena\">Contrase&ntilde;a</label><input type=\"password\" name=\"TBContrasena\" id=\"TBContrasena\" placeholder=\"Contraseña\"/>\n");
      out.write("                        </div>\n");
      out.write("                        <div class=\"form-field\">\n");
      out.write("                            <input type=\"submit\" name=\"BTEnviar\" id=\"BTEnviar\" value=\"Acceder\" />\n");
      out.write("                        </div>\n");
      out.write("                    </form>\n");
      out.write("\n");
      out.write("                    <section>\n");
      out.write("                        <h3>Noticias relacionadas</h3>\n");
      out.write("                        <article class=\"holder_news\">\n");
      out.write("                            <span class=\"news_span\">02.03.2011</span>\n");
      out.write("                            <h4>Crecen 139% asaltos en taxis en cinco a&ntilde;os</h4>\n");
      out.write("                            <p>Durante 20 horas Karla experiment&oacute; el terror a bordo de un taxi en el centro de la ciudad de M&eacute;xico.\n");
      out.write("                                <span class=\"readmore\"><a href=\"http://www.eluniversal.com.mx/ciudad/105375.html\">Leer m&aacute;s...</a></span></p>\n");
      out.write("                        </article>\n");
      out.write("\n");
      out.write("                        <article class=\"holder_news\">\n");
      out.write("                            <span class=\"news_span\">19.02.2011</span>\n");
      out.write("                            <h4>Taxistas prevendr&aacute;n delitos</h4>\n");
      out.write("                            <p>La Procuradur&iacute;a General de Justicia del Distrito Federal (PGJDF), la Secretar&iacute;a de Transportes y Vialidad\n");
      out.write("                                (SETRAVI), as&iacute; como 17 organizaciones de taxistas firmaron un convenio para una red de protecci&oacute;n para prevenir\n");
      out.write("                                los delitos en taxis.\n");
      out.write("                                <span class=\"readmore\"><a href=\"http://www.eluniversal.com.mx/ciudad/105264.html\">Leer m&aacute;s...</a></span></p>\n");
      out.write("                        </article>\n");
      out.write("                        <a class=\"photo_hover\" href=\"#\"><img src=\"images/picture3.jpg\" width=\"257\" height=\"295\" alt=\"picture\"/></a>\n");
      out.write("                    </section>\n");
      out.write("                </aside>\n");
      out.write("            </div>\n");
      out.write("            <!--end holder-->\n");
      out.write("        </div>\n");
      out.write("        <!--end container-->\n");
      out.write("\n");
      out.write("        <!--start footer-->\n");
      out.write("        <footer>\n");
      out.write("            <div class=\"container\">\n");
      out.write("                <div id=\"FooterLeft\">Desarrollado por Carlos Jim&eacute;nez y Francisco Negrete / 2014</div>\n");
      out.write("                <div id=\"FooterRight\">© TT2013-A022</div>\n");
      out.write("            </div>\n");
      out.write("        </footer>\n");
      out.write("        <!--end footer-->\n");
      out.write("        <!-- Free template distributed by http://freehtml5templates.com -->\n");
      out.write("    </body>\n");
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
