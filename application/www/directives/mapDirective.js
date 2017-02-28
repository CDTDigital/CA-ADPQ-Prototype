angular.module('CRNSDirective', [])
.directive('mapDirective', function() {
  return {
    restrict: 'E',
    transclude: true,
    template: '<div id="map" style="height: 190px; width: 100%" class="map-frame mapStyle" data-tap-disabled="true"></div>',
    scope: {
      mapData: '=mapData',
      markerClick: '=markerClick',
      zoomLevel: '=zoomLevel',
      errorCallBack: '=errorCallBack'
    },
    link: function(scope, el, attr) {
        console.log(scope.mapData);

        try {
          var map = new google.maps.Map(document.getElementById('map'), {
              mapTypeId: google.maps.MapTypeId.ROADMAP,
              streetViewControl: false,
              zoomControl: false
          });
        } catch(e) {
          scope.errorCallBack(false); return;
        };

        scope.initializeMap = function() {
          var bounds = new google.maps.LatLngBounds();
          var latLng;
          for (var i = 0; i < scope.mapData.length; i++) {
              if(scope.mapData[i].Latitude != undefined &&
                  scope.mapData[i].Longitude != undefined &&
                  scope.mapData[i].Latitude != null &&
                  scope.mapData[i].Longitude != null &&
                  scope.mapData[i].Latitude != '0' &&
                  scope.mapData[i].Longitude != '0') {
                  latLng = new google.maps.LatLng(scope.mapData[i].Latitude, scope.mapData[i].Longitude);
                  new google.maps.Marker({
                      position: latLng,
                      map: map
                  });
                  bounds.extend(latLng);
              }
          }

          map.setCenter(latLng);
          if(scope.zoomLevel != undefined) map.setZoom(Number(scope.zoomLevel));
        };
      scope.initializeMap();
    }
  }
});