<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String usuario = null;
    
    //Checamos que exista la sesion
    if(session != null && session.getAttribute("nombre_usuario") != null)
    {
        usuario = (String)session.getAttribute("nombre_usuario");
        session.removeAttribute("nombre_usuario");
    }
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Taxi Tracking - Confirmacion de Cuenta</title>
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
                        <li><a href="index.jsp" class="current">Inicio</a></li>
                        <li><a href="acercaDe.jsp">Acerca de</a></li>
                        <li><a href="contacto.jsp">Contacto</a></li>
                    </ul>
                </nav>
                <!--end menu-->
            </header>
            <!--end header-->

            <!--start intro-->
            <div id="intro">
                <img src="images/banner.png" alt="baner"/>
            </div>
            <section class="group_bannner_left">
                <hgroup>
                    <h1>Confirmaci&oacute;n exitosa</h1>
                    <h2 class="index">Bienvenido <%if(usuario != null)out.println(usuario);%> tu cuenta ha sido activada correctamente.</h2>
                </hgroup>
            </section>
            <!--end intro-->            
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