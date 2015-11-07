define(["models/UserModel"],
    function (UserModel) {

        var NewUserModel = UserModel.extend({

            validatePassword: function (self, value, userModel) {
                return ( value.length > 0 ) ? {isValid: true} : {isValid: false, message: self.messages.EMPTY};

            },
            validateConfirmPassword: function (self, value, userModel) {
                if (value.length == 0) {
                    return {isValid: false, message: self.messages.EMPTY};
                } else {
                    return (value == userModel.password) ?
                    {isValid: true} : {isValid: false, message: self.messages.CONFIRMATION_PASSWORD};
                }
            }


        });
        return NewUserModel;
    });