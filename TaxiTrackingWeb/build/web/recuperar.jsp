<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="xmil.css" rel="stylesheet" type="text/css" media="all" />
    </head>
    <body>
        <blockquote>
            <h1>Recuperar Contraseña </h1>
        </blockquote>
        <form ACTION="ServletContras" METHOD="POST">
            <input type='text' name='email'/>	
            <input type="submit" name="Submit" value="Enviar">
        </form>
    </body>
</html>
