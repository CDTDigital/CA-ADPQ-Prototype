angular.module('CRNSFilters').filter('truncateStr', function() {
  return function(input, len) {
    input = input || '';
    len = len || null;

    var output = '';

    if (len && len > 0 && input.length > len) {
      output = input.substring(0, len);
      output += '...';
    } else {
      output = input;
    }

    return output;
  };
});
