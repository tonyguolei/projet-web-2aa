/**
 * Created with IntelliJ IDEA.
 * User: lepeteil
 * Date: 14/05/14
 * Time: 18:28
 * To change this template use File | Settings | File Templates.
 */
var init = true;

$(document).bind("ready", function () {
    rechercherParcours();
    $("#boutonChercherParcours").bind("click", rechercherParcours);
});

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
                            "<td>" + value.nbPlacesInitiales+"</td>"+
                            "<td>"+ value.prix +"</td>"+
                            "<td><div class='ui small negative disabled button'>Réserver</div></td>"+
                            "</tr>"
                    );
                });
            }
        })
        .fail(function(error) {
            console.log("error");
        })
}
