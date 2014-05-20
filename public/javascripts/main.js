/**
 * Created with IntelliJ IDEA.
 * User: lepeteil
 * Date: 13/05/14
 * Time: 21:57
 * To change this template use File | Settings | File Templates.
 */

$(document).bind("ready", function () {
    $("#boutonSinscrire").bind("click", sinscrire);
    //$("#boutonValidationSinscrire").bind("click", validerInscription);
});

function sinscrire(){
    $('.small.modal').modal('show');
    $('.ui.selection.dropdown')
        .dropdown()
    ;
}

function validerInscription(){
    //TODO Verifier la validite des champs saisis
    var nom = $("#nom").val();
    var prenom = $("#prenom").val();
    var age = $("#age").val();
    var mdp = $("#motdepasse").val();
    var email = $("#email").val();
    var sexe = $("#sexe").val();


    $.ajax({
        url: "/sinscrire",
        data: {nom:nom,
            prenom:prenom,
            mdp:mdp,
            age:age,
            email:email,
            sexe:sexe}
    })
        .done(function(data) {
            if(data.length<=0){
                //TODO Afficher message pas trouvé
            }
            else{

            }
        })
        .fail(function(error) {
            console.log("error");
        })
}

    $('#formInscript')
        .form({
            prenom: {
                identifier  : 'prenom',
                rules: [
                    {
                        type   : 'empty',
                        prompt : 'Merci de saisir votre prénom!'
                    }
                ]
            },
            nom: {
                identifier  : 'nom',
                rules: [
                    {
                        type   : 'empty',
                        prompt : 'Merci de saisir votre nom!'
                    }
                ]
            },
            motdepasse: {
                identifier : 'motdepasse',
                rules: [
                    {
                        type   : 'empty',
                        prompt : 'Merci de saisir votre mot de passe!'
                    },
                    {
                        type   : 'Taille <= 20',
                        prompt : 'Votre mot de passe doit avoir 20 caractères.'
                    }
                ]
            },
            age: {
                identifier : 'age',
                rules: [
                    {
                        type   : 'empty',
                        prompt : 'Votre age doit être un chiffre.'
                    }
                ]
            }
        });
