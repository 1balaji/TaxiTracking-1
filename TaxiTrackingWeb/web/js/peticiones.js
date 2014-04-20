//Funcion que se ejecuta al termino de la carga de la pagina
$(document).ready(function()
{
    getPeticion();    //Para cargar las peticiones al terminar de cargar la pagina
});

//Funcion para obtener la peticion de un usuario por medio de una peticion asincrona
function getPeticion()
{
    $.post("Usuario_Negocio",   //URL del servlet al que se llamara
            {q:11},              //Parametros a enviar
            function(data)      //Funcion que se ejecuta despues de la respuesta. data es lo que regresa el servlet.
            {
		$("#contenedorTabla").html(data);   //Se anexa el codigo al div que contendra los datos
            }
        );
}

//Funcion que gestiona los eventos de los botones de control
function gestionar(operacion)
{
    //Opacamos la ventana
    $("html").fadeTo(0,0.3);
    
    //Operacion a llamar
    var q = -1;
    
    var textoConfirmacion;
    
    //Obtenemos el mensaje que corresponde a la accion
    switch(operacion)
    {
        case 12:
            textoConfirmacion = "¿Desea cancelar la petición?";
            q = 12;
            break;
        case 13:
            textoConfirmacion = "¿Desea hacer la petición?";
            q = 13;
            break;
    }
    
    //Abrimos un cuadro de confirmacion
    var confirmar = confirm(textoConfirmacion);
    
    if (confirmar)
    {
        //Creamos el hidden para la variable q
        var input = $("<input>").attr("type", "hidden").val(q)
                                .attr("id", "q")
                                .attr("name", "q");
        
        if(operacion === 12)
        {
            //Lo agregamos al formulario
            $("#formCancelarPeticion").append($(input));
        }
        else if(operacion === 13)
        {
            //Lo agregamos al formulario
            $("#formEnviarPeticion").append($(input));
        }
    }
    
    //Restauramos la ventana
    $("html").fadeTo(0,1);
}