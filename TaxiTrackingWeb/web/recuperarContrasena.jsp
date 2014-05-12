<%
    int rol = -1;
    
    //Checamos si existe una sesion
    if(session != null && session.getAttribute("rol") != null)
    {
        rol = (Integer)session.getAttribute("rol");

        //Si no es administrador se redirecciona al inicio de un usuario normal
        if(rol == 0){response.sendRedirect("bienvenido.jsp");}

        //Si es administrador se redirecciona al inicio del administrador
        if(rol == 1){response.sendRedirect("bienvenidoAdministrador.jsp");}
    }
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Taxi Tracking - Recuperar Contrase&ntilde;a</title>
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
                <h1>Recuperar Contrase&ntilde;a</h1>
            </section>
            <!--end intro-->

            <!--start holder-->
            <div class="holder_content contenedorTabla centrado">
                <%if(session.getAttribute("errorRecuperarContrasena") != null) 
                {
                    out.print("<div class='error centrado'>\n" + (String) session.getAttribute("errorRecuperarContrasena") + "\n</div>"); 
                    session.removeAttribute("errorRecuperarContrasena");
                }
                else
                    out.print("Ingresa tu direcci&oacute;n de email para obtener una nueva contrase&ntilde;a"); 
                %>
                <form action="<%=request.getContextPath()%>/ManejoSesion?q=4" method="POST">
                    <div class="input-group">
                            <label class="input-group-label mediano" for="TBContrasena">Email</label>
                            <input type="email" name="TBEmail" id="TBEmail" class="form-control largo" placeholder="ejemplo@dominio.mx" required="required"/>
                        </div>
                    <div class="centrado">
                        <button class="aceptar" type="submit" name="BTEnviar" id="BTEnviar">
                            <i class="fa fa-sign-in fa-fw"></i>Enviar
                        </button>
                    </div>
                </form>
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