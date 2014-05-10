/**
 * Created with IntelliJ IDEA.
 * User: lepeteil
 * Date: 10/05/14
 */

$( document ).ready(function() {
    $.ajax( "/tousLesParcours" )
        .done(function(data) {
            console.log(data);
        })
        .fail(function(error) {
            console.log("error");
        })
});
