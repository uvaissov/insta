app.controller('MainCtrl', MainCtrl);
function MainCtrl () {
  var main = this; 
  var contentPrefixPath = "cabinet/container/";
  main.navigationList = navItems ;
  //init renderPage
  angular.forEach(main.navigationList, function(value) {
	  if(value.active===true){
		  main.pageUrl = contentPrefixPath+value.itemPage
	  }
  });
   
  main.changeContent=function(item){
	  angular.forEach(main.navigationList, function(value) {
		  if(value.active===true){
			  value.active=false;
		  }
		});
	  
	  item.active=true;
	  main.pageUrl = contentPrefixPath+item.itemPage
  }
};