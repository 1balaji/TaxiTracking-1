//Funcion que se ejecuta al termino de la carga de la pagina
$(document).ready(function()
{
    getEmail(0);    //Para cargar el email al terminar de cargar la pagina

    //Si hay mensajes de error desplegamos el formulario
    if($("#errorEmail").length > 0)
        getEmail(1);
    
    else if($("#errorContrasena").length > 0)
        editarContrasena();
        
});

//Funcion para obtener el email por medio de una peticion asincrona
function getEmail(opcion)
{
    //Le quitamos el icono
    $("#iconoEditarEmail").removeClass("fa-pencil-square-o");
    
    //Lo cambiamos por el de carga
    $("#iconoEditarEmail").addClass("fa-refresh fa-spin");

    $.post("Usuario_Negocio",   //URL del servlet al que se llamara
            {q:8},              //Parametros a enviar
            function(data)      //Funcion que se ejecuta despues de la respuesta. data es lo que regresa el servlet.
            {
                //Si se invoca porque se acaba de cargar la pagina
                if(opcion === 0)
                    $("#direccionEmail").text(data);
                //Si se invoca porque se va a editar
                else
                    creaFormulario(data);
            }
        );

    //Le quitamos el icono
    $("#iconoEditarEmail").removeClass("fa-refresh fa-spin");
    
    //Lo cambiamos por el normal
    $("#iconoEditarEmail").addClass("fa-pencil-square-o");
}

function creaFormulario(data)
{
    resetContrasena();
    var form = $("<form>").attr("action", "Usuario_Negocio?q=9")
                          .attr("method", "POST")
                          .attr("id", "formEmail");
                var div = $("<div>").attr("id", "divEmail")
                                    .addClass("input-group");
                var label = $("<label>").addClass("input-group-label mediano centrado")
                                        .attr("for", "TBEmail")
                                        .text("Email");
                var input = $("<input>").attr("type", "email")
                                        .attr("id", "TBEmail")
                                        .attr("name", "TBEmail")
                                        .attr("placeholder", "ejemplo@dominio.mx")
                                        .attr("required", "required")
                                        .attr("title", "Ingresa un email válido")
                                        .attr("maxlength", "45")
                                        .attr("value", data)
                                        .attr("oninput", "validaCorreosIguales()")
                                        .addClass("form-control largo");
                var divConfirmar = $("<div>").attr("id", "divConfirmarEmail")
                                    .addClass("input-group");
                var labelConfirmar = $("<label>").addClass("input-group-label mediano centrado")
                                        .attr("for", "TBConfirmarEmail")
                                        .text("Confirmar Email");
                var inputConfirmar = $("<input>").attr("type", "email")
                                        .attr("id", "TBConfirmarEmail")
                                        .attr("name", "TBConfirmarEmail")
                                        .attr("placeholder", "ejemplo@dominio.mx")
                                        .attr("required", "required")
                                        .attr("title", "Ingresa un email válido")
                                        .attr("maxlength", "45")
                                        .attr("value", data)
                                        .attr("oninput", "validaCorreosIguales()")
                                        .addClass("form-control largo");
                var divControl = $("<div>").attr("id", "divControl")
                                            .addClass("centrado");
                var button = $("<button>").attr("id", "BTEditarEmail")
                                          .html("<i class=\"fa fa-fw fa-pencil-square-o\"></i>Editar");
                var reset = $("<button>").attr("id", "BTResetEmail")
                                         .attr("type", "button")
                                         .attr("onclick", "resetEmail()")
                                         .html("<i class=\"fa fa-fw fa-rotate-left\"></i>Cancelar");

                //Escondemos el contenido
                $("#resumenEmail").fadeOut("slow");
                
                $("#detalleEmail").css("margin-top", "10px");
                
                //Agregamos las etiquetas
                $("#detalleEmail").append($(form));
                $("#formEmail").append($(div));
                $("#formEmail").append($(divConfirmar));
                $("#divEmail").append($(label));
                $("#divEmail").append($(input));
                $("#divConfirmarEmail").append($(labelConfirmar));
                $("#divConfirmarEmail").append($(inputConfirmar));
                $("#formEmail").append($(divControl));
                $("#divControl").append($(button));
                $("#divControl").append($(reset));
}

function resetEmail()
{
    $("#resumenEmail").fadeIn("slow");
    $("#detalleEmail").empty()
                      .css("margin-top", "0");
    getEmail(0);
}

function editarContrasena()
{
    resetEmail();
    var form = $("<form>").attr("action", "Usuario_Negocio?q=10")
                          .attr("method", "POST")
                          .attr("id", "formContrasena");
                var div = $("<div>").attr("id", "divPassword")
                                    .addClass("input-group");
                var label = $("<label>").addClass("input-group-label mediano centrado")
                                        .attr("for", "TBContrasena")
                                        .text("Contraseña");
                var input = $("<input>").attr("type", "password")
                                        .attr("id", "TBContrasena")
                                        .attr("name", "TBContrasena")
                                        .attr("required", "required")
                                        .attr("maxlength", "30")
                                        .addClass("form-control largo");
                var divNuevaContrasena = $("<div>").attr("id", "divNuevoPassword")
                                                   .addClass("input-group");
                var labelNuevaContrasena = $("<label>").addClass("input-group-label mediano centrado")
                                        .attr("for", "TBNuevaContrasena")
                                        .text("Nueva");
                var inputNuevaContrasena = $("<input>").attr("type", "password")
                                        .attr("id", "TBNuevaContrasena")
                                        .attr("name", "TBNuevaContrasena")
                                        .attr("required", "required")
                                        .attr("maxlength", "30")
                                        .attr("oninput", "validaContrasenasIguales()")
                                        .addClass("form-control largo");
                var divConfirmarContrasena = $("<div>").attr("id", "divConfirmarPassword")
                                                   .addClass("input-group");
                var labelConfirmarContrasena = $("<label>").addClass("input-group-label mediano centrado")
                                        .attr("for", "TBConfirmarContrasena")
                                        .text("Confirmar");
                var inputConfirmarContrasena = $("<input>").attr("type", "password")
                                        .attr("id", "TBConfirmarContrasena")
                                        .attr("name", "TBConfirmarContrasena")
                                        .attr("required", "required")
                                        .attr("maxlength", "30")
                                        .attr("oninput", "validaContrasenasIguales()")
                                        .addClass("form-control largo");
                var divControl = $("<div>").attr("id", "divControl")
                                            .addClass("centrado");
                
                var button = $("<button>").attr("id", "BTEditarContrasena")
                                          .html("<i class=\"fa fa-fw fa-pencil-square-o\"></i>Editar");
                
                var reset = $("<button>").attr("id", "BTResetEmail")
                                         .attr("type", "button")
                                         .attr("onclick", "resetContrasena()")
                                         .html("<i class=\"fa fa-fw fa-rotate-left\"></i>Cancelar");
                
                //Escondemos el contenido
                $("#resumenContrasena").fadeOut("slow");
                
                $("#detalleContrasena").css("margin-top", "10px");
                
                //Agregamos las etiquetas
                $("#detalleContrasena").append($(form));
                $("#formContrasena").append($(div));
                $("#formContrasena").append($(divNuevaContrasena));
                $("#formContrasena").append($(divConfirmarContrasena));
                $("#divPassword").append($(label));
                $("#divPassword").append($(input));
                $("#divNuevoPassword").append($(labelNuevaContrasena));
                $("#divNuevoPassword").append($(inputNuevaContrasena));
                $("#divConfirmarPassword").append($(labelConfirmarContrasena));
                $("#divConfirmarPassword").append($(inputConfirmarContrasena));
                $("#formContrasena").append($(divControl));
                $("#divControl").append($(button));
                $("#divControl").append($(reset));
}

function resetContrasena()
{
    $("#resumenContrasena").fadeIn("slow");
    $("#detalleContrasena").empty()
                      .css("margin-top", "0");
}

function validaCorreosIguales()
{
    var email = document.getElementById('TBEmail');
    var emailConfirmado = document.getElementById('TBConfirmarEmail');
    
    //Comparamos que sean iguales
    if (email.value !== emailConfirmado.value)
        emailConfirmado.setCustomValidity('Los correos electrónicos no coinciden');
    else
        emailConfirmado.setCustomValidity('');
}

function validaContrasenasIguales()
{
    var contrasena = document.getElementById('TBNuevaContrasena');
    var contrasenaConfirmada = document.getElementById('TBConfirmarContrasena');
    
    //Comparamos que sean iguales
    if (contrasena.value !== contrasenaConfirmada.value)
        contrasenaConfirmada.setCustomValidity('Las contraseñas no coinciden');
    else
        contrasenaConfirmada.setCustomValidity('');
}