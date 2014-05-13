/**
 * Created with IntelliJ IDEA.
 * User: lepeteil
 * Date: 13/05/14
 * Time: 21:57
 * To change this template use File | Settings | File Templates.
 */

$(document).bind("ready", function () {
    $('.modal')
        .modal('hidden');
    ;
    $("#boutonSeconnecter").bind("click", connectUser);
    $("#boutonSinscrire").bind("click", register);
});

function register(){
    $('.modal').modal('show');
}

function connectUser(){
    alert("connexion user");
}