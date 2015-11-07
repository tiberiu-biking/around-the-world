define([
        "js/Utils"]
    , function (Utils) {
        BaseView = Backbone.View.extend({

            initialize: function () {
                this.utils = new Utils();
                this.$el.html(this.template());
                this.el = $(this.el);
                this.el.hide();

            },
            addValidationError: function (field, message) {
                var controlGroup = this.$el.find('#' + field).parent().parent();
                controlGroup.addClass('error');
                $('.help-inline', controlGroup).html(message);
            },

            removeValidationError: function (field) {
                var controlGroup = this.$el.find('#' + field).parent().parent();
                controlGroup.removeClass('error');
                $('.help-inline', controlGroup).html('');
            },
            removeAllValidationErrors: function () {
                if ((this.model == null) && (this.model !== undefined)) {
                    for (var entry in this.model.attributes) {
                        this.removeValidationError(entry);
                    }
                }

            },
            hide: function () {
                promise = $.Deferred(_.bind(function (dfd) {
                    this.el.fadeOut('fast', dfd.resolve)
                }, this));
                return promise.promise();
            },

            show: function () {
                promise = $.Deferred(_.bind(function (dfd) {
                    this.el.fadeIn('fast', dfd.resolve)
                }, this));
                return promise.promise();
            }

        });
        return BaseView;
    });