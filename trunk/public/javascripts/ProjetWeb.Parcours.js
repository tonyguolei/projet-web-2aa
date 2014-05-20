/**
 * Created with IntelliJ IDEA.
 * User: lepeteil
 * Date: 14/05/14
 * Time: 17:54
 * To change this template use File | Settings | File Templates.
 */

/*La Classe Parcours*/

/*NameSpace*/
var ProjetWeb = {}

ProjetWeb.Parcours = function() {
    /*propriétés de la classe Parcours*/
    this.id = null;
    this.depart = null;
    this.arrivee = null;
    this.prix = null;
    this.nbplacesinitiales = null;
    this.dateparcours = null;
    this.heure = null;
    this.min = null;
    this.supprime = null;
    this.createur = null;
    this.membresInscrits = {};

    /*constructeur de la classe Parcours*/
    this.constructor = function(id, depart, arrivee, prix, nbplacesinitiales,
                                dateparcours, heure, min, supprime, createur, membresinscrits){
        this.id = id;
        this.depart = depart;
        this.arrivee = arrivee;
        this.prix = prix;
        this.heure = heure;
        this.min = min;
        this.nbplacesinitiales = nbplacesinitiales;
        this.dateparcours = dateparcours;
        this.supprime = supprime;
        this.createur = createur;
        this.membresInscrits = membresinscrits;
    };
};

/*methodes pour la classe Parcours*/
ProjetWeb.Parcours.prototype = {

    recupererParcoursInfo: function(parcour_id, cb_success, cb_error) {
        var self = this;
        $.ajax({
            url: "/recupererParcoursInfo",
            data: {id: parcour_id}
        })
            .done(function(data) {
                console.log(data);
                self.constructor(data.id,data.depart,data.arrivee,data.prix,
                    data.nbplacesinitiales,data.dateparcours,data.heure,
                    data.min,data.supprime,data.createur,data.membresinscrits);
                cb_success(data);
            })
            .fail(function(error) {
                console.log("error recupererParcoursInfo");
                cb_error(error);
            })
    },

    recupererMembresInscrits: function(parcour_id, cb_success, cb_error) {
        var self = this;
        $.ajax( {
            url: "/recupererMembreInscrit",
            data: { id: parcour_id }
        } )
            .done(function(data) {
                console.log(data);
                cb_success(data);
            })
            .fail(function(error) {
                console.log("error recupererMembreInscrit");
                cb_error(error);
            })
    },

    recupererMembreCreateur: function(parcour_id, cb_success, cb_error) {
        var self = this;
        $.ajax( {
            url: "/recupererMembreCreateur",
            data: { id: parcour_id }
        } )
            .done(function(data) {
                console.log(data);
                cb_success(data);
            })
            .fail(function(error) {
                console.log("error recupererMembreCreateur");
                cb_error(error);
            })
    }

}