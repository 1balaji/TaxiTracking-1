
$(document).ready(function() 
{
    getPeticiones();
//    $("#Comentar").click(function()
//    {
//        var comment = $("#newComment").val();
//        var idPhoto = $("#idPhoto").val();
//        var tags = $("#taggedFriends").val();
//        setComment(comment, idPhoto, tags);
//    });
//    setAutocomplete();
});

function getPeticiones()
{
    $.post("Usuario_Negocio", 
            {q:4},
            function(data) 
            {
                alert("hola");
		$(".contenedorTabla").html(data);
            }
        );
}

//function setComment(comment, idPhoto, tags)
//{
//    $.post("SetComment",
//            {
//                comment: comment,
//                idPhoto: idPhoto,
//                date: getDate(),
//                tags: tags
//            },
//            function(data)
//            {
//                getComments();
//            }
//        );
//}
//
//function getDate()
//{
//    var meses = new Array ("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre");
//    var diasSemana = new Array("Domingo","Lunes","Martes","Miercoles","Jueves","Viernes","Sabado");
//    var date = new Date();
//    var fullDate = diasSemana[date.getDay()] + ", " + date.getDate() + " de " + meses[date.getMonth()] + " de " + date.getFullYear();
//    return fullDate;
//}
//
//function setAutocomplete()
//{
//    //Lo que se va escribiendo en el campo de amigos
//    var query = $("#taggedFriends").val();
//    
//    //Asignamos la propiedad de los tokens, y para los resultados llamamos al servlet Autocomplete con el parametro query
//    $("#taggedFriends").tokenInput("Autocomplete?q="+query,
//                            {
//                                propertyToSearch: "name", 
//                                resultsFormatter: function(item){ return "<li>" + "<img src='" + item.url + "' title='" + item.name + "' height='25px' width='25px' />" + "<div style='display: inline-block; padding-left: 10px;'><div class='full_name'>" + item.name + "</div><div class='email'>" + item.email + "</div></div></li>" },
//                                tokenFormatter: function(item) { return "<li><p>" + item.name + "</p></li>" },
//                                preventDuplicates: true,
//                                hintText: "Amigo",
//                                noResultsText: "No hay resultados D:",
//                                searchingText: "Buscando...",
//                                theme: "facebook"
//                            });
//}