define([], function () {
    var MarkerModel = Backbone.Model.extend({
        defaults: {
            id: '',
            latitude: 0,
            longitude: 0,
            name: ''
        },
        initialize: function () {
            this.validators = {};

            this.validators.name = function (value) {
                return value.length > 0 ? {isValid: true} : {isValid: false, message: "You can't leave this empty."};
            };

        },

        validateItem: function (key) {
            return (this.validators[key]) ? this.validators[key](this.get(key)) : {isValid: true};

        },

        validateAll: function () {

            var messages = {};

            for (var key in this.validators) {
                if (this.validators.hasOwnProperty(key)) {
                    var check = this.validators[key](this.get(key));
                    if (check.isValid === false) {
                        messages[key] = check.message;
                    }
                }
            }

            return _.size(messages) > 0 ? {isValid: false, messages: messages} : {isValid: true};
        }

    });
    return MarkerModel;
});