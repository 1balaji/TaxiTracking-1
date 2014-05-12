<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String usuario = null;
    
    //Checamos que exista la sesion
    if(session != null && session.getAttribute("rol") != null)
        usuario = (String)session.getAttribute("nombre_usuario");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Taxi Tracking - Acerca De</title>
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
                <a href="index.jsp" id="logo"><img src="images/logo.png" width="221" height="84" alt="logo"/></a>
                <!--end logo-->

                <!--start menu-->
                <nav>
                    <ul>
                        <li><a href="index.jsp">Inicio</a></li>
                        <li><a href="acercaDe.jsp" class="current">Acerca de</a></li>
                        <li><a href="contacto.jsp">Contacto</a></li>
                        <%if(usuario != null)
                        {%>
                            <li>
                                <div id="dd" class="wrapper-dropdown-5"><%=usuario%>
                                    <ul class="dropdown">
                                        <li><a href="verPerfil.jsp"><i class="fa fa-user"></i>Perfil</a></li>
                                        <li><a href="configurarPerfil.jsp"><i class="fa fa-cog"></i>Configuraci&oacute;n</a></li>
                                        <li><a href="<%=request.getContextPath()%>/ManejoSesion?q=3"><i class="fa fa-sign-out"></i>Log out</a></li>
                                    </ul>
                                </div>
                            </li>
                        <%}%>
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
                <h1>Acerca De</h1>
            </section>
            <!--end intro-->

            <!--start holder-->
            <div class="holder_content">
                <section class="group1">
                    <h3 class="left">Acerca de nosotros</h3>
                    <p>Somos estudiantes de la Escuela superior de C&oacute;mputo del Instituto Polit&eacute;cnico Nacional. Actualmente cursamos el 8 semestre de la carrera de Ingenier&iacute;a en 
                    Sistemas Computacionales. Nosotros somos:</p>
                    <hgroup>
                        <h5>Jim&eacute;nez L&oacute;pez Carlos Adrian</h5>
                        <h5>Negrete Ju&aacute;rez Francisco Javier</h5>
                    </hgroup>
                </section>
                <aside class="group2">
                    <section>
                        <a class="photo_hover" href="http://www.isc.escom.ipn.mx" target="_blank"><img src="images/logoescom.png" width="257" height="190" alt="ESCOM"/></a>
                    </section>
                </aside>
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
    </body>
</html>