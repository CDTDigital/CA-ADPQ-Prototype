/**
 * Created by sunil.jhamnani on 2/26/17.
 */
(function () {
    var app = angular.module("CRNS");
    app.constant("GConfiguration", {

        // used Google autocompete function
        GOOGLE_AUTOCOMPLETE_OPTIONS: {
            country: 'us',
            types: []
        }
    })
}());