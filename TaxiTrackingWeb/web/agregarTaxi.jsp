<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
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
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Taxi Tracking - Agregar Taxi</title>
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
                <h1>Agregar Taxi</h1>
            </section>
            <!--end intro-->

            <!--start holder-->
            <div class="holder_content">
                <section class="gestion">
                    <form action="<%=request.getContextPath()%>/Taxi_Negocio?q=2" method="POST">
                        <label for="TBNombre">Nombre</label><input type="text" id="TBNombre" name="TBNombre" placeholder="Nombre" /><br/>
                        <label for="TBApellidoPaterno">Apellido Paterno</label><input type="text" id="TBApellidoPaterno" name="TBApellidoPaterno" placeholder="Apellido Paterno" /><br/>
                        <label for="TBApellidoMaterno">Apellido Materno</label><input type="text" id="TBApellidoMaterno" name="TBApellidoMaterno" placeholder="Apellido Materno" /><br/>
                        <label for="TBRFC">RFC</label><input type="text" id="TBRFC" name="TBRFC" placeholder="RFC" /><br/>
                        <label for="TBMatricula">Matricula</label><input type="text" id="TBMatricula" name="TBMatricula" placeholder="Matricula" /><br/>
                        <label for="TBVigencia">Vigencia</label><input type="date" id="TBVigencia" name="TBVigencia" /><br/>
                        <label for="TBFolio">Folio</label><input type="text" id="TBFolio" name="TBFolio" placeholder="Folio" /><br/>
                        <button type="submit" id="BTAgregarTaxi" name="BTAgregarTaxi">
                                <i class="fa fa-plus-circle fa-fw"></i>Agregar
                        </button>
                    </form>
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
    </body>
</html>