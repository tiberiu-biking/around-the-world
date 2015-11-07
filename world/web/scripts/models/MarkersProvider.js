define([],
    function () {
        function getMarkersIDs(arg, callback) {
            var resultArray = [];

            $.ajax({
                type: 'POST',
                url: "MarkersServlet",
                contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                data: {filter: arg},
                success: function (result) {
                    var err = result.result.errors;
                    if ((err) && (err !== null)) {
                        console.log("errors");
                    } else {
                        resultArray = result.result.data;
                        $.each(result.result.data, function (key, option) {

                        });
                        callback(resultArray);
                    }

                }
            });
        }

        var MarkersProvider = function (arg, callback) {
            getMarkersIDs(arg, callback);
        };
        return MarkersProvider;
    });