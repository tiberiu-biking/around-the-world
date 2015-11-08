define(["text!../tmpl/editMarker.htm",
        "js/BaseView",
        "models/MarkerModel"
    ],
    function (editMarker, BaseView, MarkerModel) {
        ich.addTemplate("editMarker", editMarker);
        var MarkerView = BaseView.extend({

            template: _.template(editMarker),
            parent: $('#placeholderDiv'),
            className: "editMarker",

            initialize: function (args) {
                this.constructor.__super__.initialize.apply(this);

                var self = this;

                if (!args.model) {
                    var deferred = $.Deferred();
                    $.ajax({
                        type: 'POST',
                        url: "GetMarkersServlet",
                        dataType: 'json',
                        data: {userId: sessionStorage.getItem("userId"), markerId: args.markerId},
                        success: function (result) {
                            var err = result.errors;
                            if ((err) && (err !== null)) {
                                console.log("errors");
                            } else {
                                deferred.resolve(result.data.markers[0]);
                            }

                        }
                    });
                    deferred.done(function (markerDB) {
                        self.model = new MarkerModel(markerDB);
                        self.render();
                    });

                } else {
                    this.render();
                }

            },
            events: {
                "change": "change",
                "click #saveButton": "beforeSave",
                "click #cancelButton": "cancelHandler"
            },
            render: function () {
                this.$el.attr('id', _.uniqueId("edit_dlg"));

                var dialogContent = ich.editMarker(this.model.attributes);
                this.$el.html(dialogContent);
                this.utils.hideAlert();
                var timePicker = this.$el.find(".datepicker");
                timePicker.datepicker({
                    dateFormat: 'yy-mm-dd',
                    beforeShow: function (input, inst) {
                        setTimeout(function () {
                            inst.dpDiv.css({
                                left: $(inst.input).position().left - 40,
                            });
                        }, 0);
                    }
                });
                timePicker.datepicker('setDate', new Date(this.model.get("date")));

                return this;
            },
            updateContent: function () {
                var content = ich.signup(this.model.attributes);
                this.$el.html(content);
            },
            change: function (event) {
                // Apply the change to the model
                var target = event.target;
                var change = {};
                change[target.name] = target.value;
                this.model.set(change);
                // Run validation rule (if any) on changed item
                var check = this.model.validateItem(target.id);
                if (check.isValid === false) {
                    this.addValidationError(target.id, check.message);
                } else {
                    this.removeValidationError(target.id);
                }

            },
            beforeSave: function (evt) {
                evt.preventDefault();
                var check = null;
                var failedValidation = false;
                for (var entry in this.model.attributes) {
                    check = this.model.validateItem(entry, this.model.attributes);
                    if (check.isValid === false) {
                        failedValidation = true;
                        this.addValidationError(entry, check.message);
                    } else {
                        this.removeValidationError(entry);
                    }

                }
                if (failedValidation === true) {
                    // this.utils.displayValidationErrors(check.messages, $(".markerErrorContainer"));
                    return false;
                } else {
                    this.saveMarker();
                }

            },
            saveMarker: function () {
                var self = this;
                var jsonString = JSON.stringify(this.model);
                console.log("Marker jsonString " + jsonString);
                $.ajax({
                    type: 'POST',
                    cache: false,
                    dataType: 'json',
                    url: "UpdateMarkerServlet",
                    data: {marker: jsonString},
                    success: function (result) {
                        console.log('Success!', 'marker saved successfully');
                        window.app.navigate("home", {trigger: true});
                        window.app.trigger("updateTimeline");
                    },
                    error: function (result) {
                        console.log('Error', 'An error occurred while trying to save this item');
                        self.utils.displayValidationErrors(result, $(".markerErrorContainer"));
                    }
                });
            },
            cancelHandler: function () {
                window.history.back();
            },
            getDialogOptions: function () {
                return dlgOptions = {
                    title: "Edit " + this.model.get("name"),
                    resizable: false,
                    modal: true,
                    closeButton: true
                };
            }

        });

        return MarkerView;
    });