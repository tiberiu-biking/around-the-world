define([],
    function () {

        function connectTo(callback) {
            var CLIENT_ID = "VBTDS2HOUSNYSBMPHZ1L323O1OVHCYXH14H1OKNASCI31TFI";
            var CLIENT_SECRET = "Z2L1441PKNPMZMF1X301REX55IIV3B55OHKVKAK3CEDUPQPP";
            var REDIRECT_URL = "http://localhost:8080/#foursquare/";
            var link = 'https://foursquare.com/oauth2/authenticate'
                + '?client_id=' + CLIENT_ID
                + '&response_type=code'
                + '&redirect_uri=' + encodeURIComponent(REDIRECT_URL);

            doAuthRedirect(link);
        }

        function doAuthRedirect(link) {
            window.location.href = link;
        }

        var ConnectToFoursquare = function (callback) {
            connectTo(callback);
        };
        return ConnectToFoursquare;
    });

