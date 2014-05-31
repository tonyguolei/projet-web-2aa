/**
 * Created with IntelliJ IDEA.
 * User: lepeteil
 * Date: 13/05/14
 * Time: 21:57
 * To change this template use File | Settings | File Templates.
 */
var init;

$(document).bind("ready", function () {
    testBrowser();

    $("#boutonSinscrire").bind("click", sinscrire);

    $("#conduire").bind("click", conduire);
    $("#sefaireconduire").bind("click", sefaireconduire);
    $("#accueil").bind("click", accueil);
    $("#quisommesnous").bind("click", quisommesnous);
    $("#contact").bind("click", contact);

    //affectuer valeur par defaut pour sexe
    sexe_value = $('#default_sexe_value').text();
    $('#sexe_dropdown').dropdown({
        onChange: function(val) {
            sexe_value = val;
        }
    });
    $('#formInscript').form(rules, settings);
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

function testBrowser(){
    if ($.browser.mozilla) {
        alert("Vous etre en train d'utiliser Firefox, merci d'utiliser chrome");
    } else if ($.browser.msie) {
        alert("Vous etre en train d'utiliser IE, merci d'utiliser chrome");
    }
}
/*----------------------FONCTION----------------------------*/

function accueil(){
    $('#contenu').empty();
    $('#contenu').append(
        '<div class="ui green segment">'+
            '<div class="ui green ribbon label">Accueil</div>'+
            'Mettre du blabla sur le site'+
            "(Qu'est-ce que c'est? créé qd? images...)"+
            '</div>'
    );
}
function sefaireconduire(){
    $('#contenu').empty();
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
    $('#contenu').append(
        '<div class="ui teal segment">'+
            '<div class="ui teal ribbon label">Se faire conduire</div>'+
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
            '<div id="message_failed_search" class="ui primary inverted red segment">Aucun parcours n\'a été trouvé.</div>'+
            '</div>'+
            '</div>'+ tableauParcours +
            '</div>');
    init = true;
    obtenirDate();
    rechercherParcours();
    $("#boutonChercherParcours").bind("click", rechercherParcours);
}
function conduire(){
    $('#contenu').empty();
    $('#contenu').append(
        '<div class="ui blue segment">'+
            '<div class="ui blue ribbon label">Conduire</div>'+
            '</div>'
    );
}
function quisommesnous(){
    $('#contenu').empty();
    $('#contenu').append(
        '<div class="ui purple segment">'+
            '<div class="ui purple ribbon label">Qui sommes-nous?</div>'+
            'Schéma équipe avec photo + description  !!'+
            '</div>'
    );
}
function contact(){
    $('#contenu').empty();
    $('#contenu').append(
        '<div class="ui red segment">'+
            '<div class="ui red ribbon label">Contact</div>'+
            'Adresse / Société / Numéro tel ...'+
            '</div>'
    );
}
/*----------------------FONCTION DE RECHERCHE DE PARCOURS--------------------------*/
function rechercherParcours(){
    var depart = document.getElementsByName("depart")[0].value;
    var arrivee = document.getElementsByName("arrivee")[0].value;
    var date = document.getElementsByName("date")[0].value;

    if(init==true)
        init=false;
    else if (depart=="" & arrivee==""){
        //TODO Aucune saisie
        alert("aucune saisie");
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
                            "<div class='item'>"+value.dateParcours+"</div>"+
                            "<div class='item'>"+value.heure + "h" + value.min + "</div>" +
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

/*----------------------FONCTION----------------------------*/

var rules = {
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

function handle_submitForm() {
    var formData = {
        'email' : $('input[name=email]').val(),
        'prenom': $('input[name=prenom]').val(),
        'nom': $('input[name=nom]').val(),
        'date': $('input[name=date]').val(),
        'sexe': sexe_value,
        'motdepasse': $('input[name=motdepasse]').val()
    };
    $.ajax({
        type : 'POST',
        url : "/sinscrire",
        data: formData
    })
        .done(function(data) {
            $("#message_success_modify_profil").show().delay(5000).fadeOut();
        })
        .fail(function (e) {
            $("#message_failed_modify_profil").show().delay(5000).fadeOut();
        })
};

var settings = {
    inline : true,
    onSuccess : function() {
        event.preventDefault();
        handle_submitForm();
    }
}
