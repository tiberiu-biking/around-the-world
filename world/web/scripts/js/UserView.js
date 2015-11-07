/**
 *
 */
define(["text!../tmpl/userView.htm",
        "js/BaseView"
    ],
    function (userView, BaseView) {
        ich.addTemplate("userView", userView);
        var UserView = BaseView.extend({

            //container for the view
            template: _.template(userView),
            parent: $('#placeholderDiv'),
            className: "editUser",

            //handle events
            events: {
                "click #cancelButton": "cancelHandler",
                "click #saveButton": "beforeSave",
                "change": "change",
                "change #fileupload": "uploadPicture"
            },
            initialize: function () {
                this.constructor.__super__.initialize.apply(this);
            },
            init: function () {
                var self = this;
                this.def = $.Deferred();
                this.getUser();
                this.def.done(function (view) {
                    var content = ich.userView(view);
                    self.$el.html(content);
                    self.parent.append(self.$el);
                });
            },
            //render the signup page
            getUser: function () {
                var self = this;
                $.ajax({
                    type: 'POST',
                    url: "GetUserServlet",
                    dataType: 'json',
                    data: {userId: sessionStorage.getItem("userId")},
                    success: function (result) {
                        var err = result.errors;
                        if ((err) && (err !== null)) {
                            self.displayValidationErrors(err, $(".userErrorContainer"));
                        } else {
                            var userDB = result.data.user;
                            self.model.attributes = userDB;
                            self.model.set("password", "");
                            self.model.set("password2", "");
                            self.def.resolve(result.data.user);
                        }
                    }
                });

                return this;

            },
            uploadPicture: function () {
                var input = document.getElementById("fileupload");
                if (input.files && input.files[0]) {

                    var reader = new FileReader();
                    reader.onload = function (e) {
                        $('#userImage')
                            .attr('src', e.target.result)
                            .width(140)
                            .height(140);
                    };
                    reader.readAsDataURL(input.files[0]);
                }
            },

            change: function (event) {
                // Apply the change to the model
                var target = event.target;
                this.model.set(target.name, target.value);

                // Run validation rule (if any) on changed item
                var check = this.model.validateItem(target.id, this.model.attributes);
                if (check.isValid === false) {
                    this.addValidationError(target.id, check.message);
                } else {
                    this.removeValidationError(target.id);
                }
                if (target.name == "password") {
                    $('#password2').attr('value', "");
                    this.model.set("password2", "");
                    this.removeValidationError("password2");
                }
            },
            beforeSave: function (evt) {
                evt.preventDefault();
                var failedValidation = false;
                // var check = this.model.validateAll();
                var check;
                for (var entry in this.model.attributes) {
                    check = this.model.validateItem(entry, this.model.attributes);
                    if (check.isValid === false) {
                        failedValidation = true;
                        this.addValidationError(entry, check.message);
                    } else {
                        this.removeValidationError(entry);
                    }
                }
                if (failedValidation === true) {
                    //this.utils.displayValidationErrors(check.message, $(".userErrorContainer"));
                    return false;
                } else {
                    this.utils.hideAlert();
                    this.updateUser();
                }
            },
            updateUser: function (evt) {
                var password = this.model.get("password");
                this.model.unset("password");
                this.model.unset("password2");
                var userString = JSON.stringify(this.model);

                $.ajax({
                    type: 'POST',
                    cache: false,
                    dataType: 'json',
                    url: "UpdateUserServlet",
                    data: {user: userString, password: password},
                    success: function (result) {
                        var err = result.errors;
                        if ((err) && (err !== null)) {
                            this.utils.displayValidationErrors(result);
                        } else {
                            sessionStorage.setItem("username", result.data.firstName);
                            window.app.navigate("home", {trigger: true});
                        }
                    },
                    error: function (error) {
                        console.log("error on edit user");
                        window.app.navigate("", {trigger: true});
                    }
                });

            },
            updateContent: function () {
                this.model.set("password", "");
                this.model.set("password", "");
                var content = ich.userView(this.model.attributes);
                this.$el.html(content);
            },
            cancelHandler: function (evt) {
                evt.preventDefault();
                this.model.attributes = this.model.previousAttributes();
                window.app.navigate("home", {trigger: true});
            }


        });
        return UserView;
    });

