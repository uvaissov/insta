/**
 * Контроллер для управления должностями
 * 
 */
app.controller('Setting_EmployeesCtrl',['$http','$q', Setting_EmployeesCtrl]);
function Setting_EmployeesCtrl ($http,$q) {
  var emp = this; 
  var pointUrl = '/setting/employees';
  emp.type = 'list';
  emp.switchContent=function(type){
	  emp.employ = null;
	  emp.type = type;
	  if('add'===type){
		  if(emp.positions.length==0)
			  console.log('2')
	  }
  };
  emp.message = null;
  emp.employees = [];
  emp.positions=[];
  emp.branches=[];
  
  emp.addEmp = function(employ){
		if(	angular.isDefined(employ) &&
	  		angular.isDefined(employ.firstName) && angular.isDefined(employ.surname) &&
	  		angular.isDefined(employ.phone) && angular.isDefined(employ.position)
	  		){
				if(angular.isDefined(employ.id)){
					$http.put(pointUrl+'/'+employ.id, employ)
					.then(
						       function(response){
						    	   emp.employees.push(response.data);
						    	   emp.type = 'list';
						    	   emp.getEmployees();
						       }, 
						       function(response){
						    	   console.log(response);
						    	   emp.message = response.message;
						       }
						    );
				} else {
					$http.post(pointUrl, employ)
					.then(
					       function(response){
					    	   emp.employees.push(response.data);
					    	   emp.type = 'list';
					       }, 
					       function(response){
					    	   console.log(response);
					    	   emp.message = response.message;
					       }
					    );
				}
	  	}
  };
  
  emp.updateEmp = function(employ){
	  emp.type = 'add';
	  emp.employ = employ;
  };
  
  emp.removeEmp = function(item,index){
  		$http.delete(pointUrl+'/'+item.id, null)
		.then(
			       function(response){
			    	   emp.getEmployees();
			       }, 
			       function(response){
			    	   console.log(response);
			    	   emp.message = response.message;
			       }
			    ); 
  };
  
  emp.getEmployees = function(){
	  $http.get(pointUrl, null)
		.then(
			       function(response){
			    	   emp.employees = response.data;
			       }, 
			       function(response){
			    	   console.log(response);
			    	   emp.message = response.message;
			       }
			    ); 
  };
  
  emp.getPosition = function(){
	  $http.get('/setting/position', null)
		.then(
			       function(response){
			    	   emp.positions =response.data;
			       }, 
			       function(response){
			    	   console.log(response);
			    	   emp.message = response.message;
			       }
			    ); 
	  
  };
  
  emp.getBraches = function(){
	  $http.get('/setting/branch', null)
		.then(
			       function(response){
			    	   emp.branches=response.data;
			       }, 
			       function(response){
			    	   console.log(response);
			    	   emp.message = response.message;
			       }
			    ); 
	  
  };
  
  // load positions
  emp.getEmployees();
  
};