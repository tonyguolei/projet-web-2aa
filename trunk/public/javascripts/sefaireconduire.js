/**
 * Created with IntelliJ IDEA.
 * User: lepeteil
 * Date: 10/05/14
 */

$( document ).ready(function() {

    function searchTrip(){
        $("#boutonChercherParcours").click(function(){
            //TODO Recuperer les champs du form
            alert("boutonChercherParcours");
            $.ajax( "/tousLesParcours" )
                .done(function(data) {
                    console.log(data);
                })
                .fail(function(error) {
                    console.log("error");
                })
        })
    }


});



