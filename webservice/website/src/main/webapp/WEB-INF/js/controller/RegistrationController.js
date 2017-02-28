/**
 * Created by sunil.jhamnani on 2/25/17.
 */
(function () {

    function RegistrationController($scope, $timeout, HttpFactory, toaster, GooglePlacesFactory) {
        function onInit() {
            $scope.register = {};
        }
        //$scope.getLocation = function(input) {
        //    var resComp = document.getElementById('resComponent');
        //    if (input!=undefined) {
        //        if($scope.isMapVisible)
        //            $scope.isMapVisible = false;
        //
        //        GooglePlacesFactory.getSearchPredictions(input + ' california', $scope.locPredictionsCallback);
        //        if(resComp != undefined && resComp.childElementCount>0) {
        //            resComp.style.display = 'block';
        //        }
        //    } else {
        //        resComp.style.display = 'none';
        //    }
        //};
        //
        ///* Callback for hanlding the searched predictions */
        //$scope.locPredictionsCallback = function(predictions, status) {
        //    console.log(predictions);
        //    if (status != google.maps.places.PlacesServiceStatus.OK) {
        //        $scope.locResults = {'Object': {'description': 'No results found!', 'code': 'NORESULT'}};
        //        $scope.$apply();
        //    } else {
        //        /* Removed County, State and Country from results */
        //        var filterPredictions = angular.copy(predictions);
        //        for(var i =0; i < predictions.length; i++) {
        //            if(predictions[i].types[0] == 'administrative_area_level_1' || predictions[i].types[0] == 'administrative_area_level_2' || predictions[i].types[0] == 'country') {
        //                filterPredictions[i] = {};
        //            }
        //        }
        //
        //        console.log(filterPredictions);
        //        $scope.locResults = filterPredictions;
        //        console.log($scope.locResults.length);
        //        $scope.$apply();
        //    }
        //};
        //
        ///* Callback for handling the results from Geocoder place api */
        //$scope.locationResults = function(results, status) {
        //    var addressObj = {};
        //    if (status === google.maps.GeocoderStatus.OK) {
        //        console.log(results);
        //        if(results[0].address_components) {
        //            results[0].address_components.forEach(function(elem){
        //                addressObj[elem.types[0]] = elem.long_name
        //            });
        //            $scope.register.location =  {
        //                addressLine1: addressObj.street_number,
        //                addressLine2: addressObj.route,
        //                city: addressObj.locality,
        //                zipCode: addressObj.postal_code,
        //                placeId: results[0].place_id,
        //                latitude: results[0].geometry.location.lat(),
        //                longitude: results[0].geometry.location.lng(),
        //                currentLocation: false
        //            };
        //        }
        //    } else {
        //        console.log('Geocoder failed due to: ' + status);
        //    }
        //};
        //
        ///* To get the location detail on basis on place id */
        //$scope.getLocationDescription = function(data) {
        //    console.log(data);
        //    if(data.description == 'No results found!') return;
        //    $scope.isMapVisible = true;
        //    $scope.location = data.description
        //    GooglePlacesFactory.getPlaceInformation(data.place_id, $scope.locationResults);
        //};


        $scope.submit = function () {
            $scope.register.location = getLocationObject($scope.locationDetails);
            if ($scope.register.password != $scope.confirmPassword) {
                toaster.pop('error', "Password and Confirm password are not matching");
                return
            }
            $scope.isLoading = true;
            HttpFactory.signup($scope.register).then(function (response) {
                toaster.pop('info', "User successfully registered");
                $timeout(function () {
                    $scope.go('/login');
                }, 2000);
            }, function (error) {
                toaster.pop('error', error.message);
            })
        };

        function getLocationObject(loc) {
            var addressObj={};
            if(loc.address_components) {
                loc.address_components.forEach(function(elem){
                    addressObj[elem.types[0]] = elem.long_name
                });
                return  {
                    addressLine1: addressObj.street_number,
                    addressLine2: addressObj.route,
                    city: addressObj.locality,
                    zipCode: addressObj.postal_code,
                    placeId: loc.place_id,
                    latitude: loc.geometry.location.lat(),
                    longitude: loc.geometry.location.lng(),
                    currentLocation: false
                };
            }
        }

        onInit();
    }

    var app = angular.module('CRNS'),
        requires = [
            '$scope',
            '$timeout',
            'modules.core.HttpFactory',
            'toaster',
            'modules.google.GooglePlacesFactory',
            RegistrationController
        ];
    app.controller('RegistrationController', requires)
}());
