var app = angular.module('BookingApp',['ui.bootstrap','ui.mask']);


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
}])
$(document).ready(function(){
	$("#preloader").hide();
});
