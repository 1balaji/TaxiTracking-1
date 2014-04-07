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
		$(".contenedorTabla").html(data);   //Se anexa el codigo al div que contendra a la tabla
            }
        );
}

//Funcion que gestiona los eventos de los botones de control
function gestionar(nombreUsuario, operacion)
{
    //Obtenemos la parte del mensaje que corresponde a la accion
    switch(operacion)
    {
        case 2:
            texto = "bloquear";
            break;
        case 3:
            texto = "desbloquear";
            break;
        case 4:
            texto = "eliminar";
            break;
    }
    
    //Abrimos un cuadro de confirmacion
    confirmar = confirm("¿Desea " + texto + " a " + nombreUsuario + "?");
    
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
                    if(true)
                    {
                        alert("La operación se hizo correctamente");
                        $(".contenedorTabla").html("<i class='fa fa-refresh fa-spin fa-4x'></i>");
                        getPeticiones();    //Se recargan las peticiones
                    }
                    else
                        alert("Error al realizar la operación");
                }
            );
    }
}