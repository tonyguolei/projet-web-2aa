/**
 * Created with IntelliJ IDEA.
 * User: lepeteil
 * Date: 13/05/14
 * Time: 21:57
 * To change this template use File | Settings | File Templates.
 */
var map;
var panel;
var initialize;
var calculate;
var direction;

$(document).bind('ready', function () {
    obtenirDate();
    $(".cp").bind("keyup", function () {
        var inputcp = this;
        setTimeout(function () {
            rechercherVille(inputcp);
        }, 1000);
    });
});


function obtenirDate() {
    $("#date").datepicker({
        dateFormat: 'dd/mm/yy'
    });

    if($("#date").val()==""){
        var myDate = new Date();
        var month = myDate.getMonth() + 1;
        var day = myDate.getDate();

        if (month < 11) {
            var prettyDate = day + '/0' + month + '/' + myDate.getFullYear();
        }
        else {
            var prettyDate = day + '/' + month + '/' + myDate.getFullYear();
        }
        $("#date").val(prettyDate);
        return prettyDate;
    }
    else{
        return $("#date").val();
    }

}

/*----------------------LIES A DES EVENEMENTS--------------------------*/
function rechercherVille(cp) {
    var parent = cp.parentNode;
    var type = parent.getAttribute("name");
    var list = parent.getElementsByClassName('dropdown')[0];

    $.ajax({
        url: "http://api.zippopotam.us/fr/" + cp.value,
        cache: false,
        dataType: "json",
        type: "GET",
        success: function (result, success) {
            suggestions = [];

            for (ii in result['places']) {
                suggestions.push(result['places'][ii]['place name']);
            }
            if (suggestions.length > 0) {

                $(".dropdown." + type).empty();
                //resultats de ville avec le code postal saisi
                $(".dropdown." + type).append(
                    '<div class="text">Selectionner</div>' +
                        '<i class="dropdown icon"></i>' +
                        '<input name="' + type + '" type="hidden">' +
                        '<div class="menu ' + type + '"></div>');
                for (var i = 0; i < suggestions.length; i++) {
                    $(".menu." + type).append(
                        "<div class='item' data-value='" + suggestions[i] + "'>" + suggestions[i] + "</div>"
                    );
                }
                $('.ui.selection.dropdown.' + type).dropdown();
            }

        },
        error: function (result, success) {
            console.log("erreur recherche villes");
        }
    });
}

function afficherParcoursInfo(parcours) {
    var nbplacesrestantes = parcours.nbplacesinitiales - parcours.membresInscrits.length;
    var listmembres = '';
    for (var i = 0; i < parcours.membresInscrits.length; i++) {
        listmembres = listmembres + '<div class="item"><i class="user icon"></i>' +
            parcours.membresInscrits[i].prenom + ' ' + parcours.membresInscrits[i].nom + '</div>';
    }
    if (parcours.membresInscrits.length==0){
        listmembres = 'Aucun passager inscrit';
    }

    $('#lieu').append(
        '<div class="ui one column page grid ">' +
            '<div class="ui column">' +
                '<div class="ui large label">' +
                    '<i class=" circular inverted map marker icon"></i>Départ' +
                '</div>'+
                '<div class="ui small label">' +
                    '<p>'+parcours.depart.nom + ' [ '+
                    parcours.depart.codePostal+' ] </p>'+
                '</div>' +
            '</div>' +
        '</div>' +
            '<div class="ui one column page grid ">' +
            '<div class="ui column">' +
            '<div class="ui large label">' +
            '<i class=" circular inverted map marker icon"></i>Arrivée' +
            '</div>'+
            '<div class="ui small label">' +
            '<p>'+parcours.arrivee.nom + ' ['+
             parcours.arrivee.codePostal+' ] </p>'+
            '</div>' +
            '</div>' +
            '</div>'
    );

    $('#resparcours').append(
            '<div class="ui orange segment">' +
                '<i class="circular inverted green truck icon"></i>Créateur' +
                '<div class="ui list">'+
                    '<div class="item">'+
                            //'<div class="content">'+
                            parcours.createur.nom +' '+ parcours.createur.prenom +
                           //'</div>'+
                    '</div>'+
                '</div>'+
            '</div>' +
            '<div class="ui orange segment">' +
            '<i class="circular inverted orange users icon"></i>Passagers' +
            '<div class="ui list">' +
            listmembres + '</div>' +
            '</div>' +
            '<div class="ui orange secondary segment">' +
            '<p>Date: ' + parcours.dateparcours + '</p>' +
            '<p>Heure : ' + parcours.heure + 'h ' + parcours.min + '</p>' +
            '<p>Prix fixé: ' + parcours.prix + '€</p>' +
            '<p>Nombre de places proposées: ' + parcours.nbplacesinitiales + '</p>' +
            '<p>Nombre de places restantes: ' + nbplacesrestantes + '</p>' +
            '</div>' +
            '</div>'
    );

}

function afficherCarte(parcours) {
    $('.raised.segment').empty();
    $('.raised.segment').append(
        '<div class="ui two column top aligned relaxed grid basic segment">' +
            '<div class="column">' +
            '<div class="" id="lieu" >' +
            '</div>' +
            '<div class="ui items">' +
                '<div class="item" id="map" >' +
                '</div>' +
            '</div>' +
            '</div>' +
            '<div class="column">' +
                '<div class="ui items">' +
                    '<div class="item" id="resparcours"  >' +
                    '</div>' +
                '</div>' +
            '</div>' +
            '</div>'
    );
    initialize();
    calculate(parcours);
}

/*-----------------------GESTION MAP--------------------------*/
initialize = function () {
    var latLng = new google.maps.LatLng(50.6371834, 3.063017400000035); // Correspond au coordonnées de Lille
    var myOptions = {
        zoom: 15, // Zoom par défaut
        center: latLng, // Coordonnées de départ de la carte de type latLng
        mapTypeId: google.maps.MapTypeId.TERRAIN, // Type de carte, différentes valeurs possible HYBRID, ROADMAP, SATELLITE, TERRAIN
        maxZoom: 20
    };

    map = new google.maps.Map(document.getElementById('map'), myOptions);
    //panel    = document.getElementById('panel');

    var marker = new google.maps.Marker({
        position: latLng,
        map: map
        //title    : "Lille"
        //icon     : "marker_lille.gif" // Chemin de l'image du marqueur pour surcharger celui par défaut
    });

    direction = new google.maps.DirectionsRenderer({
        map: map
        //panel : panel // Dom element pour afficher les instructions d'itinéraire
    });
};

calculate = function (parcours) {
    origin = parcours.depart.nom; // Le point départ
    destination = parcours.arrivee.nom; // Le point d'arrivé
    if (origin && destination) {
        var request = {
            origin: origin,
            destination: destination,
            travelMode: google.maps.DirectionsTravelMode.DRIVING // Mode de conduite
        }
        var directionsService = new google.maps.DirectionsService(); // Service de calcul d'itinéraire
        directionsService.route(request, function (response, status) { // Envoie de la requête pour calculer le parcours
            if (status == google.maps.DirectionsStatus.OK) {
                direction.setDirections(response); // Trace l'itinéraire sur la carte et les différentes étapes du parcours
            }
        });
    }
};



