define(["text!../tmpl/header.htm"], function (header) {
    ich.addTemplate("header", header);
    HeaderView = Backbone.View.extend({

        initialize: function () {
            this.render();
        },

        render: function () {
            this.$el.empty();
            var tmplObj = {};
            tmplObj.username = sessionStorage.getItem("username");
            var markerContent = ich.header(tmplObj);
            this.$el.append(markerContent);

            return this;
        }

    });
    return HeaderView;
});