/**
 *
 */
define([], function () {
    var AppSession = Backbone.Model.extend({
        defaults: {
            access_token: null,
            user_id: null
        },
        isAuthenticated: function () {
            return this.access_token;
        },
        save: function (autoHash) {
            $.cookie("user_id", autoHash.id);
            $.cookie("acces_token", autoHash.acces_token);
        },
        load: function () {
            this.access_token = $.cookie('user_id');
            this.user_id = $.cookie('access_token');
        }

    });
    return AppSession;
});