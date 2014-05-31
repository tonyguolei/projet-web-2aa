/**
 * Created by tonyguolei on 5/23/14.
 */
var rules = {
    depart : {
        identifier : 'depart',
        rules : [{
            type : 'empty',
            prompt : 'La ville de départ est vide'
        }]
    },
    arrivee : {
        identifier : 'arrivee',
        rules : [{
            type : 'empty',
            prompt : "La ville d'arrivée est vide"
        }]
    },
    date : {
        identifier : 'date',
        rules : [{
            type : 'empty',
            prompt : 'La date est vide'
        }]
    },
    heure : {
        identifier : 'heure',
        rules : [{
            type : 'empty',
            prompt : "L'heure est vide"
        }]
    },
    min : {
        identifier : 'min',
        rules : [{
            type : 'empty',
            prompt : 'Les minutes sont vides'
        }]
    },
    prix : {
        identifier : 'prix',
        rules : [{
            type : 'empty',
            prompt : 'Le prix est vide'
        }]
    },
    nbplaces : {
        identifier : 'nbplaces',
        rules : [{
            type : 'empty',
            prompt : 'Le nombre de places est vide'
        }]
    }
};

function handle_submitForm() {
    var formData = {
        'depart': $('input[name=depart]').val(),
        'arrivee': $('input[name=arrivee]').val(),
        'date': $('input[name=date]').val(),
        'heure': $('input[name=heure]').val(),
        'min': $('input[name=min]').val(),
        'prix': $('input[name=prix]').val(),
        'nbplaces': $('input[name=nbplaces]').val()
    };

    $.ajax({
        type : 'POST',
        url : "/proposerParcours",
        data: formData
    })
        .done(function(data) {
            $("#message_success_creer_parcours").show().delay(5000).fadeOut();
        })
        .fail(function (e) {
            $("#message_failed_creer_parcours").show().delay(5000).fadeOut();
        })
};

var settings = {
    inline : true,
    onSuccess : function() {
        event.preventDefault();
        handle_submitForm();
    }
}

$( document ).ready(function() {
    $('#formProproserParcours').form(rules, settings);
})