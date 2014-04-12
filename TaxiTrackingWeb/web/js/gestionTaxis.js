//Funcion que se ejecuta al termino de la carga de la pagina
$(document).ready(function()
{
    $("#agregarTaxiToggle").click(function()
    {
        $("#agregarTaxi").fadeToggle("slow");
    });
});

//Funcion que gestiona los eventos de los botones de control
function gestionar(nombreUsuario, operacion)
{
    //Opacamos la ventana
    $("#container").fadeTo(0,0.5);
    $(".container").fadeTo(0,0.5);
    
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
                        $(".contenedorTabla").html("<i class='fa fa-refresh fa-spin fa-4x'></i>");
                        getPeticiones();    //Se recargan las peticiones
                    }
                    else
                        alert("Error al realizar la operación");
                }
            );
    }
    
    //Restauramos la ventana
    $("#container").fadeTo(0,1);
    $(".container").fadeTo(0,1);
}