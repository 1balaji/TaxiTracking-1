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
                <a href="bienvenidoAdministrador.jsp" id="logo"><img src="images/logo.png" width="221" height="84" alt="logo"/></a>
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
                <img src="images/banner.png" alt="baner"/>
            </div>
            <section class="group_bannner_left">
                <h1>Gesti&oacute;n de Taxis</h1>
            </section>
            <!--end intro-->

            <!--start holder-->
            <div class="holder_content">
                <section class="gestion">
                    <div class="agregar">
                        <button id="agregarTaxiToggle">
                            <i class="fa fa-plus-circle fa-fw"></i>Agregar Taxi
                        </button>
                    </div>
                    <div class="search">
                        <form action="<%=request.getContextPath()%>/Taxi_Negocio?q=1" method="POST">
                            <input type="text" name="TBBuscarTaxi" id="TBBuscarTaxi" placeholder="Buscar" />
                            <button type="submit" id="BTBusquedaTaxi" name="BTBusquedaTaxi">
                                <i class="fa fa-search fa-fw"></i>Buscar
                            </button>
                        </form>
                    </div>
                    <div id="agregarTaxi" class="centrado">
                        <form action="<%=request.getContextPath()%>/Taxi_Negocio?q=2" method="POST">
                            <%if(session.getAttribute("errorNombre") != null) {out.print("<div class='error centrado'>\n" + (String) session.getAttribute("errorNombre") + "\n</div>"); session.removeAttribute("errorNombre");}%>
                            <div class="input-group">
                                <label class="input-group-label mediano centrado" for="TBNombre">Nombre</label>
                                <input type="text" id="TBNombre" name="TBNombre" class="form-control largo" placeholder="Nombre" required="required" maxlength="30" />
                            </div>
                            <%if(session.getAttribute("errorApellidoPaterno") != null) {out.print("<div class='error centrado'>\n" + (String) session.getAttribute("errorApellidoPaterno") + "\n</div>"); session.removeAttribute("errorApellidoPaterno");}%>
                            <div class="input-group">
                                <label class="input-group-label mediano centrado" for="TBApellidoPaterno">Apellido Paterno</label>
                                <input type="text" id="TBApellidoPaterno" name="TBApellidoPaterno" class="form-control largo" placeholder="Apellido Paterno" required="required" maxlength="30" />
                            </div>  
                            <%if(session.getAttribute("errorApellidoMaterno") != null) {out.print("<div class='error centrado'>\n" + (String) session.getAttribute("errorApellidoMaterno") + "\n</div>");} session.removeAttribute("errorApellidoMaterno");%>
                            <div class="input-group">
                                <label class="input-group-label mediano centrado" for="TBApellidoMaterno">Apellido Materno</label>
                                <input type="text" id="TBApellidoMaterno" name="TBApellidoMaterno" class="form-control largo" placeholder="Apellido Materno" required="required" maxlength="30" />
                            </div>
                            <%if(session.getAttribute("errorIdTaxista") != null) {out.print("<div class='error centrado'>\n" + (String) session.getAttribute("errorIdTaxista") + "\n</div>");} session.removeAttribute("errorIdTaxista");%>
                            <div class="input-group">
                                <label class="input-group-label mediano centrado" for="TBIdTaxista">Clave Operador</label>
                                <input type="text" id="TBIdTaxista" name="TBIdTaxista" class="form-control largo" placeholder="Clave Operador" required="required" maxlength="14" />
                            </div>
                            <%if(session.getAttribute("errorCurp") != null) {out.print("<div class='error centrado'>\n" + (String) session.getAttribute("errorCurp") + "\n</div>");} session.removeAttribute("errorCurp");%>
                            <div class="input-group">
                                <label class="input-group-label mediano centrado" for="TBCURP">CURP</label>
                                <input type="text" id="TBCURP" name="TBCURP" class="form-control largo" placeholder="RFC" required="required" maxlength="18" />
                            </div>
                            <%if(session.getAttribute("errorMatricula") != null) {out.print("<div class='error centrado'>\n" + (String) session.getAttribute("errorMatricula") + "\n</div>");} session.removeAttribute("errorMatricula");%>
                            <div class="input-group">
                                <label class="input-group-label mediano centrado" for="TBMatricula">Matricula</label>
                                <input type="text" id="TBMatricula" name="TBMatricula" class="form-control largo" placeholder="X-XX-XXX" required="required" maxlength="8" />
                            </div>
                            <%if(session.getAttribute("errorFolio") != null) {out.print("<div class='error centrado'>\n" + (String) session.getAttribute("errorFolio") + "\n</div>");} session.removeAttribute("errorFolio");%>
                            <div class="input-group">
                                <label class="input-group-label mediano centrado" for="TBFolio">Folio</label>
                                <input type="text" id="TBFolio" name="TBFolio" class="form-control largo" placeholder="Folio" required="required" maxlength="10" />
                            </div>
                            <%if(session.getAttribute("errorNumeroLicencia") != null) {out.print("<div class='error centrado'>\n" + (String) session.getAttribute("errorNumeroLicencia") + "\n</div>");} session.removeAttribute("errorNumeroLicencia");%>
                            <div class="input-group">
                                <label class="input-group-label mediano centrado" for="TBNumeroLicencia">No. Licencia</label>
                                <input type="text" id="TBNumeroLicencia" name="TBNumeroLicencia" class="form-control largo" placeholder="No. Licencia" required="required" maxlength="10" />
                            </div>
                            <%if(session.getAttribute("errorVigencia") != null) {out.print("<div class='error centrado'>\n" + (String) session.getAttribute("errorVigencia") + "\n</div>");} session.removeAttribute("errorVigencia");%>
                            <div class="input-group">
                                <label class="input-group-label mediano centrado" for="TBVigencia">Vigencia</label>
                                <input type="text" id="TBVigencia" name="TBVigencia" class="form-control largo" placeholder="AAAA/MM/DD" required="required" maxlength="10" />
                            </div>
                            <%if(session.getAttribute("errorFechaExpedicion") != null) {out.print("<div class='error centrado'>\n" + (String) session.getAttribute("errorFechaExpedicion") + "\n</div>");} session.removeAttribute("errorFechaExpedicion");%>
                            <div class="input-group">
                                <label class="input-group-label mediano centrado" for="TBFechaExpedicion">Fecha Expedici&oacute;n</label>
                                <input type="text" id="TBFechaExpedicion" name="TBFechaExpedicion" class="form-control largo" placeholder="AAAA/MM/DD" required="required" maxlength="10" />
                            </div>
                            <%if(session.getAttribute("errorHoraExpedicion") != null) {out.print("<div class='error centrado'>\n" + (String) session.getAttribute("errorHoraExpedicion") + "\n</div>");} session.removeAttribute("errorHoraExpedicion");%>
                            <div class="input-group">
                                <label class="input-group-label mediano centrado" for="TBHoraExpedicion">Hora Expedici&oacute;n</label>
                                <input type="text" id="TBHoraExpedicion" name="TBHoraExpedicion" class="form-control largo" placeholder="HH:MM:SS" required="required" maxlength="8" />
                            </div>
                            <%if(session.getAttribute("errorTipo") != null) {out.print("<div class='error centrado'>\n" + (String) session.getAttribute("errorTipo") + "\n</div>");} session.removeAttribute("errorTipo");%>
                            <div class="input-group">
                                <label class="input-group-label mediano centrado" for="TBTipo">Tipo de Taxi</label>
                                <select id="TBTipo" name="TBTipo" class="form-control select" required="required">
                                    <option value="">Seleccionar</option>
                                    <option value="0">Taxi Normal</option>
                                    <option value="1">Radio Taxi</option>
                                    <option value="2">Taxi de Sitio</option>
                                </select>
                            </div>
                            <div class="centrado">
                                <button type="submit" id="BTAgregarTaxi" name="BTAgregarTaxi">
                                    <i class="fa fa-plus-circle fa-fw"></i>Agregar
                                </button>
                            </div>
                        </form>
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
        <script type="text/javascript" src="js/gestionTaxis.js"></script>
    </body>
</html>