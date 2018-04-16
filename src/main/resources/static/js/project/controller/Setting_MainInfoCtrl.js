app.controller('Setting_MainInfoCtrl', Setting_MainInfoCtrl);

function Setting_MainInfoCtrl ($http) {
  var mainInfo = this;
  
  var pointUrl = '/setting/mainInfo';
  mainInfo.message = null;
  mainInfo.country = [];
  mainInfo.city = [];
  
  mainInfo.saveInfo = function(info){
		if(	angular.isDefined(info) &&
	  		angular.isDefined(info.name)){
			if(angular.isDefined(info.id)){
				$http.put(pointUrl+'/'+info.id, info)
				.then(
					       function(response){
					    	   alert('Info saved');
					       }, 
					       function(response){
					    	   console.log(response);
					    	   mainInfo.message = response.message;
					       }
					    );
			} else {
			$http.post(pointUrl, info)
			.then(
				       function(response){
				    	   alert('Info updated');
				       }, 
				       function(response){
				    	   console.log(response);
				    	   mainInfo.message = response.message;
				       }
				    );
			}
	  	}
  };
  
  mainInfo.getCountry = function(){
	  $http.get('/dictionary/country', null)
		.then(
			       function(response){
			    	   mainInfo.country =response.data;
			       }, 
			       function(response){
			    	   console.log(response);
			    	   mainInfo.message = response.message;
			       }
			    ); 
	  
  };
  
  mainInfo.getCity = function(countyId){
	  if(angular.isDefined(countyId)){
	  $http.get('/dictionary/city/'+countyId, null)
		.then(
			       function(response){
			    	   mainInfo.city =response.data;
			       }, 
			       function(response){
			    	   console.log(response);
			    	   mainInfo.message = response.message;
			       }
			    ); 
	  }
  };
 
  
  mainInfo.getInfo = function(){
	  $http.get(pointUrl, null)
		.then(
			       function(response){
			    	   mainInfo.info = response.data;
			    	   //console.log(mainInfo.info);
			    	   if(angular.isDefined(mainInfo.info) 
			    			   && angular.isDefined(mainInfo.info.country) ) {
			    	   		mainInfo.getCity(mainInfo.info.country.countryId);
			    	   }
			       }, 
			       function(response){
			    	   console.log(response);
			    	   mainInfo.message = response.message;
			       }
			    ); 
  }
  
  // load info
  mainInfo.getInfo();
};