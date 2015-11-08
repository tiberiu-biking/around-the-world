/**
 *
 */
// This is the main entry point for the App
define(['AppRouter'], function (AppRouter) {
    var init = function () {

        this.router = new AppRouter({
            append_at: $('#placeholderDiv')
        });


        window.app = this.router;
        Backbone.history.start();

        // Tell jQuery to watch
        //for any statusCode and handle them appropriately
        $.ajaxSetup({
            type: 'POST',
            cache: false,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            datatype: "json",
            statusCode: {
                401: function () {
                    console.log("400 ----->Redirect to the login page.");
                    window.location.replace('#denied');

                },
                500: function () {
                    console.log("500 --->Reddirect to denied page");
                    window.location.replace('#denied');
                }
            }
        });

        /*
         * Add a 'close' method to each Backbone.View, this will remove elements
         * from the DOM, clean the events and call onClose (if any) to run custom
         * clean-up code.
         */
        Backbone.View.prototype.close = function () {

            // - if there is a onClose function, call it
            if (this.onClose) {
                this.onClose();
            }

            // - remove the HTML that populates this.el
            // - clean DOM element events (events: {...})
            this.remove();
            // - unbind any events that the view triggers directly
            this.unbind();
        };

    };

    return {
        init: init
    };
});