define([],
    function () {
        var instance = null;

        function AuthenticationManager() {
            this.isInitialized = false;
            this.objectMapDefault = {};
        }

        AuthenticationManager.prototype = {

            /**
             *  Initialize the dashboard manager(please see the usage)
             * @param args
             * @returns
             * @memberOf AuthenticationManager#
             * @public
             */
            initialize: function (args) {
                this.isInitialized = true;
                this.objectMapDefault["user_id"] = args["user_id"];
                this.objectMapDefault["acces_token"] = args["acces_token"];
            },
            /**
             * Is manager initialized?
             * @returns true is initialize, false otherwise
             * @memberOf AuthenticationManager#
             * @public
             */
            initialized: function () {
                return this.isInitialized;
            }

        };

        /**
         * Access the instance of this Singleton class.
         */
        AuthenticationManager.getInstance = function () {
            if (instance === null) {
                instance = new AuthenticationManager();
            }
            return instance;
        };

        return AuthenticationManager.getInstance();

    });