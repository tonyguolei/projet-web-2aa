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

    var list = document.getElementsByClassName("ui divided list")[0];
    $(".ui divided list .item").remove();

    $.ajax( "/tousLesParcours" )
        .done(function(data) {
            $.each(data, function (key, value) {
                var newdivitem = document.createElement('div');
                newdivitem.setAttribute('class', 'item');
                var newdivbutton = document.createElement('div');
                newdivbutton.setAttribute('class', 'right floated tiny teal ui button');
                newdivbutton.innerHTML = "Voir";
                var newdivcontent = document.createElement('div');
                newdivcontent.setAttribute('class', 'content');
                var newdivheader = document.createElement('div');
                newdivheader.setAttribute('class', 'header');
                newdivheader.innerHTML = value.dep.nom + ' ' +  value.arr.nom + ' ' + value.dateParcours;

                list.appendChild(newdivitem);
                newdivitem.appendChild(newdivbutton);
                newdivitem.appendChild(newdivcontent);
                newdivcontent.appendChild(newdivheader);
            });

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



