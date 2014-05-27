/**
 * Created with IntelliJ IDEA.
 * User: lepeteil
 * Date: 13/05/14
 * Time: 21:57
 * To change this template use File | Settings | File Templates.
 */

$(document).bind("ready", function () {
    obtenirDate();
    $("#boutonSinscrire").bind("click", sinscrire);
    $('#formInscript').form(rules, settings);
    //$("#boutonValidationSinscrire").bind("click", validerInscription);
});

/*----------------------LIES A DES EVENEMENTS--------------------------*/
function sinscrire(){
    $('.small.modal').modal('show');
    $('#sexe_dropdown').dropdown({
        onChange: function(val) {
            sexe_value = val;
        }
    });
    $('.ui.selection.dropdown').dropdown();
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
        'email' : $('input[name=email]').attr('placeholder'),
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
