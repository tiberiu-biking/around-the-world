/**
 *
 */
define([], function () {
    var MapModel = Backbone.Model.extend({
        defaults: {
            id: '',
            latitude: null,
            longitude: null,
            maxZoom: 16,
            minZoom: 3,
            zoom: 10,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        },
        initialize: function () {
//		var self= this;
//            require(["models/MarkersProvider"],
//                function(Command) {
//                    Command(filterArg, function(dataProviderArray) {
//                           self.set("markersMap", dataProviderArray);
//						   
//                    });
//            });	
        }
    });
    return MapModel;
});