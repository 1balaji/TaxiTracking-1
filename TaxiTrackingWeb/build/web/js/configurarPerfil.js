//Funcion que se ejecuta al termino de la carga de la pagina
$(document).ready(function()
{
    getEmail(0);    //Para cargar el email al terminar de cargar la pagina
});

//Funcion para obtener el email por medio de una peticion asincrona
function getEmail(opcion)   //Opcion es para saber quie
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
                if(opcion == 0)
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
    var form = $("<form>").attr("action", "Usuario_Negocio?q=9")
                          .attr("method", "POST")
                          .attr("id", "formEmail")
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
                
                //Quitamos los efectos del css
                $("#contentEmail").removeClass("sombreado");
                
                //Quitamos el evento onclick
                $("#contentEmail").removeAttr("onClick");
                
                //Escondemos el contenido
                toogleVinculoEmail();
                
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
}

function toogleVinculoEmail()
{
    $("#vinculoEmail").toggle();
}

function editarContrasena()
{
    $("#contentEmail").remove();
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
                                        .addClass("form-control largo");
                var divControl = $("<div>").attr("id", "divControl")
                                            .addClass("centrado");
                
                var button = $("<button>").attr("id", "BTEditarContrasena")
                                          .html("<i class=\"fa fa-fw fa-pencil-square-o\"></i>Editar");
                
                //Quitamos los efectos del css
                $("#contentPassword").removeClass("sombreado");
                
                //Quitamos el evento onclick
                $("#contentPassword").removeAttr("onClick");
                
                //Removemos el contenido
                $("#contentPassword").empty();
                
                //Agregamos las etiquetas
                $("#contentPassword").append($(form));
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
}

function validaCorreosIguales()
{
    var email = document.getElementById('TBEmail');
    var emailConfirmado = document.getElementById('TBConfirmarEmail');
    
    //Comparamos que sean iguales
    if (email.value != emailConfirmado.value)
        emailConfirmado.setCustomValidity('Los correos electrónicos deben coincidir');
    else
        emailConfirmado.setCustomValidity('');
}