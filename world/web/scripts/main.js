require.config({
    baseUrl: 'scripts',
    paths: {
        "backbone": "pluggins/backbone",
        "underscore": "pluggins/underscore",
        "scripts": "scripts",
        "googleMapsLoader": 'pluggins/google-maps-loader',
        "jqueryui": "pluggins/jquery-ui-1.10.4.custom",
        "text": "pluggins/text",
        "css": "pluggins/css.min",
        "layout": "pluggins/jquery.layout-latest",
        "ich": "pluggins/ICanHaz.min",
        "cookie": "pluggins/jquery.cookie",
        "bootstrap": "pluggins/bootstrap",
        "jquery.ui.widget": "pluggins/jquery.ui.widget",
        "fileupload": "pluggins/jquery.fileupload",
        "dynatree": "pluggins/jquery.dynatree",
        "modernizr": "pluggins/modernizr.custom"

    },
    shim: {
        'backbone': {
            deps: ['jquery', 'underscore'],
            exports: 'Backbone'
        },
        'underscore': {
            exports: '_'
        },
        'jqueryui': {
            deps: ['jquery']
        },
        'dynatree': {
            deps: ['jqueryui']
        },
    }
});
/*
 * Start the application, Load the core libraries and call init.
 */
require([
        'jquery',
        'googleMapsLoader',
        'backbone',
        'underscore',
        'jqueryui',
        "text",
        "css",
        "layout",
        "ich",
        "cookie",
        "bootstrap",
        "jquery.ui.widget",
        "fileupload",
        "dynatree",
        "modernizr"


    ],
    function ($, googleMapsLoader) {
        googleMapsLoader.done(function () {
            require(['app'], function (app) {
                app.init();
            });
        }).fail(function () {
            console.error("ERROR: Google maps library failed to load");
        });

    });
