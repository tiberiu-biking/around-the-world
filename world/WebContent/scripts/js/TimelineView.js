/**
 *
 */
define(["text!../tmpl/timeline.htm",
        "text!../tmpl/timelineComp.htm",
        "text!../tmpl/timelineBubble.htm",
        "js/BaseView"
    ],
    function (timeline, timelineComp, timelineBubble, BaseView) {
        ich.addTemplate("timeline", timeline);
        ich.addTemplate("timelineComp", timelineComp);
        ich.addTemplate("timelineBubble", timelineBubble);
        var TimelineView = Backbone.View.extend({

            //container for the view
            template: _.template(timeline),
            parent: $('.timelineContent'),
            className: "timeline",

            //handle events
            events: {
                "click #cancelButton": "cancelHandler",
            },
            initialize: function () {
                this.constructor.__super__.initialize.apply(this);
                var content = ich.timeline();
                this.$el.html(content);
                this.parent.append(this.$el);
            },
            render: function () {
                TimelineView.__super__.render.apply(this, arguments);
                this.$el.find(".cbp_tmtimeline").empty();
                var self = this;
                this.def = $.Deferred();
                this.getUser();
                this.def.done(function (years) {
                    years.forEach(function (yearGroup) {
                        var yearObj = {};
                        yearObj.date = yearGroup.year;
                        yearObj.year = yearGroup.year;
                        var content = ich.timelineComp(yearObj);
                        var newTimeLine = self.$el.find(".cbp_tmtimeline");
                        newTimeLine.append(content);
                        yearGroup.markers.forEach(function (marker) {
                            var markerTmpl = {};
                            markerTmpl.name = marker.name;
                            markerTmpl.note = marker.note;
                            markerTmpl.date = marker.date;
                            var markerContent = ich.timelineBubble(markerTmpl);
                            content.find(".markers").append(markerContent);
                        })
                    });
                });
            },
            //render the signup page
            getUser: function () {
                var self = this;
                $.ajax({
                    type: 'POST',
                    url: "GetTimelineServlet",
                    dataType: 'json',
                    data: {userId: sessionStorage.getItem("userId")},
                    success: function (result) {
                        var err = result.errors;
                        if ((err) && (err !== null)) {
                            self.displayValidationErrors(err, $(".userErrorContainer"));
                        } else {
                            self.def.resolve(result.data.timeline);
                        }
                    }
                });

                return this;

            }


        });
        return TimelineView;
    });

