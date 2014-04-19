//Funcion que se ejecuta al termino de la carga de la pagina
$(document).ready(function()
{
    getPeticiones();    //Para cargar las peticiones al terminar de cargar la pagina
});

//Funcion para obtener las peticiones por medio de una peticion asincrona
function getPeticiones()
{
    $.post("Usuario_Negocio",   //URL del servlet al que se llamara
            {q:5},              //Parametros a enviar
            function(data)      //Funcion que se ejecuta despues de la respuesta. data es lo que regresa el servlet.
            {
		$("#contenedorTabla").html(data);   //Se anexa el codigo al div que contendra a la tabla
            }
        );
}

//Funcion que gestiona los eventos de los botones de control
function gestionar(nombreUsuario, operacion)
{
    //Opacamos la ventana
    $("html").fadeTo(0,0.3);
    
    //Obtenemos el mensaje que corresponde a la accion
    switch(operacion)
    {
        case 2:
            textoConfirmacion = "¿Desea bloquear al usuario " + nombreUsuario + "?";
            textoSalida = "El usuario " + nombreUsuario + " se bloqueó correctamente";
            break;
        case 3:
            textoConfirmacion = "¿Desea desbloquear al usuario " + nombreUsuario + "?";
            textoSalida = "El usuario " + nombreUsuario + " se desbloqueó correctamente";
            break;
        case 4:
            textoConfirmacion = "¿Desea eliminar al usuario " + nombreUsuario + "?";
            textoSalida = "El usuario " + nombreUsuario + " se eliminó correctamente";
            break;
    }
    
    //Abrimos un cuadro de confirmacion
    confirmar = confirm(textoConfirmacion);
    
    //Si acepta se hace la peticion asincrona
    if (confirmar)
    {
        $.post("Usuario_Negocio",   //URL del servlet al que se llamara
                {
                    q:operacion,    //Parametros a enviar
                    nombreUsuario: nombreUsuario
                },
                function(data)
                {
                    //Si fue correcta la peticion
                    if(data == 1)
                    {
                        alert(textoSalida);
                        
                        //Ponemos el simbolo de cargando
                        $("#contenedorTabla").html("<i class='fa fa-refresh fa-spin fa-4x'></i>");
                        getPeticiones();    //Se recargan las peticiones
                    }
                    else
                        alert("Error al realizar la operación");
                }
            );
    }
    
    //Restauramos la ventana
    $("html").fadeTo(0,1);
}