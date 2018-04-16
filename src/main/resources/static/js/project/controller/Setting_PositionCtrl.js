/**
 * Контроллер для управления должностями
 * 
 */
app.controller('Setting_PositionCtrl', Setting_PositionCtrl);

function Setting_PositionCtrl ($http) {
  var pos = this; 
  var pointUrl = '/setting/position';
  pos.type = 'list';
  pos.switchContent=function(type){
	  pos.position = null;
	  pos.type = type;
  }
  pos.message = null;
  pos.positions = [];
  
  pos.addPosition = function(position){
		if(	angular.isDefined(position) &&
	  		angular.isDefined(position.name)){
			if(angular.isDefined(position.id)){
				$http.put(pointUrl+'/'+position.id, position)
				.then(
					       function(response){
					    	   pos.positions.push(response.data);
					    	   pos.type = 'list';
					    	   pos.getPosition();
					       }, 
					       function(response){
					    	   console.log(response);
					    	   pos.message = response.message;
					       }
					    );
			} else {
			$http.post(pointUrl, position)
			.then(
				       function(response){
				    	   pos.positions.push(response.data);
				    	   pos.type = 'list';
				       }, 
				       function(response){
				    	   console.log(response);
				    	   pos.message = response.message;
				       }
				    );
			}
	  	}
  };
  pos.updatePosition = function(position){
	  pos.type = 'add';
	  pos.position = position;
  };
  
  pos.removePos = function(item,index){
  		$http.delete(pointUrl+'/'+item.id, null)
		.then(
			       function(response){
			    	   pos.getPosition();
			       }, 
			       function(response){
			    	   console.log(response);
			    	   pos.message = response.message;
			       }
			    ); 
  };
  
  pos.getPosition = function(){
	  $http.get(pointUrl, null)
		.then(
			       function(response){
			    	   pos.positions = response.data;
			       }, 
			       function(response){
			    	   console.log(response);
			    	   pos.message = response.message;
			       }
			    ); 
  }
  
  // load positions
  pos.getPosition();
  
};