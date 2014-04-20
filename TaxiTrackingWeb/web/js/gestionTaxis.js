//Funcion que se ejecuta al termino de la carga de la pagina
$(document).ready(function()
{
    $("#agregarTaxiToggle").click(function()
    {
        $("#agregarTaxiToggle").remove();
        $("#agregarTaxi").fadeIn("slow");
    });
    
    //Si hay mensajes de error desplegamos el formulario
    if($(".error").length > 0)
    {
        $("#agregarTaxiToggle").remove();
        $("#agregarTaxi").fadeIn("slow");
    }
});

//Funcion que gestiona los eventos de los botones de control
function gestionar(operacion)
{
    //Opacamos la ventana
    $("html").fadeTo(0,0.3);
    
    var idTaxista = $("#TBIdTaxista").val();
    
    //Operacion a llamar
    var q = -1;
    
    var textoConfirmacion;
    
    //Obtenemos el mensaje que corresponde a la accion
    switch(operacion)
    {
        case 3:
            textoConfirmacion = "¿Desea generar el QR del taxista " + idTaxista + "?";
            q = 3;
            break;
        case 4:
            textoConfirmacion = "¿Desea bloquear al taxista " + idTaxista + "?";
            q = 4;
            break;
        case 5:
            textoConfirmacion = "¿Desea desbloquear al taxista " + idTaxista + "?";
            q = 5;
            break;
        case 6:
            textoConfirmacion = "¿Desea eliminar al taxista " + idTaxista + "?";
            q = 6;
            break;
    }
    
    //Abrimos un cuadro de confirmacion
    var confirmar = confirm(textoConfirmacion);
    
    if (confirmar)
    {
        //Si es QR hay que generarlo en una nueva pestaña
        if (q == 3)
            $("#formBusquedaTaxista").attr("target", "_blank");
        
        //Creamos el hidden para la variable q
        var input = $("<input>").attr("type", "hidden").val(q)
                                .attr("id", "q")
                                .attr("name", "q");
        
        //Lo agregamos al formulario
        $("#formBusquedaTaxista").append($(input));
        
        //Enviamos el formulario
        $("#formBusquedaTaxista").submit();
        
        //Quitamos el atributo target por si despues clickea otra opcion
        $("#formBusquedaTaxista").removeAttr("target");
        
        //Removemos el hidden
        $("#q").remove();
    }
    
    //Restauramos la ventana
    $("html").fadeTo(0,1);
}
