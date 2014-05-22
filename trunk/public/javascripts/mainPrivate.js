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
    $('.button.consulter').bind('click', consulterParcoursMembre);
    $('.button.annuler').bind('click', supprimerParcoursCree);
    $('.button.desinscrire').bind('click', annulerReservationParcours);
});
/*----------------------EVENEMENTS--------------------------*/
function consulterParcoursMembre(){
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
function annulerReservationParcours(){
    //Annuler sa reservation
    var tr = this.parentNode.parentNode.parentNode;
    var idp = tr.getElementsByTagName('input')[0].value;
    var membre1 = new ProjetWeb.Membre();
    membre1.annulerReservationParcours(idp,
        function() {
            console.log("annulation resa parcours");
        },
        function() {
            //gerer erreur
            console.log("erreur fonction");
        });
}
function supprimerParcoursCree(){
    //Annuler la creation
    var tr = this.parentNode.parentNode.parentNode;
    var idp = tr.getElementsByTagName('input')[0].value;
    var membre1 = new ProjetWeb.Membre();
    membre1.supprimerParcours(idp,
        function() {
              console.log("suppression parcours");
        },
        function() {
            //gerer erreur
            console.log("erreur fonction");
        });

}

/*----------------------FONCTION----------------------------*/
function afficherParcoursInfo(parcours) {
    var nbplacesrestantes = parcours.nbplacesinitiales - parcours.membresInscrits.length;
    $('#resparcours').append(
            '<p>depart: ' + parcours.depart.nom + parcours.depart.codePostal + '</p>' +
            '<p>arrivee: ' + parcours.arrivee.nom + parcours.arrivee.codePostal + '</p>'+
            '<div class="ui teal inverted segment">' +
            '<p>createur: ' + parcours.createur.nom + parcours.createur.prenom + '</p>'+
            '</div>'+
            '<div class="ui secondary segment">' +
            '<p>date: ' + parcours.dateparcours + '</p>'+
            '<p>heure : ' + parcours.heure + 'h ' + parcours.min +'</p>'+
            '<p>nbplacesinit: ' + parcours.nbplacesinitiales + '</p>'+
            '<p>nb places restantes: ' + nbplacesrestantes + '</p>'+
                '</div>'
    );
}

function afficherCarte(parcours){
    $('.raised.segment').empty();
    $('.raised.segment').append(
        '<div class="ui two column top aligned relaxed grid basic segment">'+
            '<div class="ui column">'+
                '<div class="ui segment" id="map" style="height:300px"></div>' +
            '</div>' +
            '<div class="ui column">'+
                '<div class="ui secondary inverted segment" id="resparcours" ></div>' +
            '</div>' +
            '</div>'
    );
    initialize();
    calculate(parcours);
}

/*-----------------------GESTION MAP--------------------------*/
initialize = function(){
    var latLng = new google.maps.LatLng(50.6371834, 3.063017400000035); // Correspond au coordonnées de Lille
    var myOptions = {
        zoom      : 15, // Zoom par défaut
        center    : latLng, // Coordonnées de départ de la carte de type latLng
        mapTypeId : google.maps.MapTypeId.TERRAIN, // Type de carte, différentes valeurs possible HYBRID, ROADMAP, SATELLITE, TERRAIN
        maxZoom   : 20
    };

    map      = new google.maps.Map(document.getElementById('map'), myOptions);
    //panel    = document.getElementById('panel');

    var marker = new google.maps.Marker({
        position : latLng,
        map      : map
        //title    : "Lille"
        //icon     : "marker_lille.gif" // Chemin de l'image du marqueur pour surcharger celui par défaut
    });

    direction = new google.maps.DirectionsRenderer({
        map   : map
        //panel : panel // Dom element pour afficher les instructions d'itinéraire
    });
};

calculate = function(parcours){
    origin      = parcours.depart.nom; // Le point départ
    destination = parcours.arrivee.nom; // Le point d'arrivé
    if(origin && destination){
        var request = {
            origin      : origin,
            destination : destination,
            travelMode  : google.maps.DirectionsTravelMode.DRIVING // Mode de conduite
        }
        var directionsService = new google.maps.DirectionsService(); // Service de calcul d'itinéraire
        directionsService.route(request, function(response, status){ // Envoie de la requête pour calculer le parcours
            if(status == google.maps.DirectionsStatus.OK){
                direction.setDirections(response); // Trace l'itinéraire sur la carte et les différentes étapes du parcours
            }
        });
    }
};



