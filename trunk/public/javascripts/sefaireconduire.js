/**
 * Created with IntelliJ IDEA.
 * User: lepeteil
 * Date: 10/05/14
 */

$( document ).ready(function() {
    searchCity();
    $("#boutonChercherParcours").bind("click", searchTravel());
});


function searchTravel(){
    //TODO Recuperer les champs du form
    $.ajax( "/tousLesParcours" )
        .done(function(data) {
            console.log(data);
        })
        .fail(function(error) {
            console.log("error");
        })
}

function searchCity(){
    $.ajax( "/toutesLesVilles" )
        .done(function(data) {
            console.log(data);
        })
        .fail(function(error) {
            console.log("error");
        })
}



