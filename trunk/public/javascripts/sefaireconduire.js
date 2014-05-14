/**
 * Created with IntelliJ IDEA.
 * User: lepeteil
 * Date: 14/05/14
 * Time: 18:28
 * To change this template use File | Settings | File Templates.
 */

/*Instance de la classe Parcours */
var parcours1 = new ProjetWeb.Parcours();

$(document).bind("ready", function () {
    var listeParcours = document.getElementById("listeParcours");
    var tbody = listeParcours.getElementsByTagName("tbody")[0];
    obtenirDate();
    afficherTousLesParcours(tbody);
    //$("#boutonChercherParcours").bind("click", rechercherParcours(tbody));
});

function obtenirDate(){
    $("#date").datepicker({
        dateFormat: 'dd/mm/yy'
    });
    var myDate = new Date();
    var month = myDate.getMonth() + 1;
    var day = myDate.getDate();

    if(month < 11){
        var prettyDate = day + '/0' + month + '/' + myDate.getFullYear();
    }
    else{
        var prettyDate = day + '/' + month + '/' + myDate.getFullYear();
    }
    $("#date").val(prettyDate);
    return prettyDate;
}

/*functions pour page conduire*/
function afficherTousLesParcours(eltparent) {
    //TODO Récuperer le nb de places restantes
    //Si 0 place class="negative" + bouton activé
    //Si toutes les places class="positive"

    $.ajax( "/tousLesParcours")
        .done(function(data) {
            if(data.length<=0){
                alert("aucun résultat");
            }
            $.each(data, function (key, value) {
                $(eltparent).append(
                    "<tr>"+
                        "<td>"+ value.depart.nom +"</td>"+
                        "<td>"+ value.arrivee.nom +"</td>"+
                        "<td>"+ value.dateparcours+"</td>"+
                        "<td>"+ value.prix+"</td>"+
                        "<td>"+ value.nbplacesinitiales +"</td>"+
                        "<td><div class='right floated tiny teal ui button'>Voyager</div></td>"+
                    "</tr>"
                );
            });
        })
        .fail(function(error) {
            console.log("error");
        })
}

function rechercherParcours(eltparent){
    var depart = document.getElementsByName("depart")[0].value;
    var arrivee = document.getElementsByName("arrivee")[0].value;
    var date = document.getElementsByName("date")[0].value;

    $.ajax( "/chercherParcours?depart="+depart+"&arrivee="+arrivee+"&date="+date)
        .done(function(data) {
            if(data.length<=0){
                $(eltparent).append("<tr>Aucun résultat trouvé !</tr>");
            }
            else{
                eltparent.remove();
            }
            $.each(data, function (key, value) {
                $(eltparent).append(
                    "<tr>"+
                        "<td>"+ value.depart.nom +"</td>"+
                        "<td>"+ value.arrivee.nom +"</td>"+
                        "<td>"+ value.dateparcours+"</td>"+
                        "<td>"+ value.prix+"</td>"+
                        "<td>"+ value.nbplacesinitiales +"</td>"+
                        "<td><div class='right floated tiny teal ui button'>Voyager</div></td>"+
                        "</tr>"
                );
            });
        })
        .fail(function(error) {
            console.log("error");
        })
}

