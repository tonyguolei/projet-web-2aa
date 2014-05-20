/**
 * Created with IntelliJ IDEA.
 * User: lepeteil
 * Date: 20/05/14
 * Time: 18:54
 * To change this template use File | Settings | File Templates.
 */

var init = true;
/*Instance de la classe Parcours*/
var parcours1 = new ProjetWeb.Parcours();

$(document).bind("ready", function () {
    obtenirDate();
    rechercherParcours();
    $("#boutonChercherParcours").bind("click", rechercherParcours);
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

function rechercherParcours(){
    var depart = document.getElementsByName("depart")[0].value;
    var arrivee = document.getElementsByName("arrivee")[0].value;
    var date = document.getElementsByName("date")[0].value;

    if(init==true)
        init=false;
    else if (depart=="" & arrivee==""){
        //TODO Aucune saisie
        return;
    }

    $.ajax({
        url: "/chercherParcours",
        data: {depart:depart,
            arrivee:arrivee,
            date:date}
    })
        .done(function(data) {
            if(data.length<=0){
                //TODO Afficher message pas trouvé
            }
            else{
                $("#tabcontenu").empty();
                $.each(data, function (key, value) {
                    var nbplacesrestantes = value.nbPlacesInitiales - value.membresInscrits.length;
                    if(nbplacesrestantes<=0){
                        $("#tabcontenu").append(
                            "<tr>"+
                                "<td>"+ value.depart.nom +"</td>"+
                                "<td>"+ value.arrivee.nom +"</td>"+
                                "<td>" +
                                "<div class='ui divided list'>" +
                                "<div class='item'>"+value.dateParcours+"</div>"+
                                "<div class='item'>"+value.heure + "h" + value.min + "</div>" +
                                "</div>" +
                                "</td>"+
                                "<td>"+ nbplacesrestantes + "/" + value.nbPlacesInitiales+"</td>"+
                                "<td>"+ value.prix +"</td>"+
                                "<td><div class='ui small negative disabled button'>" +
                                "<i class='warning users icon'></i>Réserver</div></td>"+
                                "</tr>"
                        );
                    }
                    else{
                        $("#tabcontenu").append(
                            "<tr>"+
                                "<td>"+ value.depart.nom +"</td>"+
                                "<td>"+ value.arrivee.nom +"</td>"+
                                "<td>"+ value.dateParcours+"</td>"+
                                "<td>"+ nbplacesrestantes + "/" + value.nbPlacesInitiales+"</td>"+
                                "<td>"+ value.prix +"</td>"+
                                "<td><div class='ui small teal button'>Réserver</div></td>"+
                                "</tr>"
                        );
                    }

                });
            }
        })
        .fail(function(error) {
            console.log("error");
        })
}
