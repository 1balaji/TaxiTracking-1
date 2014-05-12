<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Beans.Usuario;"%>
<%
    String usuario = null;
    int rol = -1;
    Usuario objUsuario = new Usuario();
    
    //Checamos que exista la sesion
    if(session != null && session.getAttribute("rol") != null)
    {
        rol = (Integer)session.getAttribute("rol");

        //Si no es administrador se redirecciona al inicio de un usuario normal
        if(rol!=1){response.sendRedirect("bienvenido.jsp");}

        usuario = (String)session.getAttribute("nombre_usuario");
        
        if(session.getAttribute("objUsuario") != null)
        {
            objUsuario = (Usuario)session.getAttribute("objUsuario");
            
            //Quitamos el usuario de la sesion
            session.removeAttribute("objUsuario");
        }
        else
            response.sendRedirect("bienvenidoAdministrador.jsp");
    }
    else
        response.sendRedirect("index.jsp");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Taxi Tracking - Gesti&oacute;n de Usuarios</title>
        <link rel="icon" href="images/favicon.gif" type="image/x-icon"/>

        <!--[if lt IE 9]>
        <script src="js/html5shiv/dist/html5shiv.js"></script>
        <![endif]-->

        <link rel="shortcut icon" href="images/favicon.gif" type="image/x-icon"/>
        <link rel="stylesheet" type="text/css" href="css/styles.css"/>
    </head>
    <body>
        <!--start container-->
        <div id="container">

            <!--start header-->
            <header>

                <!--start logo-->
                <a href="bienvenidoAdministrador.jsp" id="logo"><img src="images/logo.png" width="221" height="84" alt="logo"/></a>
                <!--end logo-->

                <!--start menu-->
                <nav>
                    <ul>
                        <li><a href="bienvenidoAdministrador.jsp">Inicio</a></li>
                        <li><a href="gestionTaxis.jsp">Gesti&oacute;n de Taxis</a></li>
                        <li><a href="gestionUsuarios.jsp" class="current">Gesti&oacute;n de Usuarios</a></li>
                        <li>
                            <div id="dd" class="wrapper-dropdown-5"><%=usuario%>
                                <ul class="dropdown">
                                    <li><a href="verPerfil.jsp"><i class="fa fa-user"></i>Perfil</a></li>
                                    <li><a href="configurarPerfil.jsp"><i class="fa fa-cog"></i>Configuraci&oacute;n</a></li>
                                    <li><a href="<%=request.getContextPath()%>/ManejoSesion?q=3"><i class="fa fa-sign-out"></i>Log out</a></li>
                                </ul>
                            </div>
                        </li>
                    </ul>
                </nav>
                <!--end menu-->
            </header>
            <!--end header-->

            <!--start intro-->
            <div id="intro">
                <object classid=clsid:D27CDB6E-AE6D-11cf-96B8-444553540000 codebase=http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,2,0 width="600" height="300">
                    <param name=movie value=triplechar.swf>
                    <param name=quality value=high>
                    <param name="wmode" value="transparent">

                    <embed src=triplechar.swf
                           quality=high
                           pluginspage=http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash type=application/x-shockwave-flash
                           width=950
                           height=300
                           bgcolor=#FFFFFF
                           scale=showall
                           menu="false"
                           wmode="transparent">
                    </embed>
                </object>
            </div>
            <section class="group_bannner_left">
                <h1>B&uacute;squeda de Usuario</h1>
            </section>
            <!--end intro-->

            <!--start holder-->
            <div class="holder_content">
                <section class="gestion">
                    <div class="search">
                        <form action="<%=request.getContextPath()%>/Usuario_Negocio?q=1" method="POST">
                            <input class="busqueda" type="text" name="TBBuscarUsuario" id="TBBuscarUsuario" placeholder="Usuario" />
                            <button type="submit" id="BTBusquedaUsuario" name="BTBusquedaUsuario">
                                <i class="fa fa-search fa-fw"></i>Buscar
                            </button>
                        </form>
                    </div>
                    <div class="contenedorTabla centrado" id="contenedorTabla">
                        <table id="tabla">
                            <tr>
                                <th>Usuario</th>
                                <th class="ajustado">Nombre</th>
                                <th class="ajustado">Correo Electr&oacute;nico</th>
                                <th colspan="2">Control</th>
                            </tr>
                            <tr>
                                <%if(objUsuario.getNombreUsuario().compareTo("") == 0)
                                {%>
                                    <td colspan=4>No hay resultados</td>
                               <%}
                               else
                                {%>
                                    <td><%=objUsuario.getNombreUsuario()%></td>
                                    <td><%=objUsuario.getNombre()%> <%=objUsuario.getApellidoPaterno()%> <%=objUsuario.getApellidoMaterno()%></td>
                                    <td><%=objUsuario.getEmail()%></td>
                                    <td>
                                        <%if(objUsuario.getStatus() == 0)   //Si esta bloqueado
                                        {
                                            out.println("<button class='desbloquear' id='BTDesbloquearUsuario' name='BTDesbloquearUsuario' onClick='gestionar(\"" + objUsuario.getNombreUsuario() + "\",3)'><i class=\"fa fa-unlock fa-fw\"></i>Desbloquear</button>\n");
                                        }
                                        else
                                        {
                                            out.println("<button class='bloquear' id='BTBloquearUsuario' name='BTBloquearUsuario' onClick='gestionar(\"" + objUsuario.getNombreUsuario() + "\",2)'><i class=\"fa fa-lock fa-fw\"></i>Bloquear</button>\n");
                                        }%>
                                    </td>
                                    <td>
                                        <button class='eliminar' id='BTEliminarUsuario' name='BTEliminarUsuario' onClick="gestionar('<%=objUsuario.getNombreUsuario()%>',4)"><i class="fa fa-times fa-fw"></i>Eliminar</button>
                                    </td>
                                <%}%>
                            </tr>
                        </table>
                    </div>
                </section>
            </div>
            <!--end holder-->
        </div>
        <!--end container-->

        <!--start footer-->
        <footer>
            <div class="container">
                <div id="FooterLeft">Desarrollado por Carlos Jim&eacute;nez y Francisco Negrete / 2014</div>
                <div id="FooterRight">© TT2013-A022</div>
            </div>
        </footer>
        <!--end footer-->
        <!-- Free template distributed by http://freehtml5templates.com -->
        <script type="text/javascript" src="js/jquery-1.11.0.min.js"></script>
        <script type="text/javascript" src="js/toggleMenu.js"></script>
        <script type="text/javascript" src="js/buscarUsuario.js"></script>
    </body>
</html>