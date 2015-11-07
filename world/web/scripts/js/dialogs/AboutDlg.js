define(["text!../../tmpl/about.htm",
        "models/MarkerBubbleModel",
        "js/dialogs/MarkerBubbleView",
    ],
    function (about, MarkerBubbleModel, MarkerBubbleView) {
        ich.addTemplate("about", about);
        var AboutDlg = Backbone.View.extend({
            initialize: function () {
            },
            events: {
                "click #submitFiles": "submitFiles"
            },
            render: function () {
                this.$el.attr('id', "about_dlg");
                this.$el.dialog(this.getDialogOptions());

                var dialog = $(this.$el.parent());
                //bind dialog close event because closing dialog with esc key doesnt fire the onClose function
                $(this.$el.parent()).on("dialogclose", function (event, ui) {
                    $("#select_dlg").dialog("destroy").remove();
                });

                this.files = [];
                var dialogContent = ich.about();
                this.$el.append(dialogContent);
            },
            getDialogOptions: function () {
                var self = this;

                return dlgOptions = {
                    title: "About Around the World",
                    resizable: false,
                    modal: true,
                    width: "350px",
                    closeButton: true
                };

            },


        });

        return AboutDlg;
    });