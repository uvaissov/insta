app.controller('MainCtrl', MainCtrl);
function MainCtrl ($cookies) {
  var main = this; 
  var contentPrefixPath = _contextPath+"cabinet/container/";
  main.navigationList = navItems ;
  //init renderPage
  main.selectedPage =null;
  var selectedTab = $cookies.get('selectedTab');
  angular.forEach(main.navigationList, function(value) {
	  if(selectedTab!=null){
		  if( contentPrefixPath+value.itemPage===selectedTab){
			  //main.pageUrl = contentPrefixPath+value.itemPage
			  main.pageUrl = value.itemPage
			  main.selectedPage=value.itemPage;
			  value.active= true;
		  } else {
			  value.active= false;
		  }
	  } else {
		  if(value.active===true){
			  //main.pageUrl = contentPrefixPath+value.itemPage
			  main.pageUrl = value.itemPage
			  main.selectedPage=value.itemPage;
		  }
	  }
  });
  main.changeContent=function(item){
	  angular.forEach(main.navigationList, function(value) {
		  if(value.active===true){
			  value.active=false;
		  }
		});
	  item.active=true;
	  //main.pageUrl = contentPrefixPath+item.itemPage;
	  main.pageUrl = item.itemPage
	  main.selectedPage=item.itemPage;
	  $cookies.put('selectedTab',main.pageUrl);
  }
};