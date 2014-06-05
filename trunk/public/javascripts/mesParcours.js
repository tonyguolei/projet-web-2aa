/**
 * Created with IntelliJ IDEA.
 * User: lepeteil
 * Date: 31/05/14
 * Time: 15:48
 * To change this template use File | Settings | File Templates.
 */

$(document).bind('ready', function () {
    $('#parcoursactuels').bind('click', afficherMesFutursParcours);
    $('#parcoursprecedents').bind('click', afficherMesParcoursPrecedents);
    afficherMesFutursParcours();
    $('.button.consulter').bind('click', consulterParcoursMembre);
    $('.button.desinscrire').bind('click', annulerReservationParcours);
    $('.button.annuler').bind('click', supprimerParcoursCree);
    $('.button.reactiver').bind('click', reactiverParcoursCree);
});

function consulterParcoursMembre() {
    //Consulter un parcours (créé ou réservé)
    var tr = this.parentNode.parentNode.parentNode;
    var idp = tr.getElementsByTagName('input')[0].value;
    var parcours1 = new ProjetWeb.Parcours();
    parcours1.recupererParcoursInfo(idp,
        function () {
            afficherCarte(parcours1);
            afficherParcoursInfo(parcours1);
        },
        function () {
            //gerer erreur
        });
}
function annulerReservationParcours() {
    //Annuler sa reservation
    if (confirm("Etes-vous sur de vouloir vous désinscrire de ce parcours ?")) {
        var tr = this.parentNode.parentNode.parentNode;
        var idp = tr.getElementsByTagName('input')[0].value;
        var membre1 = new ProjetWeb.Membre();
        membre1.annulerReservationParcours(idp,
            function () {
                afficherMesParcoursChoisis(membre1);
            },
            function () {
                //gerer erreur
                console.log("erreur fonction");
            });
    }

}
function supprimerParcoursCree() {
    //Annuler la creation du parcours
    if (confirm("Etes-vous sur de vouloir supprimer ce parcours ?")) {
        var tr = this.parentNode.parentNode.parentNode;
        var idp = tr.getElementsByTagName('input')[0].value;
        var membre1 = new ProjetWeb.Membre();
        membre1.supprimerParcours(idp,
            function () {
                afficherMesParcoursCrees(membre1);
            },
            function () {
                //gerer erreur
                console.log("erreur fonction");
            });
    }
}
function reactiverParcoursCree() {
    //Annuler la suppression du parcours cree
    if (confirm("Etes-vous sur de vouloir réactiver ce parcours ?")) {
        var tr = this.parentNode.parentNode.parentNode;
        var idp = tr.getElementsByTagName('input')[0].value;
        var membre1 = new ProjetWeb.Membre();
        membre1.reactiverParcours(idp,
            function () {
                afficherMesParcoursCrees(membre1);
            },
            function () {
                //gerer erreur
                console.log("erreur fonction");
            });
    }
}
/*----------------------FONCTION----------------------------*/
function afficherMesFutursParcours(){
    $('#parcoursprecedents').attr("class","item");
    $(this).attr("class","active item");
    $("#affichageListeParcours").show();
    $('#listeParcoursCrees_2').hide();
    $('#listeParcoursChoisis_2').hide();
    $('#listeParcoursCrees').show();
    $('#listeParcoursChoisis').show();
}

function afficherMesParcoursPrecedents(){
    $('#parcoursactuels').attr("class","item");
    $(this).attr("class","active item");
    $("#affichageListeParcours").show();
    $('#listeParcoursCrees').hide();
    $('#listeParcoursChoisis').hide();
    $('#listeParcoursCrees_2').show();
    $('#listeParcoursChoisis_2').show();
}

function afficherMesParcoursCrees(membre) {
    $('#listeParcoursCrees').empty();

    for (var j = 0; j < membre.lesParcoursCrees.length; j++) {
        var pcrees = membre.lesParcoursCrees[j];

        if (!pcrees.supprime) {
            var listoptions = '<div class="ui small purple button annuler">' +
                '<i class="remove icon"></i>Annuler' +
                '</div>';
        }
        else {
            var listoptions = '<div class="ui small red button reactiver">' +
                '<i class="backward icon"></i>Réactiver' +
                '</div>';
        }
        var nbplacesrestantes = pcrees.nbPlacesInitiales - pcrees.membresInscrits.length;

        $('#listeParcoursCrees').append(
            '<tr>' +
                '<input type="hidden" name="pcreesid" value="' + pcrees.id + '"/>' +
                '<td><strong class=\'titre_parcour_mobile\'>Départ: &nbsp;&nbsp;&nbsp;&nbsp;</strong>' + pcrees.depart.nom + '</td>' +
                '<td><strong class=\'titre_parcour_mobile\'>Arrivée: &nbsp;&nbsp;</strong>' + pcrees.arrivee.nom + '</td>' +
                '<td>' +
                '<div class="ui divided list">' +
                '<div><strong class=\'titre_parcour_mobile\'>Date: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</strong>' + afficherDate(pcrees.dateParcours) + '</div>' +
                '<div><strong class=\'titre_parcour_mobile\'>heure: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</strong>' + pcrees.heure + 'h' + pcrees.min + '</div>' +
                '</div>' +
                '</td>' +
                '<td><strong class=\'titre_parcour_mobile\'>Place: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</strong>' + nbplacesrestantes + '</td>' +
                '<td><strong class=\'titre_parcour_mobile\'>Prix: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</strong>' + pcrees.prix + '</td>' +
                '<td>' +
                '<div class="ui vertical buttons">' +
                '<div class="ui small blue button consulter">' +
                '<i class="unhide icon"></i>Consulter' +
                '</div>' +
                listoptions +
                '</div>' +
                '</td>' +
                '</tr>'
        );
    }
    $('.button.annuler').bind('click', supprimerParcoursCree);
    $('.button.reactiver').bind('click', reactiverParcoursCree);
}

function afficherMesParcoursChoisis(membre) {
    $('#listeParcoursChoisis').empty();

    for (var j = 0; j < membre.lesParcoursChoisis.length; j++) {
        var pchoisis = membre.lesParcoursChoisis[j];
        if (pchoisis.supprime == true) {
            var listoptions = '<div class="ui small negative disabled button">' +
                '<i class="warning users icon"></i>Annulé' +
                '</div>';
        } else {
            var listoptions = '<div class="ui small positive button desinscrire">' +
                '<i class="remove icon"></i>Me désinscrire' +
                '</div>';
        }

        $('#listeParcoursChoisis').append(
            '<tr>' +
                '<input type="hidden" name="pcreesid" value="' + pchoisis.id + '"/>' +
                '<td><strong class=\'titre_parcour_mobile\'>Départ: &nbsp;&nbsp;&nbsp;&nbsp;</strong>' + pchoisis.depart.nom + '</td>' +
                '<td><strong class=\'titre_parcour_mobile\'>Arrivée: &nbsp;&nbsp;</strong>' + pchoisis.arrivee.nom + '</td>' +
                '<td>' +
                '<div class="ui divided list">' +
                '<div><strong class=\'titre_parcour_mobile\'>Date: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</strong>' + afficherDate(pchoisis.dateParcours) + '</div>' +
                '<div><strong class=\'titre_parcour_mobile\'>heure: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</strong>' + pchoisis.heure + 'h' + pchoisis.min + '</div>' +
                '</div>' +
                '</td>' +
                '<td><strong class=\'titre_parcour_mobile\'>Prix: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</strong>' + pchoisis.prix + '</td>' +
                '<td>' +
                '<div class="ui vertical buttons">' +
                '<div class="ui small blue button consulter">' +
                '<i class="unhide icon"></i>Consulter' +
                '</div>' +
                listoptions +
                '</div>' +
                '</td>' +
                '</tr>'
        );

    }
    $('.button.consulter').bind('click', consulterParcoursMembre);
    $('.button.desinscrire').bind('click', annulerReservationParcours);
}