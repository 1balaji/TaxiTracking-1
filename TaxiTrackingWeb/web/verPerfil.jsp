<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Beans.Usuario;"%>
<%
    String usuario = null;
    int rol = -1;
    
    //Checamos que exista la sesion
    if(session != null && session.getAttribute("rol") != null)
    {
        rol = (Integer)session.getAttribute("rol");

        usuario = (String)session.getAttribute("nombre_usuario");
    }
    else
        response.sendRedirect("index.jsp");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Taxi Tracking - Perfil</title>
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
                <a href = "
                <%if(rol == 1)  //Si es administrador
                    out.println("bienvenidoAdministrador.jsp ");
                else
                    out.println("bienvenido.jsp ");
                %>
                " id="logo"><img src="images/logo.png" width="221" height="84" alt="logo"/></a>
                <!--end logo-->

                <!--start menu-->
                <nav>
                    <ul>
                        <%if(rol == 1)  //Si es administrador
                        {%>
                            <li><a href="bienvenidoAdministrador.jsp" class="current">Inicio</a></li>
                            <li><a href="gestionTaxis.jsp">Gesti&oacute;n de Taxis</a></li>
                            <li><a href="gestionUsuarios.jsp">Gesti&oacute;n de Usuarios</a></li>
                        <%}
                        else
                        {%>
                            <li><a href="bienvenido.jsp" class="current">Inicio</a></li>
                            <li><a href="peticiones.jsp">Peticiones</a></li>
                        <%}%>
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
                <h1>Ver Perfil</h1>
            </section>
            <!--end intro-->

            <!--start holder-->
            <div class="holder_content">
                <div class="contenedorTabla centrado" id="contenedorTabla">
                    <i class="fa fa-refresh fa-spin fa-4x"></i>
                </div>
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
        <script type="text/javascript" src="js/verPerfil.js"></script>
    </body>
</html>