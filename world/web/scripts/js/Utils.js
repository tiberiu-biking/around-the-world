define(["text!../tmpl/errorMessage.htm",], function (errorMessage) {
    ich.addTemplate("errorTemplate", errorMessage);
    Utils = Backbone.View.extend({
        initialize: function () {

        },

        render: function (msg) {
            var errorMsg = {};
            errorMsg.errorMessage = msg;
            var errorContent = ich.errorTemplate(errorMsg);
            this.$el.html(errorContent);
            return this;
        },
        showAlert: function (title, text, klass, parent) {
            this.render(text);
            if (klass == "ui-state-highlight") {
                this.$el.find(".action.has-error").hide();
            } else {
                this.$el.find(".action.has-success").hide();
            }
            parent.append(this.$el);
        },

        hideAlert: function (parent) {
            this.$el.find("#errorWidget").empty().hide();

        },
        showAndHide: function (parent) {
            parent.show().delay(5000).fadeOut();

        },
        displayValidationWarnings: function (messages, parent) {
            parent.empty();
            if (!messages) {
                messages = 'Fix validation errors and try again';
            }
            this.render(messages);
            this.$el.find(".action.has-error").hide();
            parent.append(this.$el);
            this.showAndHide(parent);
        },
        displayValidationErrors: function (messages, parent) {
            this.$el.empty();
//        for (var key in messages) {
//            if (messages.hasOwnProperty(key)) {
//                this.addValidationError(key, messages[key]);
//            }
//        }
            if (!messages) {
                messages = 'Fix validation errors and try again';
            }
            this.render(messages);
            this.$el.find(".action.has-success").hide();
            parent.append(this.$el);
        }
    });
    return Utils;
});
