app.controller('LinksCtrl', LinksCtrl);

function LinksCtrl ($http) {
  var me = this;
  var pointUrl = '/cabinet/data/links';
  me.message = null;
  me.save = function(model){
		if(	angular.isDefined(model))
			$http.post(pointUrl, model)
			.then(
				       function(response){
				    	   alert('Info updated');
				       }, 
				       function(response){
				    	   console.log(response);
				    	   me.message = response.message;
				       }
				    );
	  	
  };

  me.get = function(){
	  $http.get(pointUrl, null)
		.then(
			       function(response){
			    	   me.data = response.data;
			    	   console.log(me.data);
			       }, 
			       function(response){
			    	   console.log(response);
			    	   me.message = response.message;
			       }
			    ); 
  }
  
  // load info
  me.get();
	  $(document).ready(function() {
		
	});
  
};