define(["js/MarkerBubbleView"
], function (MarkerBubbleView) {
    var MarkersBubbleCollection = Backbone.Collection.extend({
        model: MarkerBubbleView,
        url: "GetMarkersServlet"
    });
    return MarkersBubbleCollection;
});
