/**
 * Created with IntelliJ IDEA.
 * User: lepeteil
 * Date: 13/05/14
 * Time: 21:57
 * To change this template use File | Settings | File Templates.
 */
var init;

$(document).bind("ready", function () {
    $("#boutonSinscrire").bind("click", sinscrire);
    $("#conduire").bind("click", conduire);
    $("#sefaireconduire").bind("click", sefaireconduire);
    $("#quisommesnous").bind("click", quisommesnous);
    $("#contact").bind("click", contact);
    $("#nouspublic").hide();
    $("#contactpublic").hide();
    $("#conduirepublic").hide();
    $("#sefaireconduirepublic").hide();

    //affecter valeur par defaut pour sexe
    sexe_value = $('#default_sexe_value').text();
    $('#sexe_dropdown').dropdown({
        onChange: function(val) {
            sexe_value = val;
        }
    });
    $('#formInscript').form(rules_inscription, settings_inscription);
    $('#formconnexion').form(rules_connexion, settings_connexion);
});

/*----------------------LIES A DES EVENEMENTS--------------------------*/
function sinscrire(){
    $('.small.modal').modal('show');
    $("#date").datepicker({
        dateFormat: 'dd/mm/yy'
    });
}
function obtenirDate(){
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
function reinitialiserChamps(){
    document.getElementsByName("depart")[0].value = "";
    document.getElementsByName("arrivee")[0].value = "";
    obtenirDate();
    init = true;
    rechercherParcours();
}

/*----------------------FONCTION AFFICHAGE DES PAGES----------------------------*/

function sefaireconduire(){
    $("#accueilpublic").hide();
    $("#nouspublic").hide();
    $("#contactpublic").hide();
    $("#conduirepublic").hide();
    $('#sefaireconduirepublic').empty();

    var tableauParcours = '<table class="ui sortable table segment" id="listeParcours">'+
        '<thead><tr>'+
        '<th>Départ</th>'+
        '<th>Arrivée</th>'+
        '<th>Date</th>'+
        '<th>Nombre de places restantes</th>'+
        '<th>Prix</th>'+
        '<th>Options</th>'+
        '</tr></thead>'+
        '<tbody id="tabcontenu">'+
        '</tbody>'+
        '</table>';
    $('#sefaireconduirepublic').append(
        '<div class="ui teal segment">'+
            '<div class="ui teal ribbon label">Se faire conduire</div>'+
            '<div id="rechercheParcours">'+
                '<div class="ui fluid form segment">'+
                '<div class="ui three fields message">'+
                '<div class="field">'+
                '<label>Ville de départ</label>'+
                '<input placeholder="Exemple : Grenoble, 38000" type="text" name="depart">'+
                '</div>'+
                '<div class="field">'+
                '<label>Ville d\'arrivée</label>'+
                '<input placeholder="Exemple : Annecy, 74000" type="text" name="arrivee">'+
                '</div>'+
                '<div class="field">'+
                '<label>Date</label>'+
                '<input type="text" name="date" id="date">'+
                '</div>'+
                '<button class="ui small button" id="boutonChercherParcours" name="boutonChercherParcours">Chercher</button>'+
                '<button class="ui small button" id="boutonReinitChercher" name="boutonReinitChercher">Reinitialiser</button>'+
                '<div id="message_failed_search" class="ui primary inverted red segment">Aucun parcours n\'a été trouvé.</div>'+
                '</div>'+
                '</div>'+ tableauParcours +
                '</div>'+
            '</div>'+
            '<div id="affichageParcours">'+
            '</div>'
    );
    init = true;
    obtenirDate();
    rechercherParcours();
    $('#sefaireconduirepublic').show();
    $("#boutonChercherParcours").bind("click", rechercherParcours);
    $("#boutonReinitChercher").bind("click", reinitialiserChamps);
}
function conduire(){
    $("#conduirepublic").show();
    $("#accueilpublic").hide();
    $("#nouspublic").hide();
    $("#contactpublic").hide();
    $("#sefaireconduirepublic").empty();
}
function quisommesnous(){
    $("#nouspublic").show();
    $("#conduirepublic").hide();
    $("#accueilpublic").hide();
    $("#contactpublic").hide();
    $("#sefaireconduirepublic").empty();
}
function contact(){
    $("#contactpublic").show();
    $("#accueilpublic").hide();
    $("#nouspublic").hide();
    $("#sefaireconduirepublic").empty();
}
/*----------------------FONCTION DE RECHERCHE DE PARCOURS--------------------------*/
function rechercherParcours(){
    var depart = document.getElementsByName("depart")[0].value;
    var arrivee = document.getElementsByName("arrivee")[0].value;
    var date = document.getElementsByName("date")[0].value;

    if(init==true)
        init=false;
    else if (depart=="" & arrivee==""){
        return;
    }

    $.ajax({
        url: "/chercherParcours",
        data: {depart:depart,
            arrivee:arrivee,
            date:date}
    })
        .done(function(data) {
            if(data.length<=0){
                $("#message_failed_search").show().delay(5000).fadeOut();
            }
            else{
                $("#tabcontenu").empty();
                $.each(data, function (key, value) {
                    $("#tabcontenu").append(
                        "<tr>"+
                            "<td>"+ value.depart.nom +"</td>"+
                            "<td>"+ value.arrivee.nom +"</td>"+
                            "<td>" +
                            "<div class='ui divided list'>" +
                            "<div>"+value.dateParcours+"</div>"+
                            "<div>"+value.heure + "h" + value.min + "</div>" +
                            "</div>" +
                            "</td>"+
                            "<td>" + value.nbPlacesInitiales+"</td>"+
                            "<td>"+ value.prix +"</td>"+
                            "<td><div class='ui small negative disabled button'>Réserver</div></td>"+
                            "</tr>"
                    );
                });
            }
        })
        .fail(function(error) {
            console.log("error");
        })
}

/*----------------------GESTION INSCRIPTION----------------------------*/

var rules_inscription = {
    prenom : {
        identifier : 'prenom',
        rules : [{
            type : 'empty',
            prompt : 'Le prénom est vide'
        }]
    },
    nom : {
        identifier : 'nom',
        rules : [{
            type : 'empty',
            prompt : "Le nom est vide"
        }]
    },
    date : {
        identifier : 'date',
        rules : [{
            type : 'empty',
            prompt : 'La date de naissance est vide'
        }]
    },
    email : {
        identifier : 'email',
        rules : [{
            type : 'empty',
            prompt : 'L\'email est vide'
        }]
    },
    motdepasse : {
        identifier : 'motdepasse',
        rules : [
            {
                type : 'empty',
                prompt : "Le mot de passe est vide"
            },
            {
                type   : 'length[6]',
                prompt : 'Le mot de passe est au mimimum de 6 lettres'
            }

        ]
    }
};
var sexe_value;

function handle_submitForm_inscription() {
    //crypter mot de passe en sha1
    var mdp_sha1 = CryptoJS.SHA1($('input[name=motdepasse]').val()).toString();
    var formData = {
        'email' : $('input[name=email]').val(),
        'prenom': $('input[name=prenom]').val(),
        'nom': $('input[name=nom]').val(),
        'date': $('input[name=date]').val(),
        'sexe': sexe_value,
        'motdepasse':mdp_sha1
    };
    $.ajax({
        type : 'POST',
        url : "/sinscrire",
        data: formData
    })
        .done(function(data) {
            $("#message_success_sinscrire").show().delay(5000).fadeOut();
            setTimeout(function(){$('.small.modal').modal('hide');},2000);
        })
        .fail(function (e) {
            $("#message_failed_sinscrire").show().delay(5000).fadeOut();
        })
};

var settings_inscription = {
    inline : true,
    onSuccess : function(event) {
        event.preventDefault();
        handle_submitForm_inscription();
    }
}

/*----------------------GESTION CONNEXION----------------------------*/

var rules_connexion = {
    emailform : {
        identifier : 'emailform',
        rules : [{
            type : 'empty',
            prompt : 'L\'email est vide'
        }]
    },
    motdepasseform : {
        identifier : 'motdepasseform',
        rules : [
            {
                type : 'empty',
                prompt : "Le mot de passe est vide"
            },
            {
                type   : 'length[6]',
                prompt : 'Le mot de passe est au mimimum de 6 lettres'
            }

        ]
    }
};

function handle_submitForm_connexion() {
    //cripter mot de passe en sha1
    var mdp_sha1 = CryptoJS.SHA1($('input[name=motdepasseform]').val()).toString();
    $('#password_connexion').val(mdp_sha1);
};

var settings_connexion = {
    inline : true,
    onSuccess : function() {
        handle_submitForm_connexion();
    }
}
