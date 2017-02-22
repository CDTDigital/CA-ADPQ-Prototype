'use strict';
angular.module('CRNSSrv')
    .service('GoogleMapService', [function() {
        // Default Server params
        var autoCompleteObj;
        var placesObj;

        /* Configuration for autocomplete search api  */
        var config = {
            country: 'us',
            types: []
        };

        /* It will check either google is defined or not */
        this.isGoogleDefined = function() {
            try {
                if(!google || !angular.isObject(google) || !angular.isObject(google.maps)) {
                    return false;
                } else {
                    return true;
                }
            } catch(e) {
                return false;
            }
        };

        /* Create a instance for Autocomplete search api */
        this.startGooglePlaceAPI = function() {
            console.log('CONSOLE startGooglePlaceAPI');
            try{
                autoCompleteObj = new google.maps.places.AutocompleteService();
            } catch(ex) {
                console.log('ERROR to load initGooglePlaceAPI');
                console.log(ex);
            }
        };

        /* Create a instance for Autocomplete search api */
        this.startGeocoderAPI = function() {
            console.log('CONSOLE startGeocoderAPI');
            placesObj = new google.maps.Geocoder;
        };

        /* Using Autocomplete getPlacePredictions method, get the search predictions on the basis on input text.
        *  And will return the data in callback.
        */
        this.getSearchPredictions = function(input, callback) {
            console.log('Getting More Predictions');
            if(autoCompleteObj != undefined && autoCompleteObj != null) {
                console.log(input);
                autoCompleteObj.getPlacePredictions({
                    input: input,
                    componentRestrictions: {
                        types: config.types,
                        country: config.country
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
    }]);
