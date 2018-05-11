app.controller('PrimaryCtrl', PrimaryCtrl);

function PrimaryCtrl ($http) {
  var primary = this;
  var pointUrl = '/cabinet/data/primary';
  primary.backgroundList = backItems ;
  console.log(primary.backgroundList);
  primary.message = null;
  primary.saveInfo = function(info){
		if(	angular.isDefined(info) &&
	  		angular.isDefined(info.username)){
			angular.forEach(primary.backgroundList, function(value) {
				  if(value.active===true){
					  info.background = value.name;
				  }
			});
			
			$http.post(pointUrl, info)
			.then(
				       function(response){
				    	   alert('Info updated');
				       }, 
				       function(response){
				    	   console.log(response);
				    	   primary.message = response.message;
				       }
				    );
	  	}
  };
  primary.rowClassBack = function(background){
	  var str = background.name;
	  if(background.active===true){
		 str+=' selected';
	  }
	  return str;
  }
  primary.selectBackground=function(item){
	  angular.forEach(primary.backgroundList, function(value) {
		  if(value.active===true){
			  value.active=false;
		  }
		});
	  item.active=true;
  }
  primary.get = function(){
	  $http.get(pointUrl, null)
		.then(
			       function(response){
			    	   primary.info = response.data;
			    	   console.log(primary.info);
			       }, 
			       function(response){
			    	   console.log(response);
			    	   primary.message = response.message;
			       }
			    ); 
  }
  
  // load info
  primary.get();
	  $(document).ready(function() {
		$(".slicker-class").slick({
			infinite : true,
			slidesToShow : 3,
			speed : 400,
			slidesToScroll : 3
		});

		angular.forEach(primary.backgroundList, function(value, index) {
			if (value.active === true) {
				$('.slicker-class').slick('slickGoTo', index);
			}
		});
	});
  
};