define(["models/MarkerModel"
    ],
    function (MarkerModel) {
        var MarkersCollection = Backbone.Collection.extend({
            model: MarkerModel,

        });
        return MarkersCollection;
    });
