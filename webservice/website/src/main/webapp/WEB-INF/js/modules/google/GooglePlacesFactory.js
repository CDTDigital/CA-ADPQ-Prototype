/**
 * Created by sunil.jhamnani on 2/26/17.
 */
(function () {
    'use strict';
    function GooglePlacesFactory($log, GConfiguration) {

        var autoCompleteObj;
        var placesObj;

        /* It will check either google is defined or not */
        function isGoogleDefined() {
            try {
                if(!google || !angular.isObject(google) || !angular.isObject(google.maps)) {
                    return false;
                } else {
                    return true;
                }
            } catch(e) {
                return false;
            }
        }

        /* Create a instance for Autocomplete search api */
        this.startGooglePlaceAPI = function() {
            try{
                autoCompleteObj = new google.maps.places.AutocompleteService();
            } catch(ex) {
                $log.debug('ERROR to load initGooglePlaceAPI');
                $log.debug(ex);
            }
        };

        /* Create a instance for Autocomplete search api */
        this.startGeocoderAPI = function() {
            placesObj = new google.maps.Geocoder;
        };

        /* Using Autocomplete getPlacePredictions method, get the search predictions on the basis on input text.
         *  And will return the data in callback.
         */
        this.getSearchPredictions = function(input, callback) {
            if(autoCompleteObj != undefined && autoCompleteObj != null) {
                autoCompleteObj.getPlacePredictions({
                    input: input,
                    componentRestrictions: {
                        types: GConfiguration.GOOGLE_AUTOCOMPLETE_OPTIONS.types,
                        country: GConfiguration.GOOGLE_AUTOCOMPLETE_OPTIONS.country
                    }
                }, callback);
            } else {
                this.startGooglePlaceAPI();
            }
        };

        /* Using Geocoder geocode method, get the place details on the basis on google place id.
         *  And will return the results and status in callback.
         */
        this.getPlaceInformation = function(placeid, callback) {
            if(placesObj == undefined || placesObj == null) this.startGeocoderAPI();

            placesObj.geocode({'placeId': placeid}, function(results, status) {
                if (!callback) return results;
                callback(results, status);
            });
        };

        // Set the search location address into local storage to use for future.
        this.setLocationAddress = function(data) {
            localStorage.setItem('locationData', angular.toJson(data));
        };

        // Get the search location address from local stroage.
        this.getLocationAddress = function() {
            var lData = angular.fromJson(localStorage.getItem('locationData'));
            if(lData == undefined || lData == null) {
                return {address: '', placeId: ''};
            } else return lData;
        };

        if (!isGoogleDefined()) $log.debug('Problem initiating google api')
    }

    var app = angular.module('CRNS'),
        requires = [
            '$log',
            'GConfiguration',
            GooglePlacesFactory
        ];
    app.service('modules.google.GooglePlacesFactory', requires);
}());
