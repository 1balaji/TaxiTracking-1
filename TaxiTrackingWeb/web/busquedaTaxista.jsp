<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Beans.Taxi;"%>
<%
    String usuario = null;
    int rol = -1;
    Taxi objTaxi = new Taxi();
    
    //Checamos que exista la sesion
    if(session != null && session.getAttribute("rol") != null)
    {
        rol = (Integer)session.getAttribute("rol");

        //Si no es administrador se redirecciona al inicio de un usuario normal
        if(rol!=1){response.sendRedirect("bienvenido.jsp");}

        usuario = (String)session.getAttribute("nombre_usuario");
        
        if(session.getAttribute("objTaxi") != null)
        {
            objTaxi = (Taxi)session.getAttribute("objTaxi");
            
            //Quitamos el usuario de la sesion
            session.removeAttribute("objTaxi");
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
        <title>Taxi Tracking - Gesti&oacute;n de Taxis</title>
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
                <a href="#" id="logo"><img src="images/logo.png" width="221" height="84" alt="logo"/></a>
                <!--end logo-->

                <!--start menu-->
                <nav>
                    <ul>
                        <li><a href="bienvenidoAdministrador.jsp">Inicio</a></li>
                        <li><a href="gestionTaxis.jsp" class="current">Gesti&oacute;n de Taxis</a></li>
                        <li><a href="gestionUsuarios.jsp">Gesti&oacute;n de Usuarios</a></li>
                        <li>
                            <div id="dd" class="wrapper-dropdown-5"><%=usuario%>
                                <ul class="dropdown">
                                    <li><a href="#"><i class="fa fa-user"></i>Perfil</a></li>
                                    <li><a href="#"><i class="fa fa-cog"></i>Configuraci&oacute;n</a></li>
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
                <img src="images/banner.png" alt="baner"/>
            </div>
            <section class="group_bannner_left">
                <h1>B&uacute;squeda de Taxi</h1>
            </section>
            <!--end intro-->

            <!--start holder-->
            <div class="holder_content">
                <section class="gestion">
                    <div class="search">
                        <form action="<%=request.getContextPath()%>/Taxi_Negocio?q=1" method="POST">
                            <input type="text" name="TBBuscarUsuario" id="TBBuscarUsuario" placeholder="Usuario" />
                            <button type="submit" id="BTBusquedaUsuario" name="BTBusquedaUsuario">
                                <i class="fa fa-search fa-fw"></i>Buscar
                            </button>
                        </form>
                    </div>
                    <div class="contenedorTabla centrado">
                        <table id="tabla">
                            <tr>
                                <th>Usuario</th>
                                <th class="ajustado">Nombre</th>
                                <th class="ajustado">Correo Electr&oacute;nico</th>
                                <th colspan="2">Control</th>
                            </tr>
                            <tr>
                                <%if(objTaxi.getRFC().compareTo("") == 0)
                                {%>
                                    <td colspan=4>No hay resultados</td>
                               <%}
                               else
                                {%>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td>
                                        <%if(objTaxi.getStatus() == 0)   //Si esta bloqueado
                                        {
                                            out.println("<button id='BTDesbloquearUsuario' name='BTDesbloquearUsuario' onClick='gestionar(\"" + objTaxi.getRFC()+ "\",3)'><i class=\"fa fa-unlock fa-fw\"></i>Desbloquear</button>\n");
                                        }
                                        else
                                        {
                                            out.println("<button id='BTBloquearUsuario' name='BTBloquearUsuario' onClick='gestionar(\"" + objTaxi.getRFC() + "\",2)'><i class=\"fa fa-lock fa-fw\"></i>Bloquear</button>\n");
                                        }%>
                                    </td>
                                    <td>
                                        <button id='BTEliminarTaxi' name='BTEliminarUsuario' onClick="gestionar('<%=objTaxi.getRFC()%>',4)"><i class="fa fa-times fa-fw"></i>Eliminar</button>
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