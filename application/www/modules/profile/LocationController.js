'use strict';
angular.module('CRNSCtrl')
.controller('LocationCtrl', ['$scope', 'GoogleMapService', '$rootScope', function($scope, GoogleMapService, $rootScope) {
    $scope.location = {search: ''};
    $scope.isMapVisible = false;
    $scope.locResults = [];

    /* On every search input change, it will get the predictions result from google services. */
    $scope.getLocation = function(input) {
        var resComp = document.getElementById('resComponent');
        if (input!='') {
            if($scope.isMapVisible) $scope.isMapVisible = false;

            GoogleMapService.getSearchPredictions(input, $scope.locPredictionsCallback);
            if(resComp != undefined && resComp.childElementCount>0) {
                resComp.style.display = 'block';
            }
        } else {
            resComp.style.display = 'none';
        }
    };

    /* Callback for hanlding the searched predictions */
    $scope.locPredictionsCallback = function(predictions, status) {
        console.log(predictions);
        if (status != google.maps.places.PlacesServiceStatus.OK) {
            $scope.locResults = {'Object': {'description': 'No results found!', 'code': 'NORESULT'}};
            $scope.$apply();
        } else {
            /* Removed County, State and Country from results */
            var filterPredictions = angular.copy(predictions);
            for(var i =0; i < predictions.length; i++) {
                if(predictions[i].types[0] == 'administrative_area_level_1' || predictions[i].types[0] == 'administrative_area_level_2' || predictions[i].types[0] == 'country') {
                    filterPredictions[i] = {};
                }
            }

            console.log(filterPredictions);
            $scope.locResults = filterPredictions;
            console.log($scope.locResults.length);
            $scope.$apply();
        }
    };

    /* Callback for handling the results from Geocoder place api */
    $scope.locationResults = function(results, status) {
        if (status === google.maps.GeocoderStatus.OK) {
            console.log(results);
            if(results[0].address_components) {
                var tempObj = {address: results[0].formatted_address, placeId: results[0].place_id};
                GoogleMapService.setLocationAddress(tempObj);
                $rootScope.goBack();
            }
        } else {
            console.log('Geocoder failed due to: ' + status);
        }
    };

    /* To get the location detail on basis on place id */
    $scope.getLocationDescription = function(data) {
        console.log(data);
        if(data.description == 'No results found!') return;
        GoogleMapService.getPlaceInformation(data.place_id, $scope.locationResults);
    };
}]);