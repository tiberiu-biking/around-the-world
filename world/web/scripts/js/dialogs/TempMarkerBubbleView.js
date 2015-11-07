/**
 *
 */
define(["text!../../tmpl/tempMarkerBubble.htm",
        "models/MarkerBubbleModel",
        "js/dialogs/MarkerBubbleView",
        "js/Utils"
    ],
    function (tempMarkerBubble, MarkerBubbleModel, MarkerBubbleView, Utils) {
        ich.addTemplate("tempMarkerBubble", tempMarkerBubble);
        var TempMarkerBubbleView = Backbone.View.extend({

            defaults: {},
            events: {
                "click #deleteButton": "deleteMarker",
                "click #addButton": "add"
            },
            initialize: function (args) {
                _.bindAll(this, 'render');
                this.render();
                var self = this;
                this.utils = new Utils();
                this.mapView = args.mapView;
                google.maps.event.addListener(self.model.get("marker"), 'click', function () {
                    self.clickHander(this);
                });

            },
            clickHander: function (marker) {
                var infoWindow = this.model.get("infoWindow");
                infoWindow.setContent(this.el);
                infoWindow.open(this.model.get("map"), marker);
            },
            render: function () {
                var tmplObj = {};
                tmplObj.name = this.model.get('markerDB').name;
                tmplObj.date = this.model.get('markerDB').date;
                tmplObj.note = this.model.get('markerDB').note;

                var markerContent = ich.tempMarkerBubble(tmplObj);
                this.$el.append(markerContent);

                return this;
            },
            deleteMarker: function () {
                var marker = this.model.get("marker");
                marker.setMap(null);

            },
            add: function () {
                var self = this;
                var map = this.model.get("map");
                var marker = this.model.get("marker");
                marker.setMap(null);

                var infoWindow = this.model.get("infoWindow");
                infoWindow.close();

                var markerString = JSON.stringify(this.model.get("markerDB"));
                var deferred = $.Deferred();
                $.ajax({
                    url: "CreateMarkerServlet",
                    data: {marker: markerString, userId: sessionStorage.getItem("userId")},
                    success: function (result) {
                        console.log("CreateMarkerServlet successfully " + result.data.markers[0].name);
                        self.utils.displayValidationWarnings("Marker '" + result.data.markers[0].name + "' added successfully", $(".homeErrorContainer"));
                        deferred.resolve(result.data.markers[0], map);
                        window.app.trigger("updateTimeline");
                    },
                    error: function (result) {
                        console.log("add:error");
                    }
                });

                deferred.done(function (markerDB, map) {
                    var markerBubbleModel = new MarkerBubbleModel({markerDB: markerDB});
                    self.mapView.bubbleMarkers.push(markerBubbleModel);
                    markerBubbleModel.init(map, self.model.get("infoWindow"));
                    var markerBubbleView = new MarkerBubbleView({model: markerBubbleModel});
                });


            }

        });
        return TempMarkerBubbleView;
    });