define([
    "models/MarkersCollection",
    "models/MarkerModel",
    "js/Utils"], function (MarkersCollection, MarkerModel, Utils) {
    var AppRouter = Backbone.Router.extend({
        routes: {
            "": "login",
            "login": "login",
            "home": "home",
            "foursquare/*path": "foursquareRedirect",
            "login/:id": "login",
            "denied": "denied",
            "logout": "logout",
            "signup": "signup",
            "profileSettings": "editUser",
            "home/:id": "editMarker",
            "about": "about"
        },
        initialize: function (params) {
            var self = this;
            this.utils = new Utils();
            self.views = {};
            self.view = null;
            self.append_at = params.append_at;

        },
        foursquareRedirect: function () {
            var self = this;
            this.markersCollection = new MarkersCollection();

            var txfURL = window.location.hash;
            var token = txfURL.split("=", 2);
            sessionStorage.setItem("foursquareToken", token[1]);

            var deferred = $.Deferred();

            require(['js/MainView'], function (MainView) {

                var view = self.views['homeRoute'];
                if (!view) {
                    view = (self.views['homeRoute'] = (new MainView({model: self.markersCollection})));
                    $(self.append_at).append(view.render().el);
                    view.setCenterPoint(new google.maps.LatLng(47.74032077195754, 14.90416651426722));
                }
                self.compleHomeNavigation(view, 'home');
                view.updateMenuContent();

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
                        }
                        deferred.resolve(markerList, centerP, view);
                    }
                });
                deferred.done(function (markerList, centerP, view) {
                    var markersCollection = new MarkersCollection();
                    markerList.forEach(function (marker) {
                        var markerModel = new MarkerModel(marker);
                        markersCollection.push(markerModel);
                    });
                    view.updateMarkers(markersCollection);
                    view.setCenterPoint(new google.maps.LatLng(centerP.latitude, centerP.longitude));
                });
            });
        },
        home: function () {
            var self = this;
            var deferred = $.Deferred();
            this.markersCollection = new MarkersCollection();
            var markerList = null;
            var centerP = null;

            require(['js/MainView'], function (MainView) {

                var view = self.views['homeRoute'];
                if (!view) {
                    view = (self.views['homeRoute'] = (new MainView({model: self.markersCollection})));
                    $(self.append_at).append(view.render().el);
                }
                view.updateMenuContent();
                self.compleHomeNavigation(view, 'home');

                $.ajax({
                    type: 'POST',
                    url: "GetMarkersServlet",
                    data: {userId: sessionStorage.getItem("userId")},
                    success: function (result) {
                        var err = result.errors;
                        if ((err) && (err !== null)) {
                            console.log("errors");
                        } else {
                            markerList = result.data.markers;
                            centerP = result.data.centerPoint;
                        }
                        deferred.resolve(markerList, centerP, view);
                    }
                });

                deferred.done(function (markerList, centerP) {
                    var markersCollection = new MarkersCollection();
                    markerList.forEach(function (marker) {
                        var markerModel = new MarkerModel(marker);
                        markersCollection.push(markerModel);
                    });
                    view.updateMarkers(markersCollection);
                    view.setCenterPoint(new google.maps.LatLng(centerP.latitude, centerP.longitude));
                });
            });

        },
        signup: function () {

            var self = this;
            require(['js/SignupView', 'models/NewUserModel'], function (SignupView, NewUserModel) {
                var userModel = new NewUserModel();
                var view = self.views['signup'];

                if (!view) {
                    view = (self.views['signup'] = (new SignupView({model: userModel})));
                    $(self.append_at).append(view.render().el);

                } else {
                    view.updateContent(userModel);
                }
                self.completeNavigation(view, 'signup');
            });

        },
        editUser: function () {

            var self = this;
            require(['js/UserView', 'models/UserModel'], function (UserView, UserModel) {
                var userModel = new UserModel();
                var view = self.views['profileSettings'];

                if (!view) {
                    view = (self.views['profileSettings'] = (new UserView({model: userModel})));
                    view.init();
                } else {
                    view.updateContent();
                }
                self.completeNavigation(view, 'profileSettings');
            });


        },
        dropboxResponse: function (url) {
            var code = url.split("=", 2)[1];
            sessionStorage.setItem("dropboxCode", code);
            console.log("dropbox code");
            sessionStorage.setItem("messagesToShow", "You are now connected to Dropbox");
            var url = "https://api.dropbox.com/1/oauth2/token"
                + "?code=" + code
                + "&grant_type=authorization_code"
                + "&client_id=i2aznvdzgh6cr8r"
                + "&client_secret=04l0xdse0rtq3cw"
                + "&redirect_uri=http://localhost:8080/";

            $.ajax({
                type: 'POST',
                url: url,
                dataType: "json",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                crossDomain: true,
                success: function (data) {
                    sessionStorage.setItem("accessTokenDropbox", data.access_token);
                    window.location.replace("http://localhost:8080/#home");
                },
                error: function (jqXHR, exception, errorstr) {
                    console.log(jqXHR);
                    sessionStorage.setItem("dataToken", "error");
                }
            });


        },
        login: function (username) {
            var self = this;
            var url = window.location.href;
            if (url.indexOf("?code") !== -1) {
                this.dropboxResponse(url);
            } else {
                var view = this.views['login'];
                if (!view) {
                    require(['js/LoginView'], function (LoginView) {
                        view = (self.views['login'] = (new LoginView()));
                        $(self.append_at).append(view.render().el);
                        self.completeNavigation(view, 'login');
                    });
                } else {
                    view.setUser(username);
                    view.utils.hideAlert();
                    this.completeNavigation(view, 'login');
                }

            }

        },

        denied: function () {
            this.deleteSessionVar();
            var self = this;

            require(['js/LoginView'], function (LoginView) {
                var view = self.views['login'];
                if (!view) {
                    view = (self.views['login'] = (new LoginView()));
                    $(self.append_at).append(view.render().el);
                }
                var err = "An unexpected error occured.";
                view.utils.displayValidationErrors(err, $(".loginErrorContainer"));
                self.completeNavigation(view, 'denied');
            });
        },
        logout: function () {
            this.deleteSessionVar();
            var self = this;
            require(['js/LoginView'], function (LoginView) {
                var view = self.views['login'];
                if (!view) {
                    view = (self.views['login'] = (new LoginView()));
                    $(self.append_at).append(view.render().el);
                } else {
                    view.utils.hideAlert();
                }
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
                self.completeNavigation(view, 'login');
            });


        },

        editMarker: function (id) {
            var self = this;
            require(['js/MarkerView'], function (MarkerView) {
                var view = self.views['home' + id];
                if (!view) {
                    view = (self.views['home' + id] = (new MarkerView({markerId: id})));
                    $(self.append_at).append(view.el);
                } else {
                    view.updateContent();
                }
                self.completeNavigation(view, 'home/' + id);
            });
        },
        about: function () {
            var self = this;
            var view = this.views['about'];
            if (!view) {
                require(['js/dialogs/AboutDlg'], function (AboutDlg) {
                    view = (self.views['about'] = (new AboutDlg()));
                    view.render();
                });
            }
        },
        deleteSessionVar: function () {
            delete sessionStorage.userId;
            delete sessionStorage.username;
            delete sessionStorage.dropboxCode;
            delete sessionStorage.foursquareToken;
        },
        completeNavigation: function (view, route) {
            var self = this;
            $.when(this.hideAllViews()).then(
                function () {
                    self.view = view;
                    self.navigate(route);
                    view.show();
                    view.removeAllValidationErrors();
                });
        },
        compleHomeNavigation: function (view, route) {
            var self = this;
            $.when(this.hideAllViews()).then(
                function () {
                    self.view = view;
                    self.navigate(route);
                    view.show();
                    view.mapView.resizeMap();
                });
        },
        /**
         * Hide all views and return true if the operation succeeded.
         **/
        hideAllViews: function () {
            return _.select(
                _.map(this.views, function (v) {
                    return v.hide();
                }),
                function (t) {
                    return t != null;
                });
        },

    });
    return AppRouter;
});
