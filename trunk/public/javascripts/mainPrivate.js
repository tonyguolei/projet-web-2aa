/**
 * Created with IntelliJ IDEA.
 * User: lepeteil
 * Date: 13/05/14
 * Time: 21:57
 * To change this template use File | Settings | File Templates.
 */


$(document).bind("ready", function () {
    $('.button.consulter').bind("click", consulterParcours);
    $('.button.annuler').bind("click", annulerParcours);
    $('.button.desinscrire').bind("click", seDesinscrireParcours);
});
/*----------------------EVENEMENTS--------------------------*/
function consulterParcours(){
    var tr = this.parentNode.parentNode.parentNode;
    var idp = tr.getElementsByTagName('input')[0].value;
    var parcours1 = new ProjetWeb.Parcours();
    parcours1.recupererParcoursInfo(idp,
        function() {
            afficherParcoursInfo(parcours1);
        },
        function() {
            //gerer erreur
        });
}
function annulerParcours(){
    var tr = this.parentNode.parentNode.parentNode;
    var idp = tr.getElementsByTagName('input')[0];
}
function seDesinscrireParcours(){
    var tr = this.parentNode.parentNode.parentNode;
    var idp = tr.getElementsByTagName('input')[0];
}

/*----------------------FONCTION----------------------------*/
function afficherParcoursInfo(parcours) {

    $(".raised.segment").empty();
    $('.raised.segment').append(
        "<p>ID: " + parcours.id + "</p>" +
            "<p>depart: " + parcours.depart.nom + "</p>" +
            "<p>arrivee: " + parcours.arrivee.nom + "</p>"+
            "<p>createur: " + parcours.createur.nom + "</p>"+
            "<p>date: " + parcours.dateparcours + "</p>"+
            "<p>heure: " + parcours.heure + "</p>"+
            "<p>min: " + parcours.min + "</p>" +
            "<p>nbplacesinit: " + parcours.nbplacesinitiales + "</p>"
    );
}