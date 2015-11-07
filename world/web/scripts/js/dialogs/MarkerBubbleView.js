/**
 *
 */
define(["text!../../tmpl/markerBubble.htm",
        "models/MarkerModel",
        "js/MarkerView"
    ],
    function (markerBubble, MarkerModel, MarkerView) {
        ich.addTemplate("markerBubble", markerBubble);
        var MarkerBubbleView = Backbone.View.extend({
            events: {
                "click #deleteButton": "deleteMarker",
                "click #editButton": "edit",
                'click .rating input[type="radio"]': "changeRate"
            },
            initialize: function () {
                _.bindAll(this, 'render');
                this.render();
                var self = this;
                google.maps.event.addListener(self.model.get("marker"), 'click', function () {
                    self.clickHander(this);
                });
            },
            changeRate: function () {
                var rating = this.$el.find('input[name="rating"]:checked')[0].value;

                var markerDB = this.model.get("markerDB");
                markerDB.rating = rating;

                var jsonString = JSON.stringify(markerDB);

                console.log("Change rate to" + rating);
                $.ajax({
                    type: 'POST',
                    url: "UpdateMarkerServlet",
                    data: {marker: jsonString},
                    success: function (result) {
                        console.log("ChangeRate ->" + result.data.message);
                    }
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

                var markerContent = ich.markerBubble(tmplObj);
                this.$el.append(markerContent);

                this.renderStars(this.model.get("rating"));
                return this;
            },
            renderStars: function (rating) {
                var rating = this.model.get("markerDB").rating;
                if (rating > 0) {
                    this.$el.find('input[name="rating"]').each(function (i) {
                        if (i <= rating) {
                            this.checked = true;
                        }
                    });
                }
            },
            deleteMarker: function () {

                var marker = this.model.get("marker");
                var markerId = this.model.get("markerDB").id;
                $.ajax({
                    url: "DeleteMarkerServlet",
                    data: {markerId: markerId},
                    success: function (result) {
                        console.log("Delete " + markerId + " successfuly");
                        marker.setMap(null);
                        window.app.trigger("updateTimeline");
                    }

                });

            },
            edit: function (event) {
                event.preventDefault();
                var infoWindow = this.model.get("infoWindow");
                infoWindow.close();

                var model = this.model.get("markerDB");
                var id = model.id;
                var markerModel = new MarkerModel(model);

                var router = window.app;
                var view = router.views['home' + id];
                if (!view) {
                    view = (router.views['home' + id] = (new MarkerView({model: markerModel})));
                    $(router.append_at).append(view.el);
                }

                $.when(router.hideAllViews()).then(
                    function () {
                        router.view = view;
                        router.navigate("home/" + id);
                        view.show();
                    });
            }

        });
        return MarkerBubbleView;
    });