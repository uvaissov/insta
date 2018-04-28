app.controller('PrimaryCtrl', PrimaryCtrl);

function PrimaryCtrl ($http) {
  var primary = this;
  var pointUrl = '/cabinet/data';
  primary.message = null;
  primary.saveInfo = function(info){
		if(	angular.isDefined(info) &&
	  		angular.isDefined(info.username)){
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
  
  
  primary.get = function(){
	  $http.get(pointUrl+'/primary', null)
		.then(
			       function(response){
			    	   primary.info = response.data;
			    	   console.log(mainInfo.info);
			       }, 
			       function(response){
			    	   console.log(response);
			    	   primary.message = response.message;
			       }
			    ); 
  }
  
  // load info
  primary.get();
};