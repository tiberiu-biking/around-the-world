/**
 *
 */
define(["text!../tmpl/signup.htm",
        "js/BaseView"
    ],
    function (signup, BaseView) {
        ich.addTemplate("signup", signup);
        var SignupView = BaseView.extend({

            template: _.template(signup),

            //container for the view
            parent: $('#placeholderDiv'),
            className: "signup",

            //handle events
            events: {
                "click #cancelButton": "cancelHandler",
                "click #signupButton": "beforeSave",
                "change": "change",
                "change #fileupload": "uploadPicture"
            },
            initialize: function () {
                this.constructor.__super__.initialize.apply(this);

            },
            //render the signup page
            render: function () {
                delete sessionStorage.username;
                var content = ich.signup(this.model.attributes);
                this.$el.html(content);
                return this;
            },
            updateContent: function (model) {
                var content = ich.signup(model);
                this.$el.html(content);
            },
            uploadPicture: function () {
                var input = document.getElementById("picUpload");
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
                var change = {};
                change[target.name] = target.value;

                this.model.set(change);

                // Run validation rule (if any) on changed item
                var check = this.model.validateItem(target.id, this.model.attributes);
                if (check.isValid === false) {
                    this.addValidationError(target.id, check.message);
                } else {
                    this.removeValidationError(target.id);
                }
                if (target.name == "email") {
                    this.$el.find('#password').attr('value', "");
                    this.model.set("password", "");
                    this.$el.find('#password2').attr('value', "");
                    this.model.set("password2", "");
                }

            },
            beforeSave: function (evt) {
                evt.preventDefault();
                var check = null;
                var failedValidation = false;

                for (var entry in this.model.attributes) {
                    if (entry !== "files[]") {
                        check = this.model.validateItem(entry, this.model.attributes);
                        if (check.isValid === false) {
                            failedValidation = true;
                            this.addValidationError(entry, check.message);
                        } else {
                            this.removeValidationError(entry);
                        }
                    }

                }
                if (failedValidation === true) {
                    //this.utils.displayValidationErrors(check.messages, $(".signupErrorContainer"));
                    return false;
                } else {
                    this.utils.hideAlert();
                    this.signup();
                }
            },
            signup: function (evt) {

                var username = this.model.get("email");
                var self = this;

                var password = this.model.get("password");
                this.model.unset("password");
                this.model.unset("password2");
                var userString = JSON.stringify(this.model);
//			$('#picUpload').fileupload('send', 
//			{files: this.files, 
//			formData: { user :userString},

//			success : function (result){
//			console.log("a new user was created");
//			window.app.navigate("login/"+username, {trigger: true});
//			}
//			});
                $.ajax({
                    type: 'POST',
                    cache: false,
                    dataType: 'json',
                    url: "SignupServlet",
                    data: {user: userString, password: password},
                    success: function (result) {
                        var err = result.errors;
                        if ((err) && (err !== null)) {
                            self.utils.displayValidationErrors(err, $(".signupErrorContainer"));
                        } else {
                            window.app.navigate("login/" + username, {trigger: true});


                        }
                    }
                });
            },
            cancelHandler: function () {
                window.app.navigate("", {trigger: true});

            }
        });
        return SignupView;
    });

