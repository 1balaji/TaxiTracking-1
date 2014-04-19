//Funcion que se ejecuta al termino de la carga de la pagina
$(document).ready(function()
{
    getPerfil();    //Para cargar las peticiones al terminar de cargar la pagina
});

//Funcion para obtener las peticiones por medio de una peticion asincrona
function getPerfil()
{
    $.post("Usuario_Negocio",   //URL del servlet al que se llamara
            {q:6},              //Parametros a enviar
            function(data)      //Funcion que se ejecuta despues de la respuesta. data es lo que regresa el servlet.
            {
		$("#contenedorTabla").html(data);   //Se anexa el codigo al div que contendra los datos
            }
        );
}
