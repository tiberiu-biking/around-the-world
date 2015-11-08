define(["text!../tmpl/map.htm",
        "text!../tmpl/markerBubble.htm",
        "models/MarkerBubbleModel",
        "js/dialogs/MarkerBubbleView",
        "js/dialogs/TempMarkerBubbleView",
        "js/Utils"
    ],
    function (map, markerBubble, MarkerBubbleModel, MarkerBubbleView, TempMarkerBubbleView, Utils) {
        var MapView = Backbone.View.extend({
            initialize: function () {
                _.bindAll(this, 'render');
                this.utils = new Utils();
                this.bubbleMarkers = [];
            },

            render: function () {
                var latlng = new google.maps.LatLng(47.74032077195754, 14.90416651426722);
                var options = {
                    zoom: 6,
                    center: latlng,
                    mapTypeControl: true,
                    mapTypeControlOptions: {
                        style: google.maps.MapTypeControlStyle.DROPDOWN_MENU
                    },
                    zoomControl: true,
                    zoomControlOptions: {
                        style: google.maps.ZoomControlStyle.SMALL
                    },
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };
                this.map = new google.maps.Map(this.el, options);
                return this;
            },

            createMarkerView: function (infoWindow, model, i) {
                var markerBubbleModel = new MarkerBubbleModel({markerDB: model});
                this.bubbleMarkers.push(markerBubbleModel);
                markerBubbleModel.init(this.map, infoWindow);
                setTimeout(function () {
                    var markerBubbleView = new MarkerBubbleView({model: markerBubbleModel});
                }, i * 300);

            },
            createTempMarkerView: function (place) {
                var markerDB = this.createMarkerDB(place.name, place.geometry.location.lat(), place.geometry.location.lng());
                var markerBubbleModel = new MarkerBubbleModel({markerDB: markerDB});
                markerBubbleModel.init(this.map, this.infoWindow);
                var markerBubbleView = new TempMarkerBubbleView({model: markerBubbleModel, mapView: this});

            },
            /*
             * Add markers to the map
             * Create a bubble view for each one
             */
            loadMarkers: function () {

                var markers = this.model.get("mapMarkers");
                this.infoWindow = new google.maps.InfoWindow();
                for (var i = 0; i < markers.length; i++) {
                    var point = markers[i];
                    this.createMarkerView(this.infoWindow, point.attributes, i + 1);
                }
                //	console.log("load markers DONE "+ JSON.stringify(markers));
            },

            addNewStep: function (currentPos, name) {
                console.log("add new step:" + name);

                var markerDB = this.createMarkerDB(name, currentPos.coords.latitude, currentPos.coords.longitude);
                var markerString = JSON.stringify(markerDB);
                var deferred = $.Deferred();

                var self = this;
                $.ajax({
                    url: "CreateMarkerServlet",
                    data: {marker: markerString},
                    success: function (result) {
                        console.log("CreateMarkerServlet successfully");
                        deferred.resolve(result.data.markers[0]);
                        self.utils.displayValidationWarnings("Marker '" + result.data.markers[0].name + "' added successfully", $(".homeErrorContainer"));
                    },
                    error: function (result) {
                        console.log("add:error");
                    }
                });
                deferred.done(function (markerDB) {
                    var markerBubbleModel = new MarkerBubbleModel({markerDB: markerDB});
                    self.bubbleMarkers.push(markerBubbleModel);
                    markerBubbleModel.init(self.map, self.infoWindow);
                    var markerBubbleView = new MarkerBubbleView({model: markerBubbleModel});
                });
            },

            createMarkerDB: function (name, lat, lng) {
                var markerDB = {};
                markerDB.name = name;
                markerDB.latitude = lat;
                markerDB.longitude = lng;
                markerDB.date = this.createCurrentDay();
                markerDB.userId = sessionStorage.getItem("userId");
                return markerDB;
            },
            createCurrentDay: function () {
                var d = new Date();
                var curr_date = d.getDate();
                var curr_month = d.getMonth() + 1; //Months are zero based
                var curr_year = d.getFullYear();
                return curr_year + "-" + curr_month + "-" + curr_date;
            },
            resizeMap: function () {
                google.maps.event.trigger(this.map, 'resize');
                console.log("resize map");
            }

        });
        return MapView;
    });