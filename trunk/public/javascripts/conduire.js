/**
 * Created by tonyguolei on 5/9/14.
 */

/*Instance de la classe Membre*/
var membre1 = new ProjetWeb.Membre();

/*functions pour page conduire*/
function afficherMembreInfo(membre) {
    $('#createur_info').append(
        "<p>ID: " + membre.id + "</p>" +
        "<p>Nom: " + membre.nom + "</p>" +
        "<p>Prenom: " + membre.prenom + "</p>" +
        "<p>Age: " + membre.age + "</p>" +
        "<p>Email: " + membre.email + "</p>" +
        "<p>DateInscription: " + membre.dateInscription + "</p>"
    );
}

function afficherParcoursProposes(membre) {
    $.each( membre.lesParcoursCrees, function( key, value ) {
        $('#parcours_propose').append(
            "<a id="+value.id+">Click ici pour afficher membres Inscrits pour ce parcour en bas</a>" +
            "<p>Parcour ID: " + value.id + "</p>" +
            "<p>Depart : " + value.depart.nom + "</p>" +
            "<p>Arrive : " + value.arrivee.nom + "</p>" +
            "<p>Date : " + value.dateParcours + "</p>" +
            "<p>Nombre Place: " + value.nbPlacesInitiales + "</p>" +
            "<p>Prix: " + value.prix + "</p>" +
            "<p>---------------------------</p>"
        );
    });
}

function afficherParcoursChosis(membre) {
    $.each( membre.lesParcoursChoisis, function( key, value ) {
        $('#parcours_choisis').append(
            "<p>Parcour ID: " + value.id + "</p>" +
            "<p>Depart : " + value.depart.nom + "</p>" +
            "<p>Arrive : " + value.arrivee.nom + "</p>" +
            "<p>Date : " + value.dateParcours + "</p>" +
            "<p>Nombre Place: " + value.nbPlacesInitiales + "</p>" +
            "<p>Prix: " + value.prix + "</p>" +
            "<p>---------------------------</p>"
        );
    });
}

function addEventPourParcours(membre) {
    $("a").click(function(){
        membre.recupererMembresInscrits(this.id,
            function(data){
                $.each( data.membresInscrits, function( key, value ) {
                    $('#membreInscrit_info').append(
                        "<p>Membre Inscrit ID: " + value.id + "</p>" +
                        "<p>Nom : " + value.nom + "</p>" +
                        "<p>Prenom : " + value.prenom + "</p>" +
                        "<p>Email : " + value.email + "</p>" +
                        "<p>---------------------------</p>"
                    );
                });
            },
            function(){

            });
    })
}

$( document ).ready(function() {
    membre1.recupererMembreInfo("al@clu.fr",
        function() {
            afficherMembreInfo(membre1);
            afficherParcoursProposes(membre1);
            addEventPourParcours(membre1);
            afficherParcoursChosis(membre1);
        } ,
        function() {
             //gerer error
        }
    );
});
