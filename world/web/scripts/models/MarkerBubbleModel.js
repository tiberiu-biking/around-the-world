/**
 *
 */
define([], function () {
    var MarkerBubbleModel = Backbone.Model.extend({
        initialize: function () {
            this.set("title", this.get('markerDB').name);
        },
        init: function (map, infoWindow) {
            this.set("map", map);
            this.set("infoWindow", infoWindow);
            this.latlng = new google.maps.LatLng(this.get('markerDB').latitude, this.get('markerDB').longitude);
            var marker = new google.maps.Marker({
                position: this.latlng,
                map: map,
                title: this.get("title"),
                animation: google.maps.Animation.DROP
            });
            this.set("marker", marker);

        },
        setMap: function (map) {
            this.set("map", map);
        }
    });
    return MarkerBubbleModel;
});