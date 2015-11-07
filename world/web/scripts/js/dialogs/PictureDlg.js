define(["text!../../tmpl/selectPicture.htm",
        "models/MarkerBubbleModel",
        "js/dialogs/MarkerBubbleView",
    ],
    function (selectPicture, MarkerBubbleModel, MarkerBubbleView) {
        ich.addTemplate("selectPicture", selectPicture);
        var PictureDlg = Backbone.View.extend({
            initialize: function (args) {
                this.map = args.map;
                this.mapView = args.mapView;
                this.infoWindow = args.infoWindow;
            },
            events: {
                "click #submitFiles": "submitFiles"
            },
            render: function () {
                this.$el.attr('id', "select_dlg");
                this.$el.dialog(this.getDialogOptions());

                var dialog = $(this.$el.parent());
                //bind dialog close event because closing dialog with esc key doesnt fire the onClose function
                $(this.$el.parent()).on("dialogclose", function (event, ui) {
                    $("#select_dlg").dialog("destroy").remove();
                });

                this.files = [];
                var dialogContent = ich.selectPicture();
                this.$el.append(dialogContent);
                var self = this;
                var deferred = $.Deferred();

                $('#fileupload').fileupload({
                    dataType: 'json',
                    autoUpload: false,
                    add: function (e, data) {
                        console.log('Added file: ' + data.files[0].name);
                        self.files.push(data.files[0]);
                        var fileNameStr = $('#fileLabel')[0].innerHTML;
                        fileNameStr += "<br>" + data.files[0].name;
                        $('#fileLabel')[0].innerHTML = fileNameStr;
                    },
                    done: function (e, data) {
                    }
                });
                deferred.done(function (markerDB, map, infoWindow) {
                    var markerBubbleModel = new MarkerBubbleModel({markerDB: markerDB});
                    markerBubbleModel.init(map, infoWindow);
                    var markerBubbleView = new MarkerBubbleView({model: markerBubbleModel});
                    $(".ui-dialog-titlebar-close").click();
                });

                return this;
            },
            submitFiles: function () {
                console.log("submit files");
                var self = this;
                var deferred = $.Deferred();
                $('#fileupload').fileupload('send',
                    {
                        files: this.files,
                        formData: {userId: sessionStorage.getItem("userId")},
                        success: function (result) {
                            window.app.trigger("newMessage", result.data.message);
                            deferred.resolve(result.data.markers, self.map, self.infoWindow);
                        }
                    });

                deferred.done(function (markers, map, infoWindow) {
                    var mapview = self.mapView;
                    markers.forEach(function (markerDB) {

                        var markerBubbleModel = new MarkerBubbleModel({markerDB: markerDB});
                        self.mapView.bubbleMarkers.push(markerBubbleModel);
                        markerBubbleModel.init(self.map, self.infoWindow);
                        var markerBubbleView = new MarkerBubbleView({model: markerBubbleModel});
                    });

                    $(".ui-dialog-titlebar-close").click();
                });
            },

            getDialogOptions: function () {
                var self = this;

                return dlgOptions = {
                    title: "Select a picture ",
                    resizable: false,
                    modal: true,
                    width: "370px",
                    closeButton: true
                };
            },
        });

        return PictureDlg;
    });