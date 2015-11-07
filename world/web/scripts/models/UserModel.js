define([], function () {
    var UserModel = Backbone.Model.extend({
        defaults: {
            firstName: '',
            lastName: '',
            email: '',
            country: 'Romania',
            password: '',
            password2: ''
        },
        messages: {
            EMPTY: "You can't leave this empty",
            EMAIL: "Incorrect email.",
            CONFIRMATION_PASSWORD: "Password must match.",
        },
        initialize: function () {

            this.validators = {
                email: this.validateEmail,
                firstName: this.validateFirstName,
                password: this.validatePassword,
                password2: this.validateConfirmPassword
            };
        },

        validateEmail: function (self, value) {
            if (value.length == 0) {
                return {isValid: false, message: self.messages.EMPTY};
            } else {
                var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                return re.test(value) ? {isValid: true} : {isValid: false, message: self.messages.EMAIL};
            }

        },
        validateFirstName: function (self, value, userModel) {
            return ( value.length > 0 ) ? {isValid: true} : {isValid: false, message: self.messages.EMPTY};
        },
        validatePassword: function () {
            return true;
        },
        validateConfirmPassword: function (self, value, userModel) {
            if ((value.length == 0) && (userModel.password.length > 0)) {
                return {isValid: false, message: "You can't leave this empty."};
            } else {
                return (value == userModel.password) ?
                {isValid: true} : {isValid: false, message: self.messages.CONFIRMATION_PASSWORD};
            }

        },

        validateItem: function (key, userModel) {
            var self = this;
            return (this.validators[key]) ? this.validators[key](self, this.get(key), userModel) : {isValid: true};
        },

        validateAll: function () {
            var validationResult = [];
            for (var key in this.validators) {
                if (this.validators.hasOwnProperty(key)) {
                    var check = this.validators[key](this.get(key));
                    if (check.isValid === false) {
                        var attr = {};
                        attr.msg = check.message;
                        attr.isvalid = false;
                        validationResult.push(key, attr);
                    }
                }
            }

            return _.size(validationResult) > 0 ? {isValid: false, messages: validationResult} : {isValid: true};
        }

    });
    return UserModel;
});