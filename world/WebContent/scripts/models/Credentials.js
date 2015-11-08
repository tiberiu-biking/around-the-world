define([], function () {
    var Credentials = Backbone.Model.extend({

        initialize: function () {
            this.bind("change", this.attributesChanged);
        },

        attributesChanged: function () {
            var valid = false;
            if (this.get('username') && this.get('password'))
                valid = true;
            this.set({valid: true});
            //this.trigger("validated", valid);
        }

    });
    return Credentials;
});
