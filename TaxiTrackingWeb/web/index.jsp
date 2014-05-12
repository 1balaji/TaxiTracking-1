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
        <title>Taxi Tracking - Inicio</title>
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
                <hgroup>
                    <h1>Taxi Tracking</h1>
                    <h2 class="index">TT2013-A022</h2>
                </hgroup>
            </section>
            <!--end intro-->

            <!--start holder-->
            <div class="holder_content">
                <section class="group1">
                    <h3 class="left">Caracter&iacute;sticas</h3>
                    <p>Taxi Tracking es una aplicaci&oacute;n cuyo objetivo es el de proveer una herramienta, a trav&eacute;s de dispositivos m&oacute;viles, a los 
                        usuarios que realizan viajes en el sistema de transporte tipo taxi, para monitorear su viaje y proporcionar una opci&oacute;n de ayuda en caso
                        de ser necesario.</p>
                    <p>A continuaci&oacute;n se enlistan las caracter&iacute;sticas que permiten a Taxi Tracking llevar a cabo su prop&oacute;sito.</p>

                    <article class="holder_gallery">
                        <a class="photo_hover" href="images/seguimiento.png"><img src="images/seguimiento.png" width="150" height="200" alt="seguimiento"/></a>
                        <h2>Seguimiento</h2>
                        <p>Taxi Tracking da seguimiento al viaje del usuario mediante la tecnolog&iacute;a GPS y API's de Google.</p>
                    </article>

                    <article class="holder_gallery">
                        <a class="photo_hover" href="images/panico.png"><img src="images/panico.png" width="150" height="200" alt="panico"/></a>
                        <h2>Env&iacute;o de alertas</h2>
                        <p>Taxi Tracking realiza denuncias en l&iacute;nea en la red social <a href="https://twitter.com/CASPOLICIA_CDMX" target="_blank">
                                <i>Twitter</i></a> de el <a href="http://www.ssp.df.gob.mx/Emergencias/Pages/CAS.aspx" target="_blank">Centro de Atenci&oacute;n del Secretario de 
                                Seguridad P&uacute;blica del D.F.</a> El cual promete una respuesta de patrullas	o elementos en un m&aacute;ximo de 10 minutos.</p>
                    </article>

                    <article class="holder_gallery">
                        <a class="photo_hover" href="images/rutas.png"><img src="images/rutas.png" width="150" height="200" alt="sugerencia rutas"/></a>
                        <h2>Sugerencia de rutas</h2>
                        <p>Para un servicio m&aacute;s completo, Taxi Tracking puede sugerir una ruta para el viaje del usuario.</p>
                    </article>

                    <article class="holder_gallery">
                        <a class="photo_hover" href="images/costo.png"><img src="images/costo.png" width="150" height="200" alt="estimacion costo"/></a>
                        <h2>Estimaci&oacute;n de costo y distancia</h2>
                        <p>Con la finalidad de evitar los abusos en el cobro del viaje, Taxi Tracking cuenta con un tax&iacute;metro virtual que te da un
                            estimado del costo de tu viaje.</p>
                    </article>

                    <article class="holder_gallery">
                        <a class="photo_hover" href="images/codigoQR.png"><img src="images/codigoQR.png" width="150" height="200" alt="codigo QR"/></a>
                        <h2>Identificaci&oacute;n de taxis por QR</h2>
                        <p>Con la intenci&oacute;n de mantener un control de los taxis leg&iacute;timos y evitar los taxis piratas,
                            Taxi Tracking genera un c&oacute;digo QR &uacute;nico para cada unidad.</p>
                    </article>

                    <article class="holder_gallery">
                        <a class="photo_hover" href="images/ranking.png"><img src="images/ranking.png" width="150" height="200" alt="ranking"/></a>
                        <h2>Ranking de taxis</h2>
                        <p>Para una informaci&oacute;n m&aacute;s espec&iacute;fica acerca de la unidad, Taxi Tracking permite a los usuarios evaluar
                            al taxi y con base en esto se realiza un promedio del servicio brindado.</p>
                    </article>
                </section>

                <aside class="group2">
                    <p id="login">Login</p>
                    <form action="<%=request.getContextPath()%>/ManejoSesion?q=1" method="post">
                        <%if(session.getAttribute("error") != null)
                        {
                            String error = (String) session.getAttribute("error");
                            session.removeAttribute("error");
                            
                            //Usuario o contraseña incorrectos
                            if(error.equals("1"))
                                out.print("<div id='error' class='error centrado'>Error: usuario o contrase&ntilde;a incorrectos</div>");
                            //Cuenta bloqueada
                            else if(error.equals("2"))
                                out.print("<div id='error' class='error centrado'>Error: la cuenta no est&aacute; activada</div>");
                        }%>
                        <div class="input-group">
                                <label class="input-group-label" for="TBUsuario"><i class="fa fa-user fa-fw"></i></label>
                                <input type="text" name="TBUsuario" id="TBUsuario" class="form-control" placeholder="Usuario" required="required" />
                        </div>
                        <div class="input-group">
                            <label class="input-group-label" for="TBContrasena"><i class="fa fa-key fa-fw"></i></label>
                            <input type="password" name="TBContrasena" id="TBContrasena" class="form-control" placeholder="Contrase&ntilde;a" required="required"/>
                        </div>
                        <div class="centrado">
                            <button type="submit" name="BTEnviar" id="BTEnviar">
                                <i class="fa fa-sign-in fa-fw"></i>Acceder
                            </button>
                        </div>
                        <div class="centrado">
                            <a href="recuperarContrasena.jsp">Olvide mi contrase&ntilde;a?</a>
                        </div>
                    </form>

                    <section>
                        <h3>Noticias relacionadas</h3>
                        <article class="holder_news">
                            <span class="news_span">02.03.2011</span>
                            <h4>Crecen 139% asaltos en taxis en cinco a&ntilde;os</h4>
                            <p>Durante 20 horas Karla experiment&oacute; el terror a bordo de un taxi en el centro de la ciudad de M&eacute;xico.
                                <span class="readmore"><a href="http://www.eluniversal.com.mx/ciudad/105375.html">Leer m&aacute;s...</a></span></p>
                        </article>

                        <article class="holder_news">
                            <span class="news_span">19.02.2011</span>
                            <h4>Taxistas prevendr&aacute;n delitos</h4>
                            <p>La Procuradur&iacute;a General de Justicia del Distrito Federal (PGJDF), la Secretar&iacute;a de Transportes y Vialidad
                                (SETRAVI), as&iacute; como 17 organizaciones de taxistas firmaron un convenio para una red de protecci&oacute;n para prevenir
                                los delitos en taxis.
                                <span class="readmore"><a href="http://www.eluniversal.com.mx/ciudad/105264.html">Leer m&aacute;s...</a></span></p>
                        </article>
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