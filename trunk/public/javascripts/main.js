/**
 * Created with IntelliJ IDEA.
 * User: lepeteil
 * Date: 13/05/14
 * Time: 21:57
 * To change this template use File | Settings | File Templates.
 */

$(document).bind("ready", function () {
    $("#boutonSeconnecter").bind("click", seconnecter);
    $("#boutonSinscrire").bind("click", sinscrire);
});

function sinscrire(){
    $('#formInscript').modal('show');
}

function seconnecter(){

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
