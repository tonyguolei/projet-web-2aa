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
    gererEvenements();
});

function gererEvenements(){
    $('.button.consulter').bind('click', consulterParcoursMembre);
    $('.button.annuler').bind('click', supprimerParcoursCree);
    $('.button.desinscrire').bind('click', annulerReservationParcours);
    $('.button.reactiver').bind('click', reactiverParcoursCree);
    $(".cp").bind("keyup", function () {
        var inputcp = this;
        setTimeout(function () {
            rechercherVille(inputcp);
        }, 1000);
    });
}
/*----------------------LIES A DES EVENEMENTS--------------------------*/
function consulterParcoursMembre(){
    //Consulter un parcours (créé ou réservé)
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
            afficherMesParcoursChoisis(membre1);
        },
        function() {
            //gerer erreur
            console.log("erreur fonction");
        });
}
function supprimerParcoursCree(){
    //Annuler la creation du parcours
    var tr = this.parentNode.parentNode.parentNode;
    var idp = tr.getElementsByTagName('input')[0].value;
    var membre1 = new ProjetWeb.Membre();
    membre1.supprimerParcours(idp,
        function() {
            afficherMesParcoursCrees(membre1);
        },
        function() {
            //gerer erreur
            console.log("erreur fonction");
        });

}
function reactiverParcoursCree(){
    //Annuler la suppression du parcours cree
    var tr = this.parentNode.parentNode.parentNode;
    var idp = tr.getElementsByTagName('input')[0].value;
    var membre1 = new ProjetWeb.Membre();
    membre1.reactiverParcours(idp,
        function() {
            afficherMesParcoursCrees(membre1);
        },
        function() {
            //gerer erreur
            console.log("erreur fonction");
        });
}
function rechercherVille(cp){
    var parent = cp.parentNode;
    var type = parent.getAttribute("name");
    var list = parent.getElementsByClassName('dropdown')[0];

    $.ajax({
        url: "http://api.zippopotam.us/fr/"+cp.value,
        cache: false,
        dataType: "json",
        type: "GET",
        success: function(result, success) {
            suggestions = [];
            //TODO Désactiver la sélection
            for ( ii in result['places']){
                suggestions.push(result['places'][ii]['place name']);
            }
            if ( suggestions.length > 0){

                $(".dropdown."+ type).empty();
                //resultats de ville avec le code postal saisi
                $(".dropdown."+ type).append(
                    '<div class="text">Selectionner</div>'+
                    '<i class="dropdown icon"></i>'+
                    '<input name="cp" type="hidden">' +
                    '<div class="menu '+type+'"></div>');
                for(var i=0;i<suggestions.length;i++){
                    $(".menu."+type).append(
                    "<div class='item' data-value='" + suggestions[i] + "'>" + suggestions[i] + "</div>"
                    );
                }
                $('.ui.selection.dropdown.'+type).dropdown();
            }

        },
        error: function(result, success) {
            console.log("erreur recherche villes");
        }
    });



}

/*----------------------FONCTION----------------------------*/
function afficherMesParcoursCrees(membre){
    $('#listeParcoursCrees').empty();

    for(var j=0;j<membre.lesParcoursCrees.length;j++){
        var pcrees = membre.lesParcoursCrees[j];

        if(!pcrees.supprime){
            var listoptions = '<div class="ui small purple button annuler">'  +
                '<i class="remove icon"></i>Annuler'  +
                '</div>';
        }
        else{
            var listoptions = '<div class="ui small red button reactiver">'+
                '<i class="backward icon"></i>Réactiver'+
                '</div>';
        }
        var nbplacesrestantes = pcrees.nbPlacesInitiales - pcrees.membresInscrits.length;
        $('#listeParcoursCrees').append(
        '<tr>'+
            '<input type="hidden" name="pcreesid" value="'+pcrees.id+'"/>'+
            '<td>'+pcrees.depart.nom+'</td>'+
            '<td>'+pcrees.arrivee.nom+'</td>'+
            '<td>'+
                '<div class="ui divided list">'+
                    //TODO Convertir date!
                    //'<div class="item">'+pcrees.dateParcours+'</div>'  +
                    '<div class="item">'+pcrees.heure + 'h' + pcrees.min+'</div>'+
                '</div>'+
            '</td>'+
            '<td>'+nbplacesrestantes+'</td>'+
            '<td>'+pcrees.prix+'</td>'+
            '<td>'+
                '<div class="ui vertical buttons">' +
                    '<div class="ui small blue button consulter">' +
                        '<i class="unhide icon"></i>Consulter'  +
                    '</div>'+
                    listoptions+
                '</div>' +
            '</td>'+
        '</tr>'
    );
    }
    gererEvenements();
}

function afficherMesParcoursChoisis(membre){
    $('#listeParcoursChoisis').empty();

    for(var j=0;j<membre.lesParcoursChoisis.length;j++){
        var pchoisis = membre.lesParcoursChoisis[j];
        if (pchoisis.supprime == true){
            var listoptions = '<div class="ui small negative disabled button">'+
                '<i class="warning users icon"></i>Annulé'+
                '</div>';
        }else{
            var listoptions = '<div class="ui small positive button desinscrire">'  +
                '<i class="remove icon"></i>Me désinscrire'  +
                '</div>';
        }

        $('#listeParcoursChoisis').append(
            '<tr>'+
                '<input type="hidden" name="pcreesid" value="'+pcrees.id+'"/>'+
                '<td>'+pchoisis.depart.nom+'</td>'+
                '<td>'+pchoisis.arrivee.nom+'</td>'+
                '<td>'+
                '<div class="ui divided list">'+
                //TODO Convertir date en string
                '<div class="item">'+pchoisis.dateParcours+'</div>'  +
                '<div class="item">'+pchoisis.heure + 'h' + pchoisis.min+'</div>'+
                '</div>'+
                '</td>'+
                '<td>'+pchoisis.prix+'</td>'+
                '<td>' +
                '<div class="ui vertical buttons">' +
                    '<div class="ui small blue button consulter">' +
                    '<i class="unhide icon"></i>Consulter'  +
                    '</div>'+
                    listoptions+
                '</div>'+
                '</td>'+
            '</tr>'
        );
    }
    gererEvenements();
}

function afficherParcoursInfo(parcours) {
    var nbplacesrestantes = parcours.nbplacesinitiales - parcours.membresInscrits.length;
    var listmembres = '';
    for(var i=0;i<parcours.membresInscrits.length;i++){
      listmembres = listmembres + '<p>'+parcours.membresInscrits[i].prenom+parcours.membresInscrits[i].nom+'</p>';
    }
    $('#resparcours').append(
            '<p>depart: ' + parcours.depart.nom + parcours.depart.codePostal + '</p>' +
            '<p>arrivee: ' + parcours.arrivee.nom + parcours.arrivee.codePostal + '</p>'+
            '<div class="ui teal inverted segment">' +
            '<p>createur: ' + parcours.createur.nom + parcours.createur.prenom + '</p>'+
            '</div>'+
            '<div class="ui green inverted segment">membres: ' + listmembres + '</div>'+
            '<div class="ui secondary segment">' +
            '<p>date: ' + parcours.dateparcours + '</p>'+
            '<p>heure : ' + parcours.heure + 'h ' + parcours.min +'</p>'+
            '<p>prix: ' + parcours.prix + '</p>'+
            '<p>nbplacesinit: ' + parcours.nbplacesinitiales + '</p>'+
            '<p>nb places restantes: ' + nbplacesrestantes + '</p>'+
            '</div>'+

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



