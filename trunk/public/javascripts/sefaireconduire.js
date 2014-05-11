/**
 * Created with IntelliJ IDEA.
 * User: lepeteil
 * Date: 10/05/14
 */

$(document).bind("ready", function () {
    $("#boutonChercherParcours").bind("click", searchTravel);
    manageDate();
});

function searchTravel(){
    var depart = document.getElementsByName("depart")[0].value;
    var arrivee = document.getElementsByName("arrivee")[0].value;
    var date = document.getElementsByName("date")[0].value;

    var list = document.getElementsByClassName("ui divided list")[0];
    var item = list.getElementsByClassName("item");

    if (item.length != 0){
        item.remove();
    }

    $.ajax( "/chercherParcours?depart="+depart+"&arrivee="+arrivee+"&date="+date)
        .done(function(data) {
            $.each(data, function (key, value) {
                var newdivitem = document.createElement('div');
                newdivitem.setAttribute('class', 'item');
                var newdivbutton = document.createElement('div');
                newdivbutton.setAttribute('class', 'right floated tiny teal ui button');
                newdivbutton.innerHTML = "S'inscrire";
                //TODO Bouton activé si encore des places dispos
                var newdivcontent = document.createElement('div');
                newdivcontent.setAttribute('class', 'content');
                var newdivheader = document.createElement('div');
                newdivheader.setAttribute('class', 'header');
                newdivheader.innerHTML = value.depart.nom + ' ' +  value.arrivee.nom + ' ' + value.dateParcours;

                list.appendChild(newdivitem);
                newdivitem.appendChild(newdivbutton);
                newdivitem.appendChild(newdivcontent);
                newdivcontent.appendChild(newdivheader);
            });

            if(data.length<=0){
                alert("aucun résultat");
            }
        })
        .fail(function(error) {
            console.log("error");
        })
}


function manageDate(){
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
}


