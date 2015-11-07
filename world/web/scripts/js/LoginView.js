define(["text!../tmpl/login.htm",
        "text!../tmpl/errorMessage.htm",
        "js/BaseView"],
    function (login, errorMessage, BaseView) {
        ich.addTemplate("login", login);
        ich.addTemplate("errorMessage", errorMessage);
        var LoginView = BaseView.extend({

            template: _.template(login),

            //container for the view
            parent: $('#placeholderDiv'),
            className: "login",
            //handle events
            events: {
                "submit": "submit"
            },
            initialize: function () {
                this.constructor.__super__.initialize.apply(this);
                return this;
            },

            //render the login page
            render: function () {
                this.utils.hideAlert();
                delete sessionStorage.username;
                return this;

            },
            submit: function (evt) {
                evt.preventDefault();
                var username = $('#userEmail').val();
                var password = $('#userPassword').val();

                var self = this;
                $.ajax({
                    type: 'POST',
                    cache: false,
                    dataType: 'json',
                    url: "SignInServlet",
                    data: {username: username, password: password},
                    success: function (result) {
                        var err = result.errors;
                        if ((err) && (err !== null)) {
                            self.utils.displayValidationErrors(err, $(".loginErrorContainer"));
                        } else {
                            if ((result.data != null) && (result.data != undefined)) {
                                sessionStorage.setItem("userId", result.data.userId);
                                sessionStorage.setItem("username", result.data.firstName);
                                sessionStorage.setItem("foursquareToken", result.data.foursquareToken);
                                //sessionStorage.setItem("dropboxToken", result.data.dropboxToken);
                                window.app.navigate("home", {trigger: true});

                            }

                        }
                    }
                });

            },
            setUser: function (username) {
                $('#username').val(username);
            }

        });
        return LoginView;
    });

