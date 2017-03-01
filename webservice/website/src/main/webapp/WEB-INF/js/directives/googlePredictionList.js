/**
 * Created by sunil.jhamnani on 2/28/17.
 */

(function () {
    'use strict';

    function googlePredictionList() {
        function linkFunc(scope, eleement, attr) {

        }
        function GooglePredictionListController($scope, GooglePlacesFactory) {
            $scope.$watch('name', function () {
                $scope.search = $scope.name;
            });
            $scope.getLocation = function(input) {
                var resComp = document.getElementById('resComponent');
                if (input!=undefined) {
                    if($scope.isPredictionVisible)
                        $scope.isPredictionVisible = false;

                    GooglePlacesFactory.getSearchPredictions(input + ' california', $scope.locPredictionsCallback);
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
                    $scope.locResults = {'obj': {'description': 'No results found!', 'code': 'NORESULT'}};
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
                var addressObj = {};
                if (status === google.maps.GeocoderStatus.OK) {
                    //return results[0];
                    console.log(results);
                    $scope.location = results[0];
                } else {
                    console.log('Geocoder failed due to: ' + status);
                }
            };

            /* To get the location detail on basis on place id */
            $scope.getLocationDescription = function(data) {
                console.log(data);
                if(data.description == 'No results found!') return;
                $scope.isPredictionVisible = true;
                $scope.search = data.description;
                GooglePlacesFactory.getPlaceInformation(data.place_id, $scope.locationResults);
            };

        }
        return {
            restrict: 'E',
            templateUrl: "/view/shared/prediction-list.html",
            controller: ['$scope','modules.google.GooglePlacesFactory', GooglePredictionListController],
            scope: {
                location: '=location',
                name: '=name'
            },
            link: linkFunc
        }
    }

    var app = angular.module("CRNS"),
        requires = [
            googlePredictionList
        ];
    app.directive("googlePredictionList", requires)
}());
