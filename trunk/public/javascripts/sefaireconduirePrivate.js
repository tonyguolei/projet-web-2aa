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
    rechercherParcours();
    $("#boutonReinitChercher").bind("click", reinitialiserChamps);
    $("#boutonChercherParcours").bind("click", rechercherParcours);
    gererEvenementsRecherche();
});
/*----------------------FONCTION----------------------------*/
function gererEvenementsRecherche(){
    $('.button.consulter1').bind('click', consulterParcours);
    $('.button.reserver').bind('click', reserverParcours);
}
function reinitialiserChamps(){
    document.getElementsByName("depart")[0].value = "";
    document.getElementsByName("arrivee")[0].value = "";
    obtenirDate();
    init = true;
    rechercherParcours();
}
/*----------------------LIES A DES EVENEMENTS--------------------------*/
function rechercherParcours(){
    var depart = document.getElementsByName("depart")[0].value;
    var arrivee = document.getElementsByName("arrivee")[0].value;
    var date = document.getElementsByName("date")[0].value;

    if(init==true)
        init=false;
    else if (depart=="" & arrivee==""){
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
                $("#message_failed_search").show().delay(5000).fadeOut();
            }
            else{
                $("#tabcontenu").empty();
                $.each(data, function (key, value) {
                    var nbplacesrestantes = value.nbPlacesInitiales - value.membresInscrits.length;

                    if(nbplacesrestantes<=0){
                        var listoptions = "<td><div class='ui small negative disabled button'>" +
                            "<i class='warning users icon'></i>Complet</div></td>";
                    }
                    else{
                        var listoptions = "<td><div class='ui vertical buttons'>"+
                            "<div class='ui small green button consulter1'>"+
                            "<i class='unhide icon'></i>Consulter"+
                            "</div>"+
                            "<div class='ui small teal button reserver'>Réserver</div>"+
                            "</div></td>";
                    }
                    $("#tabcontenu").append(
                        "<tr>"+
                            "<input name='idparcours' type='hidden' value='" +value.id + "'/>"+
                            "<td>"+ value.depart.nom +"</td>"+
                            "<td>"+ value.arrivee.nom +"</td>"+
                            "<td>"+
                            "<div class='ui divided list'>" +
                            "<div>"+value.dateParcours+"</div>"+
                            "<div>"+value.heure + "h" + value.min + "</div>" +
                            "</div>" +
                            "</td>"+
                            "<td>"+ nbplacesrestantes + "/" + value.nbPlacesInitiales+"</td>"+
                            "<td>"+ value.prix +"</td>"+
                            listoptions +
                            "</tr>"
                    );
                });
                gererEvenementsRecherche();
            }
        })
        .fail(function(error) {
            $("#message_failed_search").show().delay(5000).fadeOut();
        })
}

function consulterParcours(){
    var tr = this.parentNode.parentNode.parentNode;
    var idp = tr.getElementsByTagName('input')[0].value;

    var parcours1 = new ProjetWeb.Parcours();
    parcours1.recupererParcoursInfo(idp,
        function() {
            afficherCarte(parcours1);
            afficherParcoursInfo(parcours1);
        },
        function() {
            //gerer erreur
        });
}

function reserverParcours(){
    var tr = this.parentNode.parentNode.parentNode;
    var idp = tr.getElementsByTagName('input')[0].value;

    if(confirm("Etes-vous sur de vouloir vous inscrire sur ce parcours ?")){
    var membre1 = new ProjetWeb.Membre();
    membre1.reserverParcours(idp,
        function() {
            init = true;
            rechercherParcours();
        },
        function() {
            $("#message_failed_update").show().delay(5000).fadeOut();
        })
    }
}