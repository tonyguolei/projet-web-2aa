/**
 * Created by tonyguolei on 5/10/14.
 */

/*La Classe Membre*/

/*NameSpace*/
var ProjetWeb = {}

ProjetWeb.Membre = function() {
    /*properties of class Membre*/
    this.id = null;
    this.nom = null;
    this.prenom = null;
    this.dateNaissance =null;
    this.email = null;
    this.sexe = null;
    this.dateInscription = null;
    this.lesParcoursCrees = {};
    this.lesParcoursChoisis = {};

    /*consctructor of class Membre*/
    this.constructor = function(id, nom, prenom, dateNaissance, email, sexe,dateInscription, lesParcoursCrees, lesParcoursChoisis){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.sexe = sexe;
        this.dateInscription = dateInscription;
        this.lesParcoursCrees = lesParcoursCrees;
        this.lesParcoursChoisis = lesParcoursChoisis;
    };
};

/*methodes of class Membre*/
ProjetWeb.Membre.prototype = {
    recupererMembreInfo: function(cb_success, cb_error) {
        var self = this;
        $.ajax({
            url: "/recupererMembreInfo"
        })
            .done(function(data) {
                self.constructor(data.id, data.nom, data.prenom, data.dateNaissance, data.email,data.sexe,
                    data.dateInscription, data.lesParcoursCrees, data.lesParcoursChoisis);
                cb_success(data);
            })
            .fail(function(error) {
                console.log("error recupererMembreInfo");
                cb_error(error);
            })
    },

    reserverParcours : function(parcour_id, cb_success, cb_error) {
        var self = this;
        $.ajax( {
            url: "/reserverParcours",
            data: { id: parcour_id }
        } )
            .done(function(data) {
                console.log(data);
                self.constructor(data.id, data.nom, data.prenom, data.dateNaissance, data.email,data.sexe,
                    data.dateInscription, data.lesParcoursCrees, data.lesParcoursChoisis);
                cb_success(data);
            })
            .fail(function(error) {
                console.log("erreur reserverParcours");
                cb_error(error);
            })
    },

    annulerReservationParcours : function(parcour_id, cb_success, cb_error) {
        var self = this;
        $.ajax( {
            url: "/annulerReservation",
            data: { id: parcour_id }
        } )
            .done(function(data) {
                //self.constructor(data.id, data.nom, data.prenom, data.dateNaissance, data.email,data.sexe,
                    //data.dateInscription, data.lesParcoursCrees, data.lesParcoursChoisis);
                self.lesParcoursChoisis = data;
                cb_success(data);
            })
            .fail(function(error) {
                console.log("erreur annulerReservation");
                cb_error(error);
            })
    },

    supprimerParcours : function(parcour_id, cb_success, cb_error) {
        var self = this;
        $.ajax( {
            url: "/supprimerParcours",
            data: { id: parcour_id }
        } )
            .done(function(data) {
                //self.constructor(data.id, data.nom, data.prenom, data.dateNaissance, data.email,data.sexe,
                    //data.dateInscription, data.lesParcoursCrees, data.lesParcoursChoisis);
                self.lesParcoursCrees = data;
                cb_success(data);
            })
            .fail(function(error) {
                console.log("error supprimerParcours");
                cb_error(error);
            })
    },
    reactiverParcours : function(parcour_id, cb_success, cb_error) {
        var self = this;
        $.ajax( {
            url: "/reactiverParcours",
            data: { id: parcour_id }
        } )
            .done(function(data) {
                //self.constructor(data.id, data.nom, data.prenom, data.dateNaissance, data.email,data.sexe,
                    //data.dateInscription, data.lesParcoursCrees, data.lesParcoursChoisis);
                self.lesParcoursCrees = data;
                cb_success(data);
            })
            .fail(function(error) {
                console.log("error reactiverParcours");
                cb_error(error);
            })
    }

}