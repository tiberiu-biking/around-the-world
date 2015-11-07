//define the MainView
define(["text!../tmpl/mainPage.htm",
        "models/MapModel",
        "models/MarkerModel",
        "js/MapView",
        "js/HeaderView",
        "js/BaseView",
        "models/MarkersCollection"
    ],
    function (main, MapModel, MarkerModel, MapView, HeaderView, BaseView, MarkersCollection) {
        var MainView = Backbone.View.extend({

            el: $('#container'),
            className: "home",
            template: _.template(main),
            events: {
                "click #currentLocation": "addCurrentLocation",
                "click #markerFromPic": "markerFromPic",
                "click #connectToFoursquare": "connectToFoursquare",
                "click #importFromFoursquare": "importFromFoursquare",
                "click #connectToDropbox": "connectToDropbox",
                "click #importFromDropbox": "importFromDropbox",
                "click #about": "about",
                "click #timeline": "timeline"
            },

            initialize: function (args) {
                this.el = $(this.el);
                this.el.css("height", "100%");
                window.app.on("newMessage", this.checkAndShowMessages, this);
                window.app.on("newMarker", this.addNewMarker, this);
                window.app.on("updateTimeline", this.updateTimeline, this);
                $(window).bind("beforeunload", function () {
                    $.ajax({
                        type: 'POST',
                        cache: false,
                        dataType: 'json',
                        url: "LogoutServlet",
                        data: {userId: sessionStorage.getItem("userId")},
                        success: function (result) {
                            console.log("Logout.");
                        }
                    });
                });
            },

            render: function () {
                this.$el.html(this.template);

                this.headerView = new HeaderView();
                $('.header').html(this.headerView.el);

                this.renderLayout();
                this.renderMap();
                return this;

            },
            renderLayout: function () {
                var layout = this.$el.layout({
                    north: {
                        closable: false,
                        resizable: false,
                        slidable: false,
                        spacing_open: 0,
                        spacing_closed: 0

                    },

                    south: {
                        initClosed: true,
                        closable: false,
                        resizable: false,
                        slidable: false,
                        spacing_open: 0,
                        spacing_closed: 0
                    }
                });
                layout.sizePane("east", 350);
                layout.allowOverflow("north");
                this.createMenu();
            },
            renderMap: function () {
                var mapModel = new MapModel();
                var mapView = new MapView({el: this.$('.map')[0], model: mapModel});
                mapView.render();
                this.mapView = mapView;
                this.map = mapView.map;
                this.renderSearchButton();
                this.checkAndShowMessages();
            },

            renderSearchButton: function () {
                var markers = [];
                var self = this;

                // Create the search box and link it to the UI element.
                var input = (document.getElementById('pac-input'));
                this.map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

                var searchBox = new google.maps.places.SearchBox((input));

                // addListener for when the user selects an item from search box
                google.maps.event.addListener(searchBox, 'places_changed', function () {
                    var places = searchBox.getPlaces();
                    for (var i = 0, marker; marker = markers[i]; i++) {
                        marker.setMap(null);
                    }

                    markers = [];
                    for (var i = 0, place; place = places[i]; i++) {
                        self.mapView.createTempMarkerView(place);
                        self.setCenterPoint(place.geometry.location);
                    }
                });

                google.maps.event.addListener(this.map, 'bounds_changed', function () {
                    var bounds = self.map.getBounds();
                    searchBox.setBounds(bounds);
                });
            },

            checkAndShowMessages: function (msg) {
                var self = this;
                if (msg) {
                    self.mapView.utils.displayValidationWarnings(msg, $(".homeErrorContainer"));
                } else if (sessionStorage.getItem("messagesToShow") !== null) {
                    self.mapView.utils.displayValidationWarnings(sessionStorage.getItem("messagesToShow"), $(".homeErrorContainer"));
                    delete sessionStorage.messagesToShow;
                    $('#cssmenu > ul > li > a#external').click();
                }
            },
            setCenterPoint: function (centerP) {
                if ((centerP.lat() == 0 ) && (centerP.lng() == 0)) {
                    this.centerPoint = new google.maps.LatLng(45.541260, 24.513882);
                } else {
                    this.centerPoint = centerP;
                }
                this.map.setCenter(this.centerPoint, 9);
            },
            updateMarkers: function (markers) {
                //clear map, remove old markers
                var markersList = this.mapView.bubbleMarkers;
                if (( markersList !== null) && (markersList !== undefined)) {
                    markersList.forEach(function (markerBubble) {
                        markerBubble.get("marker").setMap(null);
                    });
                    this.mapView.bubbleMarkers = [];
                }

                this.mapView.model.set("mapMarkers", markers.models);
                this.mapView.loadMarkers(this.mapView.model);
                this.checkAndShowMessages();
                console.log("UpdateMarkers");
            },
            /*
             * Show/hide Dropbox buttons depending of dropboxcode
             * If the user has already allowed our application to use this dropbox file
             * only the chooser dialog will show up
             */
            updateMenuContent: function () {
                this.headerView.render();
                if ((sessionStorage.getItem("dropboxCode") !== null) && (sessionStorage.getItem("dropboxCode") !== "undefined")) {
                    this.$el.find("#connectToDropbox").hide();
                    this.$el.find("#importFromDropbox").show();
                } else {
                    this.$el.find("#connectToDropbox").show();
                    this.$el.find("#importFromDropbox").hide();
                }
                if ((sessionStorage.getItem("foursquareToken") !== null) && (sessionStorage.getItem("foursquareToken") !== "undefined")) {
                    this.$el.find("#connectToFoursquare").hide();
                    this.$el.find("#importFromFoursquare").show();
                } else {
                    this.$el.find("#connectToFoursquare").show();
                    this.$el.find("#importFromFoursquare").hide();
                }

            },
            createMenu: function () {
                $('#cssmenu > ul > li > a').click(function () {
                    if ((this.id !== "timeline") && ($("#timelineOption").hasClass("opened"))) {
                        $(".timelineContent").empty();
                        $("#timelineOption").removeClass("opened");
                    }
                    $('#cssmenu li').removeClass('active');
                    $(this).closest('li').addClass('active');
                    var checkElement = $(this).next();
                    if ((checkElement.is('ul')) && (checkElement.is(':visible'))) {
                        $(this).closest('li').removeClass('active');
                        checkElement.slideUp('normal');
                    }
                    if ((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
                        $('#cssmenu ul ul:visible').slideUp('normal');
                        checkElement.slideDown('normal');
                    }
                    if ($(this).closest('li').find('ul').children().length == 0) {
                        return true;
                    } else {
                        return false;
                    }
                });
                this.updateMenuContent();
            },

            addCurrentLocation: function () {
                console.log("AddCurrentLocation was clicked");
                this.getLocation();
            },
            getLocation: function () {
                var currentMap = this.map;
                var mapView = this.mapView;
                var self = this;
                navigator.geolocation.getCurrentPosition(function (position) {
                    self.foundLoc(position, currentMap, mapView);
                }, this.showError, {timeout: 30000});
            },

            foundLoc: function (position, currentMap, mapView) {
                console.log("Latitude: " + position.coords.latitude + "Longitude: " + position.coords.longitude);
                var currentPos = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
                var geocoder = new google.maps.Geocoder();
                var bounds = new google.maps.LatLngBounds(
                    new google.maps.LatLng(position.coords.latitude + 0.0000011, position.coords.longitude + 0.0000011),
                    new google.maps.LatLng(position.coords.latitude + 0.0000011, position.coords.longitude + 0.0000011)
                );
                geocoder.geocode({'latLng': currentPos, 'bounds': bounds}, function (results, status) {
                    if (status == google.maps.GeocoderStatus.OK) {
                        mapView.addNewStep(position, results[0].formatted_address);
                    } else {
                        this.mapView.utils.displayValidationErrors("Geocoder failed due to: " + status, $(".homeErrorContainer"));
                    }
                });
                return position;
            },


            showError: function (error) {
                switch (error.code) {
                    case error.PERMISSION_DENIED:
                        this.mapView.utils.displayValidationWarnings("User denied the request for Geolocation.", $(".homeErrorContainer"));
                        break;
                    case error.POSITION_UNAVAILABLE:
                        this.mapView.utils.displayValidationWarnings("Location information is unavailable.", $(".homeErrorContainer"));
                        break;
                    case error.TIMEOUT:
                        this.mapView.utils.displayValidationWarnings("The request to get user location timed out.", $(".homeErrorContainer"));
                        break;
                    case error.UNKNOWN_ERROR:
                        this.mapView.utils.displayValidationWarnings("An unknown error occurred", $(".homeErrorContainer"));
                        break;
                }
            },
            addNewMarker: function (marker) {
                this.mapView.createMarkerView(this.mapView.infoWindow, marker);
                this.setCenterPoint(new google.maps.LatLng(marker.latitude, marker.longitude));
                this.mapView.utils.displayValidationWarnings("Notification: A new check-in was added.", $(".homeErrorContainer"));
            },
            markerFromPic: function () {
                console.log("Add markerFromPicture  was clicked");
                var self = this;
                require(['js/dialogs/PictureDlg'], function (PictureDlg) {
                    var pictureDlg = new PictureDlg({
                        map: self.map,
                        infoWindow: self.mapView.infoWindow,
                        mapView: self.mapView
                    });
                    pictureDlg.render();
                });
            },
            about: function () {
                require(['js/dialogs/AboutDlg'], function (AboutDlg) {
                    view = new AboutDlg();
                    view.render();
                });
            },
            connectToFoursquare: function () {
                var CLIENT_ID = "VBTDS2HOUSNYSBMPHZ1L323O1OVHCYXH14H1OKNASCI31TFI";
                var REDIRECT_URL = "http://localhost:8080/#foursquare/";

                var link = 'https://foursquare.com/oauth2/authenticate'
                    + '?client_id=' + CLIENT_ID
                    + '&response_type=code'
                    + '&redirect_uri=' + encodeURIComponent(REDIRECT_URL);
                window.location.href = link;
            },
            importFromFoursquare: function () {
                var deferred = $.Deferred();
                var self = this;
                $.ajax({
                    type: 'POST',
                    url: "FoursquareImportServlet",
                    data: {userId: sessionStorage.getItem("userId"), code: sessionStorage.getItem("foursquareToken")},
                    success: function (result) {
                        console.log("FoursquareImportServlet result");
                        var err = result.errors;
                        if ((err) && (err !== null)) {
                            console.log("errors");
                        } else {
                            markerList = result.data.markers;
                            centerP = result.data.centerPoint;
                            self.mapView.utils.displayValidationWarnings(result.data.message, $(".homeErrorContainer"));
                        }
                        deferred.resolve(markerList, centerP, self);
                    }
                });
                deferred.done(function (markerList, centerP, view) {
                    var markersCollection = new MarkersCollection();
                    markerList.forEach(function (marker) {
                        var markerModel = new MarkerModel(marker);
                        markersCollection.push(markerModel);
                    });
                    self.updateMarkers(markersCollection);
                    self.setCenterPoint(new google.maps.LatLng(centerP.latitude, centerP.longitude));
                    window.app.trigger("updateTimeline");
                });
            },
            connectToDropbox: function () {
                var url = "https://www.dropbox.com/1/oauth2/authorize"
                    + "?response_type=code"
                    + "&client_id=i2aznvdzgh6cr8r"
                    + "&redirect_uri=http://localhost:8080/";
                window.location.href = url;
            },

            importFromDropbox: function () {
                console.log("ConnectToDropbox  was clicked");
                var self = this;
                var def = $.Deferred();
                Dropbox.choose({
                    linkType: "direct",
                    multiselect: true,
                    extensions: ['.png', '.jpeg', '.bmp', '.gif', '.jpg'],
                    success: function (files) {
                        var filesParam = [];
                        files.forEach(function (file) {
                            filesParam.push(file.link);
                            console.log(file.link);
                        });

                        def.resolve(filesParam);
                    }
                });
                def.done(function (files) {
                    var jsonFiles = JSON.stringify(files);
                    var deferred = $.Deferred();
                    //  $(".loader").css("display", "block");
                    $.ajax({
                        type: 'POST',
                        url: "DropboxImportServlet",
                        data: {
                            files: jsonFiles,
                            userId: sessionStorage.getItem("userId"),
                            code: sessionStorage.getItem("accessTokenDropbox")
                        },
                        success: function (result) {
//	    					setTimeout(function() {
//	    						$(".loader").fadeOut("slow");
//	    					}, 10000);

                            console.log("DropboxImportServlet success" + result.data.markers.length);
                            var markers = result.data.markers;
                            deferred.resolve(markers, self.mapView, self.mapView.infoWindow);
                            self.mapView.utils.displayValidationWarnings(result.data.message, $(".homeErrorContainer"));
                        }
                    });

                    deferred.done(function (markers, mapView, infoWindow) {
                        markers.forEach(function (marker) {
                            mapView.createMarkerView(infoWindow, marker);
                        });

                        window.app.trigger("updateTimeline");
                    });
                });


            },
            updateTimeline: function () {
                if (this.$el.find("#timelineOption").hasClass("opened")) {
                    this.$el.find(".timelineContent").empty();
                    require(['js/TimelineView'], function (TimelineView) {
                        var view = new TimelineView();
                        view.render();
                    });
                }

            },
            timeline: function (event) {
                var timelineOption = $(event.target.parentNode.parentElement);
                if (timelineOption.hasClass("active")) {
                    timelineOption.addClass("opened");
                    this.$el.find(".timelineContent").empty();
                    require(['js/TimelineView'], function (TimelineView) {
                        view = new TimelineView();
                        view.render();
                    });
                } else {
                    console.log("hide timeline");
                    this.$el.find(".timelineContent").empty();
                    timelineOption.removeClass("opened");
                }

            },

            hide: function () {
                promise = $.Deferred(_.bind(function (dfd) {
                    this.el.fadeOut('fast', dfd.resolve)
                }, this));
                return promise.promise();
            },

            show: function () {
                promise = $.Deferred(_.bind(function (dfd) {
                    this.el.fadeIn('fast', dfd.resolve)
                }, this));
                return promise.promise();
            }
        });
        return MainView;
    });
