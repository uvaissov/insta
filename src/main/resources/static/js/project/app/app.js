var app = angular.module('BookingApp',['ui.bootstrap','ui.mask','ngAnimate']);


app.directive('ngConfirmClick', [
    function(){
        return {
            link: function (scope, element, attr) {
                var msg = attr.ngConfirmClick || "Вы уверены ?";
                var clickAction = attr.confirmedClick;
                element.bind('click',function (event) {
                    if ( window.confirm(msg) ) {
                        scope.$eval(clickAction)
                    }
                });
            }
        };
}]);
app.directive('customOnChange', function() {
	  return {
	    restrict: 'A',
	    link: function (scope, element, attrs) {
	      var onChangeHandler = scope.$eval(attrs.customOnChange);
	      element.on('change', onChangeHandler);
	      element.on('$destroy', function() {
	        element.off();
	      });

	    }
	  };
	});
$(document).ready(function(){
	$("#preloader").hide();
});
