/**
 * Created by tonyguolei on 5/23/14.
 */
var rules = {
    depart : {
        identifier : 'depart',
        rules : [{
            type : 'empty',
            prompt : 'depart est vide'
        }]
    },
    arrivee : {
        identifier : 'arrivee',
        rules : [{
            type : 'empty',
            prompt : 'arrivee est vide'
        }]
    },
    date : {
        identifier : 'date',
        rules : [{
            type : 'empty',
            prompt : 'date est vide'
        }]
    },
    heure : {
        identifier : 'heure',
        rules : [{
            type : 'empty',
            prompt : 'heure est vide'
        }]
    },
    min : {
        identifier : 'min',
        rules : [{
            type : 'empty',
            prompt : 'min est vide'
        }]
    },
    prix : {
        identifier : 'prix',
        rules : [{
            type : 'empty',
            prompt : 'prix est vide'
        }]
    },
    nbplaces : {
        identifier : 'nbplaces',
        rules : [{
            type : 'empty',
            prompt : 'nbplaces est vide'
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
            console.log("proproser parcour error");
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