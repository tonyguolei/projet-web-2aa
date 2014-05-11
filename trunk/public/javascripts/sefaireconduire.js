/**
 * Created with IntelliJ IDEA.
 * User: lepeteil
 * Date: 10/05/14
 */

$(document).bind("ready", function () {
    manageDate();
    displayAllTravels();
    $("#boutonChercherParcours").bind("click", searchTravel);
});

function displayAllTravels(){
    var listeParcours = document.getElementById("listeParcours");
    var tbody = listeParcours.getElementsByTagName("tbody")[0];

    $.ajax( "/tousLesParcours")
        .done(function(data) {
            if(data.length<=0){
                alert("aucun résultat");
            }
            $.each(data, function (key, value) {
                var newline = document.createElement('tr');
                var departtmp = document.createElement('td');
                departtmp.innerHTML = value.depart.nom;
                var arrivetmp = document.createElement('td');
                arrivetmp.innerHTML = value.arrivee.nom;
                var datetmp = document.createElement('td');
                datetmp.innerHTML = value.date;
                var prixtmp = document.createElement('td');
                prixtmp.innerHTML = value.prix;
                //TODO Récuperer le nb de places restantes
                //Si 0 place class="negative" + bouton activé
                //Si toutes les places class="positive"
                var nbplacestmp = document.createElement('td');
                nbplacestmp.innerHTML = value.nbplacesinitiales;
                var buttontmp = document.createElement('div');
                buttontmp.setAttribute('class', 'right floated tiny teal ui button');
                buttontmp.innerHTML = "Voyager";

                tbody.appendChild(newline);
                newline.appendChild(departtmp);
                newline.appendChild(arrivetmp);
                newline.appendChild(datetmp);
                newline.appendChild(prixtmp);
                newline.appendChild(nbplacestmp);
                newline.appendChild(buttontmp);
            });


        })
        .fail(function(error) {
            console.log("error");
        })
}

function searchTravel(){
    var listeParcours = document.getElementById("listeParcours");
    var tbody = listeParcours.getElementsByTagName("tbody")[0];

    var depart = document.getElementsByName("depart")[0].value;
    var arrivee = document.getElementsByName("arrivee")[0].value;
    var date = document.getElementsByName("date")[0].value;

    $.ajax( "/chercherParcours?depart="+depart+"&arrivee="+arrivee+"&date="+date)
        .done(function(data) {
            if(data.length<=0){
                alert("aucun résultat");
            }
            else{
                tbody.remove();
            }

            $.each(data, function (key, value) {
                var newline = document.createElement('tr');
                var departtmp = document.createElement('td');
                departtmp.innerHTML = value.depart.nom;
                var arrivetmp = document.createElement('td');
                arrivetmp.innerHTML = value.arrivee.nom;
                var datetmp = document.createElement('td');
                datetmp.innerHTML = value.date;
                var prixtmp = document.createElement('td');
                prixtmp.innerHTML = value.prix;
                //TODO Récuperer le nb de places restantes
                //Si 0 place class="negative" + bouton activé
                //Si toutes les places class="positive"
                var nbplacestmp = document.createElement('td');
                nbplacestmp.innerHTML = value.nbplacesinitiales;
                var buttontmp = document.createElement('div');
                buttontmp.setAttribute('class', 'right floated tiny teal ui button');
                buttontmp.innerHTML = "Voyager";

                tbody.appendChild(newline);
                newline.appendChild(departtmp);
                newline.appendChild(arrivetmp);
                newline.appendChild(datetmp);
                newline.appendChild(prixtmp);
                newline.appendChild(nbplacestmp);
                newline.appendChild(buttontmp);
            });


        })
        .fail(function(error) {
            console.log("error");
        })
}

function searchTravel2(){
    var depart = document.getElementsByName("depart")[0].value;
    var arrivee = document.getElementsByName("arrivee")[0].value;
    var date = document.getElementsByName("date")[0].value;

    /*var list = document.getElementsByClassName("ui divided list")[0];
    var item = list.getElementsByClassName("item");

    if (item.length != 0){
        item.remove();
    }*/

    var listeParcours = document.getElementById("listeParcours");

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
    return prettyDate;
}


