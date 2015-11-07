var google_maps_loaded_def = null;

define(['jquery'], function ($) {

    if (!google_maps_loaded_def) {

        google_maps_loaded_def = $.Deferred();

        window.google_maps_loaded = function () {
            google_maps_loaded_def.resolve(google.maps);
        };
        require(['http://maps.google.com/maps/api/js?key=AIzaSyDEWNrVa4bcYOZ2kyP369hFB_Gaz3ZV9-w&sensor=true&&libraries=places&callback=google_maps_loaded'], function () {
        }, function (err) {
            google_maps_loaded_def.reject();
        });

    }

    return google_maps_loaded_def.promise();

});