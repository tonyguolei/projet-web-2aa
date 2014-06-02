/**
 * Created by tonyguolei on 5/25/14.
 */

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
    new_password : {
        identifier : 'new_password',
        rules : [
            {
                type : 'empty',
                prompt : "Le nouveau mot de passe est vide"
            },
            {
                type   : 'length[6]',
                prompt : 'Le mot de passe est au mimimum 6 lettres'
            }

        ]
    },
    confirm_password : {
        identifier : 'confirm_password',
        rules : [
            {
                type : 'empty',
                prompt : 'Le confirmation mot de passe est vide'
            },
            {
                type   : 'match[new_password]',
                prompt : 'Le mot de passe n\'est pas cohérant'
            }
        ]
    }
};

var sexe_value;
var mdp_sha1;
function handle_submitForm() {
    //cripter mot de passe en sha1
    var formData = {
        'email' : $('input[name=email]').attr('placeholder'),
        'prenom': $('input[name=prenom]').val(),
        'nom': $('input[name=nom]').val(),
        'date': $('input[name=date]').val(),
        'sexe': sexe_value,
        'new_password': mdp_sha1
    };

    $.ajax({
        type : 'POST',
        url : "/modifierMonProfil",
        data: formData
    })
        .done(function(data) {
            $("#message_success_modify_profil").show();
            setTimeout(function()
            {
                $("#message_success_modify_profil").hide();
            }, 5000);

            //$("#message_success_modify_profil").show().delay(5000).fadeOut();
        })
        .fail(function (e) {
            //$("#message_failed_modify_profil").show().delay(5000).fadeOut();
        })
};

var settings = {
    inline : true,
    onSuccess : function(event) {
        //event.preventDefault();
        handle_submitForm();
        alert("ok");
    }
}

$( document ).ready(function() {
    sexe_value = $('#default_sexe_value').text();
    mdp_sha1 = $('#password_monprofil').val();
    $( "#password_monprofil" ).keyup(function() {
        mdp_sha1 = CryptoJS.SHA1($('#password_monprofil').val()).toString();
    });
    $('#sexe_dropdown').dropdown({
        onChange: function(val) {
            sexe_value = val;
        }
    });
    $('#formProfil').form(rules, settings);
})