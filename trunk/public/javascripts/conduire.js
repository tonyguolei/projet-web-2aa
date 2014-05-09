/**
 * Created by tonyguolei on 5/9/14.
 */
$( document ).ready(function() {
    $.ajax( "/testAjax" )
        .done(function(data) {
            console.log(data);
        })
        .fail(function(error) {
            console.log("error");
        })
});
