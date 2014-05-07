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
            
            //Quitamos el taxi de la sesion
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
                <h1>B&uacute;squeda de Taxi</h1>
            </section>
            <!--end intro-->

            <!--start holder-->
            <div class="holder_content">
                <section class="gestion">
                    <div class="search">
                        <form action="<%=request.getContextPath()%>/Taxi_Negocio?q=1" method="POST">
                            <input type="text" name="TBBuscarTaxista" id="TBBuscarTaxista" placeholder="Buscar" />
                            <button type="submit" id="BTBusquedaTaxista" name="BTBusquedaTaxista">
                                <i class="fa fa-search fa-fw"></i>Buscar
                            </button>
                        </form>
                    </div>
                    <div class="contenedorTabla centrado">
                        <%if (objTaxi.getIdTaxista().compareTo("") == 0) {%>
                            <div>No hay resultados</div>
                        <%} 
                        else 
                        {%>
                            <form action="<%=request.getContextPath()%>/Taxi_Negocio" method="POST" id="formBusquedaTaxista">
                                <div class="input-group">
                                    <label class="input-group-label mediano centrado" for="TBNombre">Nombre</label>
                                    <input type="text" id="TBNombre" name="TBNombre" class="form-control largo" value="<%=objTaxi.getNombre()%>" readonly="readonly" />
                                </div>
                                <div class="input-group">
                                    <label class="input-group-label mediano centrado" for="TBApellidoPaterno">Apellido Paterno</label>
                                    <input type="text" id="TBApellidoPaterno" name="TBApellidoPaterno" class="form-control largo" value="<%=objTaxi.getApellidoPaterno()%>" readonly="readonly" />
                                </div>
                                <div class="input-group">
                                    <label class="input-group-label mediano centrado" for="TBApellidoMaterno">Apellido Materno</label>
                                    <input type="text" id="TBApellidoMaterno" name="TBApellidoMaterno" class="form-control largo" value="<%=objTaxi.getApellidoMaterno()%>" readonly="readonly" />
                                </div>
                                <div class="input-group">
                                    <label class="input-group-label mediano centrado" for="TBIdTaxista">Clave Operador</label>
                                    <input type="text" id="TBIdTaxista" name="TBIdTaxista" class="form-control largo" value="<%=objTaxi.getIdTaxista()%>" readonly="readonly" />
                                </div>
                                <div class="input-group">
                                    <label class="input-group-label mediano centrado" for="TBCURP">CURP</label>
                                    <input type="text" id="TBCURP" name="TBCURP" class="form-control largo" value="<%=objTaxi.getCURP()%>" readonly="readonly" />
                                </div>
                                <div class="input-group">
                                    <label class="input-group-label mediano centrado" for="TBMatricula">Matricula</label>
                                    <input type="text" id="TBMatricula" name="TBMatricula" class="form-control largo" value="<%=objTaxi.getMatricula()%>" readonly="readonly" />
                                </div>
                                <div class="input-group">
                                    <label class="input-group-label mediano centrado" for="TBFolio">Folio</label>
                                    <input type="text" id="TBFolio" name="TBFolio" class="form-control largo" value="<%=objTaxi.getFolio()%>" readonly="readonly" />
                                </div>
                                <div class="input-group">
                                    <label class="input-group-label mediano centrado" for="TBNumeroLicencia">No. Licencia</label>
                                    <input type="text" id="TBNumeroLicencia" name="TBNumeroLicencia" class="form-control largo" value="<%=objTaxi.getNumeroLicencia()%>" readonly="readonly" />
                                </div>
                                <div class="input-group">
                                    <label class="input-group-label mediano centrado" for="TBVigencia">Vigencia</label>
                                    <input type="text" id="TBVigencia" name="TBVigencia" class="form-control largo" value="<%=objTaxi.getVigencia()%>" readonly="readonly" />
                                </div>
                                <div class="input-group">
                                    <label class="input-group-label mediano centrado" for="TBFechaExpedicion">Fecha Expedici&oacute;n</label>
                                    <input type="text" id="TBFechaExpedicion" name="TBFechaExpedicion" class="form-control largo" value="<%=objTaxi.getFechaExpedicion()%>" readonly="readonly" />
                                </div>
                                <div class="input-group">
                                    <label class="input-group-label mediano centrado" for="TBHoraExpedicion">Hora Expedici&oacute;n</label>
                                    <input type="text" id="TBHoraExpedicion" name="TBHoraExpedicion" class="form-control largo" value="<%=objTaxi.getHoraExpedicion()%>" readonly="readonly" />
                                </div>
                                <div class="input-group">
                                    <label class="input-group-label mediano centrado" for="TBTipo">Tipo de Taxi</label>
                                    <%
                                        int tipo = objTaxi.getTipo();
                                        String s;
                                        if(tipo == 0)
                                            s = "Taxi Normal";
                                        else if(tipo == 1)
                                            s = "Radio Taxi";
                                        else
                                            s = "Taxi de Sitio";
                                    %>
                                    <input type="text" id="TBTipo" name="TBTipo" class="form-control largo" value="<%=s%>" readonly="readonly" />
                                </div>
                                <div class="centrado">
                                    <%if(objTaxi.getStatus() == 0)   //Si esta bloqueado
                                    {
                                        out.println("<button type='button' id='BTDesbloquearTaxista' name='BTDesbloquearTaxista' onClick='gestionar(5)'><i class=\"fa fa-unlock fa-fw\"></i>Desbloquear</button>\n");
                                    }
                                    else
                                    {
                                        out.println("<button type='button' id='BTBloquearTaxista' name='BTBloquearTaxista' onClick='gestionar(4)'><i class=\"fa fa-lock fa-fw\"></i>Bloquear</button>\n");
                                    }%>
                                    <button type="button" id="BTEliminarTaxi" name="BTEliminarUsuario" onClick="gestionar(6)"><i class="fa fa-times fa-fw"></i>Eliminar</button>
                                    <button type="button" id="BTGenerarQR" name="BTGenerarQR" onClick="gestionar(3)"><i class="fa fa-qrcode fa-fw"></i>Generar QR</button>
                                </div>
                            </form>
                        <%}%>
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